package gui.newOrder.discountsLayout;

import dto.CustomerDTO;
import dto.ProductDTO;
import dto.orderDTO.*;
import gui.mainMenuTabPane.MainMenuTabPaneController.INewOrder;
import gui.newOrder.finalOrderLayout.FinalOrderLayoutBoarderPaneController;
import gui.storeInfo.buyDiscountInStore.BuyDiscountInStoreTableViewController;
import gui.storeInfo.getDiscountInStore.GetDiscountInStoreTableViewController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountLayoutBorderPaneController {

    @FXML private ComboBox<StoreOrderDTO> storesComboBox;
    @FXML private Button activeDiscountButton;
    @FXML private SplitPane splitPanel;

    private INewOrder engine;
    private Stage orderStage;
    private List<StoreOrderDTO> storesOrder;
    private CustomerDTO customer;

    private BuyDiscountInStoreTableViewController discountViewController;
    private GetDiscountInStoreTableViewController getDiscountsController;

    private ReadOnlyObjectProperty<DiscountProductsDTO> buyProductPropertySelection;
    private ReadOnlyObjectProperty<OfferDiscountDTO> getProductPropertySelection;

    private SimpleBooleanProperty isOneOfSelection;
    private List<OfferDiscountDTO> offersDiscountDTO;
    private LocalDate date;

    @FXML
    private void initialize() {
        ScrollPane discountTableView = initializeDiscountsTableView();
        ScrollPane getTableView = initializeGetTableView();
        splitPanel.getItems().set(0, discountTableView);
        splitPanel.getItems().set(1, getTableView);
        isOneOfSelection = getDiscountsController.getIsNeededSelectionProperty();
        activeDiscountButton.disableProperty().bind(Bindings.or(buyProductPropertySelection.isNull(),
                Bindings.and(isOneOfSelection,getProductPropertySelection.isNull())));
        buyProductPropertySelection.addListener((observer, oldValue, newValue)->{
            if(newValue != null){
                showOfferDiscount(buyProductPropertySelection.getValue());
            }
        });
    }
    private ScrollPane initializeDiscountsTableView() {
        FXMLLoader loader = null;
        ScrollPane load = null;

        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\storeInfo\\buyDiscountInStore\\BuyDiscountInStoreTableViewGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        discountViewController  = loader.getController();
        buyProductPropertySelection = discountViewController.getPropertySelectionDiscount();

        return load;
    }

    private ScrollPane initializeGetTableView() {
        FXMLLoader loader = null;
        ScrollPane load = null;

        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\storeInfo\\getDiscountInStore\\GetDiscountInStoreTableViewGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getDiscountsController  = loader.getController();
        getProductPropertySelection = getDiscountsController.getPropertySelectionDiscount();

        return load;
    }


    @FXML
    void onSelectionStoreComboBox(ActionEvent event) {
        setDisplayDataForGetDiscount();
    }

    private void setDisplayDataForGetDiscount() {
        List<DiscountProductsDTO> discounts = engine.getDiscountsByStoreId(storesComboBox.getSelectionModel().getSelectedItem().getId());
        discounts = discounts.stream().filter(this::isShowDiscount).collect(Collectors.toList());
        discountViewController.setDiscounts(discounts);
    }

    private boolean isShowDiscount(DiscountProductsDTO discount){
        ProductOrderDTO product = getOrderProduct(discount);
        if(product == null){return false;}
        return (product.getAmountBought() - product.getAmountUseDiscount() >= discount.getQuantityNeeded());
    }

    private void showOfferDiscount(DiscountProductsDTO discountSelected){
        OffersDiscountDTO offersDiscount = engine.getOffersDiscount(storesComboBox.getSelectionModel().getSelectedItem().getId(),discountSelected);
        offersDiscountDTO = offersDiscount.getOffersDiscount();
        getDiscountsController.setOffersDiscount(offersDiscount);
    }


    @FXML
    void onActiveDiscountButton(ActionEvent event) {
        StoreOrderDTO storeSelection = storesComboBox.getSelectionModel().getSelectedItem();
        List<OfferDiscountDTO> addToOrderOffers = new ArrayList<>();
        if (isOneOfSelection.getValue()){
            addToOrderOffers.add(getProductPropertySelection.getValue());
        }
        else {
            addToOrderOffers = offersDiscountDTO;
        }
        addProductsToOrder(addToOrderOffers);

        clearSelection();
    }

    private void addProductToOrder(OfferDiscountDTO offer) {
        DiscountProductsDTO discountProduct =  buyProductPropertySelection.get();
        ProductOrderDTO storeOrderProduct = getOrderProduct(buyProductPropertySelection.get());
        StoreOrderDTO storeOrder = storesComboBox.getSelectionModel().getSelectedItem();
        List<ProductOrderDTO> products = storeOrder.getProducts();

        ProductOrderDTO discountOrderProduct = getDiscountOrderProduct(offer, products);
        if (discountOrderProduct == null){
            discountOrderProduct = new ProductOrderDTO(offer.getId(),offer.getName(),offer.getCategory(),offer.getPrice() / offer.getAmount(),offer.getAmount(),true);
            products.add(discountOrderProduct);
        }
        else {
            discountOrderProduct.setAmountBought(discountOrderProduct.getAmountBought() + offer.getAmount());
        }

        storeOrderProduct.setAmountUseDiscount(storeOrderProduct.getAmountUseDiscount() +  discountProduct.getQuantityNeeded());

    }

    private void addProductsToOrder(List<OfferDiscountDTO> addToOrderOffers) {
        addToOrderOffers.forEach(this::addProductToOrder);
    }


    private void clearSelection() {
        discountViewController.clearSelection();
        getDiscountsController.clearSelection();
        setDisplayDataForGetDiscount();
    }


    private ProductOrderDTO getDiscountOrderProduct(OfferDiscountDTO offerProduct, List<ProductOrderDTO> products){
        return products.stream().filter(productOrderDTO ->
                productOrderDTO.getId().equals(offerProduct.getId()) &&
                        productOrderDTO.isBoughtInDiscount()).
                findFirst().orElse(null);
    }

    private ProductOrderDTO getOrderProduct(DiscountProductsDTO discount){
        List<ProductOrderDTO> products = storesComboBox.getSelectionModel().getSelectedItem().getProducts();
        return products.stream().filter(productOrderDTO ->
                productOrderDTO.getId().equals(discount.getProductId()) &&
                        !productOrderDTO.isBoughtInDiscount()).
                findFirst().orElse(null);
    }

    public void setData(List<StoreOrderDTO> storesOrder, CustomerDTO customer, INewOrder engine, Stage orderStage, LocalDate date) {
        this.engine = engine;
        this.orderStage = orderStage;
        this.storesOrder = storesOrder;
        this.customer = customer;
        this.date = date;
        storesComboBox.setItems(FXCollections.observableArrayList(this.storesOrder));
    }


    @FXML
    void onNextButton(ActionEvent event) {
        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\finalOrderLayout\\FinalOrderLayoutBoarderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FinalOrderLayoutBoarderPaneController controller  = loader.getController();
        controller.setData(engine,orderStage,storesOrder,customer,date);

//        controller.setData(storeOrder,customer,engine,orderStage);
        Scene scene = new Scene(load, 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }
}
