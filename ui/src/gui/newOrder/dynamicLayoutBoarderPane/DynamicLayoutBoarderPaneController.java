package gui.newOrder.dynamicLayoutBoarderPane;

import dto.CustomerDTO;
import dto.orderDTO.StoreOrderDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController;
import gui.newOrder.discountsLayout.DiscountLayoutBorderPaneController;
import gui.newOrder.dynamicStoreOrderInfo.DynamicStoreOrderInfoController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static gui.mainMenuTabPane.MainMenuTabPaneController.*;

public class DynamicLayoutBoarderPaneController {

    @FXML private BorderPane boarderPaneLayout;
    @FXML private Button nextButton;
    @FXML private ComboBox<StoreOrderDTO> storeComboBox;

    private List<StoreOrderDTO> storesOrder;
    private CustomerDTO customer;
    private DynamicStoreOrderInfoController StoreInfoController;
    private DiscountLayoutBorderPaneController discountLayoutController;
    private INewOrder engine;
    private Stage orderStage;

    @FXML
    void OnStoreSelectionComboBox(ActionEvent event) {
        ScrollPane storeOrder = setDynamicLayoutBoarderPaneScene();
        StoreOrderDTO store = storeComboBox.getSelectionModel().getSelectedItem();
        Double distance = engine.getDistance(customer.getCordX(),customer.getCordY(),store.getCordX(),store.getCordY());
        StoreInfoController.setStoreData(store,distance);
        boarderPaneLayout.setCenter(storeOrder);
    }


    @FXML
    void onNextButton(ActionEvent event) {
        Scene scene = new Scene(setDiscountLayoutBoarderPane(), 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }

    private ScrollPane setDynamicLayoutBoarderPaneScene() {
        FXMLLoader loader = null;
        ScrollPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\dynamicStoreOrderInfo\\DynamicStoreOrderInfoGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StoreInfoController = loader.getController();

        return load;
    }

    public void setData(List<StoreOrderDTO> storesOrder, CustomerDTO customer, INewOrder engine, Stage orderStage, LocalDate date){
        this.storesOrder = storesOrder;
        this.customer = customer;
        this.engine = engine;
        this.orderStage = orderStage;
        storeComboBox.setItems(FXCollections.observableArrayList(this.storesOrder));
    }

    private BorderPane setDiscountLayoutBoarderPane() {
        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\discountsLayout\\DiscountLayoutBorderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        discountLayoutController  = loader.getController();
        discountLayoutController.setData(storesOrder,customer,engine,orderStage);
        return load;
    }

}
