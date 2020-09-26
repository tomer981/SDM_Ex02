package gui.shopTabLayout;

import dto.StoreDTO;
import gui.productsInStoreTableView.ProductsInStoreTableViewController;
import gui.storeInfo.layoutDiscounts.BuyDiscountInStoreBoarderPaneController;
import gui.storeInfoTableView.StoreInfoTableViewController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import static gui.mainMenuTabPane.MainMenuTabPaneController.IStoreInfo;

public class ShopTabLayoutController {

    @FXML private SplitPane LStoreRDiscountSplitPane;
    @FXML private AnchorPane storeInfoLocationAnchorPane;
    @FXML private AnchorPane DiscountLocationAnchorPane;
    @FXML private SplitPane LProductROrder;
    @FXML private AnchorPane productLocationAnchorPane;
    @FXML private AnchorPane orderLocationAnchorPane;

    private IStoreInfo engine;
    private StoreInfoTableViewController storeInfoController;
    private ProductsInStoreTableViewController storeProductsController;
    private BuyDiscountInStoreBoarderPaneController discountLayoutController;


    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;


    public void setEngine(IStoreInfo engine) {
        this.engine = engine;
        Supplier<List<StoreDTO>> storesDTO = ()->engine.getStoresDTO();
        storeInfoController.setData(storesDTO);
    }

    public ShopTabLayoutController() {

    }

    @FXML
    private void initialize() {
        initializeStoreInfoTabView();
        initializeStoreProductTableView();
        initializeOrderInfoTableView();
        initializeDiscountView();

        selectionStoreProperty = storeInfoController.getSelectionStore();
        selectionStoreProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                storeProductsController.setData(engine.getStoreProductsDTO(selectionStoreProperty.get().getId()));
            }
        }));
    }

    private void initializeDiscountView() {
        ScrollPane storeInfoTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\storeInfo\\layoutDiscounts\\BuyDiscountInStoreBoarderPaneGui.fxml"));
            storeInfoTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        discountLayoutController = loader.getController();
        LStoreRDiscountSplitPane.getItems().set(1, storeInfoTableView);
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
        storeInfoController = loader.getController();
        LStoreRDiscountSplitPane.getItems().set(0, storeInfoTableView);
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
        storeProductsController = loader.getController();
        LProductROrder.getItems().set(0, storeProductTableView);
    }


    private void initializeOrderInfoTableView() {

    }
}
