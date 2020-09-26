package gui.newOrder.productsSummery;

import dto.orderDTO.StoreProductOrderDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsSummeryTableViewController {

    @FXML private TableView<StoreProductOrderDTO> orderProductsTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> pricePerUnit;
    @FXML private TableColumn<?, ?> amount;
    @FXML private TableColumn<StoreProductOrderDTO, Double> totalProductCost;
    @FXML private TableColumn<StoreProductOrderDTO, Boolean> partOfDiscount;



    public void setData(List<StoreProductOrderDTO> products) {
        products.forEach(product->product.setPricePerUnit(RoundDouble(product.getPricePerUnit())));
        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        category.setCellValueFactory(new PropertyValueFactory("category"));
        pricePerUnit.setCellValueFactory(new PropertyValueFactory("pricePerUnit"));
        amount.setCellValueFactory(new PropertyValueFactory("amountBought"));
        totalProductCost.setCellFactory((tableColumn) -> {
            TableCell<StoreProductOrderDTO, Double> tableCell = new TableCell<StoreProductOrderDTO, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    StoreProductOrderDTO rowItem = (StoreProductOrderDTO)getTableRow().getItem();
                    if (rowItem == null){
                        this.setText("");
                        return;
                    }

                    item = rowItem.getAmountBought() * rowItem.getPricePerUnit();
                    this.setItem(item);
                    this.setGraphic(null);

                    if(!empty){
                        this.setText(item.toString());
                    }
                }
            };

            return tableCell;
        });

        partOfDiscount.setCellFactory((tableColumn) -> {
            TableCell<StoreProductOrderDTO, Boolean> tableCell = new TableCell<StoreProductOrderDTO, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    StoreProductOrderDTO rowItem = (StoreProductOrderDTO)getTableRow().getItem();
                    if (rowItem == null){
                        this.setText("");
                        return;
                    }

                    item = rowItem.getAmountUseDiscount() == -1.0;
                    this.setItem(item);
                    this.setGraphic(null);

                    if(!empty){
                        this.setText(item.toString());
                    }
                }
            };

            return tableCell;
        });



        orderProductsTableView.setItems(FXCollections.observableArrayList(products));
    }

    public static Double RoundDouble(Double number) {
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }

}
