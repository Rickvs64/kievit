package classes.repositories;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager implements IConnection {
    private Connection connection;

    public ConnectionManager() throws ClassNotFoundException, SQLException, IOException {
        Properties prop = new Properties();
        InputStream in = new FileInputStream("config.properties");
        prop.load(in);
        in.close();

        String driver = prop.getProperty("jdbc.driver");
        String connectionURL = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");

        Class.forName(driver);

        this.connection = DriverManager.getConnection(connectionURL, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
