package dto;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerDTO {
    private Integer id;
    private String name;
    private Integer cordX;
    private Integer cordY;
    private Integer totalOrders;
    private Double avgPricePerDelivery;
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
        return avgPricePerDelivery;
    }

    public Double getAvgPricePerOrder() {
        return avgPricePerOrder;
    }


    public CustomerDTO(Integer id, String name, Integer cordX, Integer cordY, Integer totalOrders, Double avgPricePerDelivery, Double avgPricePerOrder) {
        this.id = id;
        this.name = name;
        this.cordX = cordX;
        this.cordY = cordY;
        this.totalOrders = totalOrders;
        this.avgPricePerDelivery = avgPricePerDelivery;
        this.avgPricePerOrder = avgPricePerOrder;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
