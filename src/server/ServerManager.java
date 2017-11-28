package server;

import classes.domains.IPlayer;
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

        System.out.println("Lobby created");
        lobbyList.add(new Lobby(id));
    }

    @Override
    public ILobby getLobby(int id) {
        System.out.println("player joined Yay!!!");
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                    return  l;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void start(int id) {
        System.out.println("started lobby " + id);
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                    l.setStatus(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public ILobby update(IPlayer p,int lobbyId)
    {
        for (ILobby l:lobbyList
             ) {
            try {
                if (l.getId() == lobbyId)
                {
                    l.update(p);
                    return  l;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
