package gui.newOrder.discountsLayout;

import dto.CustomerDTO;
import dto.StoreDTO;
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
import java.util.*;
import java.util.stream.Collectors;

public class DiscountLayoutBorderPaneController {

    @FXML private ComboBox<StoreDTO> storesComboBox;
    @FXML private Button activeDiscountButton;
    @FXML private SplitPane splitPanel;

    private INewOrder engine;
    private Stage orderStage;
    private OrderDTO orderDTO;


    private BuyDiscountInStoreTableViewController discountViewController;
    private GetDiscountInStoreTableViewController getDiscountsController;

    private ReadOnlyObjectProperty<DiscountProductsDTO> buyProductPropertySelection;
    private ReadOnlyObjectProperty<OfferDiscountDTO> getProductPropertySelection;

    private SimpleBooleanProperty isOneOfSelection;
    private List<OfferDiscountDTO> offersDiscountDTO;

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
            loader = new FXMLLoader(BuyDiscountInStoreTableViewController.class.getResource("BuyDiscountInStoreTableViewGui.fxml"));
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
            loader = new FXMLLoader(GetDiscountInStoreTableViewController.class.getResource("GetDiscountInStoreTableViewGui.fxml"));
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
        StoreProductOrderDTO product = getOrderProduct(discount);
        if (product == null){return false;}
        return (product.getAmountBought() - product.getAmountUseDiscount() >= discount.getQuantityNeeded());
    }

    private StoreProductOrderDTO getOrderProduct(DiscountProductsDTO discount){
        StoreDTO storeSelected = storesComboBox.getSelectionModel().getSelectedItem();
        List<StoreProductOrderDTO> products = orderDTO.getKStoresVSubOrders().get(storeSelected).getStoreProductsDTO();
        return products.stream().filter(productOrderDTO ->
                productOrderDTO.getId().equals(discount.getProductId())).
                findFirst().orElse(null);
    }

    private void showOfferDiscount(DiscountProductsDTO discountSelected){
        OffersDiscountDTO offersDiscount = engine.getOffersDiscount(storesComboBox.getSelectionModel().getSelectedItem().getId(),discountSelected);
        offersDiscountDTO = offersDiscount.getOffersDiscount();
        getDiscountsController.setOffersDiscount(offersDiscount);
    }

    @FXML
    void onActiveDiscountButton(ActionEvent event) {
        StoreDTO storeSelection = storesComboBox.getSelectionModel().getSelectedItem();
        SubOrderDTO subOrder = orderDTO.getKStoresVSubOrders().get(storeSelection);
        StoreProductOrderDTO selectedProduct = subOrder.getStoreProductsDTO().
                stream().filter(product ->
                product.getId().equals(buyProductPropertySelection.get().getProductId())).
                findFirst().orElse(null);

        OffersDiscountDTO selectedOfferDiscount = getDiscountsController.getOffersDiscount();

        Map<OffersDiscountDTO,Integer> KOffersDiscountVTimeUse = subOrder.getKOffersDiscountVTimeUse();

        if(!isOneOfSelection.getValue()){
            addOfferDiscountToOrder(KOffersDiscountVTimeUse,selectedOfferDiscount);
        }
        else {
            List<OfferDiscountDTO> singleDiscount = new ArrayList();
            singleDiscount.add(getProductPropertySelection.getValue());
            OffersDiscountDTO modifyDiscount = new OffersDiscountDTO(selectedOfferDiscount.getCondition(), singleDiscount);
            addOfferDiscountToOrder(KOffersDiscountVTimeUse,modifyDiscount);
        }

        selectedProduct.setAmountUseDiscount(selectedProduct.getAmountUseDiscount() + buyProductPropertySelection.get().getQuantityNeeded());
        clearSelection();
    }

    private void addOfferDiscountToOrder(Map<OffersDiscountDTO,Integer> KOffersDiscountVTimeUse ,OffersDiscountDTO selectedOfferDiscount){
        if (KOffersDiscountVTimeUse.containsKey(selectedOfferDiscount)){
            KOffersDiscountVTimeUse.put(selectedOfferDiscount,KOffersDiscountVTimeUse.get(selectedOfferDiscount) + 1);
        }
        else {
            KOffersDiscountVTimeUse.put(selectedOfferDiscount,1);
        }
    }


    private void clearSelection() {
        discountViewController.clearSelection();
        getDiscountsController.clearSelection();
        setDisplayDataForGetDiscount();
    }


    public void setData(INewOrder engine, Stage orderStage, OrderDTO order) {
        this.engine = engine;
        this.orderStage = orderStage;
        this.orderDTO = order;
        storesComboBox.setItems(FXCollections.observableArrayList(this.orderDTO.getKStoresVSubOrders().keySet()));
    }

    @FXML
    void onNextButton(ActionEvent event) {
        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(FinalOrderLayoutBoarderPaneController.class.getResource("FinalOrderLayoutBoarderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FinalOrderLayoutBoarderPaneController controller  = loader.getController();
        setDataForNext();
        controller.setData(engine,orderStage,orderDTO);

        Scene scene = new Scene(load, 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }

    private void setDataForNext() {
        Set<Integer> orderProduct = new HashSet<>();

        for (SubOrderDTO subOrder : orderDTO.getKStoresVSubOrders().values()) {
            List<StoreProductOrderDTO> allTypeOfStoreProductInSubOrder = subOrder.getTotalProductsToDisplay();
            allTypeOfStoreProductInSubOrder.addAll(subOrder.getStoreProductsDTO());

            for (OffersDiscountDTO singleDiscountOffer : subOrder.getKOffersDiscountVTimeUse().keySet()){
                for (OfferDiscountDTO offerProduct : singleDiscountOffer.getOffersDiscount()){
                    StoreProductOrderDTO discountProduct = new StoreProductOrderDTO
                            (offerProduct.getId(),offerProduct.getName(),offerProduct.getCategory(),offerProduct.getPrice()/offerProduct.getAmount(),offerProduct.getAmount() * subOrder.getKOffersDiscountVTimeUse().get(singleDiscountOffer),-1.0);

                    allTypeOfStoreProductInSubOrder.add(discountProduct);

                    Double totalAmountNewProduct = discountProduct.getAmountBought();
                    Double totalCostNewProduct = discountProduct.getPricePerUnit() * totalAmountNewProduct;

                    subOrder.setAmountsOfProducts(totalAmountNewProduct);
                    subOrder.setTotalCostProducts(subOrder.getTotalCostProducts() + totalCostNewProduct);

                    orderDTO.setAmountsOfProducts(orderDTO.getAmountsOfProducts() + totalAmountNewProduct);
                    orderDTO.setTotalCostProducts(orderDTO.getTotalCostProducts() + totalCostNewProduct);
                }
            }

            subOrder.setTotalOrderCost(subOrder.getDeliverCost() + subOrder.getTotalCostProducts());

            Set<Integer> subOrderProducts = subOrder.getTotalProductsToDisplay().stream().map(StoreProductOrderDTO::getId).collect(Collectors.toSet());
            subOrder.setNumberOfDifferentProducts(subOrderProducts.size());

            orderProduct.addAll(subOrderProducts);
        }

        orderDTO.setNumberOfDifferentProducts(orderProduct.size());
        orderDTO.setTotalOrderCost(orderDTO.getTotalDeliverOrderCost() + orderDTO.getTotalCostProducts());

    }
}
