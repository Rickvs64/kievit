package classes.repositories;

import classes.domains.Item;

import java.util.List;

public interface IShopRepository {
    List<Item> getItems(int userID);
}
