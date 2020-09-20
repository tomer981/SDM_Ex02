package discount;

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
}
