package shared;

import classes.domains.Direction;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IListener extends Remote{
    void setDirectionOtherPlayer(Direction direction) throws RemoteException;
    int getLobbyID() throws RemoteException;
    int getUserID() throws RemoteException;
}
