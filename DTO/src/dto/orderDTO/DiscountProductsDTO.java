package dto.orderDTO;

public class DiscountProductsDTO {
    private String nameDiscount;
    private Integer productId;
    private String nameProduct;
    private Double quantityNeeded;
    private String condition;

    public DiscountProductsDTO(String nameDiscount, Integer productId, String nameProduct, Double quantityNeeded, String condition) {
        this.nameDiscount = nameDiscount;
        this.productId = productId;
        this.nameProduct = nameProduct;
        this.quantityNeeded = quantityNeeded;
        this.condition = condition;
    }


    public String getNameDiscount() {
        return nameDiscount;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public Double getQuantityNeeded() {
        return quantityNeeded;
    }

    public String getCondition() {
        return condition;
    }
}
