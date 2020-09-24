package gui.newOrder.storeSummery;

import dto.CustomerDTO;
import dto.orderDTO.StoreOrderDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.text.DecimalFormat;
import java.util.List;

import static javafx.scene.control.TableColumn.*;

public class StoreSummeryTableViewController {

    @FXML private TableView<StoreOrderDTO> storeTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> ppk;
    @FXML private TableColumn<StoreOrderDTO, Double> distanceCustomer;
    @FXML private TableColumn<StoreOrderDTO, Double> deliveryCost;

    private List<StoreOrderDTO> storesOrder;
    private CustomerDTO customer;


    @FXML
    private void initialize() {

    }

    public void setData(List<StoreOrderDTO> storesOrder, CustomerDTO customer) {
        this.storesOrder = storesOrder;
        this.customer = customer;

        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        ppk.setCellValueFactory(new PropertyValueFactory("ppk"));

//        distanceCustomer.setCellValueFactory(new PropertyValueFactory("distanceCustomerCalc"));
        distanceCustomer.setCellFactory((tableColumn) -> {
            TableCell<StoreOrderDTO, Double> tableCell = new TableCell<StoreOrderDTO, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    StoreOrderDTO rowItem = (StoreOrderDTO)getTableRow().getItem();
                    if (rowItem==null){
                        this.setText("");
                        return;
                    }
                    Double X =  Math.pow(rowItem.getCordX() - customer.getCordX(),2);
                    Double Y = Math.pow(rowItem.getCordY() - customer.getCordY(),2);

                    item = RoundDouble(Math.pow(X+Y,0.5));
                    this.setItem(item);
                    this.setGraphic(null);

                    if(!empty){
                        this.setText(item.toString());
                    }
                }
            };

            return tableCell;
        });

        deliveryCost.setCellFactory((tableColumn) -> {
            TableCell<StoreOrderDTO, Double> tableCell = new TableCell<StoreOrderDTO, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    StoreOrderDTO rowItem = (StoreOrderDTO)getTableRow().getItem();
                    if (rowItem==null){
                        this.setText("");
                        return;
                    }
                    Double X =  Math.pow(rowItem.getCordX() - customer.getCordX(),2);
                    Double Y = Math.pow(rowItem.getCordY() - customer.getCordY(),2);

                    item = Math.pow(X+Y,0.5);
                    item = RoundDouble(item*rowItem.getPpk());
                    this.setItem(item);
                    //this.setText(null);
                    this.setGraphic(null);

                    if(!empty){
                        this.setText(item.toString());
                    }
                }
            };

            return tableCell;
        });

        storeTableView.setItems(FXCollections.observableArrayList(this.storesOrder));
    }

    public static Double RoundDouble(Double number) {
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }

    public ReadOnlyObjectProperty<StoreOrderDTO> getSelectionStore(){
        return storeTableView.getSelectionModel().selectedItemProperty();
    }

}
