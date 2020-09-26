package gui.showSelectionOfNewOrderHBox;


import dto.CustomerDTO;
import dto.StoreDTO;
import dto.orderDTO.OrderDTO;
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

import static gui.mainMenuTabPane.MainMenuTabPaneController.INewOrder;

public class ShowSelectionOfNewOrderHBoxController {

    @FXML private ComboBox<CustomerDTO> customerComboBox;
    @FXML private DatePicker dateDatePicker;
    @FXML private ComboBox<String> dynOrStaticOrderComboBox;
    @FXML private ComboBox<StoreDTO> storeSelectionComboBox;
    @FXML private Button newOrderButton;

    private INewOrder engine;

    private final SimpleBooleanProperty isShowNewOrderButton;
    private final SimpleBooleanProperty isNeededSelected;


    public void setEngine(INewOrder engine){
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
        dateDatePicker.disableProperty().bind(customerComboBox.valueProperty().isNull());
        dynOrStaticOrderComboBox.getItems().setAll("Static","Dynamic");
        isNeededSelected.bind(customerComboBox.valueProperty().isNotNull().and(//choice customer T
                dateDatePicker.valueProperty().isNotNull().and(//choice date T
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
    void onSelectionCustomer(ActionEvent event) {
        dateDatePicker.setValue(null);
    }

    @FXML
    void newOrderButtonAction(ActionEvent event)  {

        Stage orderStage = new Stage();//TODO: maybe Primary Stage
        orderStage.setTitle("New Order - Static Order");

        FXMLLoader loader = null;
        GridPane load = null;
        try {
            loader = new FXMLLoader(NewOrderLayoutGridController.class.getResource("NewOrderLayoutGridGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewOrderLayoutGridController controller = loader.getController();
        OrderDTO orderDTO = new OrderDTO(-1,dateDatePicker.valueProperty().getValue(),customerComboBox.getValue());

        if (dynOrStaticOrderComboBox.getSelectionModel().getSelectedItem().equals("Dynamic")){
            orderStage.setTitle("New Order - Dynamic Order");
            controller.setEngine(engine,orderDTO,orderStage);
        }
        else {
            controller.setEngine(engine,orderDTO,orderStage,storeSelectionComboBox.getValue().getId());
        }

        Scene scene = new Scene(load, 600, 400);
        orderStage.setScene(scene);
        orderStage.show();
    }
}
