package server;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.Item;
import classes.domains.User;
import com.sun.org.apache.regexp.internal.RE;
import shared.ILobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerManager extends Remote {
    User login(String username, String password) throws RemoteException;
    ILobby updatePlayer(IPlayer p, int lobbyId) throws RemoteException;
    ILobby addLobby(int id,String user,String name,String password) throws RemoteException;
    ILobby getLobby(int id) throws RemoteException;
    ILobby joinLobby(int lobbyId,User user) throws RemoteException;
    boolean getStatus(int id) throws RemoteException;
    void setStatus(boolean b,int id) throws RemoteException;
    void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException;
    Direction getDirection(int userID, int id) throws  RemoteException;
    List<User> getUsers(int id)throws  RemoteException;
    List<ILobby> getAvailibleLobbys() throws RemoteException;
    void setCosmetics(int playerNumber,int lobbyID, int headID, int tailID) throws RemoteException;
    IPlayer getPlayer(int lobbyID, int playerNumber) throws RemoteException;
    void buyItem(int userID, int itemID) throws RemoteException;
    List<Item> getItems(int id) throws RemoteException;
    List<Item> getOwnedItems(int id, String type) throws RemoteException;
    int getNewLobbyID() throws RemoteException;
    boolean checkUsernameExists(String username) throws RemoteException;
    boolean createUser(User user) throws RemoteException;
}
