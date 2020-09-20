package gui.mainMenuTabPane;


import dto.CustomerDTO;
import dto.ProductDTO;
import dto.StoreDTO;
import gui.customerInfoTableView.CustomerInfoTableViewController;
import gui.productsInMarketTableView.ProductsInMarketTableViewController;
import gui.shopTabLayout.ShopTabLayoutController;
import gui.showSelectionOfNewOrderHBox.ShowSelectionOfNewOrderHBoxController;
import gui.showSelectionOfOrderHBox.ShowSelectionOfOrderHBoxController;
import gui.updateProductHBox.UpdateProductHBoxController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import market.Market;
import xml.XmlSystemFactory;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;


public class MainMenuTabPaneController {

    @FXML private TextField DirDirectoryTextField;
    @FXML private ProgressBar AdvanceLoadProgressBar;
    @FXML private Button BrowseButton;
    @FXML private Tab MarketTab;
    @FXML private Tab CustomersTab;
    @FXML private Tab StoreInfoTab;
    @FXML private Tab MapTab;
    @FXML private GridPane MarketTabGrid;


    private Stage primaryStage;

    private Market market;

    private UpdateProductHBoxController updateProductController;
    private ProductsInMarketTableViewController MarketProductsController;
    private CustomerInfoTableViewController customerController;
    private ShowSelectionOfOrderHBoxController OrderSelectionController;
    private ShowSelectionOfNewOrderHBoxController newOrderSelectionController;
    private ShopTabLayoutController storeLayoutController;

    private IUpdateProduct updateProductInterface = new IUpdateProduct() {

        @Override
        public List<StoreDTO> getStoresDTO() {
            return market.getStoresDTO();
        }

        @Override
        public List<ProductDTO> getProductsDTO() {
            return market.getMarketProductsDTO();
        }

        @Override
        public List<ProductDTO> getStoreProductDTO(Integer StoreId) {
            return market.getStoreProductDTOByStoreId(StoreId);
        }

        @Override
        public boolean isProductInDiscountInStoreByStoreId(Integer storeId, Integer productId) {
            return market.isProductInDiscountInStoreByStoreId(storeId,productId);
        }

        @Override
        public void changeProductPrice(Integer storeId,Integer productId,Double Price) {
            market.changeProductPrice(storeId,productId,Price);
            reinitializeData();
        }

        @Override
        public void addProductToStore(Integer storeId, Integer productId, Double price) {
            market.addProductToStore(storeId,productId,price);
            reinitializeData();
        }

        @Override
        public void deleteProduct(Integer storeId, Integer productId) {
            market.deleteProduct(storeId,productId);
            reinitializeData();
        }
    };
    private Supplier<List<ProductDTO>> products = ()->market.getMarketProductsDTO();
    private INewOrderInterfaceHBox newOrderInterface = new INewOrderInterfaceHBox() {
        @Override
        public List<CustomerDTO> getCustomersDTO() {
            return market.getCustomersDTO();
        }

        @Override
        public List<ProductDTO> getProductsDTO() {
            return market.getMarketProductsDTO();
        }

        @Override
        public List<StoreDTO> getStoresDTO() {
            return market.getStoresDTO();
        }
    };
    private IStoreInfo StoresInfo = new IStoreInfo() {
        @Override
        public List<StoreDTO> getStoresDTO() {
            return market.getStoresDTO();
        }

        @Override
        public List<ProductDTO> getStoreProductsDTO(Integer storeId) {
            return market.getStoreProductDTOByStoreId(storeId);
        }
    };
    private Supplier<List<CustomerDTO>> customersData = ()->market.getCustomersDTO();

    public MainMenuTabPaneController(){
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    //start
    @FXML
    void BrowseButtonAction(ActionEvent event) throws JAXBException,RuntimeException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file","*.xml"));
        File Url = fileChooser.showOpenDialog(primaryStage);
        if (Url == null){
            return;
        }
        DirDirectoryTextField.setText(Url.getAbsolutePath());
        //TODO : Check end .xml and path exist
        XmlSystemFactory factory = new XmlSystemFactory();
        market = factory.createMarket(Url);
        initializeMarketTab();
        initializeStoreInfoTab();
        initializeCustomersTab();
    }


