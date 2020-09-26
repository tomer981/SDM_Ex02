package gui.newOrder.storeSummery;

import dto.CustomerDTO;
import dto.StoreDTO;
import dto.orderDTO.StoreOrderDTO;
import dto.orderDTO.SubOrderDTO;
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
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static javafx.scene.control.TableColumn.*;

public class StoreSummeryTableViewController {

    @FXML private TableView<StoreDTO> storeTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> ppk;
    @FXML private TableColumn<StoreDTO, Double> distanceCustomer;
    @FXML private TableColumn<StoreDTO, Double> deliveryCost;

    private List<StoreDTO> storesOrder;
    private CustomerDTO customer;


    @FXML
    private void initialize() {

    }


    public void setData(List<StoreDTO> stores, CustomerDTO customer) {
        this.storesOrder = stores;
        this.customer = customer;

        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        ppk.setCellValueFactory(new PropertyValueFactory("ppk"));

        distanceCustomer.setCellFactory((tableColumn) -> {
            TableCell<StoreDTO, Double> tableCell = new TableCell<StoreDTO, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    StoreDTO rowItem = (StoreDTO) getTableRow().getItem();
                    if (rowItem == null) {
                        this.setText("");
                        return;
                    }
                    Double X = Math.pow(rowItem.getCordX() - customer.getCordX(), 2);
                    Double Y = Math.pow(rowItem.getCordY() - customer.getCordY(), 2);

                    item = RoundDouble(Math.pow(X + Y, 0.5));
                    this.setItem(item);
                    this.setGraphic(null);

                    if (!empty) {
                        this.setText(item.toString());
                    }
                }
            };

            return tableCell;
        });

        deliveryCost.setCellFactory((tableColumn) -> {
            TableCell<StoreDTO, Double> tableCell = new TableCell<StoreDTO, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    StoreDTO rowItem = (StoreDTO) getTableRow().getItem();
                    if (rowItem == null) {
                        this.setText("");
                        return;
                    }
                    Double X = Math.pow(rowItem.getCordX() - customer.getCordX(), 2);
                    Double Y = Math.pow(rowItem.getCordY() - customer.getCordY(), 2);

                    item = Math.pow(X + Y, 0.5);
                    item = RoundDouble(item * rowItem.getPpk());
                    this.setItem(item);
                    //this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
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

    public ReadOnlyObjectProperty<StoreDTO> getSelectionStore() {
        return storeTableView.getSelectionModel().selectedItemProperty();
    }
}

