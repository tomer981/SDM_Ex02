package customer;

import dto.CustomerDTO;
import location.Location;
import order.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private Integer id;
    private String name;
    private Location location;
    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getTotalPriceOfProductsOrders() {
        return TotalPriceOfProductsOrders;
    }

    public void setTotalPriceOfProductsOrders(Double totalPriceOfProductsOrders) {
        TotalPriceOfProductsOrders = totalPriceOfProductsOrders;
    }

    public Double getTotalPriceOfDeliveryOrders() {
        return TotalPriceOfDeliveryOrders;
    }

    public void setTotalPriceOfDeliveryOrders(Double totalPriceOfDeliveryOrders) {
        TotalPriceOfDeliveryOrders = totalPriceOfDeliveryOrders;
    }


    private Double TotalPriceOfProductsOrders;
    private Double TotalPriceOfDeliveryOrders;

    public Customer(Integer id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        TotalPriceOfDeliveryOrders = 0.0;
        TotalPriceOfProductsOrders= 0.0;

    }


    public CustomerDTO getCustomerData(){
        return new CustomerDTO(
                id,
                name,
                location.getCordX(),
                location.getCordY(),
                orders.size(),
                getAvgPricePerDeliveryOrder(),
                getAvgPricePerOrder());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Double getAvgPricePerOrder() {
        if (orders.size() == 0){return  0.0;}
        return TotalPriceOfProductsOrders / orders.size();
    }

    public Double getAvgPricePerDeliveryOrder() {
        if (orders.size() == 0){return  0.0;}
        return TotalPriceOfDeliveryOrders / orders.size();
    }


    @Override
    public String toString() {
        return id + " - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
