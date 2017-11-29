package server;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.User;
import shared.ILobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerManager extends Remote {
    ILobby updatePlayer(IPlayer p, int lobbyId) throws RemoteException;
    ILobby addLobby(int id) throws RemoteException;
    ILobby getLobby(int id) throws RemoteException;
    ILobby joinLobby(int lobbyId,User user) throws RemoteException;
    boolean getStatus(int id) throws RemoteException;
    void setStatus(boolean b,int id) throws RemoteException;
    void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException;
    Direction getDirection(int userID, int id) throws  RemoteException;
    List<User> getUsers(int id)throws  RemoteException;
}
