package gui.mapTabPane;

import gui.mainMenuTabPane.MainMenuTabPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MapTabPaneViewController {


    public @FXML
    AnchorPane mapArea;


    private MainMenuTabPaneController.IMap mapEngine;

    public void initialize() {
        Button newButton = new Button("Hello!");
        mapArea.getChildren().add(newButton);
    }

    public void setEngine(MainMenuTabPaneController.IMap mapEngine) {
        this.mapEngine = mapEngine;
    }
}
