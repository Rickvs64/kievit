package classes.domains;

import javafx.scene.image.Image;

public class Item {
    private int ID;
    private String type;
    private String name;

    public Item(int ID, String type,String name) {
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

    public String getType() {
        return type;
    }

    public Image getImage(){
        return new Image("@../../resources/img/item_" + ID +".png");
    }
}
