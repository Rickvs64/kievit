package classes.domains;


import javafx.scene.image.Image;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player extends UnicastRemoteObject implements IPlayer,Serializable{
    //user
    private int userID;
    //movement
    private Direction currentDirection;
    private List<Coordinate> coordinates;
    private int headID;
    private int tailID;

    public Player(int startX, int startY, Direction direction, int playerNumber, int userID) throws RemoteException {
        this.userID = userID;
        this.currentDirection = direction;
        this.coordinates = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            coordinates.add(new Coordinate(startX,startY));
        }
    }

    public int getUserID() {
        return userID;
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

    public boolean intersects(IPlayer other, boolean checkSelf) throws RemoteException {
        if (coordinates.size() <= 3 && other.getCoordinates().size() <= 1)
            return false;

        Coordinate head = coordinates.get(0);
        Coordinate previous = coordinates.get(1);

        Line2D line = new Line2D.Float(head.getX(), head.getY(), previous.getX(), previous.getY());

        Coordinate previousOther = other.getCoordinates().get(0);
        for (Coordinate c : other.getCoordinates()) {
            Line2D LineOther = new Line2D.Float(c.getX(), c.getY(), previousOther.getX(), previousOther.getY());
            if (line.intersectsLine(LineOther))
                return true;

            previousOther = c;
        }

        if (!checkSelf)
            return false;

        List<Coordinate> coordinates1 = getCoordinates();
        for (int i = 0, coordinates1Size = coordinates1.size(); i < coordinates1Size; i++) {
            Coordinate c = coordinates1.get(i);
            if (i <= 2) {
                previous = c;
                continue;
            }

            Line2D LineOther = new Line2D.Float(c.getX(), c.getY(), previous.getX(), previous.getY());
            if (line.intersectsLine(LineOther))
                return true;
            
            previous = c;
        }

        return false;
    }

    public boolean intersects(IPlayer other) throws RemoteException {
        return intersects(other, true);
    }

    public boolean hitsGrid()
    {
        Coordinate head = coordinates.get(0);

        return (head.getX() >= 10 & head.getX() <= 970) & (head.getY() <= 18) ||
                //Check bottom
                (head.getX() >= 10 & head.getX() <= 970) & (head.getY() >= 743) ||
                //Check left
                (head.getY() >= 10 & head.getY() <= 760) & (head.getX() <= 10) ||
                //Check right
                (head.getY() >= 10 & head.getY() <= 760) & (head.getX() >= 970);

    }
    public void update(IPlayer player) throws RemoteException {
        this.userID = player.getUserID();
        this.currentDirection = player.getCurrentDirection();
        this.coordinates = player.getCoordinates();
    }

    @Override
    public int getHeadID() throws RemoteException {
        return headID;
    }

    @Override
    public int getTailID() throws RemoteException {
        return tailID;
    }
    @Override
    public void setHeadID(int headID) {
        this.headID = headID;
    }
    @Override
    public void setTailID(int tailID) {
        this.tailID = tailID;
    }
}