package classes.repositories;

import classes.domains.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IRoomRepository {
    int createRoom(int playerID,String name,String password, int speed) throws SQLException, IOException, ClassNotFoundException;
    List<User> getLobby(int roomID) throws SQLException, IOException, ClassNotFoundException;
}
