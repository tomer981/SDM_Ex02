package gui.newOrder.productsSummery;

import dto.CustomerDTO;
import dto.orderDTO.ProductOrderDTO;
import dto.orderDTO.StoreOrderDTO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.DecimalFormat;
import java.util.List;

public class ProductsSummeryTableViewController {

    @FXML private TableView<ProductOrderDTO> orderProductsTableView;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> category;
    @FXML private TableColumn<?, ?> pricePerUnit;
    @FXML private TableColumn<ProductOrderDTO, Double> totalProductCost;
    @FXML private TableColumn<?, ?> partOfDiscount;
    @FXML private TableColumn<?, ?> amount;


    private List<ProductOrderDTO> products;


    public void setData(List<ProductOrderDTO> products) {

        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        category.setCellValueFactory(new PropertyValueFactory("category"));
        partOfDiscount.setCellValueFactory(new PropertyValueFactory("boughtInDiscount"));
        amount.setCellValueFactory(new PropertyValueFactory("amountBought"));
        totalProductCost.setCellFactory((tableColumn) -> {
            TableCell<ProductOrderDTO, Double> tableCell = new TableCell<ProductOrderDTO, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    ProductOrderDTO rowItem = (ProductOrderDTO)getTableRow().getItem();
                    if (rowItem == null){
                        this.setText("");
                        return;
                    }

                    item = RoundDouble(rowItem.getPrice()*rowItem.getAmountBought());
                    this.setItem(item);
                    this.setGraphic(null);

                    if(!empty){
                        this.setText(item.toString());
                    }
                }
            };

            return tableCell;
        });
        this.products = products;
        products.forEach(product->{
            product.setPrice(RoundDouble(product.getPrice()));
        });
        pricePerUnit.setCellValueFactory(new PropertyValueFactory("price"));
        orderProductsTableView.setItems(FXCollections.observableArrayList(this.products));
    }

    public static Double RoundDouble(Double number) {
        return Double.parseDouble(new DecimalFormat(".##").format(number));
    }

}
