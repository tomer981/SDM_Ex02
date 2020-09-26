package gui.newOrder.finalOrderLayout;

import dto.CustomerDTO;
import dto.StoreDTO;
import dto.orderDTO.*;
import gui.mainMenuTabPane.MainMenuTabPaneController.INewOrder;
import gui.newOrder.productsSummery.ProductsSummeryTableViewController;
import gui.newOrder.storeSummery.StoreSummeryTableViewController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FinalOrderLayoutBoarderPaneController {

    @FXML private Text totalProductsCost;
    @FXML private Text totalDeliveryCost;
    @FXML private Text totalCost;
    @FXML private SplitPane splitPane;


    private INewOrder engine;
    private Stage orderStage;
    private OrderDTO orderDTO;

    private StoreSummeryTableViewController storeController;
    private ProductsSummeryTableViewController productController;

    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;

    public void setData(INewOrder engine, Stage orderStage, OrderDTO orderDTO) {
        this.engine = engine;
        this.orderStage = orderStage;
        this.orderDTO = orderDTO;
        List<StoreDTO> storesDTO = new ArrayList(orderDTO.getKStoresVSubOrders().keySet());
        CustomerDTO customer = orderDTO.getCustomer();
        storeController.setData(storesDTO,customer);
        setTotalValues();
    }

    @FXML
    private void initialize() {
        initializeStoreLayout();
        selectionStoreProperty = storeController.getSelectionStore();
        initializeProductsLayout();
        selectionStoreProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productController.setData(orderDTO.getKStoresVSubOrders().get(selectionStoreProperty.get()).getTotalProductsToDisplay());
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
        totalProductsCost.setText("Total Products Cost: " + RoundDouble(orderDTO.getTotalCostProducts()));
        totalDeliveryCost.setText("Total Delivery Cost: " + RoundDouble(orderDTO.getTotalDeliverOrderCost()));
        totalCost.setText("Total Cost: " + RoundDouble(orderDTO.getTotalOrderCost()));
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
        engine.addOrder(orderDTO);
        orderStage.close();
    }
}
