import java.io.*;
import java.util.*;
public class BFS {
    private Queue<Long> frontier;
    private HashSet<Long> visited;
    private HashMap<Long,Long> parentSet;
    private Grid startState ;
    private HashMap<Long,Integer> nodeDepth;
    public ArrayList<Grid> Path;
    private boolean success;
    private int maxDepth;
    public BFS(Grid state){
        frontier = new LinkedList<>();
        visited = new HashSet<>();
        parentSet = new HashMap<>();
        startState = state;
        nodeDepth = new HashMap<>();
        Path = new ArrayList<>();
        success = false;
        maxDepth = 0;
    }

    public void excute(){
        frontier.add(startState.get());
        parentSet.put(startState.get(), null);
        nodeDepth.put(startState.get(),0);
        while(!frontier.isEmpty()){
            Grid current = new Grid(frontier.poll());
            maxDepth = Math.max(maxDepth,nodeDepth.get(current.get()));
            visited.add(current.get());
            if(current.isGoal()){
                System.out.println("success");
                this.success = true;
                return;}
            for (Grid nextState : current.getNextStates()) {
                if(visited.contains(nextState.get()) || parentSet.containsKey(nextState.get())){continue;}
                frontier.add(nextState.get());
                parentSet.put(nextState.get(), current.get());
                nodeDepth.put(nextState.get(),nodeDepth.get(current.get())+1);
            }
        }
        System.out.println("failed");
        this.success = false;
    }

    public void GetPath(){
        if(!success){
            System.out.println("There is No Path!");
            return;
        }
        long curr = 0x876543210L;
        Path.add(new Grid(curr));
        while(parentSet.get(curr) != null){
            long parent = parentSet.get(curr);
            Path.add(new Grid(parent));
            curr = parent;
        }
        Collections.reverse(Path);
    }

    // public void printStack(Stack<Grid> parentPath){
    //     while(!parentPath.empty()){
    //         Grid x = parentPath.pop();
    //         char[][] c = x.toCharArray();
    //         for (int i = 0; i < c.length; i++) {
    //             for (int j = 0; j < c[0].length; j++) {
    //                 System.out.print(c[i][j]+" ");
    //             }
    //             System.out.println();
    //         }
    //         System.out.println("=========================");

    //     }
    // }
    public void DisplayPath(){
        try {
            FileWriter fileWriter = new FileWriter("output.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for(Grid next : this.Path){
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
        System.out.println("the cost of the path in BFS: "+c);
    }
    public void NodesExpanded(){
        System.out.println("the Nodes Expanded in BFS: "+visited.size());
    }
    public void Depth(){
        System.out.println("the depth in BFS : "+maxDepth);
    }


    public static void main(String[] args) {
        Long x = 0x876543210L;
        Grid firststate = new Grid(0x876432105L);
        
        char[][] c = firststate.toCharArray();;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                System.out.print(c[i][j]+" ");
            }
            System.out.println();
        }
        
        BFS bss = new BFS(firststate);

        bss.excute();
        bss.GetPath();
        bss.DisplayPath();
        bss.Cost();
        bss.NodesExpanded();
        bss.Depth();
    }
}
    
