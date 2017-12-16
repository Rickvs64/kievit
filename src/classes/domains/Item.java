package classes.domains;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Item implements Serializable {
    private int ID;
    private String type;
    private String name;
    private int price;

    public Item(int ID, String type,String name, int price) {
        this.ID = ID;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public Image getImage(){
        return new Image("@../../resources/img/item_" + ID +".gif");
    }

    @Override
    public String toString() {
        return name;
    }
}
