package server;

import classes.domains.*;

import classes.repositories.*;
import javafx.application.Platform;
import shared.IListener;
import shared.ILobby;
import shared.Lobby;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerManager extends UnicastRemoteObject implements IServerManager {
    private Map<Integer, ILobby> lobbyList = new HashMap<>();
    private Map<Integer, IListener> clients = new HashMap<>();
    private int nextLobbyID = 1;
    private IUserRepository userRepo = new SQLUserRepository();
    private IShopRepository shopRepo = new ShopRepository();

    public ServerManager() throws IOException, SQLException, ClassNotFoundException { }

    public ILobby addLobby(int id,String user,String name,String password) throws RemoteException {
        ILobby lobby = new Lobby(id,user,name,password);
        System.out.println("Lobby created id: " + lobby.getId());
        lobbyList.put(lobby.getId(), lobby);
        return lobby;
    }

    @Override
    public synchronized ILobby getLobby(int id) {
        return lobbyList.get(id);
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
        ILobby lobby = lobbyList.get(lobbyId);
        try {
            lobby.addUser(user);
            lobby.addCount();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return lobby;
    }

    @Override
    public boolean getStatus(int id) {
        try {
            return lobbyList.get(id).getStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setStatus(boolean b,int id) {
        try {
            lobbyList.get(id).setStatus(b);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException {
        ILobby lobby = lobbyList.get(id);
        lobby.updateDirection(userID,currentDirection);

        for (IListener client:clients.values())
            if (client.getLobbyID() == id && client.getUserID() != userID)
                client.setDirectionOtherPlayer(currentDirection);
    }

    @Override
    public Direction getDirection(int userID, int id) {
        try {
            return lobbyList.get(id).getDirection(userID);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsers(int id) {
        try {
            return getLobby(id).getUsers();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ILobby> getAvailableLobbies() throws RemoteException {
        List<ILobby> availableLobbies = new ArrayList<>();
        for (ILobby l:lobbyList.values())
            if (l.getCount() < 2)
                availableLobbies.add(l);
        return availableLobbies;
    }

    @Override
    public void setCosmetics(int playerNumber, int lobbyID, int headID, int tailID) throws RemoteException {
        lobbyList.get(lobbyID).setCosmetics(playerNumber, headID, tailID);
    }
    @Override
    public Player getCosmetics(int playerNumber, int lobbyID) throws RemoteException {
        return lobbyList.get(lobbyID).getCosmetics(playerNumber);
    }

    @Override
    public IPlayer getPlayer(int lobbyID, int playerNumber) throws RemoteException {
        return lobbyList.get(lobbyID).getPlayer(playerNumber);
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
    public void updateStatus(int userID, int lobbyID) throws RemoteException {
        ILobby lobby = lobbyList.get(lobbyID);
        if (lobby == null)
            return;
        for (User user: lobby.getUsers()) {
            if (user.getId() == userID) {
                System.out.println("gebruiker gevonden");
                user.setStatus(true);
            }
        }
    }

    @Override
    public void updateCurrency(int id) throws RemoteException {
        try {
            this.userRepo.updateCurrency(id, 100);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener(IListener listener) throws RemoteException {
        this.clients.put(listener.getUserID(), listener);
    }

    @Override
    public void removeListener(IListener listener) throws RemoteException {
        this.clients.remove(listener.getUserID());
    }

    @Override
    public void updateHighscore(int userID, int score) throws IOException, SQLException, ClassNotFoundException {
        IHighscoreRepository  highscoreRepo = new HighscoreRepository();
        highscoreRepo.updateHighscores(userID,score);
    }
}
