package classes.repositories;

import classes.domains.Item;
import classes.domains.ItemType;

import java.util.ArrayList;
import java.util.List;

public class ShopRepository implements IShopRepository {
    @Override
    public List<Item> getItems(int userID) {
        List<Item> items = new ArrayList<>();
        items.add(new Item(1, ItemType.head,"WeedHead"));
        items.add(new Item(2, ItemType.tail,"WeedTail"));
        return  items;
    }
}