    //main
    private void initializeMarketTab() {
        initializeUpdateProductHBox();
        initializeShowNewOrderHBox();
        initializeShowSelectionOfOrderHBox();
        initializeShowProductsInMarketTableView();
    }
    private void initializeUpdateProductHBox() {
        HBox updateProductHBox = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\updateProductHBox\\UpdateProductHBoxGui.fxml"));
            updateProductHBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarketTabGrid.add(updateProductHBox,0,1);
        updateProductController = loader.getController();

        updateProductController.setEngine(updateProductInterface);
    }
    private void initializeShowProductsInMarketTableView() {
        ScrollPane productsInMarketTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\productsInMarketTableView\\ProductsInMarketTableViewGui.fxml"));
            productsInMarketTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarketTabGrid.add(productsInMarketTableView,0,4);
        MarketProductsController = loader.getController();

        MarketProductsController.setMarketProductData(products);
    }
    private void initializeShowSelectionOfOrderHBox() {
        HBox showSelectionOfOrderHBox = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\showSelectionOfOrderHBox\\ShowSelectionOfOrderHBoxGui.fxml"));
            showSelectionOfOrderHBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarketTabGrid.add(showSelectionOfOrderHBox,0,3);
        OrderSelectionController = loader.getController();

    }
    private void initializeShowNewOrderHBox() {
        HBox showNewOrderHBox = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\showSelectionOfNewOrderHBox\\ShowSelectionOfNewOrderHBoxGui.fxml"));//TODO load Static class for name
            showNewOrderHBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MarketTabGrid.add(showNewOrderHBox,0,2);
        newOrderSelectionController = loader.getController();

        newOrderSelectionController.setEngine(newOrderInterface);
    }

    //store
    private void initializeStoreInfoTab() {
        SplitPane storeInfo = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\shopTabLayout\\ShopTabLayoutGui.fxml"));
            storeInfo = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StoreInfoTab.setContent(storeInfo);
        storeLayoutController = loader.getController();
        storeLayoutController.setEngine(StoresInfo);
    }


    //Customer
    private void initializeCustomersTab()  {
        ScrollPane customerTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\customerInfoTableView\\CustomerInfoTableViewGui.fxml"));
            customerTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //rest tab
        CustomersTab.setContent(null);
        CustomersTab.setContent(customerTableView);
        //controller
        customerController = loader.getController();
        //set Data
        customerController.setCustomersData(customersData);
    }

    //map
    private void initializeMapTab() {
        int x = 5;
    }

    private void reinitializeData(){
        customerController.setCustomersData(customersData);
        storeLayoutController.setEngine(StoresInfo);
        MarketProductsController.setMarketProductData(products);
        updateProductController.setEngine(updateProductInterface);
        newOrderSelectionController.setEngine(newOrderInterface);

    }


    @FXML
    private void initialize(){
//        CustomersTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
//        StoreInfoTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
//        MapTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
//        MarketTab.selectedProperty().addListener((observable, oldValue, newValue) ->{ if(MarketTab.isSelected()) {initializeMarketTab();} });
//        StoreInfoTab.selectedProperty().addListener((observable, oldValue, newValue) -> { if(StoreInfoTab.isSelected()) {initializeStoreInfoTab();} });
//        CustomersTab.selectedProperty().addListener((observable, oldValue, newValue) -> { if(CustomersTab.isSelected()) {initializeCustomersTab();} });
//        MapTab.selectedProperty().addListener((observable, oldValue, newValue) -> { if(MapTab.isSelected()) {initializeMapTab();} });
    }


    public interface IUpdateProduct{
        List<StoreDTO> getStoresDTO();
        List<ProductDTO> getProductsDTO();
        List<ProductDTO> getStoreProductDTO(Integer storeId);
        boolean isProductInDiscountInStoreByStoreId(Integer storeId,Integer productId);
        void changeProductPrice(Integer storeId,Integer productId,Double Price);
        void addProductToStore(Integer storeId, Integer productId, Double price);
        void deleteProduct(Integer storeId,Integer productId);
    }

    public interface INewOrderInterfaceHBox {
        List<CustomerDTO> getCustomersDTO();
        List<ProductDTO> getProductsDTO();
        List<StoreDTO> getStoresDTO();
    }

    public interface IStoreInfo{
        List<StoreDTO> getStoresDTO();
        List<ProductDTO> getStoreProductsDTO(Integer storeId);
    }

}
