package gui.showSelectionOfOrderHBox;

import dto.orderDTO.OrderDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.List;

public class ShowSelectionOfOrderHBoxController {

    @FXML private ComboBox<OrderDTO> orderComboBox;
    @FXML private Button showOrderButton;

    @FXML
    private void initialize(){
        showOrderButton.disableProperty().bind(orderComboBox.valueProperty().isNull());
    }

    public void setData(List<OrderDTO> orderDTOList){
        orderComboBox.setItems(FXCollections.observableArrayList(orderDTOList));
    }



    @FXML
    void onShowButtonOrder(ActionEvent event) {

    }
}
