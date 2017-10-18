package classes.domains;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.List;

public class JavaFXPaintable {
    private final GraphicsContext gc;

    public JavaFXPaintable(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
    }

    public void drawPlayer(Player player)
    {
        gc.clearRect(0, 0, 500, 500);
        List<Integer> x = player.getX();
        List<Integer> y = player.getY();
        Image head = player.getHead();
        Image tail = player.getTail();
        gc.drawImage(head,x.get(0),y.get(0),20,20);
        for (int i = 0;  i < x.size()-1; i++)
        {
            gc.drawImage(tail,x.get(i+1),y.get(i+1),20,20);
        }
    }
}
