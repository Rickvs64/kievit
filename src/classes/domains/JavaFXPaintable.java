package classes.domains;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JavaFXPaintable {
    private final GraphicsContext gc;
    private Rectangle r;
    public JavaFXPaintable(Canvas canvas) {
        gc = canvas.getGraphicsContext2D();


        r = new Rectangle();
        r.setX(50);
        r.setY(50);
        r.setWidth(200);
        r.setHeight(100);
    }
    public void draw(Player player1,Player player2)
    {
        gc.clearRect(0, 0, 1000, 1000);
        drawGrid();
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
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);

        //Boven / onder
        gc.strokeLine(10, 10, 970, 10);
        gc.strokeLine(10, 760, 970, 760);

        //Links / rechts
        gc.strokeLine(10, 10, 10, 760);
        gc.strokeLine(970, 10, 970, 760);
    }
}
