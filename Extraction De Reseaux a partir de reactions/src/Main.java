import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // get path of my db
        Path currentRelativePath = Paths.get("");
        //String s = currentRelativePath.toAbsolutePath().toString()+"/src/data/test2.txt";
        String s = currentRelativePath.toAbsolutePath().toString()+"/src/data/brenda";

        Scanner myObj = new Scanner(System.in);
        System.out.println(">>>>>> Which operation you want to perform ? (test/search)");
        String choice = myObj.nextLine();

        if(choice.equals("search"))
            newSearch(s);
        else
            newTest();
    }

    public static void newTest(){
        Path currentRelativePath = Paths.get("");
        Scanner myObj = new Scanner(System.in);
        System.out.println(">>>>>> Name of the .ssa test file : ");
        String name = myObj.nextLine();
        String ssaFileName = currentRelativePath.toAbsolutePath()+"/src/testFiles/"+name+".ssa";
        String genFileName = currentRelativePath.toAbsolutePath()+"/src/outputFiles/output-"+name+".txt";

        unitTest test = new unitTest(ssaFileName,genFileName);
        test.compare();
    }

    public static void newSearch(String s){
        Scanner myObj = new Scanner(System.in);
        System.out.println(">>>>>> Enter starting point : ");
        String startingPoint = myObj.nextLine();
        System.out.println(">>>>>> Enter final point : ");
        String endingPoint = myObj.nextLine();
        System.out.println(">>>>>> Enter the search depth : ");
        String depth = myObj.nextLine();

        ArrayList<Reaction> l = new ArrayList<>();
        //reading my File
        ReadBrenda db = new ReadBrenda(s);
        db.read("reaction");

        SearchReaction sr = new SearchReaction(db.getReactionList(),db.getInhibitionList());
        sr.settingReactionAttributes();
        sr.setStartingPoint(startingPoint);
        sr.setEndingPoint(endingPoint);
        sr.setMaxDepth(Integer.valueOf(depth));

        System.out.println(">>>>>>  Searching for : " + sr.startingPoint + " -> ... -> " + sr.endingPoint);
        System.out.println(">>>>>>  Depth : " + sr.depth);

        if(sr.existStartingPoint() && sr.existEndingPoint())
            System.out.println("The path is theoratically possible");
        else{
            System.out.println("The path is not possible");
            System.exit(0);
        }

        sr.findPaths();
    }
}