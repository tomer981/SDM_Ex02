package order;


import location.Location;

import java.util.Date;
import java.util.List;

public class Order {
    private List<SubOrder> subOrders;
    private Location customerLocation;
    private Double totalProductOrdersCost;
    private Double totalDeliverOrderCost;
    private Double totalOrderCost;
    private Date date;

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
