package classes.repositories;

import classes.domains.Item;
import classes.domains.ItemType;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ShopRepository implements IShopRepository {
    @Override
    public List<Item> getItems(int userID) throws SQLException, IOException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        String getitems = "select ID,type,name from item where ID not in (select i.ID  from item i join player_item p_i on p_i.item_id=i.ID where p_i.player_id = ?);";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(getitems, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt (1, userID);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            items.add(new Item(rs.getInt("ID"),rs.getString("type"),rs.getString("name")));
        }
        conn.close();
        return items;
    }

    @Override
    public void buyItem(int itemid, int userid) throws SQLException, IOException, ClassNotFoundException {
        String buyitem = "INSERT INTO player_item (player_id, item_id) VALUES (?,?);";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt2 = conn.prepareStatement(buyitem);
        preparedStmt2.setInt (1, userid);
        preparedStmt2.setInt (2, itemid);
        preparedStmt2.execute();
        conn.close();
    }

    public List<Item> getOwnedItems(int userID) throws SQLException, IOException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        String getitems = "select i.ID,i.type,i.name from item i\n" +
                "join player_item p_i on p_i.item_id=i.ID\n" +
                "where p_i.player_id = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(getitems, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1, userID);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            items.add(new Item(rs.getInt("ID"),rs.getString("type"),rs.getString("name")));
        }
        conn.close();
        return items;
    }

}
