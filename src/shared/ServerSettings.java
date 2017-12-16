package shared;

import classes.repositories.ConnectionManager;
import classes.repositories.IConnection;
import server.IServerManager;

import java.io.IOException;
import java.sql.*;

public class ServerSettings implements IServerSettings {
    @Override
    public String getIp() throws SQLException, IOException, ClassNotFoundException {
        //String query = "SELECT * FROM ServerSettings WHERE id = ?";
        //String ip = null;
        //IConnection connection = new ConnectionManager();
        //Connection conn = connection.getConnection();
        //PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        //preparedStmt.setInt(1,1);
        //ResultSet rs = preparedStmt.executeQuery();
        //if (rs.next()) {
        //    ip = rs.getString("ip");
        //}
        //conn.close();
        //return ip;
        return "127.0.0.1";
    }

    @Override
    public int getPort() throws SQLException, IOException, ClassNotFoundException {
        //String query = "SELECT * FROM ServerSettings WHERE id = ?";
        //int port = 0;
        //IConnection connection = new ConnectionManager();
        //Connection conn = connection.getConnection();
        //PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        //preparedStmt.setInt(1,1);
        //ResultSet rs = preparedStmt.executeQuery();
        //if (rs.next()) {
        //    port = rs.getInt("port");
        //}
        //conn.close();
        //return port;
        return 1099;
    }
}
