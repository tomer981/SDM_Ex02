package dto.orderDTO;

import dto.CustomerDTO;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private Integer id;
    private Date date;
    private Integer numberOfProduct;
    private Double productsCost;
    private Double deliveryCost;
    private Double totalCost;

    private List<StoreOrderDTO> stores;
    private CustomerDTO customer;

}
