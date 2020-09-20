package order;

import product.Product;
import store.Store;

import java.util.Map;

public class SubOrder {
//    Store store;//G.A 1,2,3
//    DoubleProperty distance;//G.A 4
//    DoubleProperty orderDeliveryCost;//G.A 5
//    Map<Product, Double> KProductVQuantity;//G.B 1,2,3
//    Map<Product, Double> KProductVCost;//G.B 5 - may change because of update
//    DoubleProperty TotalCostPerStore;
//    Map<Product, Boolean> kProductVIsDiscount;
    Store store;//G.A 1,2,3
    Double distance;//G.A 4
    Double orderDeliveryCost;//G.A 5
    Map<Product, Double> KProductVQuantity;//G.B 1,2,3
    Map<Product, Double> KProductVCost;//G.B 5 - may change because of update
    Double TotalCostPerStore;
    Map<Product, Boolean> kProductVIsDiscount;
}
