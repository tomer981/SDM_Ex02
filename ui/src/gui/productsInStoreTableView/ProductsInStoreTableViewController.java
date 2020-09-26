package gui.productsInStoreTableView;

import dto.CustomerDTO;
import dto.MarketProductDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import static gui.newOrder.finalOrderLayout.FinalOrderLayoutBoarderPaneController.RoundDouble;

public class ProductsInStoreTableViewController {

    @FXML private TableView<MarketProductDTO> storeProductsTableView;
    @FXML private TableColumn<?, ?> idTableColumn;
    @FXML private TableColumn<?, ?> nameTableColumn;
    @FXML private TableColumn<?, ?> categoryTableColumn;
    @FXML private TableColumn<?, ?> costTableColumn;
    @FXML private TableColumn<?, ?> totalSoldTableColumn;

    private List<MarketProductDTO> products;

    public void setData(List<MarketProductDTO> storeProductsDTO) {
        products = storeProductsDTO;
        products.forEach(productDTO -> productDTO.setAvgPrice(RoundDouble(productDTO.getAvgPrice())));
        storeProductsTableView.setItems(FXCollections.observableArrayList(products));
        storeProductsTableView.refresh();

    }

    @FXML
    private void initialize(){
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        categoryTableColumn.setCellValueFactory(new PropertyValueFactory("category"));
        costTableColumn.setCellValueFactory(new PropertyValueFactory("avgPrice"));
        totalSoldTableColumn.setCellValueFactory(new PropertyValueFactory("timeSold"));
    }
}
