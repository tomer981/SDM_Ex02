package gui.showSelectionOfNewOrderHBox;


import dto.CustomerDTO;
import dto.StoreDTO;
import gui.newOrder.newOrderLayoutGrid.NewOrderLayoutGridController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import static gui.mainMenuTabPane.MainMenuTabPaneController.INewOrderInterfaceHBox;

public class ShowSelectionOfNewOrderHBoxController {

    @FXML private ComboBox<CustomerDTO> customerComboBox;
    @FXML private DatePicker dateDatePicker;
    @FXML private ComboBox<String> dynOrStaticOrderComboBox;
    @FXML private ComboBox<StoreDTO> storeSelectionComboBox;
    @FXML private Button newOrderButton;

    private INewOrderInterfaceHBox engine;

    private final SimpleBooleanProperty isShowNewOrderButton;
    private final SimpleBooleanProperty isNeededSelected;


    public void setEngine(INewOrderInterfaceHBox engine){
        this.engine = engine;
        customerComboBox.setItems(FXCollections.observableArrayList(engine.getCustomersDTO()));
        storeSelectionComboBox.setItems(FXCollections.observableArrayList(engine.getStoresDTO()));
    }

    public ShowSelectionOfNewOrderHBoxController(){
        isNeededSelected  = new SimpleBooleanProperty(false);
        isShowNewOrderButton = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize(){
        dynOrStaticOrderComboBox.getItems().setAll("Static","Dynamic");
        isNeededSelected.bind(customerComboBox.valueProperty().isNotNull().and(//choice customer T
                dateDatePicker.promptTextProperty().isNotEmpty().and(//choice date T
                        dynOrStaticOrderComboBox.valueProperty().isNotNull()//choice static/dynamic T
                )));

        newOrderButton.disableProperty().bind(isNeededSelected.and(
                Bindings.or(storeSelectionComboBox.disableProperty(),//if dynamic
                        storeSelectionComboBox.disableProperty().not().and(storeSelectionComboBox.valueProperty().isNotNull())//if static
                )
        ).not());
    }

    @FXML
    void onStaticOrDynSelection(ActionEvent event) {
        storeSelectionComboBox.disableProperty().setValue(dynOrStaticOrderComboBox.getSelectionModel().isSelected(1));
    }

    @FXML
    void newOrderButtonAction(ActionEvent event) throws IOException {

        Stage orderStage = new Stage();
        orderStage.setTitle("New Order - Static Order");

        FXMLLoader loader = new FXMLLoader();
        URL newOrderFXML = getClass().getResource("..\\..\\gui\\newOrder\\newOrderLayoutGrid\\NewOrderLayoutGridGui.fxml");
        loader.setLocation(newOrderFXML);
        GridPane load = loader.load();
        NewOrderLayoutGridController controller = loader.getController();

        if (dynOrStaticOrderComboBox.getSelectionModel().getSelectedItem().equals("Dynamic")){
            orderStage.setTitle("New Order - Dynamic Order");
            controller.setDynamicTableView();
        }

        Scene scene = new Scene(load, 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }
}
