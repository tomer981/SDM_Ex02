package order;

import customer.Customer;
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

    //    public Integer getId() {
//        return id;
//    }
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public Integer getNumberOfDifferentProductsInOrder(){
//        numberOfDifferentProducts = 0;
//        for(Store store : KStoreVSubStore.keySet()){
//            numberOfDifferentProducts += getNumberOfDifferentProductsInStoreOrder(store);
//        }
//
//        return numberOfDifferentProducts;
//    }
//    public Integer getNumberOfDifferentProductsInStoreOrder(Store store){
//        return KStoreVSubStore.get(store).getNumberOfDifferentProducts();
//    }
//
//    public Double getTotalProductsCost(){
//        totalCostProducts = 0.0;
//        for(Store store : KStoreVSubStore.keySet()){
//            totalCostProducts += getTotalProductsCostOfStore(store);
//        }
//
//        return totalCostProducts;
//    }
//    public Double getTotalProductsCostOfStore(Store store){
//        return KStoreVSubStore.get(store).getTotalProductsCost();
//    }
//
//    public Double getAmountOfProductsInOrder(){
//        amountsOfProducts = 0.0;
//        for(Store store : KStoreVSubStore.keySet()){
//            amountsOfProducts += getAmountOfProductsOfStoreOrder(store);
//        }
//
//        return amountsOfProducts;
//    }
//    public Double getAmountOfProductsOfStoreOrder(Store store){
//        return KStoreVSubStore.get(store).getAmountOfProducts();
//    }
//
//    public Double getTotalDeliveryOrderCost(){
//        totalDeliverOrderCost = 0.0;
//        for (Store store : KStoreVSubStore.keySet()){
//            totalDeliverOrderCost += getOrderDeliveryCostOfStore(store);
//        }
//
//        return totalDeliverOrderCost;
//    }
//    public Double getOrderDeliveryCostOfStore(Store store){
//        return KStoreVSubStore.get(store).getDeliverCost();
//    }
//
//    public Double getTotalOrderCost(){
//        totalOrderCost = 0.0;
//        for (Store store : KStoreVSubStore.keySet()){
//            totalOrderCost += getOrderDeliveryCostOfStore(store);
//        }
//
//        return totalOrderCost;
//    }
//    public Double getTotalOrderCostOfStore(Store store){
//        return KStoreVSubStore.get(store).getTotalOrderOfStoreCost();
//
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//
//    public void addSubOrder(Store storesSystem, StoreOrderDTO storesOrder) {
//        SubOrder subOrder = new SubOrder(id,storesSystem,customer,date);
//        List<StoreProductOrderDTO> ProductsOrder  = storesOrder.getProducts();
//        ProductsOrder.forEach(subOrder::addProduct);
//    }
}
