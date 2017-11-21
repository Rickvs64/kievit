package classes.domains;

public class Room {
    private int ID;
    private int Speed;
    private String name;
    private String password;
    private int players;

    private Boolean started;

    public Room(int ID, String name, String password, int started, int players) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        if(started == 1){ this.started = true;}
        else {this.started = false;}
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }
}
