package server;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.Player;
import classes.domains.User;
import javafx.embed.swing.JFXPanel;
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
    JFXPanel jfxPanel = new JFXPanel();

    public ServerManager() throws RemoteException {
    }


    public ILobby addLobby(int id) throws RemoteException {


        ILobby lobby = null;
        try {
            lobby = new Lobby(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Lobby created id: " + lobby.getId());
        lobbyList.add(lobby);
        return lobby;
    }

    @Override
    public synchronized ILobby getLobby(int id) {
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


    public synchronized ILobby updatePlayer(IPlayer p,int lobbyId)
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
    public ILobby joinLobby(int lobbyId,User user)
    {
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == lobbyId)
                {
                    l.addUser(user);
                    l.addCount();
                    return l;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean getStatus(int id) {
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                    if (l.getStatus() == true)
                    {
                        return true;
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void setStatus(boolean b,int id) {
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                   l.setStatus(b);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException {
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                    l.updateDirection(userID,currentDirection);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Direction getDirection(int userID, int id) {
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                    return l.getDirection(userID);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers(int id) {
        for (ILobby l:lobbyList
                ) {
            try {
                if (l.getId() == id)
                {
                    return l.getUsers();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
