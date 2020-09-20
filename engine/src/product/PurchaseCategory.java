package product;

public enum PurchaseCategory {
    QUANTITY,
    WEIGHT;

    public static PurchaseCategory getPurchaseCategory(String category) {
        return PurchaseCategory.valueOf(category.toUpperCase());
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}