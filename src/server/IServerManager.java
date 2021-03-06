package server;

import classes.domains.*;
import shared.IListener;
import shared.ILobby;
import shared.IRemotePublisher;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface IServerManager extends Remote, IRemotePublisher {
    User login(String username, String password) throws RemoteException;
    ILobby addLobby(int id,String user,String name,String password) throws RemoteException;
    ILobby getLobby(int id) throws RemoteException;
    ILobby joinLobby(int lobbyId, User user) throws RemoteException;
    boolean getStatus(int id) throws RemoteException;
    void setStatus(boolean b,int id) throws RemoteException;
    void updateDirection(Direction currentDirection, int id, int userID) throws RemoteException;
    Direction getDirection(int userID, int id) throws  RemoteException;
    List<User> getUsers(int id)throws  RemoteException;
    List<ILobby> getAvailableLobbies() throws RemoteException;
    void setCosmetics(int playerNumber,int lobbyID, int headID, int tailID) throws RemoteException;
    List<Integer> getCosmetics(int playerNumber, int lobbyID) throws RemoteException;
    IPlayer getPlayer(int lobbyID, int playerNumber) throws RemoteException;
    void buyItem(int userID, int itemID) throws RemoteException;
    List<Item> getItems(int id) throws RemoteException;
    List<Item> getOwnedItems(int id, String type) throws RemoteException;
    int getNewLobbyID() throws RemoteException;
    boolean checkUsernameExists(String username) throws RemoteException;
    boolean createUser(User user) throws RemoteException;
    void updateHighscore(int userID, int score) throws IOException, SQLException, ClassNotFoundException;
    void updateStatus(int userID, int lobbyID) throws RemoteException;
    User getUser(int id) throws RemoteException;
    void updateCurrency(int id)throws RemoteException;
}
