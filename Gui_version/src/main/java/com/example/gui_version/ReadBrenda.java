package com.example.gui_version;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadBrenda {
    private String filepath = "";
    private ArrayList<Reaction> reactionList = new ArrayList<Reaction>();
    private ArrayList<Inhibition> inhibitionList = new ArrayList<Inhibition>();
    public ReadBrenda(String path){
        filepath = path;
    }
    public void read(String mode){
        try {
            // reading Objects
            File myObj = new File(filepath);
            Scanner myReader = new Scanner(myObj);

            // boucle de lecture
            boolean reactionTurn = false;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                // on traite le cas des lignes non-vides
                if(data.length() > 0) {
                    // si la dites ligne nous indique la partie du fichier
                    if (data.charAt(0) == '/' && data.charAt(1) == '/'){
                        if(data.charAt(3) == 'R'){
                            reactionTurn = true;
                        }
                        else if(data.charAt(3) == 'I'){
                            reactionTurn = false;
                        }
                    }
                }

                // maintenant on lit en fonction du mode
                if(data.length() > 0 && data.charAt(0) != '/')
                    switch (mode){
                        case "reaction":
                            if(reactionTurn) {
                                reactionList.add(new Reaction(data));
                            }
                            break;
                        case "inhibition":
                            if(!reactionTurn) {
                                inhibitionList.add(new Inhibition(data));
                            }
                            break;
                        case "plain":
                            System.out.println(data);
                            break;
                    }

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Erreur de lecture.");
            e.printStackTrace();
        }
    }
    public void settingReactionAttributes(){

    }
    public int getNbreaction(){
        int i = 0;
        for(Reaction r : reactionList){
            if(r.correctlyFormatted())
                i++;
        }
        return i;
    }

    public int getNbinhibition(){
        int j = 0;
        for(Inhibition i : inhibitionList){
            if(i.correctlyFormatted())
                j++;
        }
        return j;
    }


    public ArrayList<Reaction> getReactionList(){ return reactionList; }
    public ArrayList<Inhibition> getInhibitionList() { return inhibitionList; }

}
