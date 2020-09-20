package dto;

import java.util.Date;

public class StoreOrderDTO {
    private Integer id;
    private Date date;
    private Integer productsSold;
    private Double costProducts;
    private Double deliveryCost;
    private Double totalCost;

    public StoreOrderDTO(Integer id, Date date, Integer productsSold, Double costProducts, Double deliveryCost) {
        this.id = id;
        this.date = date;
        this.productsSold = productsSold;
        this.costProducts = costProducts;
        this.deliveryCost = deliveryCost;
        this.totalCost = this.costProducts + this.deliveryCost;
    }
}
