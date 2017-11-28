package classes.domains;

import java.io.Serializable;

public class Coordinate implements Serializable{
    private int X;
    private int Y;

    public Coordinate(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }


    public int getY() {
        return Y;
    }
}
