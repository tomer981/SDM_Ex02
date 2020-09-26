package gui.shopTabLayout;

import dto.StoreDTO;
import gui.productsInStoreTableView.ProductsInStoreTableViewController;
import gui.showStoreOrder.ShowStoreOrderBorderPaneController;
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
    @FXML private SplitPane LProductROrder;


    private IStoreInfo engine;
    private StoreInfoTableViewController storeInfoController;
    private ProductsInStoreTableViewController storeProductsController;
    private BuyDiscountInStoreBoarderPaneController discountLayoutController;
    private ShowStoreOrderBorderPaneController orderInfoController;


    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;


    public void setEngine(IStoreInfo engine) {
        this.engine = engine;
        Supplier<List<StoreDTO>> storesDTO = engine::getStoresDTO;
        storeInfoController.setData(storesDTO.get());
    }

    public ShopTabLayoutController() {

    }

    @FXML
    private void initialize() throws IOException {
        initializeStoreInfoTabView();
        initializeStoreProductTableView();
        initializeOrderInfo();
        initializeDiscountView();

        selectionStoreProperty = storeInfoController.getSelectionStore();
        selectionStoreProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                storeProductsController.setData(engine.getStoreProductsDTO(selectionStoreProperty.get().getId()));
                discountLayoutController.setEngine(engine,selectionStoreProperty);
                orderInfoController.setData(engine,selectionStoreProperty);
            }
        }));
    }

    private void initializeDiscountView() {
        ScrollPane storeInfoTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(BuyDiscountInStoreBoarderPaneController.class.getResource("BuyDiscountInStoreBoarderPaneGui.fxml"));
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
            loader = new FXMLLoader(StoreInfoTableViewController.class.getResource("StoreInfoTableViewGui.fxml"));
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
            loader = new FXMLLoader(ProductsInStoreTableViewController.class.getResource("ProductsInStoreTableViewGui.fxml"));
            storeProductTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storeProductsController = loader.getController();
        LProductROrder.getItems().set(0, storeProductTableView);
    }

    private void initializeOrderInfo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\showStoreOrder\\ShowStoreOrderBorderPaneGui.fxml"));
        BorderPane discountTable = loader.load();
        orderInfoController = loader.getController();
        LProductROrder.getItems().set(1, discountTable);

    }
}
