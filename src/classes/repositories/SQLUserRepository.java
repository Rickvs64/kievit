package classes.repositories;

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

}
