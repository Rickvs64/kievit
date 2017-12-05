package repo;

import classes.domains.User;
import classes.repositories.IUserRepository;
import classes.repositories.SQLUserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * This unit test validates various DATABASE RELATED methods and functionality.
 * As if unit tests weren't dumb enough already.
 */
@FixMethodOrder(MethodSorters.JVM)
public class SQLUserRepositoryTest {
    IUserRepository userRepo;
    Random random = new Random();
    Integer randomizer = random.nextInt(999999 - 111111 + 1) + 111111; // 111111 to 999999

    String randomUsername = "unitTest_" + randomizer.toString();
    User randomUser = new User(randomUsername, "password123");

    @Before
    public void initialize() throws SQLException, IOException, ClassNotFoundException {
        userRepo = new SQLUserRepository();
        randomUser.setCredits(550);
    }

    @Test
    public void getDummyUser() throws SQLException, IOException, ClassNotFoundException {
        User dummyUser = userRepo.getDummyUser();
        Assert.assertEquals(dummyUser.getUsername(), "John");
    }

    @Test
    public void checkUserExists() throws SQLException, IOException, ClassNotFoundException {
        Assert.assertFalse(userRepo.checkUserExists(randomUser));
    }

    @Test
    public void checkUsernameExists() {
        Assert.assertFalse(userRepo.checkUsernameExists(randomUser.getUsername()));
    }

    @Test
    public void createUser() {
        userRepo.createUser(randomUser);
        randomUser.setPassword("password123");
        Assert.assertTrue(userRepo.checkUserExists(randomUser));
    }
    @Test
    public void login()
    {
        User user = null;
        try {
            user = userRepo.login("a","a");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(false, user == null);
    }
    @Test
    public void getCredits() {
       Assert.assertEquals((Integer)550, randomUser.getCredits());
    }

}