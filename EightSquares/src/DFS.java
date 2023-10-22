import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DFS {
    private Stack<Long> fronteir;
    private HashSet<Long> explored;
    private HashMap<Long,Long> Parents;
    private HashMap<Long , Integer> NodesDepth;
    private Grid StartState;
    public ArrayList<Grid> Path;
    private boolean success;
    private int maxDepth;


    public DFS(Grid start_State){
        fronteir = new Stack<Long>();
        explored = new HashSet<Long>();
        Parents = new HashMap<Long,Long>();
        NodesDepth = new HashMap<Long,Integer>();
        StartState = start_State;
        Path = new ArrayList<Grid>();
        success = false;
        maxDepth=0;
    }

    public void DisplayDFS(){
        fronteir.push(StartState.get());
        Parents.put(StartState.get(),StartState.get());
        NodesDepth.put(StartState.get(),0);
        while (!fronteir.isEmpty()) {
            Grid cur = new Grid(fronteir.pop());
            explored.add(cur.get());
            if (cur.isGoal()) {
                System.out.println("Success");
                success = true;
                return;
            }
            for (Grid next : cur.getNextStates()) {
                if (!explored.contains(next.get()) && !Parents.containsKey(next.get())) {
                    fronteir.push(next.get());
                    Parents.put(next.get(), cur.get());
                    NodesDepth.put(next.get(),NodesDepth.get(cur.get())+1);
                    maxDepth = Math.max(maxDepth,NodesDepth.get(next.get()));
                }
            }
        }
        System.out.println("Failer");
    }
    public void GetPath(){
        if(!success){
            System.out.println("There is No Path!");
            return;
        }
        Grid curr = new Grid(Grid.GOAL);
        while(curr.get() != StartState.get()){
            Path.add(curr);
            curr = new Grid(Parents.get(curr.get()));
        }
        Path.add(curr);
        Collections.reverse(Path);
    }

    public void DisplayPath(){
        GetPath();
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(Grid next : Path){
                for (char[] c : next.toCharArray()){
                    printWriter.print(c);
                    printWriter.println();
                }
                printWriter.println();
            }
            printWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void Cost(){
        int c = Path.size()-1;
        System.out.println("the cost of the path in DFS: "+c);
    }
    public void NodesExpanded(){
        System.out.println("the Nodes Expanded in DFS: "+explored.size());
    }
    public void Depth(){
        System.out.println("the depth in DFS : "+maxDepth);
    }
}
