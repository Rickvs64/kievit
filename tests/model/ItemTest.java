package model;

import classes.domains.Item;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ItemTest {
    private Item item;
    private Item item2;
    @Before
    public void setup()
    {
        item = new Item(3,"head","bob",100);
        item2 = new Item(2,"tail","jan",1000);
        JFXPanel jfxPanel = new JFXPanel();
    }

    @Test
    public void getID()
    {
        Assert.assertEquals(item.getID(),3);
    }

    @Test
    public void getType()
    {
        Assert.assertEquals(item.getType(),"head");
    }

    @Test
    public void getName()
    {
        Assert.assertEquals(item.getName(),"bob");
    }
    @Test
    public void getImage()
    {
        Image image = item.getImage();
        Assert.assertEquals(false,image == null);
    }

    @Test
    public void getPrice()
    {
        Assert.assertEquals(item.getPrice(),100);
    }

}
