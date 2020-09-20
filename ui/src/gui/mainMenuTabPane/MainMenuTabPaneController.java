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
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import market.Market;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import static market.Market.initializeMarket;


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

    public MainMenuTabPaneController(){
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void initializeMapTab() {
        int x = 5;
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
        market = initializeMarket(Url);
        initializeMarketTab();
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
        if (MarketTabGrid.getChildren().size() == MarketTabGrid.getRowConstraints().size() ){
            MarketTabGrid.getChildren().remove(1);
        }
        MarketTabGrid.add(updateProductHBox,0,1);
        UpdateProductHBoxController controller = loader.getController();
        IUpdateProduct updateProductInterface = new IUpdateProduct() {

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
            public void changeProductPrice(Integer storeId, Integer productId, Double Price) {
                market.changeProductPrice(storeId,productId,Price);
                initializeMarketTab();
            }

            @Override
            public void addProductToStore(Integer storeId, Integer productId, Double price) {
                market.addProductToStore(storeId,productId,price);
                initializeMarketTab();
            }

            @Override
            public void deleteProduct(Integer storeId, Integer productId, Double price) {
                market.deleteProduct(storeId,productId);
            }
        };

        controller.setEngine(updateProductInterface);
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
        ProductsInMarketTableViewController controller = loader.getController();
        Supplier<List<ProductDTO>> products = ()->market.getMarketProductsDTO();
        controller.setMarketProductData(products);
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
        ShowSelectionOfOrderHBoxController controller = loader.getController();

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
        ShowSelectionOfNewOrderHBoxController controller = loader.getController();
        INewOrderInterfaceHBox newOrderInterface = new INewOrderInterfaceHBox() {
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
        controller.setEngine(newOrderInterface);
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
        IStoreInfo StoresInfo = new IStoreInfo() {
            @Override
            public List<StoreDTO> getStoresDTO() {
                return market.getStoresDTO();
            }

            @Override
            public List<ProductDTO> getStoreProductsDTO(Integer storeId) {
                return market.getStoreProductDTOByStoreId(storeId);
            }
        };
        ShopTabLayoutController controller = loader.getController();
        controller.setEngine(StoresInfo);
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
        CustomerInfoTableViewController controller = loader.getController();
        //set Data
        Supplier<List<CustomerDTO>> customersData = ()->market.getCustomersDTO();
        controller.setCustomersData(customersData);
    }


    @FXML
    private void initialize(){
        CustomersTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
        StoreInfoTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
        MapTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
        MarketTab.selectedProperty().addListener((observable, oldValue, newValue) ->{ if(MarketTab.isSelected()) {initializeMarketTab();} });
        StoreInfoTab.selectedProperty().addListener((observable, oldValue, newValue) -> { if(StoreInfoTab.isSelected()) {initializeStoreInfoTab();} });
        CustomersTab.selectedProperty().addListener((observable, oldValue, newValue) -> { if(CustomersTab.isSelected()) {initializeCustomersTab();} });
        MapTab.selectedProperty().addListener((observable, oldValue, newValue) -> { if(MapTab.isSelected()) {initializeMapTab();} });
    }


    public interface IUpdateProduct{
        List<StoreDTO> getStoresDTO();
        List<ProductDTO> getProductsDTO();
        List<ProductDTO> getStoreProductDTO(Integer storeId);
        boolean isProductInDiscountInStoreByStoreId(Integer storeId,Integer productId);
        void changeProductPrice(Integer storeId,Integer productId,Double Price);
        void addProductToStore(Integer storeId, Integer productId, Double price);
        public void deleteProduct(Integer storeId,Integer productId, Double price);
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
