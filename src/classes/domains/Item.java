package classes.domains;

import javafx.scene.image.Image;

public class Item {
    private int ID;
    private ItemType type;
    private String name;

    public Item(int ID, ItemType type,String name) {
        this.ID = ID;
        this.type = type;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public Image getImage(){
        return new Image("@../../resources/img/item_" + ID +".png");
    }
}
