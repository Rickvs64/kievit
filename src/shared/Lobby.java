package shared;

import classes.domains.Direction;
import classes.domains.IPlayer;
import classes.domains.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Lobby implements ILobby, Serializable {
    private int id;
    private boolean status;
    private List<Player> players = new ArrayList<>();
    private int count;

    public Lobby(int id) {
        this.id = id;
        this.count = 0;
        this.status = false;
        try {
            players.add(new Player(50, 600, Direction.UP, 1, 0));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            players.add(new Player(950, 600, Direction.UP, 2, 0));
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

    @Override
    public IPlayer getPlayer(int id) {
        for (IPlayer p:players
             ) {
            try {
                if (p.getUserID() == id)
                    return p;
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    public void update(IPlayer player) {
        for (IPlayer p:players) {
            try {
                if (p.getUserID() == player.getUserID())
                {
                    p = player;
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

    public synchronized void addCount() throws RemoteException{
        count++;
    }
}
