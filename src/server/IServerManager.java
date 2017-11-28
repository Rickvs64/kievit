package server;

import classes.domains.IPlayer;
import shared.ILobby;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerManager extends Remote {
    ILobby update(IPlayer p, int lobbyId) throws RemoteException;
    void addLobby(int id) throws RemoteException;
    ILobby getLobby(int id) throws RemoteException;
    void start(int id) throws RemoteException;
}
