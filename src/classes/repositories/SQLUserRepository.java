package classes.repositories;

import classes.domains.CryptWithMD5;
import classes.domains.User;

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
        return new User("John", 550);
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
    public Boolean createUser(User user) {
        try {
            String encrypted = CryptWithMD5.cryptWithMD5(user.getPassword());
            user.setPassword(encrypted);

            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO player (username, password) VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "')");
            return true;
        }
        catch (Exception ex) {
            // Fuck it.
            System.out.println("Something broke, try again later.");
            return false;
        }
    }

    @Override
    public Integer getCredits(String username) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT credits FROM player WHERE username = '" + username + "';");

            if (!result.next()) {   // If next() returns false there are no matches
                System.out.println("Couldn't find matching player's credits. Dafuq?");
                return null;
            }
            else {
                return (Integer)result.getObject("credits");
            }
        }
        catch (Exception ex) {
            // Fuck it.
            System.out.println("Something broke, try again later.");
            return null;
        }
    }
}
