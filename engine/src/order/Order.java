package order;


import location.Location;
import product.Product;
import store.Store;

import java.util.List;
import java.util.Map;

public class Order {
    private Integer orderId;
    private List<SubOrder> subOrders;
    private Location customerLocation;
    private Double TotalProductOrdersCost;
    private Double TotalDeliverOrderCost;
    private Double TotalOrderCost;


//    public class SubOrder {
//        Store store;//G.A 1,2,3
//        DoubleProperty distance;//G.A 4
//        DoubleProperty orderDeliveryCost;//G.A 5
//        Map<Product, Double> KProductVQuantity;//G.B 1,2,3
//        Map<Product, Double> KProductVCost;//G.B 5 - may change because of update
//        DoubleProperty TotalCostPerStore;
//        Map<Product, Boolean> kProductVIsDiscount;
//    }

}
