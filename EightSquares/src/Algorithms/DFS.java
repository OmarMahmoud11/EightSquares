package Algorithms;

import Factory.AlgorithmInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DFS implements AlgorithmInterface {
    private Stack<Long> fronteir;
    private HashSet<Long> explored;
    private HashMap<Long,Long> Parents;
    private HashMap<Long , Integer> NodesDepth;
    private Grid StartState;
    public ArrayList<Grid> Path;
    private boolean success;
    private int maxDepth;
    private long runningtime;


    public DFS(Grid start_State){
        fronteir = new Stack<Long>();
        explored = new HashSet<Long>();
        Parents = new HashMap<Long,Long>();
        NodesDepth = new HashMap<Long,Integer>();
        StartState = start_State;
        Path = new ArrayList<Grid>();
        success = false;
        maxDepth=0;
        runningtime=0;
    }

    @Override
    public void DisplayAlgorithm(){
        long startTime = System.nanoTime();
        fronteir.push(StartState.get());
        Parents.put(StartState.get(),StartState.get());
        NodesDepth.put(StartState.get(),0);
        while (!fronteir.isEmpty()) {
            Grid cur = new Grid(fronteir.pop());
            maxDepth = Math.max(maxDepth,NodesDepth.get(cur.get()));
            explored.add(cur.get());
            if (cur.isGoal()) {
                success = true;
                long endTime   = System.nanoTime();
                runningtime = endTime - startTime;
                return;
            }
            for (Grid next : cur.getNextStates()) {
                if (!explored.contains(next.get()) && !Parents.containsKey(next.get())) {
                    fronteir.push(next.get());
                    Parents.put(next.get(), cur.get());
                    NodesDepth.put(next.get(),NodesDepth.get(cur.get())+1);
                }
            }
        }
        long endTime   = System.nanoTime();
        runningtime = endTime - startTime;
    }

    @Override
    public void GetPath(){
        if(!success){
            return;
        }
        Grid curr = new Grid(Grid.GOAL);
        while(!curr.get().equals(StartState.get())){
            Path.add(curr);
            curr = new Grid(Parents.get(curr.get()));
        }
        Path.add(curr);
        Collections.reverse(Path);
    }

    @Override
    public void DisplayPath(){
        if(!success){
            return;
        }
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

    @Override
    public int Cost(){
        int c = Path.size()-1;
       return c;
    }

    @Override
    public int NodesExpanded(){
        return explored.size();
    }

    @Override
    public int SearchDepth(){
        return maxDepth;
    }

    @Override
    public long RunningTime(){
        return runningtime;
    }

    @Override
    public boolean IsThereAPath(){
        return success;
    }

    @Override
    public ArrayList<Grid> Path(){
        return Path;
    }
}
