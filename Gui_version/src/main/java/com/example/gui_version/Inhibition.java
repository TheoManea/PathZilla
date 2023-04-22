package com.example.gui_version;

import java.util.regex.Pattern;

public class Inhibition {
    private String line = "";
    private String nomEnzyme = "";
    private String[] substrats;
    private String[] data;
    private String regex = "(\".*?\")(\\s)(\\:)(\\s)(\".*?\")(\\s)(\\|)";
    public Inhibition(String initialLine){
        line = initialLine.split("(?<=\\|)")[0];
    }
    public String getLine() {
        return line;
    }
    public boolean correctlyFormatted(){
        return Pattern.matches(regex,line);
    }

}