package server;

import classes.domains.*;
import classes.repositories.IShopRepository;
import classes.repositories.IUserRepository;
import classes.repositories.SQLUserRepository;
import classes.repositories.ShopRepository;
import shared.IListener;
import shared.ILobby;
import shared.Lobby;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerManager extends UnicastRemoteObject implements IServerManager {
    private List<ILobby> lobbyList = new ArrayList<>();
    private List<IListener> clients = new ArrayList<>();
    private int nextLobbyID = 1;
    private IUserRepository userRepo = new SQLUserRepository();
    private IShopRepository shopRepo = new ShopRepository();

    public ServerManager() throws IOException, SQLException, ClassNotFoundException { }

    public ILobby addLobby(int id,String user,String name,String password) throws RemoteException {
        ILobby lobby = new Lobby(id,user,name,password);
        System.out.println("Lobby created id: " + lobby.getId());
        lobbyList.add(lobby);
        return lobby;
    }

    @Override
    public synchronized ILobby getLobby(int id) {
        for (ILobby l:lobbyList) {
            try {
                if (l.getId() == id) {
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public ILobby joinLobby(int lobbyId,User user) {
        for (ILobby l:lobbyList ) {
            try {
                if (l.getId() == lobbyId) {
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
        for (ILobby l:lobbyList) {
            try {
                if (l.getId() == id) {
                    if (l.getStatus()) {
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
        for (ILobby l:lobbyList) {
            try {
                if (l.getId() == id) {
                   l.setStatus(b);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException {
        for (ILobby l:lobbyList) {
            try {
                if (l.getId() == id) {
                    l.updateDirection(userID,currentDirection);
                    for (IListener client:clients)
                        if (client.getLobbyID() == id && client.getUserID() != userID)
                            client.setDirectionOtherPlayer(currentDirection);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Direction getDirection(int userID, int id) {
        for (ILobby l:lobbyList) {
            try {
                if (l.getId() == id)
                    return l.getDirection(userID);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers(int id) {
        for (ILobby l:lobbyList) {
            try {
                if (l.getId() == id) {
                    return l.getUsers();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<ILobby> getAvailableLobbies() throws RemoteException {
        List<ILobby> availableLobbies = new ArrayList<>();
        for (ILobby l:lobbyList)
            if (l.getCount() < 2)
                availableLobbies.add(l);
        return availableLobbies;
    }

    @Override
    public void setCosmetics(int playerNumber, int lobbyID, int headID, int tailID) throws RemoteException {
        for (ILobby l:lobbyList)
            if (l.getId() == lobbyID)
                l.setCosmetics(playerNumber,headID,tailID);
    }

    @Override
    public IPlayer getPlayer(int lobbyID, int playerNumber) throws RemoteException {
        for (ILobby l:lobbyList)
            if (l.getId() == lobbyID)
                return l.getPlayer(playerNumber);
        return null;
    }

    @Override
    public void buyItem(int userID, int itemID) throws RemoteException {
        try {
            shopRepo.buyItem(itemID,userID);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> getItems(int id) throws RemoteException {
        try {
            return shopRepo.getItems(id);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> getOwnedItems(int id, String type) throws RemoteException {
        try {
            return shopRepo.getOwnedItems(id,type);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public synchronized int getNewLobbyID() throws RemoteException {
        return ++nextLobbyID;
    }

    @Override
    public boolean checkUsernameExists(String username) throws RemoteException {
        return userRepo.checkUsernameExists(username);
    }

    @Override
    public boolean createUser(User user) throws RemoteException {
        return userRepo.createUser(user);
    }

    @Override
    public void addListener(IListener listener) throws RemoteException {
        this.clients.add(listener);
    }

    @Override
    public void removeListener(IListener listener) throws RemoteException {
        this.clients.remove(listener);
    }
}
