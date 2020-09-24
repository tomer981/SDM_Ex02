package gui.newOrder.dynamicStoreOrderInfo;

import dto.orderDTO.StoreOrderDTO;
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


    public void setStoreData(StoreOrderDTO storeOrder, Double distance) {

        id.textProperty().setValue("Store Id: " + storeOrder.getId());
        name.textProperty().setValue("Store Name: " + storeOrder.getName());
        locationXY.textProperty().setValue("Location(x,y): " + storeOrder.getCordX() + ", " + storeOrder.getCordY());
        this.distance.textProperty().setValue("Distance: " + RoundDouble(distance));//TODO: Distance
        ppk.textProperty().setValue("PPK: " + storeOrder.getPpk());
        deliveryCost.textProperty().setValue("Delivery Cost: " + RoundDouble(storeOrder.getPpk()*distance));//TODO: delivery cost
        numberOfProducts.textProperty().setValue("Number Of Products: " + storeOrder.getProducts().size());

        AtomicReference<Double> costProduct = new AtomicReference<>(0.0);
        storeOrder.getProducts().forEach(productOrderDTO -> {
            costProduct.set(costProduct.get() + productOrderDTO.getPrice());});
        Double totalCostOrder = RoundDouble(costProduct.get() + storeOrder.getPpk()*distance);
        totalCost.textProperty().setValue("Total Cost: " + totalCostOrder);//TODO: Total cost
    }

    public static Double RoundDouble(Double number) {
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }
}
