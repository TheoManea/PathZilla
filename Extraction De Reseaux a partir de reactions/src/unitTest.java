import java.util.ArrayList;

public class unitTest {

    public String testFilePath = "";
    public String generatedFilePath = "";
    public ReadBrenda testDb;
    public ReadBrenda generatedDb;
    public ArrayList<Reaction> testReaction;
    public ArrayList<Reaction> generatedReaction;

    public unitTest(String testF, String generatedF){
        //defining paths
        this.testFilePath = testF;
        this.generatedFilePath = generatedF;
        //calling the reader for each file
        this.testDb = new ReadBrenda(this.testFilePath);
        this.testDb.read("reaction");
        //same here
        this.generatedDb = new ReadBrenda(this.generatedFilePath);
        this.generatedDb.read("reaction");
        //calling the list constructor
        this.testReaction = this.testDb.getReactionList();
        this.generatedReaction = this.generatedDb.getReactionList();
    }

    public void compare(){
        ArrayList<String> testString = new ArrayList<>();
        ArrayList<String> genString = new ArrayList<>();
        ArrayList<String> common = new ArrayList<>();
        ArrayList<String> uncommon = new ArrayList<>();

        for(Reaction r : testReaction)
            testString.add(r.lineRaw);

        for(Reaction r : generatedReaction)
            genString.add(r.lineRaw);

        int matchingItems = 0;
        int nonMatchingItems = 0;

        for (int i = 0; i < testString.size(); i++) {
            String item1 = testString.get(i);
            for (int j = 0; j < genString.size(); j++) {
                String item2 = genString.get(j);
                if (item1.equals(item2)) {
                    matchingItems++;
                    common.add(item1);
                    break; // Break inner loop to avoid duplicate matches
                }
            }
        }


        System.out.println("Nb of common reactions " + matchingItems + "/" + testReaction.size());
        System.out.println("Nb of uncommon reactions " + (testReaction.size() - matchingItems) );

    }

}
