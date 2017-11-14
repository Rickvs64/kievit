package model;

import classes.domains.Coordinate;
import classes.domains.Direction;
import classes.domains.Player;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PlayerTest {

    @Before
    public void initialize() {
        //Make panel otherwise we receive a bug.

        JFXPanel jfxPanel = new JFXPanel();
    }

    @Test
    public void testImagePlayerOne() {

        Player player = new Player(1, 1, Direction.UP, 1, 1);

        Assert.assertNotNull(player.getHead());
        Assert.assertNotNull(player.getTail());
    }

    @Test
    public void testImagePlayerTwo() {

        Player player = new Player(1, 1, Direction.DOWN, 2, 1);

        Assert.assertNotNull(player.getHead());
        Assert.assertNotNull(player.getTail());
    }

    @Test
    public void testImagePlayersDefault() {

        Player player = new Player(1, 1, Direction.DOWN, 3, 1);

        Assert.assertNotNull(player.getHead());
        Assert.assertNotNull(player.getTail());
    }

    @Test
    public void getDirection() {

        Player player = new Player(1, 1, Direction.DOWN, 3, 1);

        Assert.assertEquals(player.getCurrentDirection(), Direction.DOWN);
    }

    @Test
    public void setCurrentDirection()
    {
        Player player = new Player(1, 1, Direction.DOWN, 3, 1);
        player.setCurrentDirection(Direction.UP);

        Assert.assertEquals(player.getCurrentDirection(), Direction.UP);
    }

   @Test
    public void moveUp()
    {
       Player player = new Player(1, 1, Direction.UP, 3, 1);

       player.move();

       Assert.assertEquals(player.getCoordinates().size(), 2);
    }

    @Test
    public void moveDown()
    {
        Player player = new Player(1, 1, Direction.DOWN, 3, 1);

        player.move();

        Assert.assertEquals(player.getCoordinates().size(), 2);
    }

    @Test
    public void moveLeft()
    {
        Player player = new Player(1, 1, Direction.LEFT, 3, 1);

        player.move();

        Assert.assertEquals(player.getCoordinates().size(), 2);
    }

    @Test
    public void moveRight()
    {
        Player player = new Player(1, 1, Direction.RIGHT, 3, 1);

        player.move();

        Assert.assertEquals(player.getCoordinates().size(), 2);
    }

    @Test
    public void doesNotHitGrid()
    {
        Player player = new Player(1, 1, Direction.RIGHT, 3, 1);

        Assert.assertEquals(player.hitsGrid(), false);
    }

    @Test
    public void hitsGrid()
    {
        Player player = new Player(11, 9, Direction.RIGHT, 3, 1);

        Assert.assertEquals(player.hitsGrid(), true);
    }

    @Test
    public void playerIntersectsWithOtherPlayer()
    {
        Player player = new Player(11, 9, Direction.LEFT, 3, 1);
        Player player2 = new Player(11, 9, Direction.LEFT, 3, 1);

        Assert.assertEquals(player.intersects(player2), true);
    }

    @Test
    public void playerIntersectsHisself()
    {
        Player player2 = new Player(99999, 9999, Direction.LEFT, 3, 1);
       Player player = new Player(20,20,Direction.RIGHT,1,1);
       player.move();
       player.setCurrentDirection(Direction.DOWN);
       player.move();
       player.setCurrentDirection(Direction.LEFT);
       player.move();
       player.setCurrentDirection(Direction.UP);
       player.move();

        Assert.assertEquals(player.intersects(player2), true);
    }

    @Test
    public void playerDoesNotIntersect()
    {
        Player player = new Player(11, 9, Direction.LEFT, 3, 1);
        Player player2 = new Player(100, 100, Direction.LEFT, 3, 1);

        Assert.assertEquals(player.intersects(player2), false);
    }
}