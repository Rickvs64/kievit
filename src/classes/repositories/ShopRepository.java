package classes.repositories;

import classes.domains.Item;
import classes.domains.ItemType;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopRepository implements IShopRepository {
    @Override
    public List<Item> getItems(int userID) throws SQLException, IOException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        String queryCreateRoom = "select i.ID,i.type,i.name from item i;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(queryCreateRoom, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            items.add(new Item(rs.getInt("ID"),rs.getString("type"),rs.getString("name")));
        }
        conn.close();
        return items;
    }

    public List<Item> getOwnedItems(int userID) throws SQLException, IOException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        String queryCreateRoom = "select i.ID,i.type,i.name from item i\n" +
                "join player_item p_i on p_i.item_id=i.ID\n" +
                "join player p on p.ID = p_i.player_id\n" +
                "where p.id = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(queryCreateRoom, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1, userID);
        ResultSet rs = preparedStmt.executeQuery();
        if (rs.next()) {
            items.add(new Item(rs.getInt("ID"),rs.getString("type"),rs.getString("name")));
        }
        conn.close();
        return items;
    }

}
