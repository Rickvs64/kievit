package shared;

import classes.domains.IPlayer;
import classes.domains.Player;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class Lobby implements ILobby, Serializable {
    private int id;
    private boolean status;
    private List<Player> players;

    public Lobby(int id) {
        this.id = id;
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
                    p.update(player);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }
}
