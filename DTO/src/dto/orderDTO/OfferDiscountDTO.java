package dto.orderDTO;

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

}
