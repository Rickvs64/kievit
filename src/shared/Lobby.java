package shared;

import classes.domains.Player;

import java.util.List;

public class Lobby implements ILobby {
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
    public Player getPlayer(int id) {
        for (Player p:players
             ) {
            if (p.getUserID() == id)
                return p;

        }
        return null;
    }

    @Override
    public void update(Player player) {
        for (Player p:players) {
            if (p.getUserID() == player.getUserID())
            {
                p.update(player);
            }
        }
    }
}
