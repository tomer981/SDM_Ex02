package gui.storeInfo.getDiscountInStore;

import discount.OfferDiscount;
import dto.orderDTO.DiscountProductsDTO;
import dto.orderDTO.OfferDiscountDTO;
import dto.orderDTO.OffersDiscountDTO;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GetDiscountInStoreTableViewController {


    @FXML private TableView<OfferDiscountDTO> getTableView;
    @FXML private TableColumn<?, ?> typeOrder;
    @FXML private TableColumn<?, ?> id;
    @FXML private TableColumn<?, ?> name;
    @FXML private TableColumn<?, ?> amount;
    @FXML private TableColumn<?, ?> price;

    private OffersDiscountDTO offerDiscount;
    private final SimpleBooleanProperty isNeededSelection;

    public ReadOnlyObjectProperty<OfferDiscountDTO> getPropertySelectionDiscount(){
        return getTableView.getSelectionModel().selectedItemProperty();
    }
    public SimpleBooleanProperty getIsNeededSelectionProperty(){
        return isNeededSelection;
    }

    public GetDiscountInStoreTableViewController(){
        isNeededSelection = new SimpleBooleanProperty(true);
    }

    @FXML
    private void initialize(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        name.setCellValueFactory(new PropertyValueFactory("name"));
        amount.setCellValueFactory(new PropertyValueFactory("amount"));
        price.setCellValueFactory(new PropertyValueFactory("price"));
        isNeededSelection.setValue(true);
    }

    public void setOffersDiscount(OffersDiscountDTO offerDiscount) {
        this.offerDiscount = offerDiscount;
        setTypeCategory();
        getTableView.setItems(FXCollections.observableArrayList(this.offerDiscount.getOffersDiscount()));
        getTableView.refresh();
    }

    private void setTypeCategory(){
        if (offerDiscount.getCondition().equals("all or nothing")){
            typeOrder.setText("All Or Nothing");
            isNeededSelection.setValue(false);
        }
        else{
            typeOrder.setText("One Of" );
            isNeededSelection.setValue(true);
        }
    }
    public void clearSelection(){
        getTableView.getSelectionModel().clearSelection();
        typeOrder.setText("One-Of / All-Or-Nothing");
        getTableView.setItems(null);
    }
}
