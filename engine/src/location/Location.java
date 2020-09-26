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

    public static Double getDistance(Integer x1,Integer y1,Integer x2,Integer y2){
        Double X =  Math.pow(x1 - x2,2);
        Double Y = Math.pow(y1 - y2,2);
        return Math.pow(X+Y,0.5);
    }
}
