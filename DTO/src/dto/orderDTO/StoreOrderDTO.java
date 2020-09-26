package dto.orderDTO;

import java.util.*;

public class StoreOrderDTO {
    private Integer id;
    private String name;
    private Integer ppk;
    private Integer cordX;
    private Integer cordY;
    private Double deliveryEarned;

    private List<StoreProductOrderDTO> products = new ArrayList<>();
    private Map<OfferDiscountDTO,Integer> KOfferDiscountsVTimeUse;


    public Map<OfferDiscountDTO, Integer> getKOfferDiscountsVTimeUse() {
        return KOfferDiscountsVTimeUse;
    }

    public void setKOfferDiscountsVTimeUse(Map<OfferDiscountDTO, Integer> KOfferDiscountsVTimeUse) {
        this.KOfferDiscountsVTimeUse = KOfferDiscountsVTimeUse;
    }



    public StoreOrderDTO(Integer id, String name, Integer ppk, Double deliveryEarned, Integer cordX, Integer cordY) {
        this.id = id;
        this.name = name;
        this.ppk = ppk;
        this.deliveryEarned = deliveryEarned;
        this.cordX = cordX;
        this.cordY = cordY;
        KOfferDiscountsVTimeUse = new HashMap<>();
    }


    public void setProducts(List<StoreProductOrderDTO> products) {
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPpk() {
        return ppk;
    }

    public Double getDeliveryEarned() {
        return deliveryEarned;
    }

    public Integer getCordX() {
        return cordX;
    }

    public Integer getCordY() {
        return cordY;
    }

    public List<StoreProductOrderDTO> getProducts() {
        return products;
    }


    @Override
    public String toString() {
        return id + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreOrderDTO)) return false;
        StoreOrderDTO that = (StoreOrderDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
