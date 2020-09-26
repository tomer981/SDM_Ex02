package dto.orderDTO;

import dto.CustomerDTO;
import dto.StoreDTO;

import java.time.LocalDate;
import java.util.Map;

public class OrderDTO {
    private Integer id;
    private LocalDate date;
    private CustomerDTO customer;
    private Integer numberOfDifferentProducts = 0;
    private Double amountsOfProducts = 0.0;
    private Double totalCostProducts = 0.0;
    private Double totalDeliverOrderCost;
    private Double totalOrderCost = 0.0;

    private Map<StoreDTO, SubOrderDTO> KStoresVSubOrders;

    public OrderDTO(Integer id, LocalDate date, CustomerDTO customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public OrderDTO(Integer id, LocalDate date, CustomerDTO customer, Integer numberOfDifferentProducts, Double amountsOfProducts, Double totalCostProducts, Double totalDeliverOrderCost, Double totalOrderCost, Map<StoreDTO, SubOrderDTO> KStoresVSubOrders) {
        this.id = id;
        this.date = date;
        this.customer = customer;
        this.numberOfDifferentProducts = numberOfDifferentProducts;
        this.amountsOfProducts = amountsOfProducts;
        this.totalCostProducts = totalCostProducts;
        this.totalDeliverOrderCost = totalDeliverOrderCost;
        this.totalOrderCost = totalOrderCost;
        this.KStoresVSubOrders = KStoresVSubOrders;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public Integer getNumberOfDifferentProducts() {
        return numberOfDifferentProducts;
    }

    public void setNumberOfDifferentProducts(Integer numberOfDifferentProducts) {
        this.numberOfDifferentProducts = numberOfDifferentProducts;
    }

    public Double getAmountsOfProducts() {
        return amountsOfProducts;
    }

    public void setAmountsOfProducts(Double amountsOfProducts) {
        this.amountsOfProducts = amountsOfProducts;
    }

    public Double getTotalCostProducts() {
        return totalCostProducts;
    }

    public void setTotalCostProducts(Double totalCostProducts) {
        this.totalCostProducts = totalCostProducts;
    }

    public Double getTotalDeliverOrderCost() {
        return totalDeliverOrderCost;
    }

    public void setTotalDeliverOrderCost(Double totalDeliverOrderCost) {
        this.totalDeliverOrderCost = totalDeliverOrderCost;
    }

    public Double getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(Double totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }

    public void setKStoresVSubOrders(Map<StoreDTO, SubOrderDTO> KStoresVSubOrders) {
        this.KStoresVSubOrders = KStoresVSubOrders;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public Map<StoreDTO, SubOrderDTO> getKStoresVSubOrders() {
        return KStoresVSubOrders;
    }
}
