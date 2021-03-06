package classes.repositories;

import classes.domains.Item;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShopRepository implements IShopRepository {
    @Override
    public List<Item> getItems(int userID) throws SQLException, IOException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        System.out.println(userID);
        String getitems = "select ID,type,name,price from item where ID not in (select i.ID  from item i join player_item p_i on p_i.item_id=i.ID where p_i.player_id = ?);";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(getitems, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt (1, userID);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            items.add(new Item(rs.getInt("ID"),rs.getString("type"),rs.getString("name"),rs.getInt("price")));
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
        changeCurrency(itemid, userid);
    }

    private void changeCurrency(int itemid, int userid) throws SQLException, IOException, ClassNotFoundException {
        String changeCurrency = "UPDATE player p, item i SET credits = credits - i.price WHERE i.ID = ? AND p.ID = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(changeCurrency);
        preparedStatement.setInt(1, itemid);
        preparedStatement.setInt(2, userid);
        preparedStatement.execute();
        conn.close();

    }

    public List<Item> getOwnedItems(int userID, String type) throws SQLException, IOException, ClassNotFoundException {
        List<Item> items = new ArrayList<>();
        String getitems = "select i.ID,i.type,i.name,i.price from item i\n" +
                "join player_item p_i on p_i.item_id=i.ID\n" +
                "where p_i.player_id = ? and i.type = ?;";
        IConnection connection = new ConnectionManager();
        Connection conn = connection.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(getitems, Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setInt(1, userID);
        preparedStmt.setString(2, type);
        ResultSet rs = preparedStmt.executeQuery();
        while (rs.next()) {
            items.add(new Item(rs.getInt("ID"),rs.getString("type"),rs.getString("name"),rs.getInt("price")));
        }
        conn.close();
        return items;
    }

}
