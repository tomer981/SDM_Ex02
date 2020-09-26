package gui.showOrderInfo;

import dto.CustomerDTO;
import dto.StoreDTO;
import dto.orderDTO.OrderDTO;
import gui.newOrder.productsSummery.ProductsSummeryTableViewController;
import gui.newOrder.storeSummery.StoreSummeryTableViewController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static gui.newOrder.finalOrderLayout.FinalOrderLayoutBoarderPaneController.RoundDouble;

public class ShowOrderInfoBorderPaneController {

    @FXML private SplitPane split;
    @FXML private Text totalProductsCost;
    @FXML private Text totalDeliveryCost;
    @FXML private Text totalCost;


    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;

    private StoreSummeryTableViewController storeController;
    private ProductsSummeryTableViewController productController;

    private OrderDTO orderDTO;

    public void setData(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
        List<StoreDTO> storesDTO = new ArrayList(orderDTO.getKStoresVSubOrders().keySet());
        storeController.setData(storesDTO, orderDTO.getCustomer());
        setTotalValues();
    }

    @FXML
    private void initialize() {
        initializeStoreLayout();
        selectionStoreProperty = storeController.getSelectionStore();
        initializeProductsLayout();
        selectionStoreProperty.addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productController.setData(orderDTO.getKStoresVSubOrders().get(selectionStoreProperty.get()).getTotalProductsToDisplay());
            }
        }));
    }
    private void setTotalValues() {

        totalProductsCost.setText("Total Products Cost: " + RoundDouble(orderDTO.getTotalCostProducts()));
        totalDeliveryCost.setText("Total Delivery Cost: " + RoundDouble(orderDTO.getTotalDeliverOrderCost()));
        totalCost.setText("Total Cost: " + RoundDouble(orderDTO.getTotalOrderCost()));
    }

    private void initializeStoreLayout() {
        FXMLLoader loader = null;
        ScrollPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\newOrder\\storeSummery\\StoreSummeryTableViewGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storeController = loader.getController();
        split.getItems().set(0, load);
    }

    private void initializeProductsLayout() {
        FXMLLoader loader = null;
        ScrollPane load = null;
        try {
            loader = new FXMLLoader(getClass().getResource("..\\..\\gui\\newOrder\\productsSummery\\ProductsSummeryTableViewGui.fxml"));
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productController = loader.getController();
        split.getItems().set(1, load);

    }
}
