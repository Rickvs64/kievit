package server;

import classes.domains.IPlayer;
import shared.ILobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerManager extends Remote {
    ILobby update(IPlayer p, int lobbyId) throws RemoteException;
    ILobby addLobby(int id) throws RemoteException;
    ILobby getLobby(int id) throws RemoteException;
    ILobby addCount(int lobbyId) throws RemoteException;
    boolean getStatus(int id) throws RemoteException;

    void setStatus(boolean b,int id) throws RemoteException;
}
