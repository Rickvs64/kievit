package server;

import classes.domains.Player;
import shared.ILobby;
import shared.Lobby;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ServerManager extends UnicastRemoteObject implements IServerManager {
    List<ILobby> lobbyList = new ArrayList<>();


    public ServerManager() throws RemoteException {
    }

    public void addLobby(int id)
    {
        lobbyList.add(new Lobby(id));
    }
    public ILobby update(Player p,int lobbyId)
    {
        for (ILobby l:lobbyList
             ) {
            if (l.getId() == lobbyId)
            {
                l.update(p);
                return  l;
            }
        }
        return null;
    }
}
