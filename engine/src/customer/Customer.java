package customer;

import dto.CustomerDTO;
import javafx.beans.property.SimpleDoubleProperty;
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
    private SimpleDoubleProperty avgPricePerProductsOrder = new SimpleDoubleProperty();
    private SimpleDoubleProperty avgPricePerDeliveryOrder = new SimpleDoubleProperty();

    public Customer(Integer id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public CustomerDTO getCustomerData(){
        return new CustomerDTO(
                id,
                name,
                location.getCordX(),
                location.getCordY(),
                orders.size(),
                avgPricePerDeliveryOrder.getValue(),
                avgPricePerProductsOrder.getValue());
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
}
