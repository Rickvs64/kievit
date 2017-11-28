package server;

import classes.domains.Direction;
import classes.domains.IPlayer;
import shared.ILobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerManager extends Remote {
    ILobby updatePlayer(IPlayer p, int lobbyId) throws RemoteException;
    ILobby addLobby(int id) throws RemoteException;
    ILobby getLobby(int id) throws RemoteException;
    ILobby addCount(int lobbyId) throws RemoteException;
    boolean getStatus(int id) throws RemoteException;
    void setStatus(boolean b,int id) throws RemoteException;
    void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException;
    Direction getDirection(int userID, int id) throws  RemoteException;;
}
