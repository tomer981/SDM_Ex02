package dto.orderDTO;

import java.util.Objects;

public class OfferDiscountDTO {
    private Integer id;
    private String name;
    private Double amount;
    private Double price;
    private String category;


    public OfferDiscountDTO(Integer id, String name, Double amount, Double price, String category) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.category = category;
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OfferDiscountDTO)) return false;
        OfferDiscountDTO that = (OfferDiscountDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(price, that.price) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, price, category);
    }
}
