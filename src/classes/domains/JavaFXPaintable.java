package classes.domains;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class JavaFXPaintable {
    private final GraphicsContext gc;

    public JavaFXPaintable(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();
    }
    public void draw(Player player1,Player player2)
    {
        gc.clearRect(0, 0, 1000, 1000);
        drawPlayer(player1);
        drawPlayer(player2);
    }
    public void drawPlayer(Player player)
    {

        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        for (Coordinate c:player.getCoordinates()
             ) {
            x.add(c.getX());
            y.add(c.getY());

        }
        Image head = player.getHead();
        Image tail = player.getTail();
        gc.drawImage(head,x.get(0),y.get(0),20,20);
        for (int i = 0;  i < x.size()-1; i++)
        {
            gc.drawImage(tail,x.get(i+1),y.get(i+1),20,20);
        }
    }

    public void drawGrid()
    {
        //TODO
    }
}
