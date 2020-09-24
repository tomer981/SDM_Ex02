package dto.orderDTO;

import java.util.Objects;

public class ProductOrderDTO {
    private Integer id;
    private String name;
    private String category;
    private Double price;
    private Double amountBought;
    private boolean boughtInDiscount;
    private Double amountUseDiscount;


    public ProductOrderDTO(Integer id, String name, String category, Double price, Double amountBought, boolean boughtInDiscount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amountBought = amountBought;
        this.boughtInDiscount = boughtInDiscount;
        this.amountUseDiscount = 0.0;
    }

    public ProductOrderDTO(Integer id, String name, String category, Double price, Double amountBought, boolean boughtInDiscount, Double amountUseDiscount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.amountBought = amountBought;
        this.boughtInDiscount = boughtInDiscount;
        this.amountUseDiscount = amountUseDiscount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public Double getAmountBought() {
        return amountBought;
    }

    public Double getAmountUseDiscount() {
        return amountUseDiscount;
    }

    public boolean isBoughtInDiscount() {
        return boughtInDiscount;
    }

    public void setAmountBought(Double amountBought) {
        this.amountBought = amountBought;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setBoughtInDiscount(boolean boughtInDiscount) {
        this.boughtInDiscount = boughtInDiscount;
    }

    public void setAmountUseDiscount(Double amountUseDiscount) {
        this.amountUseDiscount = amountUseDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductOrderDTO)) return false;
        ProductOrderDTO that = (ProductOrderDTO) o;
        return boughtInDiscount == that.boughtInDiscount &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, boughtInDiscount);
    }
}
