package gui.productsInMarketTableView;

import dto.ProductDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Supplier;

public class ProductsInMarketTableViewController {

    @FXML private TableView<ProductDTO> MarketProductsTableView;
    @FXML private TableColumn<?, ?> idTableColumn;
    @FXML private TableColumn<?, ?> nameTableColumn;
    @FXML private TableColumn<?, ?> categoryTableColumn;
    @FXML private TableColumn<?, ?> storeSellTableColumn;
    @FXML private TableColumn<?, ?> avgPriceTableColumn;
    @FXML private TableColumn<?, ?> timeSoldTableColumn;

    List<ProductDTO> marketProducts;

    public void setMarketProductData(Supplier<List<ProductDTO>> marketProductsDTO) {
        marketProducts = marketProductsDTO.get();
        marketProducts.forEach(marketProduct->marketProduct.setAvgPrice(RoundDouble(marketProduct.getAvgPrice())));
        MarketProductsTableView.setItems(FXCollections.observableArrayList(marketProducts));
    }

    public static Double RoundDouble(Double number) {
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }

    @FXML
    private void initialize(){
        idTableColumn.setCellValueFactory(new PropertyValueFactory("id"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        categoryTableColumn.setCellValueFactory(new PropertyValueFactory("category"));
        storeSellTableColumn.setCellValueFactory(new PropertyValueFactory("numberOfStoreSell"));
        avgPriceTableColumn.setCellValueFactory(new PropertyValueFactory("avgPrice"));
        timeSoldTableColumn.setCellValueFactory(new PropertyValueFactory("timeSold"));

    }
}
