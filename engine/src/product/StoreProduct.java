package product;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.Objects;

public class StoreProduct {
    private SimpleDoubleProperty price;
    private Integer timeSold;
    private Double amountSold;
    private Product product;


    public StoreProduct(Product product, Double price) {
        this.price = new SimpleDoubleProperty(price);
        this.timeSold = 0;
        this.amountSold = 0.0;
        this.product = product;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreProduct)) return false;
        StoreProduct that = (StoreProduct) o;
        return Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    public Double getPrice() {
        return price.getValue();
    }

    public SimpleDoubleProperty getPropertyPrice(){
        return price;
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public Integer getTimeSold() {
        return timeSold;
    }

    public Double getAmountSold() {
        return amountSold;
    }

    public Product getProduct() {
        return product;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
}
