package gui.newOrder.productsTableView;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProductsTableViewController {

    @FXML private TableView<?> productsTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> price;

    private final SimpleBooleanProperty isDynamic;

    public ProductsTableViewController(){
        isDynamic = new SimpleBooleanProperty(false);
    }

    public void setDynamicTableView(){
        isDynamic.setValue(true);
        productsTableView.getColumns().remove(price);
    }

}
