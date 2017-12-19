package classes.repositories;

import classes.domains.HighscoreEntry;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class HighscoreRepository implements IHighscoreRepository {

    @Override
    public ArrayList<HighscoreEntry> getHighscores() throws SQLException, IOException, ClassNotFoundException {
        ArrayList<HighscoreEntry> Highscores = new ArrayList<>();
        String queryCreateRoom = "select h.player_id, p.username, h.score from highscore h\n" +
                "inner join player p on h.player_id = p.ID";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(queryCreateRoom);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            Highscores.add(new HighscoreEntry(rs.getInt("player_id"),rs.getString("username"), rs.getInt("score")));
        }
        conn.close();
        System.out.println(Highscores.size());
        return Highscores;
    }

    @Override
    public void updateHighscores(int userID, int score) throws SQLException, IOException, ClassNotFoundException {
        String queryCreateRoom = "set @playerid = ?;\n" +
                "set @newscore = ((select score from highscore where player_id=@playerid) +?);\n" +
                "Update highscore set score = @newscore where player_id =@playerid;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(queryCreateRoom);
        preparedStmt.setInt(userID,1);
        preparedStmt.setInt(score,2);
        preparedStmt.execute();
        conn.close();
    }

}
