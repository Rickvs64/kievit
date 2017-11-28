package model;

import classes.domains.Direction;
import classes.domains.JavaFXPaintable;
import classes.domains.Player;
import javafx.embed.swing.JFXPanel;
import javafx.scene.canvas.Canvas;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

public class JavaFXPaintableUnitTests {
    JavaFXPaintable paintable;
    Player player2;
    Player player1;
    @Before
    public void initialize() throws RemoteException {
        JFXPanel jfxPanel = new JFXPanel();
        paintable = new JavaFXPaintable(new Canvas());
        player1 = new Player(50,50, Direction.UP,1,1);
        player2 = new Player(400,400, Direction.DOWN,2,2);
    }

    @Test
    public void testDrawGrid()  {
        boolean passed = true;
        try{
            paintable.drawGrid();
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);
    }
    @Test
    public void testDrawplayer()  {
        boolean passed = true;
        try{
            player1.move();
            player1.move();
            paintable.drawPlayer(player1,1);
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);
    }
    @Test
    public void testDraw()  {
        boolean passed = true;
        try{
            paintable.draw(player1,player2);
        }
        catch (Exception e)
        {
            passed = false;
        }
        Assert.assertEquals(true,passed);
    }
}
