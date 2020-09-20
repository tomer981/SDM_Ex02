package location;

public class Location {
    private Integer cordX;
    private Integer cordY;

    public Location(int x, int y) {
        cordX = x;
        cordY = y;
    }

    public Integer getCordX() {
        return cordX;
    }

    public Integer getCordY() {
        return cordY;
    }
}
