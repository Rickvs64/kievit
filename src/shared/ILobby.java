package shared;

import classes.domains.Player;

public interface ILobby {
    int getId();
    boolean getStatus();
    Player getPlayer(int id);
    void update(Player player);
}
