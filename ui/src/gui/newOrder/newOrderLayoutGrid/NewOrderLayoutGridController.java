package gui.newOrder.newOrderLayoutGrid;

import dto.CustomerDTO;
import dto.orderDTO.ProductOrderDTO;
import dto.ProductDTO;
import dto.orderDTO.StoreOrderDTO;
import gui.newOrder.discountsLayout.DiscountLayoutBorderPaneController;
import gui.newOrder.dynamicLayoutBoarderPane.DynamicLayoutBoarderPaneController;
import gui.newOrder.productsTableView.ProductsTableViewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static gui.mainMenuTabPane.MainMenuTabPaneController.*;

public class NewOrderLayoutGridController {

    @FXML private GridPane newOrderGridPane;
    @FXML private Button nextButton;
    @FXML private Text amountText;
    @FXML private TextField amountTextField;
    @FXML private Button addButton;
    @FXML private Button removeButton;


    private INewOrder engine;
    private Integer storeId = -1;
    private CustomerDTO customer;
    private Stage orderStage;

    private final SimpleBooleanProperty isProductsInOrder;

    private ProductsTableViewController productsInSystemController;
    private ProductsTableViewController productsInOrderController;

    private ReadOnlyObjectProperty<ProductDTO> propertySystemSelection;
    private ReadOnlyObjectProperty<ProductDTO> propertyOrderSelection;

    private Set<ProductDTO> orderProducts = new HashSet<>();
    private LocalDate date;


    public NewOrderLayoutGridController() {
        isProductsInOrder = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        initializeProductsTableView();
        initializeOrderProductsTableView();

        addButton.disableProperty().bind(Bindings.or(propertySystemSelection.isNull(),amountTextField.textProperty().isEmpty()));
        removeButton.disableProperty().bind(propertyOrderSelection.isNull());
        nextButton.disableProperty().bind(isProductsInOrder.not());
        amountTextField.disableProperty().bind(propertySystemSelection.isNull());
        amountTextField.focusedProperty().addListener((observer, oldValue, newValue)->{
            if (!newValue && !amountTextField.getText().equals("") && isNumeric(amountTextField.getText())){
                String productCategory = propertySystemSelection.get().getCategory().toLowerCase();
                if (productCategory.equals("weight")){
                    if(!isNumeric(amountTextField.getText())){
                        amountTextField.setText("");
                    }
                }
                else if (productCategory.equals("quantity")){
                    double price = Double.parseDouble(amountTextField.getText());
                    if ((int) price != price){
                        amountTextField.setText("");
                    }
                }
                else {
                    amountTextField.setText("");
                }
            }
            else {
                amountTextField.setText("");
            }
        });

        productsInOrderController.bindIsProductsInOrderOfProperty(isProductsInOrder);
    }
    private void initializeProductsTableView() {
        ScrollPane productsInSystem = null;
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\productsTableView\\ProductsTableViewGui.fxml"));
            productsInSystem = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        newOrderGridPane.add(productsInSystem, 0, 0);
        productsInSystemController = loader.getController();

        propertySystemSelection = productsInSystemController.getPropertySelection();

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
        newOrderGridPane.add(productsInOrder, 2, 0);
        productsInOrderController = loader.getController();

        propertyOrderSelection = productsInOrderController.getPropertySelection();
    }


    public void setEngine(INewOrder engine, Integer storeId, CustomerDTO customer, Stage orderStage, LocalDate date) {
        this.storeId = storeId;
        Supplier<List<ProductDTO>> products = () -> this.engine.getStoreProductsDTO(storeId);
        setEngine(engine, products,customer,orderStage,date);
    }
    public void setEngine(INewOrder engine, CustomerDTO customer,Stage orderStage,LocalDate date) {
        Supplier<List<ProductDTO>> products = () -> this.engine.getProductsDTO();
        setEngine(engine, products,customer,orderStage,date);

        productsInSystemController.setDynamicTableView();
        productsInOrderController.setDynamicTableView();


    }
    private void setEngine(INewOrder engine, Supplier<List<ProductDTO>> products,CustomerDTO customer,Stage orderStage,LocalDate date){
        this.orderStage = orderStage;
        this.engine = engine;
        this.customer = customer;
        this.date = date;
        productsInSystemController.setProducts(products);
        productsInSystemController.setSystemProductTableView();

        propertySystemSelection.addListener((observer, oldValue, newValue)->{
            if (newValue !=null){
                if (newValue.getCategory().toLowerCase().equals("quantity")){
                    amountText.textProperty().setValue("Unit:");
                }
                else{
                    amountText.textProperty().setValue("Amount:");
                }
                amountTextField.textProperty().setValue("");
            }
            else {
                amountText.textProperty().setValue("Amount/Unit:");
            }
        });
    }



    private ProductOrderDTO createOrderProduct(ProductDTO product) {
        return new ProductOrderDTO(
                product.getId(),
                product.getName(),
                product.getCategory(),
                0.0,
                product.getAmount(),
                false
        );
    }
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @FXML
    void OnAddButton(ActionEvent event) {
        propertySystemSelection.getValue().setAmount(Double.parseDouble(amountTextField.getText()) + propertySystemSelection.getValue().getAmount());
        orderProducts.add(propertySystemSelection.getValue());
        Supplier<List<ProductDTO>> setProduct = ()-> new ArrayList<>(orderProducts);
        productsInOrderController.setProducts(setProduct);
        productsInSystemController.clearSelection();
        amountTextField.setText("");
    }

    @FXML
    void onRemoveButton(ActionEvent event) {
        propertyOrderSelection.getValue().setAmount(0.0);
        orderProducts.remove(propertyOrderSelection.getValue());
        Supplier<List<ProductDTO>> setProduct = ()-> new ArrayList<>(orderProducts);
        productsInOrderController.setProducts(setProduct);
    }

    @FXML
    void OnNextButton(ActionEvent event) {

        List<ProductDTO> products = productsInOrderController.getProducts();
        List<ProductOrderDTO> OrderProducts = new ArrayList<>();
        products.forEach(product->OrderProducts.add(createOrderProduct(product)));
        Pane load = null;

        if(storeId == -1){
            List<StoreOrderDTO> storesOrder = engine.findMinCostOrder(OrderProducts);
            load = setDynamicLayoutBoarderPaneScene(storesOrder);
        }
        else{
            List<StoreOrderDTO> storeOrder = engine.getStoreOrderByStoreId(storeId,OrderProducts);
            load = setDiscountLayoutBoarderPane(storeOrder);

        }
        Scene scene = new Scene(load, 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }
    private BorderPane setDiscountLayoutBoarderPane(List<StoreOrderDTO> storeOrder) {
        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\discountsLayout\\DiscountLayoutBorderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DiscountLayoutBorderPaneController controller  = loader.getController();
        controller.setData(storeOrder,customer,engine,orderStage,date);
        return load;
    }

    private BorderPane setDynamicLayoutBoarderPaneScene(List<StoreOrderDTO> storesOrder) {

        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\dynamicLayoutBoarderPane\\DynamicLayoutBoarderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DynamicLayoutBoarderPaneController controller  = loader.getController();
        controller.setData(storesOrder,customer,engine,orderStage,date);
        return load;
    }

}
