package discount;

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

    public void addOfferDiscount(OfferDiscount offerDiscount) {
        receiveOfferDiscount.add(offerDiscount);
    }

    public Product getBuyProduct() {
        return buyProduct;
    }
}
