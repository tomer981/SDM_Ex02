package dto;

import java.util.Objects;

public class MarketProductDTO {
    private Integer id;
    private String name;
    private String category;
    private Integer numberOfStoreSell;
    private Double avgPrice;
    private Integer timeSold;
    private Double amount;


    @Override
    public String toString() {
        return id + " - " + name;
    }

    public void setAvgPrice(Double avgPrice) {
        this.avgPrice = avgPrice;
    }

    public MarketProductDTO(Integer id, String name, String category, Integer numberOfStoreSell, Double avgPrice, Integer timeSold) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.numberOfStoreSell = numberOfStoreSell;
        this.avgPrice = avgPrice;
        this.timeSold = timeSold;
        this.amount = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Integer getNumberOfStoreSell() {
        return numberOfStoreSell;
    }

    public Double getAvgPrice() {
        return avgPrice;
    }

    public Integer getTimeSold() {
        return timeSold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarketProductDTO)) return false;
        MarketProductDTO that = (MarketProductDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
