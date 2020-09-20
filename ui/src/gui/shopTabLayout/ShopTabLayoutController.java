package gui.shopTabLayout;

import gui.productsInStoreTableView.ProductsInStoreTableViewController;
import gui.storeInfoTableView.StoreInfoTableViewController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static gui.mainMenuTabPane.MainMenuTabPaneController.IStoreInfo;

public class ShopTabLayoutController {

    @FXML private SplitPane LStoreRDiscountSplitPane;
    @FXML private AnchorPane storeInfoLocationAnchorPane;
    @FXML private AnchorPane DiscountLocationAnchorPane;
    @FXML private SplitPane LProductROrder;
    @FXML private AnchorPane productLocationAnchorPane;
    @FXML private AnchorPane orderLocationAnchorPane;


    IStoreInfo engine;

    public void setEngine(IStoreInfo storesInfo) {
        engine = storesInfo;
    }

    public ShopTabLayoutController(){

    }

    @FXML
    private void initialize(){
        initializeStoreInfoTabView();
        initializeStoreProductTableView();
        initializeOrderInfoTableView();

//        initializeDiscount();//Todo: complete name and structure
    }



    private void initializeStoreInfoTabView() {
        ScrollPane storeInfoTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\storeInfoTableView\\StoreInfoTableViewGui.fxml"));
            storeInfoTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StoreInfoTableViewController controller = loader.getController();
        LStoreRDiscountSplitPane.getItems().set(0,storeInfoTableView);
    }

    private void initializeStoreProductTableView() {
        ScrollPane storeProductTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\productsInStoreTableView\\ProductsInStoreTableViewGui.fxml"));
            storeProductTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductsInStoreTableViewController controller = loader.getController();
        LProductROrder.getItems().set(0,storeProductTableView);
    }




    private void initializeOrderInfoTableView() {

    }







}
