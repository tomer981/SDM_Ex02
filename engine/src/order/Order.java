package order;

import customer.Customer;
import dto.orderDTO.OrderDTO;
import dto.orderDTO.StoreProductOrderDTO;
import dto.orderDTO.StoreOrderDTO;
import store.Store;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    static Integer orderId = 1;
    private Integer id;
    private LocalDate date;
    private Customer customer;

    private Integer numberOfDifferentProducts;
    private Double amountsOfProducts;
    private Double totalCostProducts;
    private Double totalDeliverOrderCost;
    private Double totalOrderCost;

    private Map<Store,SubOrder> KStoreVSubStore = new HashMap<>();

    public Order(LocalDate date, Customer customer, Integer numberOfDifferentProducts, Double amountsOfProducts, Double totalCostProducts, Double totalDeliverOrderCost, Double totalOrderCost) {
        this.id = orderId++;
        this.date = date;
        this.customer = customer;
        this.numberOfDifferentProducts = numberOfDifferentProducts;
        this.amountsOfProducts = amountsOfProducts;
        this.totalCostProducts = totalCostProducts;
        this.totalDeliverOrderCost = totalDeliverOrderCost;
        this.totalOrderCost = totalOrderCost;
    }

    public static Integer getOrderId() {
        return orderId;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
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

    public Double getTotalDeliverOrderCost() {
        return totalDeliverOrderCost;
    }

    public Double getTotalOrderCost() {
        return totalOrderCost;
    }

    public Map<Store, SubOrder> getKStoreVSubStore() {
        return KStoreVSubStore;
    }

    public void addSubOrder(SubOrder subOrder) {
        KStoreVSubStore.put(subOrder.getStore(),subOrder);
    }

    public OrderDTO getOrderDTO(){
        return new OrderDTO(id,date,customer.getCustomerData(),numberOfDifferentProducts,amountsOfProducts,totalCostProducts,totalDeliverOrderCost,totalOrderCost);
    }
}
