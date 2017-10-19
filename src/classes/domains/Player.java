package classes.domains;


import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    //user
    private int userID;
    //movement
    private Direction currentDirection;
    private List<Coordinate> coordinates;
    private StatusPlayer status;
    //cosmetics
    private Image head;
    private Image tail;

    public Player(int startX, int startY, Direction direction, int playerNumber, int userID) {
        //cosmetic based on playernumber //TODO : Cosmetic from shops
        this.userID = userID;
        switch (playerNumber) {
            case 1:
                this.head = new Image("@../../resources/img/bird0.gif");
                this.tail = new Image("@../../resources/img/poop0.gif");
                break;
            case 2:
                this.head = new Image("@../../resources/img/bird1.gif");
                this.tail = new Image("@../../resources/img/poop1.gif");
                break;
            default:
                this.head = new Image("@../../resources/img/bird2.gif");
                this.tail = new Image("@../../resources/img/poop2.gif");
                break;
        }

        this.currentDirection = direction;
        this.coordinates = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
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

    //Moving the coordinates points  //TODO : move to better location (GameController etc.)
    public void move() {
        Coordinate temp = coordinates.get(0);
        switch (currentDirection) {
            case UP:
                coordinates.add(0,new Coordinate(temp.getX(),temp.getY() - 20));
                break;
            case DOWN:
                coordinates.add(0,new Coordinate(temp.getX(),temp.getY() + 20));
                break;
            case LEFT:
                coordinates.add(0,new Coordinate(temp.getX() -20,temp.getY()));
                break;
            case RIGHT:
                coordinates.add(0,new Coordinate(temp.getX() +20 ,temp.getY()));
                break;
        }
    }
    //intersection with playerself or enemy //TODO intersection with grid and unittesting and possible enum for intersection
    public boolean intersects(Player other) {
        Coordinate head = coordinates.get(0);
        for (Coordinate c:other.coordinates
             ) {
            if (c.getY() == head.getY() && c.getX() == head.getX())
            {
                System.out.println("NOT SURE IF IT WORKS CORRECTLY //Alex // Enemy");
                return true;
            }
        }
        List<Coordinate> temp = coordinates.stream().collect(Collectors.toList());
        temp.remove(0);
        for (Coordinate c2: temp) {
            if (c2.getY() == head.getY() && c2.getX() == head.getX())
            {
                System.out.println("NOT SURE IF IT WORKS CORRECTLY //Alex // Suicide");
                return true;
            }
        }
        return false;
    }

}
