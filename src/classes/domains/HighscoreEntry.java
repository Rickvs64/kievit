package classes.domains;

public class HighscoreEntry {
    private final int userid;
    private final String username;
    private final int score;

    public HighscoreEntry(int userid, String username, int score) {
        this.userid = userid;
        this.username = username;
        this.score = score;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }
}

