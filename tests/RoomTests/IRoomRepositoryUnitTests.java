package RoomTests;
import classes.domains.User;
import classes.repositories.IRoomRepository;
import classes.repositories.RoomRepository;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IRoomRepositoryUnitTests {

    @Test
    public void testConstructor() throws SQLException, IOException, ClassNotFoundException {
        boolean passed = true;
        try{
            IRoomRepository roomRepository = new RoomRepository();
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
    @Test
    public void testCreateRoom() throws SQLException, IOException, ClassNotFoundException {
        boolean passed = true;
        try{
            IRoomRepository roomRepository = new RoomRepository();
            roomRepository.createRoom(1,"bob","jan",50);

        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
    @Test
    public void testgetLobby() throws SQLException, IOException, ClassNotFoundException {
        boolean passed = true;
        try{
            IRoomRepository roomRepository = new RoomRepository();
            List<User> users = roomRepository.getLobby(30);
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
}