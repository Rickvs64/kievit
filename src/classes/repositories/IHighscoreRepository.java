package classes.repositories;

import classes.domains.HighscoreEntry;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IHighscoreRepository {
    ArrayList<HighscoreEntry> getHighscores() throws SQLException, IOException, ClassNotFoundException;
}
