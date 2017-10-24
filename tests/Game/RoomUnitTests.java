package Game;

import classes.domains.Room;
import org.junit.Assert;
import org.junit.Test;

public class RoomUnitTests {
    @Test
    public void testConstructor()  {
        boolean passed = true;
        try{
            Room room = new Room(0,"bob","Jan",false);
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
    @Test
    public void testID()  {
        Room room = new Room(0,"bob","Jan",false);
        room.setID(2);
        int ID = room.getID();

        Assert.assertEquals(2,ID);

    }
    @Test
    public void testName()  {
        Room room = new Room(0,"bob","Jan",false);
        room.setName("Karel");
        String name = room.getName();

        Assert.assertEquals("Karel",name);
    }
    @Test
    public void testPass()  {
        Room room = new Room(0,"bob","Jan",false);
        room.setPassword("1234");
        String pass = room.getPassword();

        Assert.assertEquals("1234",pass);

    }
    @Test
    public void testStarted()  {
        Room room = new Room(0,"bob","Jan",false);
        room.setStarted(true);


        Assert.assertEquals(true,room.getStarted());

    }

}
