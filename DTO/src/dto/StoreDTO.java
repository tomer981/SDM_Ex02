package dto;

import java.util.Objects;

public class StoreDTO {
    private Integer id;
    private String name;
    private Integer ppk;
    private Integer cordX;
    private Integer cordY;
    private Double deliveryEarned;
    private Integer numberOfProductsSelling;


    public StoreDTO(Integer id, String name, Integer ppk, Double deliveryEarned, Integer cordX, Integer cordY, Integer numberOfProductsSelling) {
        this.id = id;
        this.name = name;
        this.ppk = ppk;
        this.deliveryEarned = deliveryEarned;
        this.cordX = cordX;
        this.cordY = cordY;
        this.numberOfProductsSelling = numberOfProductsSelling;
    }



    public Integer getCordX() {
        return cordX;
    }

    public Integer getCordY() {
        return cordY;
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

    public Integer getNumberOfProductsSelling() {
        return numberOfProductsSelling;
    }

    @Override
    public String toString() {
        return id.toString() + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreDTO)) return false;
        StoreDTO storeDTO = (StoreDTO) o;
        return Objects.equals(id, storeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
