package discount;

import dto.orderDTO.OfferDiscountDTO;
import product.Product;

public class OfferDiscount {
    private Product product;
    private Double receiveQuantity;
    private Double forPrice;

    public OfferDiscount(Product product, Double getQuantity, Double forPrice) {
        this.product = product;
        this.receiveQuantity = getQuantity;
        this.forPrice = forPrice;
    }

    public Product getProduct() {
        return product;
    }

    public OfferDiscountDTO getOfferDiscountDTO() {
        return new OfferDiscountDTO(
                product.getId(),
                product.getName(),
                receiveQuantity,
                forPrice,
                product.getPurchaseCategory().name());
    }
}
