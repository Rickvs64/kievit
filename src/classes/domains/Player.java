package classes.domains;


import javafx.scene.image.Image;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Direction currentDirection;
    private List<Integer> x = new ArrayList<>();
    private List<Integer> y = new ArrayList<>();
    private StatusPlayer status;
    private Image head;
    private Image tail;
    public Player() {
        this.head = new Image("@../../resources/img/head.png");
        this.tail = new Image("@../../resources/img/tail.png");
        this.currentDirection = Direction.DOWN;
        for (int i = 0; i < 12; i++) {
            x.add(50);
            y.add(50);
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }


    public List<Integer> getX() {
        return x;
    }

    public void addX(int x) {
        this.x.add(x);
    }

    public List<Integer> getY() {
        return y;
    }

    public void addY(int y) {
        this.y.add(y);
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
        int[] x = this.x.stream().mapToInt(i->i).toArray();
        int[] y = this.y.stream().mapToInt(i->i).toArray();
        for (int i = x.length-1; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        switch (currentDirection){
            case UP:
                y[0] -= 20;
                break;
            case DOWN:
                y[0] += 20;
                break;
            case LEFT:
                x[0] -= 20;
                break;
            case RIGHT:
                x[0] += 20;
                break;
        }
        this.x.clear();
        this.y.clear();
        for (int i = 0; i < x.length; i++)
        {
            this.x.add(x[i]);
            this.y.add(y[i]);
        }


    }
}
