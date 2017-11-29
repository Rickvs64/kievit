package shared;

import java.io.IOException;
import java.sql.SQLException;

public interface IServerSettings {
    String getIp() throws SQLException, IOException, ClassNotFoundException;
    int getPort() throws SQLException, IOException, ClassNotFoundException;
}
