package com.example.gui_version;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ControllerUnitTest {

    private String testFilePath = "";
    private String generatedFilePath = "";
    @FXML
    private ListView<String> listReactions;
    @FXML
    private Label resume;

    @FXML
    private Label pathGLabel;

    @FXML
    private Label pathTLabel;

    public void start (Stage stage, Scene scene){

        // create a Pane to hold the tree
        Pane pane = new Pane();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void genFileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> f = fc.showOpenMultipleDialog(null);
        for(File file : f)
        {
            System.out.println(file.getAbsolutePath());
        }
        generatedFilePath = f.get(0).toString();
        pathGLabel.setText(pathGLabel.getText()  + " : " + generatedFilePath);
    }

    @FXML
    void testFileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> f = fc.showOpenMultipleDialog(null);
        for(File file : f)
        {
            System.out.println(file.getAbsolutePath());
        }
        testFilePath = f.get(0).toString();
        pathTLabel.setText(pathTLabel.getText()  + " : " + testFilePath);
    }

    @FXML
    void runTest(ActionEvent event) {
        unitTest test = new unitTest(testFilePath.toString(),generatedFilePath.toString());
        test.compare();
        ArrayList<String> common = test.common;
        ObservableList<String> ReactionObservableList = FXCollections.observableArrayList();
        ReactionObservableList.addAll(common);
        listReactions.setItems(ReactionObservableList);
        resume.setText("Number of common reaction  : " + test.matchingItems + " / "  + test.testReaction.size());
    }


}