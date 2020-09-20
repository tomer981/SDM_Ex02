package gui.customerInfoTableView;

import dto.CustomerDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Supplier;

public class CustomerInfoTableViewController {

    @FXML private TableView<CustomerDTO> CustomerTableView;
    @FXML private TableColumn<CustomerDTO, Integer> CustomersIdTableColumn;
    @FXML private TableColumn<CustomerDTO, String> CustomersNameTableColumn;
    @FXML private TableColumn<CustomerDTO, Integer> CustomersLocationsXTableColumn;
    @FXML private TableColumn<CustomerDTO, Integer> CustomersLocationsYTableColumn;
    @FXML private TableColumn<CustomerDTO, Integer> CustomersTotalOrdersTableColumn;
    @FXML private TableColumn<CustomerDTO, Double> CustomersAvgPricePerDeliveryTableColumn;
    @FXML private TableColumn<CustomerDTO, Double> CustomersAvgPricePerOrderTableColumn;

    List<CustomerDTO> customers;

    public void setCustomersData(Supplier<List<CustomerDTO>> CustomersData){
        customers = CustomersData.get();
        CustomerTableView.setItems(FXCollections.observableArrayList(customers));

    }

    @FXML
    private void initialize(){
        CustomersIdTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        CustomersNameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        CustomersLocationsXTableColumn.setCellValueFactory(new PropertyValueFactory("cordX"));
        CustomersLocationsYTableColumn.setCellValueFactory(new PropertyValueFactory("cordY"));
        CustomersTotalOrdersTableColumn.setCellValueFactory(new PropertyValueFactory("totalOrders"));
        CustomersAvgPricePerDeliveryTableColumn.setCellValueFactory(new PropertyValueFactory("avgPricePerDelivery"));
        CustomersAvgPricePerOrderTableColumn.setCellValueFactory(new PropertyValueFactory("avgPricePerOrder"));
    }
}
