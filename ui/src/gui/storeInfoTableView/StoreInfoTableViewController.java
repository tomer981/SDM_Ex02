package gui.storeInfoTableView;

import dto.StoreDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Supplier;

import static gui.productsInMarketTableView.ProductsInMarketTableViewController.RoundDouble;

public class StoreInfoTableViewController {

    @FXML private TableView<StoreDTO> storesTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> ppk;
    @FXML private TableColumn<?, ?> deliveryEarned;
    @FXML private TableColumn<?, ?> orderNumber;

    private List<StoreDTO> stores;

    public void setData(List<StoreDTO> storesDTO) {
        stores = storesDTO;
        stores.forEach(storeDTO -> storeDTO.setDeliveryEarned(RoundDouble(storeDTO.getDeliveryEarned())));
        storesTableView.setItems(FXCollections.observableArrayList(stores));
        storesTableView.refresh();
    }

    public ReadOnlyObjectProperty<StoreDTO> getSelectionStore() {
        return storesTableView.getSelectionModel().selectedItemProperty();
    }

    @FXML
    private void initialize(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        ppk.setCellValueFactory(new PropertyValueFactory("ppk"));
        deliveryEarned.setCellValueFactory(new PropertyValueFactory("deliveryEarned"));
        orderNumber.setCellValueFactory(new PropertyValueFactory("numberOfOrders"));
    }

}
