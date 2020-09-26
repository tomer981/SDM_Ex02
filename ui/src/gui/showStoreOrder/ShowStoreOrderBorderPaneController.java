package gui.showStoreOrder;

import dto.StoreDTO;
import dto.orderDTO.OrderDTO;
import dto.orderDTO.SubOrderDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController;
import gui.mainMenuTabPane.MainMenuTabPaneController.IStoreInfo;
import gui.newOrder.finalOrderLayout.FinalOrderLayoutBoarderPaneController;
import gui.newOrder.newOrderLayoutGrid.NewOrderLayoutGridController;
import gui.updateProductHBox.UpdateProductHBoxController;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static gui.productsInMarketTableView.ProductsInMarketTableViewController.RoundDouble;

public class ShowStoreOrderBorderPaneController {

    @FXML private TableView<SubOrderDTO> orderTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> date;
    @FXML private TableColumn<?, ?> totalAmount;
    @FXML private TableColumn<?, ?> costProducts;
    @FXML private TableColumn<?, ?> deliveryCost;
    @FXML private TableColumn<?, ?> totalCost;
    @FXML private Button showOrderButton;
    @FXML private Button showFullOrderButton;

    private Set<OrderDTO> ordersDTO;
    private ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty;
    private IStoreInfo engine;

    @FXML
    private void initialize(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        date.setCellValueFactory(new PropertyValueFactory("date"));
        totalAmount.setCellValueFactory(new PropertyValueFactory("amountsOfProducts"));
        costProducts.setCellValueFactory(new PropertyValueFactory("totalCostProducts"));
        deliveryCost.setCellValueFactory(new PropertyValueFactory("deliverCost"));
        totalCost.setCellValueFactory(new PropertyValueFactory("totalOrderCost"));
        showOrderButton.disableProperty().bind(orderTableView.getSelectionModel().selectedItemProperty().isNull());
        showFullOrderButton.disableProperty().bind(orderTableView.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    void onShowFullOrderButton(ActionEvent event) throws IOException {
        OrderDTO order = ordersDTO.stream().filter(orderDTO -> orderDTO.getId().equals(orderTableView.getSelectionModel().getSelectedItem())).findFirst().orElse(null);

        Stage fullOrderStage = new Stage();//TODO: maybe Primary Stage
        fullOrderStage.setTitle("Full Order");

        FXMLLoader loader = new FXMLLoader(FinalOrderLayoutBoarderPaneController.class.getResource("FinalOrderLayoutBoarderPaneGui.fxml"));
        BorderPane load = loader.load();

        FinalOrderLayoutBoarderPaneController controller = loader.getController();



        Scene scene = new Scene(load, 600, 400);
        fullOrderStage.setScene(scene);
        fullOrderStage.show();
    }

    @FXML
    void onShowOrderButton(ActionEvent event) {
        //OrderDTO orderDTO = new OrderDTO()

    }

    public void setData(IStoreInfo engine, ReadOnlyObjectProperty<StoreDTO> selectionStoreProperty) {
        orderTableView.getSelectionModel().clearSelection();
        this.engine = engine;
        this.ordersDTO =  engine.getOrdersDTO();
        this.selectionStoreProperty = selectionStoreProperty;
        List<SubOrderDTO> storeOrder = new ArrayList<>();

        for (OrderDTO orderDTO : ordersDTO){
            if (orderDTO.getKStoresVSubOrders().containsKey(this.selectionStoreProperty.get())){
                SubOrderDTO subOrderDTO = orderDTO.getKStoresVSubOrders().get(this.selectionStoreProperty.get());
                subOrderDTO.setTotalCostProducts(RoundDouble(subOrderDTO.getTotalCostProducts()));
                subOrderDTO.setTotalOrderCost(RoundDouble(subOrderDTO.getTotalOrderCost()));
                subOrderDTO.setDeliverCost(RoundDouble(subOrderDTO.getDeliverCost()));
                storeOrder.add(orderDTO.getKStoresVSubOrders().get(this.selectionStoreProperty.get()));
            }
        }

        orderTableView.setItems(FXCollections.observableArrayList(storeOrder));
        orderTableView.refresh();
    }
}
