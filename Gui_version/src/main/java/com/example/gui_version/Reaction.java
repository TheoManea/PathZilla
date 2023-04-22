package com.example.gui_version;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reaction {
    public String lineRaw;
    private String line;
    private String nomEnzyme = "";
    private ArrayList<String> substrats = new ArrayList<>();
    private ArrayList<String> produits = new ArrayList<>();
    private String regex = "(\"(.*?)\"(\\s)(\\:))((\\s)(\".*?\")(\\s)((\\+)*))(->)((\\s)(\".*?\")(\\s)((\\+)*))(\\|)";


    public Reaction(String initialLine){
        line = initialLine.split("(?<=\\|)")[0];
        lineRaw = initialLine;
    }

    /**
     * Arrivé à cette fonction on sait que la réaction est
     * correctement formaté. Cette fonction s'occupe juste
     * d'assigner les valeurs aux attributs
     **/
    public void setAttributes(){
        try{
            // here we are sure that it is correctly formatted
            String[] arrow = line.split(" -> ");
            //isolate text between quotes in a List
            ArrayList<String> textBetweenQuotes = new ArrayList<>();
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(line);
            while (m.find()) {
                textBetweenQuotes.add(m.group(1));
            }
            //name is the first element in the list
            nomEnzyme = textBetweenQuotes.get(0);
            //finding products after the arrow
            m = p.matcher(arrow[1]);
            while (m.find()) {
                produits.add(m.group(1));
            }
            //finding substrat
            for(String s : textBetweenQuotes){
                if(s.equals(nomEnzyme))
                    ;
                else if(s.equals(produits.get(0)))
                    break;
                else
                    substrats.add(s);
            }
        }
        catch (Exception e){
            System.out.println("Error on " + lineRaw);
        }

    }
    public boolean correctlyFormatted(){
        return Pattern.matches(regex,line);
    }
    public void printAttributes(){
        System.out.println("    Nom : " + nomEnzyme);
        System.out.println("    Substrat : " + String.join(", ", substrats));
        System.out.println("    Produits : " + String.join(", ", produits));
    }
    public String getLine() {
        return line;
    }

    public String getNomEnzyme() {
        return nomEnzyme;
    }

    public ArrayList<String> getSubstrats() {
        return substrats;
    }

    public ArrayList<String> getProduits() {
        return produits;
    }
}
