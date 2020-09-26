package order;

import customer.Customer;
import discount.OfferDiscount;
import product.StoreProduct;
import store.Store;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubOrder {
    private Integer orderId;
    private LocalDate date;
    private Store store;
    private Customer customer;
    private Double distance;
    private Double deliverCost;


    private Integer numberOfDifferentProducts = 0;
    private Double amountsOfProducts = 0.0;
    private Double totalCostProducts = 0.0;
    private Double totalOrderCost = 0.0;

    private List<StoreProduct> storeProducts = new ArrayList<>();
    private Map<OfferDiscount,Integer> KOfferDiscountVTimeUse = new HashMap<>();

    public SubOrder(Integer orderId, LocalDate date, Store store, Customer customer, Double distance, Double deliverCost, Integer numberOfDifferentProducts, Double amountsOfProducts, Double totalCostProducts, Double totalOrderCost, List<StoreProduct> storeProducts, Map<OfferDiscount, Integer> KOfferDiscountVTimeUse) {
        this.orderId = orderId;
        this.date = date;
        this.store = store;
        this.customer = customer;
        this.distance = distance;
        this.deliverCost = deliverCost;
        this.numberOfDifferentProducts = numberOfDifferentProducts;
        this.amountsOfProducts = amountsOfProducts;
        this.totalCostProducts = totalCostProducts;
        this.totalOrderCost = totalOrderCost;
        this.storeProducts = storeProducts;
        this.KOfferDiscountVTimeUse = KOfferDiscountVTimeUse;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Store getStore() {
        return store;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getDeliverCost() {
        return deliverCost;
    }

    public Integer getNumberOfDifferentProducts() {
        return numberOfDifferentProducts;
    }

    public Double getAmountsOfProducts() {
        return amountsOfProducts;
    }

    public Double getTotalCostProducts() {
        return totalCostProducts;
    }

    public Double getTotalOrderCost() {
        return totalOrderCost;
    }

    public List<StoreProduct> getStoreProducts() {
        return storeProducts;
    }

    public Map<OfferDiscount, Integer> getKOfferDiscountVTimeUse() {
        return KOfferDiscountVTimeUse;
    }
}
