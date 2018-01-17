package shared;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.Player;
import classes.domains.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class Lobby extends UnicastRemoteObject implements ILobby, Serializable {
    private int id;
    private boolean status;
    private List<Player> players = new ArrayList<>();
    private int count;
    private List<User> users = new ArrayList<>();
    private String name;
    private String password;
    private String user;

    public Lobby(int id,String user,String name, String password) throws RemoteException {
        this.id = id;
        this.count = 0;
        this.status = false;
        this.name = name;
        this.user = user;
        this.password = password;
        try {
            players.add(new Player(50, 600, Direction.UP, 1, 1));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            players.add(new Player(950, 600, Direction.UP, 2, 2));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public synchronized IPlayer getPlayer(int id) {
        for (IPlayer p : players) {
            try {
                if (p.getUserID() == id)
                    return p;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getUser() {
        return user;
    }
    public String getPlayername()
    {
        return users.get(0).getUsername();
    }

    @Override
    public void setCosmetics(int playerNumber, int headID, int tailID) {
        for (Player p:players) {
            if (p.getUserID() == playerNumber) {
                p.setHeadID(headID);
                p.setTailID(tailID);
            }
        }
    }
    @Override
    public Player getCosmetics(int playerNumber) {
        Player player = null;

        for (Player p:players) {
            if (p.getUserID() == playerNumber) {
                try {
                    player = new Player(50,50,Direction.LEFT,1,1);
                    player.setHeadID(p.getHeadID());
                    player.setTailID(p.getTailID());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        return player;
    }
    @Override
    public synchronized void update(IPlayer player) {
        for (IPlayer p : players) {
            try {
                if (p.getUserID() == player.getUserID()) {
                    p.update(player);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int getCount() throws RemoteException {
        return count;
    }

    public synchronized void addCount() throws RemoteException {
        count++;
    }

    @Override
    public synchronized void updateDirection(int userID, Direction currentDirection) throws RemoteException {
        for (IPlayer p : players) {
            try {
                if (p.getUserID() == userID) {
                    p.setCurrentDirection(currentDirection);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Direction getDirection(int userID) {
        for (IPlayer p : players) {
            try {
                if (p.getUserID() == userID) {
                    return p.getCurrentDirection();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) throws RemoteException {
        users.add(user);
    }

    @Override
    public List<User> getUsers() throws RemoteException {
        return users;
    }
}
