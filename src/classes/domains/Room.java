package classes.domains;

public class Room {
    int ID;
    String name;
    String password;
    Boolean started;

    public Room(int ID, String name, String password, Boolean started) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.started = started;
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
