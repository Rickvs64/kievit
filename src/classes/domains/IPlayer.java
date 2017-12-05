package classes.domains;

import javafx.scene.image.Image;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IPlayer extends Remote {
    int getUserID() throws RemoteException;
    Direction getCurrentDirection() throws RemoteException;
    void setCurrentDirection(Direction currentDirection) throws RemoteException;
    List<Coordinate> getCoordinates() throws RemoteException;
    void move() throws RemoteException;
    boolean intersects(IPlayer other) throws RemoteException;
    boolean hitsGrid() throws RemoteException;
    void update(IPlayer player) throws RemoteException;
}
