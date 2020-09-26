package gui.newOrder.dynamicLayoutBoarderPane;

import dto.StoreDTO;
import dto.orderDTO.OrderDTO;
import dto.orderDTO.StoreOrderDTO;
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


import static gui.mainMenuTabPane.MainMenuTabPaneController.*;

public class DynamicLayoutBoarderPaneController {

    @FXML private BorderPane boarderPaneLayout;
    @FXML private Button nextButton;//TODO: delete
    @FXML private ComboBox<StoreDTO> storeComboBox;

    private DynamicStoreOrderInfoController StoreInfoController;

    private INewOrder engine;
    private Stage orderStage;
    private OrderDTO order;

    @FXML
    void OnStoreSelectionComboBox(ActionEvent event) {
        ScrollPane storeOrderLayout = setDynamicLayoutBoarderPaneScene();
        StoreDTO store = storeComboBox.getSelectionModel().getSelectedItem();
        StoreInfoController.setStoreData(order.getKStoresVSubOrders().get(store));
        boarderPaneLayout.setCenter(storeOrderLayout);
    }


    @FXML
    void onNextButton(ActionEvent event) {
        Scene scene = new Scene(setDiscountLayoutBoarderPane(), 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }


    public void setData(INewOrder engine, Stage orderStage, OrderDTO order) {
        this.engine = engine;
        this.orderStage = orderStage;
        this.order = order;
        storeComboBox.setItems(FXCollections.observableArrayList(this.order.getKStoresVSubOrders().keySet()));
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


    private BorderPane setDiscountLayoutBoarderPane() {
        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\newOrder\\discountsLayout\\DiscountLayoutBorderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DiscountLayoutBorderPaneController discountLayoutController = loader.getController();
        discountLayoutController.setData(engine, orderStage, order);
        return load;
    }


}
