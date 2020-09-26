package dto.orderDTO;

import javafx.scene.layout.BorderPane;

import java.util.Objects;

public class StoreProductOrderDTO {
    private Integer id;
    private String name;
    private String category;
    private Double pricePerUnit;

    private Double amountBought;
    private Double amountUseDiscount;


    public StoreProductOrderDTO(Integer id, String name, String category, Double pricePerUnit, Double amountBought) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.pricePerUnit = pricePerUnit;
        this.amountBought = amountBought;
        this.amountUseDiscount = 0.0;
    }

    public StoreProductOrderDTO(Integer id, String name, String category, Double pricePerUnit, Double amountBought, Double amountUseDiscount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.pricePerUnit = pricePerUnit;
        this.amountBought = amountBought;
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

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public Double getAmountBought() {
        return amountBought;
    }

    public Double getAmountUseDiscount() {
        return amountUseDiscount;
    }


    public void setAmountBought(Double amountBought) {
        this.amountBought = amountBought;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }


    public void setAmountUseDiscount(Double amountUseDiscount) {
        this.amountUseDiscount = amountUseDiscount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreProductOrderDTO)) return false;
        StoreProductOrderDTO that = (StoreProductOrderDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(category, that.category) &&
                Objects.equals(pricePerUnit, that.pricePerUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, pricePerUnit);
    }
}
