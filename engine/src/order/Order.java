package order;


import javafx.beans.property.DoubleProperty;
import location.Location;

import java.util.List;

public class Order {
    private Integer orderId;
    private List<SubOrder> subOrders;
    private Location customerLocation;
    private DoubleProperty TotalProductOrdersCost;
    private DoubleProperty TotalDeliverOrderCost;
    private DoubleProperty TotalOrderCost;


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
