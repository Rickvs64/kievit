package classes.repositories;

import java.io.IOException;
import java.sql.SQLException;

public interface IRoomRepository {
    int createRoom(int playerID,String name,String password, int speed) throws SQLException, IOException, ClassNotFoundException;
}
