package product;


import dto.orderDTO.StoreProductOrderDTO;

import java.util.Objects;

public class StoreProduct implements Cloneable{
    private Double price;
    private Integer timeSold;

    public void setTimeSold(Integer timeSold) {
        this.timeSold = timeSold;
    }

    private Double amountSold;
    private Product product;


    public StoreProduct(Product product, Double price) {
//        this.price = new SimpleDoubleProperty(price);
        this.price = price;
        this.timeSold = 0;
        this.amountSold = 0.0;
        this.product = product;
    }

    public StoreProduct(Double price, Double amountSold, Product product) {
        this.price = price;
        this.amountSold = amountSold;
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
    protected StoreProduct clone() throws CloneNotSupportedException {
        return (StoreProduct)super.clone();
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

//    public Double getPrice() {
//        return price.getValue();
//    }
    public Double getPrice() {
        return price;
    }

//    public SimpleDoubleProperty getPropertyPrice(){
//        return price;
//    }
//


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
        this.price = price;
    }
}
