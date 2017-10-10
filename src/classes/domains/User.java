package classes.domains;

public class User {
    private String username;
    private Integer credits;


    public User(String username, Integer credits) {
        this.username = username;
        this.credits = credits;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}
