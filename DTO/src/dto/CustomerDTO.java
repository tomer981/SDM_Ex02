package dto;

public class CustomerDTO {
    private Integer id;
    private String name;
    private Integer cordX;
    private Integer cordY;
    private Integer totalOrders;
    private Double avgDelivery;
    private Double avgPricePerOrder;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getCordX() {
        return cordX;
    }

    public Integer getCordY() {
        return cordY;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public Double getAvgPricePerDelivery() {
        return avgDelivery;
    }

    public Double getAvgPricePerOrder() {
        return avgPricePerOrder;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCordX(Integer cordX) {
        this.cordX = cordX;
    }

    public void setCordY(Integer cordY) {
        this.cordY = cordY;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public void setAvgPricePerDelivery(Double avgPricePerDelivery) {
        this.avgDelivery = avgPricePerDelivery;
    }

    public void setAvgPricePerOrder(Double avgPricePerOrder) {
        this.avgPricePerOrder = avgPricePerOrder;
    }

    public CustomerDTO(Integer id, String name, Integer cordX, Integer cordY, Integer totalOrders, Double avgPricePerDelivery, Double avgPricePerOrder) {
        this.id = id;
        this.name = name;
        this.cordX = cordX;
        this.cordY = cordY;
        this.totalOrders = totalOrders;
        this.avgDelivery = avgPricePerDelivery;
        this.avgPricePerOrder = avgPricePerOrder;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
