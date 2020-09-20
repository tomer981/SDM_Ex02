package discount;

import product.PurchaseCategory;

public enum ConditionDiscount {
    ONE_OF,
    ALL_OR_NOTHING,
    IRRELEVANT;

    public static ConditionDiscount getConditionDiscount(String conditionDiscount) {
        return ConditionDiscount.valueOf(conditionDiscount.toUpperCase().replaceAll("-", "_"));
    }
}
