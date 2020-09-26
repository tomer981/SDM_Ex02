package gui.showSelectionOfOrderHBox;

import dto.orderDTO.OrderDTO;
import gui.newOrder.newOrderLayoutGrid.NewOrderLayoutGridController;
import gui.showOrderInfo.ShowOrderInfoBorderPaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ShowSelectionOfOrderHBoxController {

    @FXML private ComboBox<OrderDTO> orderComboBox;
    @FXML private Button showOrderButton;
    @FXML private void initialize(){
        showOrderButton.disableProperty().bind(orderComboBox.valueProperty().isNull());
    }


    public void setData(Set<OrderDTO> orderDTOList){
        orderComboBox.setItems(FXCollections.observableArrayList(orderDTOList));
    }



    @FXML
    void onShowButtonOrder(ActionEvent event) {
        Stage orderStage = new Stage();
        orderStage.setTitle("New Order - Static Order");

        FXMLLoader loader = null;
        BorderPane load = null;
        try {
            loader = new FXMLLoader(ShowOrderInfoBorderPaneController.class.getResource("ShowOrderInfoBorderPaneGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShowOrderInfoBorderPaneController controller = loader.getController();
        controller.setData(orderComboBox.getValue());

        Scene scene = new Scene(load, 600, 400);
        orderStage.setScene(scene);
        orderStage.show();

    }
}
