package discount;

import dto.orderDTO.DiscountProductsDTO;
import dto.orderDTO.OfferDiscountDTO;
import dto.orderDTO.OffersDiscountDTO;
import product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Discount {
    private String name;
    private Product buyProduct;
    private Double buyProductQuantity;
    private ConditionDiscount condition;
    private List<OfferDiscount> receiveOfferDiscount;//Todo: Property to bind the time of each one to amountDiscount/offerDiscontAmont

    public Discount(String name, Product buyProduct, Double buyProductQuantity, ConditionDiscount condition) {
        this.name = name;
        this.buyProduct = buyProduct;
        this.buyProductQuantity = buyProductQuantity;
        this.condition = condition;
        receiveOfferDiscount = new ArrayList<>();
    }

    public void addOfferDiscount(OfferDiscount offerDiscount) {
        receiveOfferDiscount.add(offerDiscount);
    }
    public Product getBuyProduct() {
        return buyProduct;
    }

    public boolean isProductIsFoundInOfferDiscount(Product product){
        return receiveOfferDiscount.stream().anyMatch(offerDiscount->offerDiscount.getProduct().equals(product));
    }
    public boolean isProductIsConditionProduct(Product product){
        return buyProduct.equals(product);
    }

    public void removeOfferDiscountContainedProduct(Product product) {
        receiveOfferDiscount.removeIf(offerDiscount -> offerDiscount.getProduct().equals(product));
    }
    public List<OfferDiscount> getReceiveOfferDiscount() {
        return receiveOfferDiscount;
    }
    public DiscountProductsDTO getDiscountDTO() {
        return new DiscountProductsDTO(
                name,
                buyProduct.getId(),
                buyProduct.getName(),
                buyProductQuantity,
                condition.name());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount)) return false;
        Discount discount = (Discount) o;
        return Objects.equals(name, discount.name) &&
                Objects.equals(buyProduct, discount.buyProduct) &&
                Objects.equals(buyProductQuantity, discount.buyProductQuantity) &&
                condition == discount.condition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, buyProduct, buyProductQuantity, condition);
    }

    public OffersDiscountDTO getOffersDiscountDTO() {
        List<OfferDiscountDTO> offersDiscount = new ArrayList<>();
        receiveOfferDiscount.forEach(offer-> offersDiscount.add(offer.getOfferDiscountDTO()));
        return new OffersDiscountDTO(condition.getName(), offersDiscount);
    }
}
