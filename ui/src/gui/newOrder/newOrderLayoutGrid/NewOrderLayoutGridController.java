package gui.newOrder.newOrderLayoutGrid;

import gui.newOrder.addOptionVBox.AddOptionVBoxController;
import gui.newOrder.productsTableView.ProductsTableViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NewOrderLayoutGridController {

    @FXML private Button nextButton;
    @FXML private GridPane newOrderGridPane;

//    private final SimpleBooleanProperty isDynamic;
    private ProductsTableViewController productsInSystemController;
    private ProductsTableViewController productsInOrderController;
    private AddOptionVBoxController addOptionVBoxController;

    private void initializeProductsTableView() {
        ScrollPane productsInSystem = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\productsTableView\\ProductsTableViewGui.fxml"));
            productsInSystem = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newOrderGridPane.add(productsInSystem,0,0);
        productsInSystemController = loader.getController();
    }

    private void initializeAddOptionVBox() {
        VBox addOptionVBox = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\addOptionVBox\\AddOptionVBoxGui.fxml"));
            addOptionVBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newOrderGridPane.add(addOptionVBox,1,0);
        addOptionVBoxController = loader.getController();
    }

    private void initializeOrderProductsTableView() {
        ScrollPane productsInOrder = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\productsTableView\\ProductsTableViewGui.fxml"));
            productsInOrder = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newOrderGridPane.add(productsInOrder,2,0);
        productsInOrderController = loader.getController();

    }

    @FXML
    private void initialize(){
        initializeAddOptionVBox();
        initializeProductsTableView();
        initializeOrderProductsTableView();
    }

    @FXML
    void OnNextButton(ActionEvent event) {

    }

    public NewOrderLayoutGridController(){
    }


    public void setDynamicTableView() {
        productsInSystemController.setDynamicTableView();
        productsInOrderController.setDynamicTableView();

    }
}
