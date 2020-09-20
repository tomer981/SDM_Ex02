package product;

import java.util.List;
import java.util.Objects;

public class Product {
    private Integer Id;
    private String Name;
    private PurchaseCategory purchaseCategory;

    public Product(Integer id, String name, PurchaseCategory purchaseCategory) {
        Id = id;
        Name = name;
        this.purchaseCategory = purchaseCategory;
    }
    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(Id, product.Id) &&
                Objects.equals(Name, product.Name) &&
                purchaseCategory == product.purchaseCategory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, Name, purchaseCategory);
    }
}
