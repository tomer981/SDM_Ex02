package gui.updateProductHBox;


import dto.ProductDTO;
import dto.StoreDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController.IUpdateProduct;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.List;


public class UpdateProductHBoxController {

    @FXML private HBox updateProductHBox;
    @FXML private ComboBox<StoreDTO> storeComboBox;
    @FXML private ComboBox<String> operationComboBox;
    @FXML private ComboBox<ProductDTO> productsComboBox;
    @FXML private TextField updatePriceTextField;
    @FXML private Button activateButton;


    private final SimpleBooleanProperty isDeleteSelected;
    private final SimpleBooleanProperty isValidPrice;



    private IUpdateProduct engine;

    public UpdateProductHBoxController(){
        isValidPrice = new SimpleBooleanProperty(true);
        isDeleteSelected= new SimpleBooleanProperty(false);
    }

    public void setEngine(IUpdateProduct engine){
        this.engine = engine;
        storeComboBox.setItems(FXCollections.observableArrayList(engine.getStoresDTO()));
    }

    @FXML
    void onStoreSelection(ActionEvent event) {
        operationComboBox.getSelectionModel().clearSelection();
        productsComboBox.getSelectionModel().clearSelection();
        updatePriceTextField.textProperty().setValue("");
    }

    @FXML
    private void initialize(){
        operationComboBox.getItems().setAll("Delete","Add Product","Change Price");
        operationComboBox.disableProperty().bind(storeComboBox.valueProperty().isNull());
        productsComboBox.disableProperty().bind(operationComboBox.valueProperty().isNull());
        updatePriceTextField.disableProperty().bind(Bindings.or(isDeleteSelected,productsComboBox.valueProperty().isNull()));
        activateButton.disableProperty().bind(productsComboBox.valueProperty().isNotNull().and(
                Bindings.or(updatePriceTextField.disabledProperty(),
                        Bindings.and(updatePriceTextField.textProperty().isNotEmpty(),isDeleteSelected.not()))
        ).not());

        updatePriceTextField.focusedProperty().addListener(this::changed);
    }

    @FXML
    void onOpSelection(ActionEvent event) {
        String selection = operationComboBox.getSelectionModel().getSelectedItem();
        if (selection == null){return;}
        isDeleteSelected.set(selection.equals("Delete"));
        productsComboBox.getSelectionModel().clearSelection();
        updatePriceTextField.textProperty().setValue("");
        List<ProductDTO> products = engine.getProductsDTO();
        List<ProductDTO> storeProducts = engine.getStoreProductDTO(storeComboBox.getSelectionModel().getSelectedItem().getId());

        if (selection.equals("Delete")){
            products.removeIf(product->product.getNumberOfStoreSell().equals(1) ||
                    storeComboBox.getSelectionModel().getSelectedItem().getNumberOfProductsSell().equals(1)||
                    !storeProducts.contains(product));
        }
        if (selection.equals("Add Product")){
            products.removeAll(storeProducts);
        }
        if (selection.equals("Change Price")){
            Integer storeId = storeComboBox.getSelectionModel().getSelectedItem().getId();
            products = engine.getStoreProductDTO(storeId);
        }
        productsComboBox.setItems(FXCollections.observableArrayList(products));
    }


//    private void deleteSelection(UpdateOpStoreDTO storeDTO, List<UpdateOpProductDTO> showProducts) {
//        updatePriceTextField.textProperty().setValue(null);
//        updatePriceTextField.disableProperty().set(true);
//        showProducts.removeIf(product-> product.getNumberOfStoreSell().equals(1) ||
//                storeDTO.getProductsStore().size() == 1);
//    }


    @FXML
    void onActivateButton(ActionEvent event) {
        Integer storeId = storeComboBox.getSelectionModel().getSelectedItem().getId();
        String op = operationComboBox.getSelectionModel().getSelectedItem();
        Integer productId = productsComboBox.getSelectionModel().getSelectedItem().getId();

        if (op.equals("Change Price")) {
            Double price = Double.parseDouble(updatePriceTextField.textProperty().getValue());
            engine.changeProductPrice(storeId, productId, price);

        } else if (op.equals("Add Product")) {
            Double price = Double.parseDouble(updatePriceTextField.textProperty().getValue());

        } else {
            if (engine.isProductInDiscountInStoreByStoreId(storeId, productId)) {
                showAlert("the Product was part of Discount");
            }
        }
    }

    @FXML
    void onProductSelection(ActionEvent event) {
        updatePriceTextField.textProperty().setValue("");
        String op = operationComboBox.getSelectionModel().getSelectedItem();
        if(op.equals("Delete")){
            Integer storeId = storeComboBox.getSelectionModel().getSelectedItem().getId();
            Integer productId = productsComboBox.getSelectionModel().getSelectedItem().getId();
            if(engine.isProductInDiscountInStoreByStoreId(storeId,productId)){
                showAlert("the Product is part of Discount");
            }
        }



    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
        if (!newValue) { // when focus lost
            String productCategory = productsComboBox.getSelectionModel().getSelectedItem().getCategory().toLowerCase();

            if (!isNumeric(updatePriceTextField.getText())) {
                updatePriceTextField.setText("");
            }
        }
    }


    private void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(msg);
        alert.showAndWait();
    }
}
