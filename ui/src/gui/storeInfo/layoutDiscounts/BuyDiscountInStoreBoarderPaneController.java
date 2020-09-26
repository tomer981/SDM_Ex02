package gui.storeInfo.layoutDiscounts;

import dto.StoreDTO;
import dto.orderDTO.DiscountProductsDTO;
import dto.orderDTO.OffersDiscountDTO;
import dto.orderDTO.OrderDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController;
import gui.newOrder.newOrderLayoutGrid.NewOrderLayoutGridController;
import gui.storeInfo.buyDiscountInStore.BuyDiscountInStoreTableViewController;
import gui.storeInfo.getDiscountInStore.GetDiscountInStoreTableViewController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static gui.mainMenuTabPane.MainMenuTabPaneController.*;

public class BuyDiscountInStoreBoarderPaneController {

    @FXML private BorderPane borderPaneDiscount;
    @FXML private Button showOfferButton;

    BuyDiscountInStoreTableViewController discountTableViewController;
    GetDiscountInStoreTableViewController offerDiscountController;

    private IStoreInfo engine;
    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;
    private ReadOnlyObjectProperty<DiscountProductsDTO> selectedDiscountProperty;

    @FXML
    private void initialize() throws IOException {
        initializeDiscountDisplay();
    }

    private void initializeDiscountDisplay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\storeInfo\\buyDiscountInStore\\BuyDiscountInStoreTableViewGui.fxml"));
        ScrollPane discountTable = loader.load();
        discountTableViewController = loader.getController();
        borderPaneDiscount.setCenter(discountTable);
        selectedDiscountProperty = discountTableViewController.getPropertySelectionDiscount();

    }

    @FXML
    void onShowOfferButton(ActionEvent event) throws IOException {
        Stage discountOfferStage = new Stage();//TODO: maybe Primary Stage
        discountOfferStage.setTitle("Offer of Discount");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\storeInfo\\getDiscountInStore\\GetDiscountInStoreTableViewGui.fxml"));
        ScrollPane offerDiscountTableView = loader.load();

        offerDiscountController = loader.getController();
        OffersDiscountDTO offersDiscount = engine.getOffersDiscount(selectionStoreProperty.get().getId(),selectedDiscountProperty.get());

        offerDiscountController.setOffersDiscount(offersDiscount);
        Scene scene = new Scene(offerDiscountTableView, 600, 400);
        discountOfferStage.setScene(scene);
        discountOfferStage.show();
    }

    public void setEngine(IStoreInfo engine, ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty) {
        this.engine = engine;
        this.selectionStoreProperty = selectionStoreProperty;
        showOfferButton.disableProperty().bind(selectionStoreProperty.isNull());
        discountTableViewController.setDiscounts(engine.getDiscountsByStoreId(selectionStoreProperty.get().getId()));

    }
}
