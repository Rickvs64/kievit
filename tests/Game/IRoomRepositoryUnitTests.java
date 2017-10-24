package Game;
import classes.domains.JavaFXPaintable;
import classes.domains.User;
import classes.repositories.IRoomRepository;
import classes.repositories.RoomRepository;
import gui.game.GameController;
import javafx.scene.canvas.Canvas;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class IRoomRepositoryUnitTests {

    @Test
    public void testConstructor() throws SQLException, IOException, ClassNotFoundException {

        IRoomRepository roomRepository = new RoomRepository();
        List<User> users = roomRepository.getLobby(30);
        org.junit.Assert.assertEquals(1,1);
    }
}