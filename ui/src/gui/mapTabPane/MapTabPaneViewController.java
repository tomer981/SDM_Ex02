package gui.mapTabPane;

import dto.CustomerDTO;
import dto.StoreDTO;
import gui.mainMenuTabPane.MainMenuTabPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapTabPaneViewController {

    private static final double BUTTON_SIZE = 30;
    private static final double MARGIN = 2;

    public @FXML
    AnchorPane mapArea;


    private MainMenuTabPaneController.IMap mapEngine;

    public void initialize() {
    }

    public void setEngine(MainMenuTabPaneController.IMap mapEngine) {
        this.mapEngine = mapEngine;
        displayData();
    }

    private IntStream joinStreams(Function<CustomerDTO, Integer> customerGetter,
                                  Function<StoreDTO, Integer> storeGetter) {
        Stream<CustomerDTO> customerStream = mapEngine.getCustomersDTO().stream();
        Stream<StoreDTO> storeStream = mapEngine.getStoresDTO().stream();

        return Stream.concat(customerStream.map(customerGetter), storeStream.map(storeGetter))
                .mapToInt(value -> value);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private void displayData() {
        mapArea.getChildren().clear();

        int minX = joinStreams(CustomerDTO::getCordX, StoreDTO::getCordX).min().getAsInt();
        int maxX = joinStreams(CustomerDTO::getCordX, StoreDTO::getCordX).max().getAsInt();
        int minY = joinStreams(CustomerDTO::getCordY, StoreDTO::getCordY).min().getAsInt();
        int maxY = joinStreams(CustomerDTO::getCordY, StoreDTO::getCordY).max().getAsInt();

        mapArea.setPrefSize(
                MARGIN + (BUTTON_SIZE + MARGIN) * (maxX - minX + 1),
                MARGIN + (BUTTON_SIZE + MARGIN) * (maxY - minY + 1)
        );

        mapArea.getChildren().addAll(
                mapEngine.getStoresDTO().stream()
                        .map(storeDTO -> createStoreButton(storeDTO, minX, maxX, minY, maxY))
                        .collect(Collectors.toList())
        );

        mapArea.getChildren().addAll(
                mapEngine.getCustomersDTO().stream()
                        .map(customerDTO -> createCustomerButton(customerDTO, minX, maxX, minY, maxY))
                        .collect(Collectors.toList())
        );

        mapArea.getChildren().forEach(node ->
                System.out.println(node.getBoundsInParent()));
    }

    private Button createStoreButton(StoreDTO store, int minX, int maxX, int minY, int maxY) {
        Button button = createButton("store", store.getName(),
                store.getCordX(), store.getCordY(), minX, maxX, minY, maxY);

        button.setOnMouseClicked(mouseEvent -> mapEngine.showStore(store));

        return button;
    }

    private Button createCustomerButton(CustomerDTO customer, int minX, int maxX, int minY, int maxY) {
        Button button = createButton("customer", customer.getName(),
                customer.getCordX(), customer.getCordY(), minX, maxX, minY, maxY);

        button.setOnMouseClicked(mouseEvent -> mapEngine.showCustomer(customer));

        return button;
    }

    private Button createButton(String type, String name, int x, int y, int minX, int maxX, int minY, int maxY) {
        Button newButton = new Button();
        newButton.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        newButton.setMaxSize(BUTTON_SIZE, BUTTON_SIZE);
        newButton.setLayoutX((x - minX) * BUTTON_SIZE + MARGIN);
        newButton.setLayoutY((y - minY) * BUTTON_SIZE + MARGIN);

        newButton.setText(name);
        newButton.setFont(Font.font(9.0));
        newButton.setTooltip(new Tooltip(String.format("%s: %s", type, name)));

        return newButton;
    }
}
