package dto.orderDTO;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoreOrderDTO {
    private Integer id;
    private String name;
    private Integer ppk;
    private Double deliveryEarned;
    private Integer cordX;
    private Integer cordY;

    List<ProductOrderDTO> products = new ArrayList<>();


    public StoreOrderDTO(Integer id, String name, Integer ppk, Double deliveryEarned, Integer cordX, Integer cordY) {
        this.id = id;
        this.name = name;
        this.ppk = ppk;
        this.deliveryEarned = deliveryEarned;
        this.cordX = cordX;
        this.cordY = cordY;
    }


    public void setProducts(List<ProductOrderDTO> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPpk() {
        return ppk;
    }

    public Double getDeliveryEarned() {
        return deliveryEarned;
    }

    public Integer getCordX() {
        return cordX;
    }

    public Integer getCordY() {
        return cordY;
    }

    public List<ProductOrderDTO> getProducts() {
        return products;
    }


    @Override
    public String toString() {
        return id + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreOrderDTO)) return false;
        StoreOrderDTO that = (StoreOrderDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
