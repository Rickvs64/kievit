package shared;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ILobby extends Remote{
    int getId() throws RemoteException;
    boolean getStatus() throws RemoteException;
    IPlayer getPlayer(int id) throws RemoteException;
    void update(IPlayer player) throws RemoteException;
    void setStatus(boolean status) throws RemoteException;
    int getCount() throws RemoteException;
    void addCount() throws RemoteException;
    void updateDirection(int userID, Direction currentDirection) throws  RemoteException;
    Direction getDirection(int userID) throws  RemoteException;
    void addUser(User user)throws  RemoteException;
    List<User> getUsers() throws  RemoteException;
    String getName() throws RemoteException;
    String getPassword() throws RemoteException;
    String getUser() throws RemoteException;
    String getPlayername() throws  RemoteException;
    void setCosmetics(int playerNumber, int headID, int tailID);

}
