package RoomTests;

import classes.domains.Room;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RoomUnitTests {
    Room room;
    @Before
    public void testConstructor()  {
        boolean passed = true;
        try{
            room = new Room(0,"bob","Jan",0,1);
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
    @Test
    public void testID()  {
        room.setID(2);
        Assert.assertEquals(2,room.getID());

    }
    @Test
    public void testName()  {
        room.setName("Karel");
        Assert.assertEquals("Karel",room.getName());
    }
    @Test
    public void testPass()  {
        room.setPassword("1234");
        Assert.assertEquals("1234",room.getPassword());

    }
    @Test
    public void testStarted()  {
        room.setStarted(true);
        Assert.assertEquals(true,room.getStarted());

    }

}
