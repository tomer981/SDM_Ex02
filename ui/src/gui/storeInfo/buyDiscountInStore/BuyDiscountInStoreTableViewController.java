package gui.storeInfo.buyDiscountInStore;

import dto.orderDTO.DiscountProductsDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class BuyDiscountInStoreTableViewController {

    @FXML private TableView<DiscountProductsDTO> discountsTableView;
    @FXML private TableColumn<?, ?> nameDiscount;
    @FXML private TableColumn<?, ?> idProduct;
    @FXML private TableColumn<?, ?> nameProduct;
    @FXML private TableColumn<?, ?> amount;

    private List<DiscountProductsDTO> discounts;

    public void setDiscounts(List<DiscountProductsDTO> discounts) {
        this.discounts = discounts;
        discountsTableView.setItems(FXCollections.observableArrayList(this.discounts));
        discountsTableView.refresh();
    }

    public ReadOnlyObjectProperty<DiscountProductsDTO> getPropertySelectionDiscount(){
        return discountsTableView.getSelectionModel().selectedItemProperty();
    }

    @FXML
    private void initialize(){
        nameDiscount.setCellValueFactory(new PropertyValueFactory("nameDiscount"));
        idProduct.setCellValueFactory(new PropertyValueFactory("productId"));
        nameProduct.setCellValueFactory(new PropertyValueFactory("nameProduct"));
        amount.setCellValueFactory(new PropertyValueFactory("quantityNeeded"));
    }

    public void clearSelection(){
        discountsTableView.getSelectionModel().clearSelection();
    }
}
