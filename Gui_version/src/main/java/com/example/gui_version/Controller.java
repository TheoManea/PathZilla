package com.example.gui_version;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Controller {
    private ReadBrenda reactionDb, inhibitionDb;

    @FXML
    private CheckBox cBoxInhibitions;
    private String filePath = "";
    @FXML
    private TextField endReactionEntry;

    @FXML
    private Text filepathStatus;

    @FXML
    private Button inhibitionButton;

    @FXML
    private Label inhibitionsStatus;

    @FXML
    private TextArea logsEntry;

    @FXML
    private TextField maxDepthEntry;

    @FXML
    private Label nbPathLabel;

    @FXML
    private Text pathLabel;

    @FXML
    private Button reactionButton;

    @FXML
    private Label reactionsStatus;

    @FXML
    private TextField startReactionEntry;

    private SearchReaction searchEngine;

    @FXML
    private ListView<String> listReactions;

    private ObservableList<String> ReactionObservableList;

    @FXML
    private Button runTestButton;

    @FXML
    void fileChooser(ActionEvent event) {
        FileChooser fc = new FileChooser();
        List<File> f = fc.showOpenMultipleDialog(null);
        for(File file : f)
        {
            System.out.println(file.getAbsolutePath());
        }
        filepathStatus.setFill(Color.GREEN);
        filepathStatus.setText("YES");
        pathLabel.setText("Path : " + f.get(0).toString());
        filePath = f.get(0).toString();
        reactionButton.setDisable(false);
        inhibitionButton.setDisable(false);
    }

    @FXML
    void loadInhibitions(ActionEvent event) {
        if(Color.RED == inhibitionsStatus.getTextFill()){
            inhibitionDb = new ReadBrenda(filePath);
            inhibitionDb.read("inhibition");
            inhibitionsStatus.setTextFill(Color.GREEN);
            inhibitionsStatus.setText("YES");

            printLog(Color.BLACK,"\n>>>>> " + inhibitionDb.getNbinhibition() + "  Inhibitions have been loaded");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Inhibitions already set");
            alert.showAndWait();
        }
    }


    @FXML
    void loadReactions(ActionEvent event) {
        if(Color.RED == reactionsStatus.getTextFill()){
            reactionDb = new ReadBrenda(filePath);
            reactionDb.read("reaction");
            reactionsStatus.setTextFill(Color.GREEN);
            reactionsStatus.setText("YES");
            printLog(Color.BLACK,"\n>>>>> " + reactionDb.getNbreaction() + " Reactions have been loaded");
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Reactions already set");
            alert.showAndWait();
        }
    }

    @FXML
    void searchReactionPath(ActionEvent event) {
        String startingPoint = startReactionEntry.getText();
        String endingPoint = endReactionEntry.getText();
        String maxDepthString = maxDepthEntry.getText();
        if(startingPoint.isEmpty() || endingPoint.isEmpty() || maxDepthString.isEmpty()){
            popup("Please fill all 3 entries");
        }
        else{
            int maxDepth = Integer.valueOf(maxDepthString);
            searchEngine = new SearchReaction(reactionDb.getReactionList(),inhibitionDb.getInhibitionList());
            searchEngine.setMaxDepth(maxDepth);
            searchEngine.settingReactionAttributes();
            printLog(Color.BLACK,"\n>>>>> Attributes for reactions have been setted ");
            searchEngine.setStartingPoint(startingPoint);
            searchEngine.setEndingPoint(endingPoint);
            printLog(Color.BLACK,"\n>>>>> Looking for a path of type : \"" + startingPoint + "\" -> ... -> \"" + endingPoint + "\"");

            if(!searchEngine.existStartingPoint())
                printLog(Color.BLACK,"\n>>>>> The initial chems is not in the database ...");

            if(!searchEngine.existEndingPoint())
                printLog(Color.BLACK,"\n>>>>> The final product is not present in the database...");

            if(searchEngine.existStartingPoint() && searchEngine.existEndingPoint()){
                printLog(Color.BLACK,"\n>>>>> The path is theoretically possible !");
                printLog(Color.BLACK,"\n>>>>> Building our rootChemical list ...");
                searchEngine.buildRootChemical();
                printLog(Color.BLACK,"\n>>>>> " + searchEngine.rootChemical.size() + " rootChemicals have been found");
                printLog(Color.BLACK,"\n>>>>> Building our rootToNext hashMap ...");
                searchEngine.buildRootToNextHashMap();
                printLog(Color.BLACK,"\n>>>>> Building our research tree ... ");
                searchEngine.findPaths();
                printLog(Color.BLACK,"\n>>>>> N-ary Tree has been done ! ");
                nbPathLabel.setText("Number of paths discovered : " + searchEngine.finalNodes.size());

                //populate the listview
                listReactions.setStyle("-fx-text-fill: red; -fx-control-inner-background: white;");
                ReactionObservableList = FXCollections.observableArrayList();
                ReactionObservableList.addAll(searchEngine.selectedReaction);
                listReactions.setItems(ReactionObservableList);

                printLog(Color.BLACK,"\n>>>>> The result has been written into the output directory");
            }
            else {
                popup("Please try a possible path");
            }




        }


    }

    @FXML
    void openTestWindow(ActionEvent event) {

        try {
            // Load the FXML file for the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unitTest-view.fxml"));

            // Create a new stage
            Stage newStage = new Stage();

            // Set the root node and controller for the new stage
            newStage.setScene(new Scene(loader.load()));
            newStage.setTitle("New Stage");
            ControllerUnitTest controller = loader.getController();
            controller.start(newStage, newStage.getScene());

            // Show the new stage
            newStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void printLog(Color color, String text){
        logsEntry.appendText(text);
    }

    void popup(String str){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setContentText(str);
        alert.showAndWait();
    }
}
