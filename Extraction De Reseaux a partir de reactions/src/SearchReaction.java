
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class SearchReaction {
    public String startingPoint, endingPoint;
    private ArrayList<Reaction> reactionList;
    private ArrayList<Inhibition> inhibitionList;
    public ArrayList<String> rootChemical = new ArrayList<>();
    private HashMap<String,List<AbstractMap.SimpleEntry<String,Reaction>>> rootToNext = new HashMap<>();
    List<NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>>> finalNodes;
    List<List<String>> paths;
    List<String> selectedReaction = new ArrayList<>();
    public int depth;
    public SearchReaction(ArrayList<Reaction> rList, ArrayList<Inhibition> iList){
        reactionList = rList;
        inhibitionList = iList;
    }

    public void setMaxDepth(int d){
        depth = d;
    }
    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }
    public void setEndingPoint(String endingPoint){
        this.endingPoint = endingPoint;
    }
    public void settingReactionAttributes(){
        for(Reaction r : reactionList){
            r.setAttributes();
        }
    }
    public boolean existStartingPoint(){
        for(Reaction r : reactionList){
            for(String sub : r.getSubstrats()){
                if(sub.equals(startingPoint))
                    return true;
            }
        }
        return false;
    }

    public boolean existEndingPoint(){
        for(Reaction r : reactionList){
            for(String sub : r.getProduits()){
                if(sub.equals(endingPoint))
                    return true;
            }
        }
        return false;
    }

    public void buildRootChemical(){
        if(reactionList.size() == 0){
            System.out.println("The reaction's list is empty ...");
        }
        else{
            for(Reaction r : reactionList){
                for(String s : r.getSubstrats()){
                    if(!rootChemical.contains(s)){
                        rootChemical.add(s);
                    }
                }
            }
        }
    }

    public void buildRootToNextHashMap(){
        if(rootChemical.size() == 0){
            System.out.println("The root list is empty ...");
        }
        else{
            for(String root : rootChemical){
                ArrayList<AbstractMap.SimpleEntry<String,Reaction>> tmp = new ArrayList<>();
                for(Reaction reaction : reactionList){
                    for(String substrat : reaction.getSubstrats()){
                        if(substrat.equals(root)){
                            for(String produit : reaction.getProduits()){
                                tmp.add(new AbstractMap.SimpleEntry<>(produit,reaction));
                            }
                        }
                    }
                }
                rootToNext.put(root,tmp);
            }
        }
    }

    public void prettyPrint(NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> node, String prefix) {
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        if (node == null) {
            return;
        }
        AbstractMap.SimpleEntry<String,Reaction> currentData = node.getData();
        boolean isTerminal = rootChemical.contains(currentData.getKey());

        try {
            FileWriter myWriter = new FileWriter(Paths.get("").toAbsolutePath()+"/src/data/output.txt",true);
            myWriter.write(prefix + currentData.getKey() + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        /*
        if(currentData.getValue() != null){
            if(isTerminal){
                System.out.println(prefix + ANSI_GREEN + currentData.getKey() + " ( " + currentData.getValue().lineRaw +") " + ANSI_RESET);
            }
            else{
                System.out.println(prefix + ANSI_RED + currentData.getKey() + " ( " + currentData.getValue().lineRaw +") " + ANSI_RESET);
            }
        }
        else{
            System.out.println(prefix + ANSI_GREEN + currentData.getKey() + ANSI_RESET);
        }*/

        List<NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>>> children = node.getChildren();
        for (int i = 0; i < children.size() - 1; i++) {
            prettyPrint(children.get(i), prefix + "├── ");
        }
        if (children.size() > 0) {
            prettyPrint(children.get(children.size() - 1), prefix + "├── ");
        }
    }

    public NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> buildTree(AbstractMap.SimpleEntry<String,Reaction> pair, int maxDepth, NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> father){
        if(maxDepth <= 0){
            return null;
        }

        NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> currentNode = new NaryTreeNode<>(new AbstractMap.SimpleEntry<>(pair.getKey(),pair.getValue()));

        if(!currentNode.getData().getKey().equals(startingPoint))
            currentNode.setFather(father);

        List<AbstractMap.SimpleEntry<String,Reaction>> currentChildren = rootToNext.get(pair.getKey());
        if(currentChildren != null){
            for(AbstractMap.SimpleEntry<String,Reaction> p : currentChildren){
                if(!p.getKey().equals(startingPoint)){
                    NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> childNode = buildTree(p,maxDepth-1, currentNode);
                    currentNode.addChild(childNode);
                }
            }
        }
        return currentNode;
    }

    public void findPaths(){
        //first step, mapping our root chems to their products
        buildRootChemical();
        buildRootToNextHashMap();

        NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> root = buildTree(new AbstractMap.SimpleEntry<>(startingPoint,null),depth+1, null);
        prettyPrint(root,"");
        System.out.println("Number of paths found  : " + countPaths(root,endingPoint));
        extractPaths();
        Scanner myObj = new Scanner(System.in);
        System.out.println(">>>>>>  Do you want to write the final reaction to a file ? (yes/no) : ");
        String answer = myObj.nextLine();
        if(answer.equals("yes")){
            writeOutputToFile();
        }

        System.out.println(">>>>>>  Program is over ");

    }

    public void writeOutputToFile(){
        try {
            FileWriter myWriter = new FileWriter(Paths.get("").toAbsolutePath()+"/src/outputFiles/output-d"+depth+"-"+startingPoint+"-"+endingPoint+".txt",true);
            for(String s : selectedReaction){
                myWriter.write(s+"\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void extractPaths(){
        paths = new ArrayList<>();

        for(NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> node : finalNodes){
           List<String> tmp = new ArrayList<>();
           NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> current = node;
           while(true){
               if(current.getData().getValue() != null)
                   tmp.add(current.getData().getValue().lineRaw);

               if(current.father == null)
                   break;
               else{
                   current = current.father;
               }
           }
           if(tmp.size() == depth){
               for(String r : tmp) {
                   if(!selectedReaction.contains(r)){
                       System.out.println(r);
                       selectedReaction.add(r);
                   }
               }
           }
           paths.add(tmp);
        }
    }

    public int countPaths(NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> root, String target) {
        finalNodes = new ArrayList<>();
        countPathsHelper(root, target);
        return finalNodes.size();
    }

    private void countPathsHelper(NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> node, String target) {
       if(node == null){
           return;
       }
       if(node.getData().getKey().equals(target)){
           finalNodes.add(node);
       }
       for(NaryTreeNode<AbstractMap.SimpleEntry<String,Reaction>> child : node.getChildren()){
           countPathsHelper(child,target);
       }

    }


}