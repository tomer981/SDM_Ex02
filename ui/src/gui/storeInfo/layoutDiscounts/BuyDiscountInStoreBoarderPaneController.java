package gui.storeInfo.layoutDiscounts;

import dto.StoreDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController;
import gui.storeInfo.buyDiscountInStore.BuyDiscountInStoreTableViewController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import static gui.mainMenuTabPane.MainMenuTabPaneController.*;

public class BuyDiscountInStoreBoarderPaneController {

    @FXML private BorderPane borderPaneDiscount;
    @FXML private Button showOfferButton;

    BuyDiscountInStoreTableViewController discountTableViewController;

    private IStoreInfo engine;
    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;

    @FXML
    private void initialize() throws IOException {
        initializeDiscountDisplay();
    }

    private void initializeDiscountDisplay() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("..\\..\\..\\gui\\storeInfo\\buyDiscountInStore\\BuyDiscountInStoreTableViewGui.fxml"));
        ScrollPane discountTable = loader.load();
        discountTableViewController = loader.getController();
        borderPaneDiscount.setCenter(discountTable);
    }

    @FXML
    void onShowOfferButton(ActionEvent event) {

    }

    public void setEngine(IStoreInfo engine, ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty) {
        this.engine = engine;
        this.selectionStoreProperty = selectionStoreProperty;
        discountTableViewController.setDiscounts(engine.getDiscountsByStoreId(selectionStoreProperty.get().getId()));

    }
}
