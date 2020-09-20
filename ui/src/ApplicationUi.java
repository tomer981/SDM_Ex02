import gui.mainMenuTabPane.MainMenuTabPaneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.net.URL;

public class ApplicationUi extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setTitle("SDM - Super Duper Store - By Tomer Avivi, Aviad Ben Dov");

        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("gui\\mainMenuTabPane\\MainMenuTabPaneGui.fxml");
        loader.setLocation(mainFXML);

        // TODO: setMinHeightAndWeight
        TabPane load = loader.load();
        MainMenuTabPaneController mainController = loader.getController();
        mainController.setPrimaryStage(primaryStage);


        //<T> load = FXMLLoader.load(getClass().getResource(""))
//        ScrollPane customerTableView = FXMLLoader.load(getClass().getResource("gui\\customerInfoTableView\\CustomerInfoTableViewGui.fxml"));
//        HBox selectionNewOrder = FXMLLoader.load(getClass().getResource("gui\\showSelectionOfNewOrderHBox\\ShowSelectionOfNewOrderHBoxGui.fxml"));
//
//        mainController.setCustomerTableView(customerTableView);
//        mainController.setSelectionNewOrder(selectionNewOrder);

        Scene scene = new Scene(load, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
