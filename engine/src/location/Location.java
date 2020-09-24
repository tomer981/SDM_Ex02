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

    public Double getDistance(Location location){
        Double X =  Math.pow(cordX - location.getCordX(),2);
        Double Y = Math.pow(cordY - location.getCordY(),2);
        return Math.pow(X+Y,0.5);
    }
}
