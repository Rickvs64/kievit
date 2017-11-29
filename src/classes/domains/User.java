package classes.domains;

import java.io.Serializable;

public class User implements Serializable{
    private int id;
    private String username;
    private String password;
    private Integer credits;


    /**
     * Test/temporary constructor.
     * @param username
     * @param credits
     */
    public User(int id, String username, Integer credits) {
        this.id = id;
        this.username = username;
        this.credits = credits;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; } //comment van Alex: dit is niet heel gebruikelijk, meestal wordt wachtwoord nooit lokaal opgeslagen

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public void setPassword(String password) { this.password = password; }
}
