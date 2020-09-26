package gui.newOrder.dynamicStoreOrderInfo;

import dto.StoreDTO;
import dto.orderDTO.StoreOrderDTO;
import dto.orderDTO.SubOrderDTO;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicReference;

public class DynamicStoreOrderInfoController {

    @FXML private Text id;
    @FXML private Text name;
    @FXML private Text locationXY;
    @FXML private Text distance;
    @FXML private Text ppk;
    @FXML private Text deliveryCost;
    @FXML private Text numberOfProducts;
    @FXML private Text totalCost;

    public void setStoreData(SubOrderDTO subOrderDTO) {
        id.textProperty().setValue("Store Id: " + subOrderDTO.getStore().getId());
        name.textProperty().setValue("Store Name: " + subOrderDTO.getStore().getName());
        locationXY.textProperty().setValue("Location(x,y): " + subOrderDTO.getStore().getCordX() + ", " + subOrderDTO.getStore().getCordY());
        distance.textProperty().setValue("Distance: " + RoundDouble(subOrderDTO.getDistance()));
        ppk.textProperty().setValue("PPK: " + subOrderDTO.getStore().getPpk());
        deliveryCost.textProperty().setValue("Delivery Cost: " + RoundDouble(subOrderDTO.getDeliverCost()));
        numberOfProducts.textProperty().setValue("Number Of Products: " + subOrderDTO.getNumberOfDifferentProducts());
        totalCost.textProperty().setValue("Total Cost Products: " + RoundDouble(subOrderDTO.getTotalCostProducts()));//TODO: Total cost
    }

    public static Double RoundDouble(Double number) {//TODO: delete 2 show place
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }


}
