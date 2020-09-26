package dto.orderDTO;

import dto.CustomerDTO;
import dto.StoreDTO;

import java.time.LocalDate;
import java.util.*;

public class SubOrderDTO {
    private Integer id;
    private LocalDate date;
    private StoreDTO store;
    private CustomerDTO customer;
    private List <StoreProductOrderDTO> storeProductsDTO = new ArrayList();

    private Double distance = 0.0;
    private Double deliverCost = 0.0;

    private Integer numberOfDifferentProducts = 0;
    private Double amountsOfProducts = 0.0;
    private Double totalCostProducts = 0.0;

    private Double totalOrderCost = 0.0;

    private Map<OffersDiscountDTO,Integer> KOfferDiscountVTimeUse = new HashMap<>();

    private List<StoreProductOrderDTO> totalProductsToDisplay = new ArrayList<>();

    public List<StoreProductOrderDTO> getTotalProductsToDisplay() {
        return totalProductsToDisplay;
    }



    public void setTotalProductsToDisplay(List<StoreProductOrderDTO> totalProductsToDisplay) {
        this.totalProductsToDisplay = totalProductsToDisplay;
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

    public Double getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(Double totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }

    public SubOrderDTO(Integer id, LocalDate date, StoreDTO store, CustomerDTO customer) {
        this.id = id;
        this.date = date;
        this.store = store;
        this.customer = customer;
    }

    public Integer getNumberOfDifferentProducts() {
        return numberOfDifferentProducts;
    }

    public void setNumberOfDifferentProducts(Integer numberOfDifferentProducts) {
        this.numberOfDifferentProducts = numberOfDifferentProducts;
    }

    public Map<OffersDiscountDTO, Integer> getKOffersDiscountVTimeUse() {
        return KOfferDiscountVTimeUse;
    }

    public void setKOffersDiscountVTimeUse(Map<OffersDiscountDTO, Integer> KOfferDiscountVTimeUse) {
        this.KOfferDiscountVTimeUse = KOfferDiscountVTimeUse;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }
    public StoreDTO getStore() {
        return store;
    }


    public Double getDistance() {
        return distance;
    }
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getDeliverCost() {
        return deliverCost;
    }
    public void setDeliverCost(Double deliverCost) {
        this.deliverCost = deliverCost;
    }

    public List<StoreProductOrderDTO> getStoreProductsDTO() {
        return storeProductsDTO;
    }
    public void setStoreProductsDTO(List<StoreProductOrderDTO> storeProductsDTO) {
        this.storeProductsDTO = storeProductsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubOrderDTO)) return false;
        SubOrderDTO that = (SubOrderDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(store, that.store) &&
                Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, store, customer);
    }


}
