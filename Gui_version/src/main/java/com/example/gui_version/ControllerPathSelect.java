package com.example.gui_version;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class ControllerPathSelect {

    private List<List<String>> paths;
    @FXML
    private Label label;

    @FXML
    private ListView<Integer> list;

    public void start (Stage stage, Scene scene, List<List<String>> paths){

        this.paths=paths;

        label.setText(paths.size() + " paths have been discovered. Click on the wanted one to have more explanation");

        List<Integer> indexing = new ArrayList<>();

        for(int i = 0; i < this.paths.size(); i++) {
            indexing.add(i);
        }

        ObservableList<Integer> ObservableList = FXCollections.observableArrayList();
        ObservableList.addAll(indexing);
        list.setItems(ObservableList);

        // Add a mouse click event listener to the ListView
        list.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                // Get the selected item
                String selectedItem = String.valueOf(list.getSelectionModel().getSelectedItem());

                try {
                    // Load the FXML file for the new stage
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("path-detail-view.fxml"));

                    // Create a new stage
                    Stage newStage = new Stage();

                    // Set the root node and controller for the new stage
                    newStage.setScene(new Scene(loader.load()));
                    newStage.setTitle("Path Detail");
                    ControllerPathDetail controller = loader.getController();
                    controller.start(newStage, newStage.getScene(),paths.get(Integer.parseInt(selectedItem)));

                    // Show the new stage
                    newStage.show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }

}