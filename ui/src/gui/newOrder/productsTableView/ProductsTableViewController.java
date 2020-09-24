package gui.newOrder.productsTableView;

import dto.ProductDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Supplier;

public class ProductsTableViewController {

    @FXML private TableView<ProductDTO> productsTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> price;
    @FXML private TableColumn<?, ?> amount;


    private final SimpleBooleanProperty isDynamic;


    private List<ProductDTO> products;

    public ProductsTableViewController(){
        isDynamic = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        category.setCellValueFactory(new PropertyValueFactory("category"));
        price.setCellValueFactory(new PropertyValueFactory("avgPrice"));
        amount.setCellValueFactory(new PropertyValueFactory("amount"));


    }

    public void setDynamicTableView(){
        isDynamic.setValue(true);
        productsTableView.getColumns().remove(price);
    }
    public void setSystemProductTableView(){
        productsTableView.getColumns().remove(amount);
    }



    public void setProducts(Supplier<List<ProductDTO>> products) {
        this.products = products.get();
        productsTableView.setItems(FXCollections.observableArrayList(this.products));
        clearSelection();
        productsTableView.refresh();
    }

    public Supplier<TableView<ProductDTO>> getProductsTableView(){
        Supplier<TableView<ProductDTO>> products = ()->productsTableView;
        return products;
    }


    public ReadOnlyObjectProperty<ProductDTO> getPropertySelection(){
        return productsTableView.getSelectionModel().selectedItemProperty();
    }

    public void clearSelection() {
        productsTableView.getSelectionModel().clearSelection();
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void bindIsProductsInOrderOfProperty(SimpleBooleanProperty productInOrder) {
        productsTableView.itemsProperty().addListener((observer, oldValue, newValue)-> {
            if (productsTableView.getItems().size()>0){
                productInOrder.setValue(true);
            }
            else{
                productInOrder.setValue(false);
            }
        });
    }
}
