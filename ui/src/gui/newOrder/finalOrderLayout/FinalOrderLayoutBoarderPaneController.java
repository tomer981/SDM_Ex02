package gui.newOrder.finalOrderLayout;

import dto.CustomerDTO;
import dto.orderDTO.DiscountProductsDTO;
import dto.orderDTO.ProductOrderDTO;
import dto.orderDTO.StoreOrderDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController.INewOrder;
import gui.newOrder.productsSummery.ProductsSummeryTableViewController;
import gui.newOrder.storeSummery.StoreSummeryTableViewController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

public class FinalOrderLayoutBoarderPaneController {

    @FXML private Text totalProductsCost;
    @FXML private Text totalDeliveryCost;
    @FXML private Text totalCost;
    @FXML private SplitPane splitPane;


    private INewOrder engine;
    private Stage orderStage;
    private List<StoreOrderDTO> storesOrder;
    private CustomerDTO customer;

    private StoreSummeryTableViewController storeController;
    private ProductsSummeryTableViewController productController;

    private ReadOnlyObjectProperty<StoreOrderDTO> selectionStoreProperty;
    private LocalDate date;

    public void setData(INewOrder engine, Stage orderStage, List<StoreOrderDTO> storesOrder, CustomerDTO customer, LocalDate date) {
        this.engine = engine;
        this.orderStage = orderStage;
        this.storesOrder = storesOrder;
        this.customer = customer;
        this.date = date;
        storeController.setData(storesOrder, customer);
        setTotalValues();
    }

    @FXML
    private void initialize() {
        initializeStoreLayout();
        selectionStoreProperty = storeController.getSelectionStore();
        initializeProductsLayout();
        selectionStoreProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productController.setData(selectionStoreProperty.get().getProducts());
            }
        }));


    }

    private void initializeStoreLayout() {
        FXMLLoader loader = null;
        ScrollPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\storeSummery\\StoreSummeryTableViewGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storeController = loader.getController();
        splitPane.getItems().set(0, load);
    }

    private void initializeProductsLayout() {
        FXMLLoader loader = null;
        ScrollPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\productsSummery\\ProductsSummeryTableViewGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productController = loader.getController();
        splitPane.getItems().set(1, load);

    }

    private void setTotalValues() {
        Double totalDeliveriesCost = 0.0;
        Double totalProductsValue = 0.0;
        for (StoreOrderDTO store : storesOrder) {
            Double X =  Math.pow(store.getCordX() - customer.getCordX(),2);
            Double Y = Math.pow(store.getCordY() - customer.getCordY(),2);
            totalDeliveriesCost = totalDeliveriesCost + Math.pow(X+Y,0.5) * store.getPpk();

            List<ProductOrderDTO> products = store.getProducts();
            for (ProductOrderDTO product : products){
                totalProductsValue = totalProductsValue+ product.getPrice() * product.getPrice();
            }
        }
        totalProductsCost.setText("Total Products Cost: " + RoundDouble(totalProductsValue).toString());
        totalDeliveryCost.setText("Total Delivery Cost: " + RoundDouble(totalDeliveriesCost).toString());
        totalCost.setText("Total Cost: " + RoundDouble(totalProductsValue + totalDeliveriesCost).toString());
    }
    public static Double RoundDouble(Double number) {
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }

    @FXML
    void onCancelButton(ActionEvent event) {
        orderStage.close();
    }

    @FXML
    void onConfirmButton(ActionEvent event) {

    }


}
