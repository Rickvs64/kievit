package model;

import classes.domains.Direction;
import classes.domains.Player;
import classes.domains.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class UserTest {

    User userOne;
    User userTwo;

    @Before
    public void initialize() {
        userOne = new User("lars", 12);
        userTwo = new User("lars", "password");
    }

    @Test
    public void getUserName() {

        Assert.assertEquals("lars", userOne.getUsername());
    }

    @Test
    public void getPassword() {

        Assert.assertEquals("password", userTwo.getPassword());
    }

    @Test
    public void getCredits() {

        Assert.assertEquals("12", userOne.getCredits().toString());
    }

    @Test
    public void setUserName() {

        userOne.setUsername("alex");
        Assert.assertEquals("alex", userOne.getUsername());
    }

    @Test
    public void setPassword() {
        userOne.setPassword("newPassword");
        Assert.assertEquals("newPassword", userOne.getPassword());
    }

    @Test
    public void setCredits() {
        userOne.setCredits(15);
        Assert.assertEquals("15", userOne.getCredits().toString());
    }
}