package classes.repositories;

import classes.domains.CryptWithMD5;
import classes.domains.Item;
import classes.domains.User;

import javax.jws.soap.SOAPBinding;
import javax.swing.plaf.nimbus.State;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class SQLUserRepository implements IUserRepository {
    private Connection connection;

    public SQLUserRepository() throws IOException, ClassNotFoundException, SQLException {
        Properties prop = new Properties();
        InputStream in = new FileInputStream("config.properties");
        prop.load(in);
        in.close();

        String driver = prop.getProperty("jdbc.driver");
        String connectionURL = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");

        Class.forName(driver);

        connection = DriverManager.getConnection(connectionURL, username, password);
    }

    @Override
    public User getDummyUser() {
        return new User(1,"John", 550);
    }

    @Override
    public Boolean checkUserExists(User user){
        try {
            String encrypted = CryptWithMD5.cryptWithMD5(user.getPassword());
            user.setPassword(encrypted);

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM player WHERE username = '" + user.getUsername() + "' AND password='" + user.getPassword() + "';");

            if (!result.next()) {   // If next() returns false there are no matches
                return false;
            }
            else {
                return true;
            }

        }
        catch (Exception ex) {
            // Fuck it.
            System.out.println("Something broke, try again later.");
            return false;
        }
    }



    @Override
    public Boolean createUser(User user) {
        try {
            String encrypted = CryptWithMD5.cryptWithMD5(user.getPassword());
            user.setPassword(encrypted);

            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO player (username, password) VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "')");
            Statement highscorecreate = connection.createStatement();
            highscorecreate.execute("INSERT  into Highscore (player_id, score) VALUES ((SELECT ID FROM player where username ='" + user.getUsername()+"'),0)");
            return true;
        }
        catch (Exception ex) {
            // Fuck it.
            System.out.println(ex.toString());
            System.out.println("Something broke, try again later.");
            return false;
        }
    }

    @Override
    public Boolean checkUsernameExists(String username) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT username FROM player WHERE username = '" + username + "';");

            if (!result.next()) {   // If next() returns false there are no matches
                return false;
            }
            else {
                return true;
            }

        }
        catch (Exception ex) {
            // Fuck it.
            System.out.println("Something broke, try again later.");
            return false;
        }
    }

    @Override
    public User login(String username, String password) throws SQLException, IOException, ClassNotFoundException {
        String query = "SELECT * FROM player WHERE username = ? AND password= ?;";
        User user = null;
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1,username.toLowerCase());
        preparedStmt.setString(2,CryptWithMD5.cryptWithMD5(password));
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
           user = new User(rs.getInt("id"),rs.getString("username"),rs.getInt("credits"));
        }
        conn.close();
        return user;
    }

    @Override
    public void updateCurrency(int userid, int amount) throws SQLException, IOException, ClassNotFoundException {
        String changeCurrency = "UPDATE player p SET credits = credits + ? WHERE p.ID = ?";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt2 = conn.prepareStatement(changeCurrency);
        preparedStmt2.setInt (1, amount);
        preparedStmt2.setInt (2, userid);
        preparedStmt2.execute();
        conn.close();
    }

    @Override
    public User getUser(int id) {
        String query = "SELECT * FROM player WHERE id = ?;";
        User user = null;
        try {
            IConnection connection = new ConnectionManager();
            Connection conn = connection.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt(1,id);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"),rs.getString("username"),rs.getInt("credits"));
            }
            conn.close();
        }
        catch (SQLException| IOException| ClassNotFoundException e)
        {

        }
        return user;
    }
}
