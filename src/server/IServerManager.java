package server;

import classes.domains.Player;
import shared.ILobby;

import java.rmi.Remote;

public interface IServerManager extends Remote {
    ILobby update(Player p,int lobbyId);
    void addLobby(int id);
}
