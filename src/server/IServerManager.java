package server;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.User;
import com.sun.org.apache.regexp.internal.RE;
import shared.ILobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerManager extends Remote {
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
}
