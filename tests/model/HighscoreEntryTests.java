package model;

import classes.domains.HighscoreEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HighscoreEntryTests {
    HighscoreEntry Entry;
    @Before
    public void testConstructor(){
        try{
            Entry = new HighscoreEntry(1,"Berend", 1337);
        }
        catch (Exception e){
            Assert.fail();
        }
        Assert.assertTrue(Entry != null);
    }

    @Test
    public void testGetid(){
        Assert.assertEquals(1, Entry.getUserid());
    }
    @Test
    public void testGetUsername(){
        Assert.assertEquals("Berend", Entry.getUsername());
    }
    @Test
    public void testGetScore(){
        Assert.assertEquals(1337, Entry.getScore());
    }
}
