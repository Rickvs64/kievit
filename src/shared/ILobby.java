package shared;

import classes.domains.IPlayer;

import java.rmi.RemoteException;

public interface ILobby {
    int getId() throws RemoteException;
    boolean getStatus() throws RemoteException;
    IPlayer getPlayer(int id) throws RemoteException;
    void update(IPlayer player) throws RemoteException;
    void setStatus(boolean status) throws RemoteException;
    int getCount() throws RemoteException;
    void addCount() throws RemoteException;
}
