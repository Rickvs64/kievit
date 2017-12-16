package server;

import classes.domains.*;
import classes.repositories.IShopRepository;
import classes.repositories.IUserRepository;
import classes.repositories.SQLUserRepository;
import classes.repositories.ShopRepository;
import javafx.embed.swing.JFXPanel;
import shared.ILobby;
import shared.Lobby;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ServerManager extends UnicastRemoteObject implements IServerManager {
    List<ILobby> lobbyList = new ArrayList<>();
    JFXPanel jfxPanel = new JFXPanel();
    private int nextLobbyID = 1;
    private IUserRepository userRepo = new SQLUserRepository();
    private IShopRepository shopRepo = new ShopRepository();
    public ServerManager() throws IOException, SQLException, ClassNotFoundException {
    }


    public ILobby addLobby(int id,String user,String name,String password) throws RemoteException {
        ILobby lobby = null;
        try {
            lobby = new Lobby(id,user,name,password);
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


    @Override
    public User login(String username, String password) throws RemoteException {
        try {
            return userRepo.login(username.toLowerCase(), password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized ILobby updatePlayer(IPlayer p, int lobbyId)
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

    @Override
    public List<ILobby> getAvailibleLobbys() throws RemoteException {
        List<ILobby> availibleLobbys = new ArrayList<>();
        for (ILobby l:lobbyList) {
            if (l.getCount() < 2)
            {
                availibleLobbys.add(l);
            }
        }
        return availibleLobbys;
    }

    @Override
    public void setCosmetics(int playerNumber, int lobbyID, int headID, int tailID) throws RemoteException {
        for (ILobby l:lobbyList) {
            if (l.getId() == lobbyID)
            {
                l.setCosmetics(playerNumber,headID,tailID);
            }
        }
    }

    @Override
    public IPlayer getPlayer(int lobbyID, int playerNumber) throws RemoteException {
        IPlayer player = null;
        for (ILobby l:lobbyList) {
            if (l.getId() == lobbyID)
            {
                player = l.getPlayer(playerNumber);
            }
        }
        return player;
    }

    @Override
    public void buyItem(int userID, int itemID) throws RemoteException {
        try {
            shopRepo.buyItem(itemID,userID);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> getItems(int id) throws RemoteException {
        try {
            return shopRepo.getItems(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> getOwnedItems(int id, String type) throws RemoteException {
        try {
            return shopRepo.getOwnedItems(id,type);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized int getNewLobbyID() throws RemoteException {
        nextLobbyID++;
        return nextLobbyID;
    }

}
