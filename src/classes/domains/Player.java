package classes.domains;


import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private Direction currentDirection;
    private List<Coordinate> coordinates;
    private StatusPlayer status;
    private Image head;
    private Image tail;

    public Player(int startX, int startY, Direction direction) {
        this.head = new Image("@../../resources/img/head.png");
        this.tail = new Image("@../../resources/img/tail.png");
        this.currentDirection = direction;
        this.coordinates = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            coordinates.add(new Coordinate(startX,startY));
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }


    public StatusPlayer getStatus() {
        return status;
    }

    public void setStatus(StatusPlayer status) {
        this.status = status;
    }

    public Image getHead() {
        return head;
    }

    public Image getTail() {
        return tail;
    }

    public void move() {
        Coordinate[] cords = new Coordinate[coordinates.size()];
        coordinates.toArray(cords);
        for (int i = cords.length - 1; i > 0; i--) {
            cords[i].setX(cords[i-1].getX());
            cords[i].setY(cords[i-1].getY());
        }
        switch (currentDirection) {
            case UP:
                cords[0].mathY(-20);
                break;
            case DOWN:
                cords[0].mathY(20);
                break;
            case LEFT:
                cords[0].mathX(-20);
                break;
            case RIGHT:
                cords[0].mathX(20);
                break;
        }
        this.coordinates.clear();
        Collections.addAll(this.coordinates, cords);
    }

    public boolean intersects(Player other) {
        Coordinate head = coordinates.get(0);
        for (Coordinate c:other.coordinates
             ) {
            if (c.getY() == head.getY() && c.getX() == head.getX())
            {
                System.out.println("NOT SURE IF IT WORKS CORRECTLY //Alex");
                return true;
            }
        }
        return false;
    }

}
