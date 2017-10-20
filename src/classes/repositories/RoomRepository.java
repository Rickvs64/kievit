package classes.repositories;

import classes.domains.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoomRepository {
    @Override
    public int createRoom(int playerID, String name, String password, int speed) throws SQLException, IOException, ClassNotFoundException {
        String queryCreateRoom = " insert into room (name, speed, password)"
                + " values (?, ?, ?)";
        String queryJoinRoom = " insert into player_room (player_id,room_id)"
                + " values (?, ?)";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(queryCreateRoom, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString (1, name);
        preparedStmt.setInt (2, speed);
        preparedStmt.setString   (3, password);
        preparedStmt.executeUpdate();
        ResultSet rs = preparedStmt.getGeneratedKeys();
        int generatedKey = 0;
        if (rs.next()) {
            generatedKey = rs.getInt(1);
        }
        System.out.println(generatedKey);
        PreparedStatement preparedStmt2 = conn.prepareStatement(queryJoinRoom);
        preparedStmt2.setInt (1, playerID);
        preparedStmt2.setInt (2, generatedKey);
        preparedStmt2.execute();
        conn.close();
        return generatedKey;
    }
    public List<User> getLobby(int roomID) throws SQLException, IOException, ClassNotFoundException {
        List<User> users = new ArrayList<>();
        String queryCreateRoom = "select username,credits from player p\n" +
                "join player_room r on r.player_id=p.ID\n" +
                "where r.room_id = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(queryCreateRoom, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1, roomID);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            users.add(new User(rs.getString("username"),rs.getInt("credits")));
        }
        conn.close();
        return users;
    }


}
