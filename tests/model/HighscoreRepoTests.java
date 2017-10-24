package model;

import classes.repositories.HighscoreRepository;
import classes.repositories.IHighscoreRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class HighscoreRepoTests {
    IHighscoreRepository repo;
    @Before
    public void testConstructor()  {
        boolean passed = true;
        try{
            repo = new HighscoreRepository();
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
    @Test
    public void testGetHighscores() throws SQLException, IOException, ClassNotFoundException {
        boolean passed = true;
        try{
            if (repo.getHighscores().isEmpty())
            {
                passed = false;
            }
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);

    }
}
