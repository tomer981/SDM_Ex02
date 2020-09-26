package gui.mainMenuTabPane;


import dto.CustomerDTO;
import dto.MarketProductDTO;
import dto.StoreDTO;
import dto.orderDTO.*;
import gui.customerInfoTableView.CustomerInfoTableViewController;
import gui.productsInMarketTableView.ProductsInMarketTableViewController;
import gui.shopTabLayout.ShopTabLayoutController;
import gui.showSelectionOfNewOrderHBox.ShowSelectionOfNewOrderHBoxController;
import gui.showSelectionOfOrderHBox.ShowSelectionOfOrderHBoxController;
import gui.updateProductHBox.UpdateProductHBoxController;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import market.Market;
import xml.XmlSystemFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
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
    @FXML private Text loadingStatus;


    private final XmlSystemFactory factory;
    private Stage primaryStage;
    private Market market;

    private UpdateProductHBoxController updateProductController;
    private ProductsInMarketTableViewController MarketProductsController;
    private CustomerInfoTableViewController customerController;
    private ShowSelectionOfOrderHBoxController OrderSelectionController;
    private ShowSelectionOfNewOrderHBoxController newOrderSelectionController;
    private ShopTabLayoutController storeLayoutController;

    private final IUpdateProduct updateProductInterface = new IUpdateProduct() {

        @Override
        public List<StoreDTO> getStoresDTO() {
            return market.getStoresDTO();
        }

        @Override
        public List<MarketProductDTO> getProductsDTO() {
            return market.getMarketProductsDTO();
        }

        @Override
        public List<MarketProductDTO> getStoreProductDTO(Integer StoreId) {
            return market.getStoreProductDTOByStoreId(StoreId);
        }

        @Override
        public boolean isProductInDiscountInStoreByStoreId(Integer storeId, Integer productId) {
            return market.isProductInDiscountInStoreByStoreId(storeId, productId);
        }

        @Override
        public void changeProductPrice(Integer storeId, Integer productId, Double Price) {
            market.changeProductPrice(storeId, productId, Price);
            reinitializeData();
        }

        @Override
        public void addProductToStore(Integer storeId, Integer productId, Double price) {
            market.addProductToStore(storeId, productId, price);
            reinitializeData();
        }

        @Override
        public void deleteProduct(Integer storeId, Integer productId) {
            market.deleteProduct(storeId, productId);
            reinitializeData();
        }
    };
    private final Supplier<List<MarketProductDTO>> products = () -> market.getMarketProductsDTO();
    private final INewOrder newOrderInterface = new INewOrder() {
        @Override
        public List<CustomerDTO> getCustomersDTO() {
            return market.getCustomersDTO();
        }

        @Override
        public List<MarketProductDTO> getProductsDTO() {
            return market.getMarketProductsDTO();
        }

        @Override
        public List<StoreDTO> getStoresDTO() {
            return market.getStoresDTO();
        }

        @Override
        public List<MarketProductDTO> getStoreProductsDTO(Integer storeId) {
            return market.getStoreProductDTOByStoreId(storeId);
        }

        @Override
        public OrderDTO findMinCostOrder(OrderDTO orderDTO, List<StoreProductOrderDTO> OrderProductsDTO){
            return market.findMinCostOrder(orderDTO, OrderProductsDTO);
        }

        @Override
        public Double getDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
            return market.getDistance(x1, y1, x2, y2);
        }

        @Override
        public OrderDTO getStoreOrderByStoreId(Integer storeId,OrderDTO orderDTO ,List<StoreProductOrderDTO> OrderProducts){
            return market.getStoreOrderByStoreId(storeId, orderDTO,OrderProducts);
        }

        @Override
        public List<DiscountProductsDTO> getDiscountsByStoreId(Integer storeId) {
            return market.getDiscountsByStoreId(storeId);
        }

        @Override
        public OffersDiscountDTO getOffersDiscount(Integer id, DiscountProductsDTO discountSelected) {
            return market.getOffersDiscount(id,discountSelected);
        }

        @Override
        public void addOrder(OrderDTO orderDTO) {
            market.addOrder(orderDTO);
            reinitializeData();
        }
    };
    private IStoreInfo StoresInfo = new IStoreInfo() {
        @Override
        public List<StoreDTO> getStoresDTO() {
            return market.getStoresDTO();
        }

        @Override
        public List<MarketProductDTO> getStoreProductsDTO(Integer storeId) {
            return market.getStoreProductDTOByStoreId(storeId);
        }
    };
    private final Supplier<List<CustomerDTO>> customersData = () -> market.getCustomersDTO();

    private final Executor executor = Executors.newCachedThreadPool();


    public MainMenuTabPaneController() {
        factory = new XmlSystemFactory();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    //start
    @FXML
    void BrowseButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML file", "*.xml"));
        File chosenFile = fileChooser.showOpenDialog(primaryStage);

        //TODO : Check end .xml and path exist
        if (chosenFile == null) {
            return;
        }

        DirDirectoryTextField.setText(chosenFile.getAbsolutePath());

        Task<Market> loadTask = new MarketLoaderTask(factory, chosenFile);
        AdvanceLoadProgressBar.progressProperty().bind(loadTask.progressProperty());
        loadingStatus.textProperty().bind(loadTask.messageProperty());
        loadTask.valueProperty().addListener((task, oldValue, newValue) -> {
            // After file loaded successfully
            this.market = newValue;
            initializeMarketTab();
            initializeStoreInfoTab();
            initializeMapTab();
            initializeCustomersTab();

        });

        loadTask.exceptionProperty().addListener((task, oldValue, newValue) -> {
            // When file failed to load
            newValue.printStackTrace();
        });
        executor.execute(loadTask);

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
            loader = new FXMLLoader(getClass().getResource("../../gui/updateProductHBox/UpdateProductHBoxGui.fxml"));
            updateProductHBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarketTabGrid.add(updateProductHBox, 0, 1);
        updateProductController = loader.getController();

        updateProductController.setEngine(updateProductInterface);
    }

    private void initializeShowProductsInMarketTableView() {
        ScrollPane productsInMarketTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("../../gui/productsInMarketTableView/ProductsInMarketTableViewGui.fxml"));
            productsInMarketTableView = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarketTabGrid.add(productsInMarketTableView, 0, 4);
        MarketProductsController = loader.getController();

        MarketProductsController.setMarketProductData(products);
    }

    private void initializeShowSelectionOfOrderHBox() {
        HBox showSelectionOfOrderHBox = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("../../gui/showSelectionOfOrderHBox/ShowSelectionOfOrderHBoxGui.fxml"));
            showSelectionOfOrderHBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MarketTabGrid.add(showSelectionOfOrderHBox, 0, 3);
        OrderSelectionController = loader.getController();


    }

    private void initializeShowNewOrderHBox() {
        HBox showNewOrderHBox = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("../../gui/showSelectionOfNewOrderHBox/ShowSelectionOfNewOrderHBoxGui.fxml"));//TODO load Static class for name
            showNewOrderHBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MarketTabGrid.add(showNewOrderHBox, 0, 2);
        newOrderSelectionController = loader.getController();
        newOrderSelectionController.setEngine(newOrderInterface);
    }

    //store
    private void initializeStoreInfoTab() {
        SplitPane storeInfo = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("../../gui/shopTabLayout/ShopTabLayoutGui.fxml"));
            storeInfo = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StoreInfoTab.setContent(storeInfo);
        storeLayoutController = loader.getController();
        storeLayoutController.setEngine(StoresInfo);
    }


    //Customer
    private void initializeCustomersTab() {
        ScrollPane customerTableView = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("../../gui/customerInfoTableView/CustomerInfoTableViewGui.fxml"));
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
        IMap mapEngine = new IMap() {
            @Override
            public List<CustomerDTO> getCustomersDTO() {
                return market.getCustomersDTO();
            }

            @Override
            public List<StoreDTO> getStoresDTO() {
                return market.getStoresDTO();
            }
        };
        //TODO: Aviad in here
    }

    //data
    private void reinitializeData() {
        updateProductController.setEngine(updateProductInterface);
        customerController.setCustomersData(customersData);
        storeLayoutController.setEngine(StoresInfo);
        MarketProductsController.setMarketProductData(products);
        newOrderSelectionController.setEngine(newOrderInterface);
//        OrderSelectionController.setData();
    }


    @FXML
    private void initialize() {
        CustomersTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
        StoreInfoTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
        MapTab.disableProperty().bind(DirDirectoryTextField.textProperty().isEmpty());
    }


    public interface IUpdateProduct {
        List<StoreDTO> getStoresDTO();

        List<MarketProductDTO> getProductsDTO();

        List<MarketProductDTO> getStoreProductDTO(Integer storeId);

        boolean isProductInDiscountInStoreByStoreId(Integer storeId, Integer productId);

        void changeProductPrice(Integer storeId, Integer productId, Double Price);

        void addProductToStore(Integer storeId, Integer productId, Double price);

        void deleteProduct(Integer storeId, Integer productId);
    }

    public interface INewOrder {
        List<CustomerDTO> getCustomersDTO();

        List<MarketProductDTO> getProductsDTO();

        List<StoreDTO> getStoresDTO();

        List<MarketProductDTO> getStoreProductsDTO(Integer storeId);

        OrderDTO findMinCostOrder(OrderDTO orderDTO, List<StoreProductOrderDTO> OrderProductsDTO);

        Double getDistance(Integer x1, Integer y1, Integer x2, Integer y2);

        OrderDTO getStoreOrderByStoreId(Integer storeId,OrderDTO orderDTO ,List<StoreProductOrderDTO> OrderProducts);

        List<DiscountProductsDTO> getDiscountsByStoreId(Integer storeId);

        OffersDiscountDTO getOffersDiscount(Integer id, DiscountProductsDTO discountSelected);

        void addOrder(OrderDTO orderDTO);
    }

    public interface IStoreInfo {
        List<StoreDTO> getStoresDTO();

        List<MarketProductDTO> getStoreProductsDTO(Integer storeId);
    }

    public interface IMap {
        public List<CustomerDTO> getCustomersDTO();

        public List<StoreDTO> getStoresDTO();
        //TODO: Aviad - tell me if you need additional method
    }


}
