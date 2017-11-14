package classes.repositories;

import classes.domains.Item;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IShopRepository {
    List<Item> getItems(int userID) throws SQLException, IOException, ClassNotFoundException;
    void buyItem(int id, int i) throws SQLException, IOException, ClassNotFoundException;
}
