package com.example.gui_version;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerPathDetail {

    List<String> myPath;

    private static final String CARD_FXML = "card.fxml";

    public void start (Stage stage, Scene scene, List<String> myPath) throws IOException {

        AnchorPane cardContainer = new AnchorPane();
        cardContainer.setPadding(new Insets(20));

        Collections.reverse(myPath);

        // Create and add the cards to the container using a for loop
        for (int i = 0; i < myPath.size(); i++) {
            // Load a new instance of the card layout for each card
            Node cardNode = FXMLLoader.load(getClass().getResource(CARD_FXML));

            ArrayList<String> substratsArray = new ArrayList<>();
            ArrayList<String> produitsArray = new ArrayList<>();

            try{
                String line = myPath.get(i);
                String[] arrow = line.split(" -> ");
                ArrayList<String> textBetweenQuotes = new ArrayList<>();
                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(line);
                while (m.find()) {
                    textBetweenQuotes.add(m.group(1));
                }
                String nomEnzyme = textBetweenQuotes.get(0);
                m = p.matcher(arrow[1]);
                while (m.find()) {
                    produitsArray.add(m.group(1));
                }
                for(String s : textBetweenQuotes){
                    if(s.equals(nomEnzyme))
                        ;
                    else if(s.equals(produitsArray.get(0)))
                        break;
                    else
                        substratsArray.add(s);
                }

                // Get the nodes for the card from the loaded layout
                Text enzymeName = (Text) cardNode.lookup("#enzymeName");
                Text substrats = (Text) cardNode.lookup("#substrats");
                Text reactifs = (Text) cardNode.lookup("#reactifs");

                enzymeName.setText(nomEnzyme);
                substrats.setText(String.join("+",substratsArray));
                reactifs.setText(String.join("+",produitsArray));
            }
            catch (Exception e){
                System.out.println("can't parse");
            }



            // Set the position of the card within the container
            AnchorPane.setTopAnchor(cardNode, (double) (i * 200)); // Position each card 200px below the previous card
            AnchorPane.setLeftAnchor(cardNode, 0.0);
            AnchorPane.setRightAnchor(cardNode, 0.0);

            // Add the card to the card container
            cardContainer.getChildren().add(cardNode);
        }

        // Get the root node of the existing Scene
        AnchorPane root = (AnchorPane) scene.getRoot();

        // Add the card container to the root node
        root.getChildren().add(cardContainer);



        stage.setScene(scene);
        stage.show();
    }

}
