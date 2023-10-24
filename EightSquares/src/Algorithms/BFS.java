package Algorithms;

import Factory.AlgorithmInterface;

import java.io.*;
import java.util.*;
public class BFS implements AlgorithmInterface {
    private Queue<Long> frontier;
    private HashSet<Long> visited;
    private HashMap<Long,Long> parentSet;
    private Grid startState ;
    private HashMap<Long,Integer> nodeDepth;
    public ArrayList<Grid> Path;
    private boolean success;
    private int maxDepth;
    private long runningtime;
    public BFS(Grid state){
        frontier = new LinkedList<>();
        visited = new HashSet<>();
        parentSet = new HashMap<>();
        startState = state;
        nodeDepth = new HashMap<>();
        Path = new ArrayList<>();
        success = false;
        maxDepth = 0;
        runningtime=0;
    }
    @Override
    public void DisplayAlgorithm(){
        long startTime = System.nanoTime();
        frontier.add(startState.get());
        parentSet.put(startState.get(), null);
        nodeDepth.put(startState.get(),0);
        while(!frontier.isEmpty()){
            Grid current = new Grid(frontier.poll());
            maxDepth = Math.max(maxDepth,nodeDepth.get(current.get()));
            visited.add(current.get());
            if(current.isGoal()){
                this.success = true;
                long endTime   = System.nanoTime();
                runningtime = endTime - startTime;
                return;}
            for (Grid nextState : current.getNextStates()) {
                if(visited.contains(nextState.get()) || parentSet.containsKey(nextState.get())){continue;}
                frontier.add(nextState.get());
                parentSet.put(nextState.get(), current.get());
                nodeDepth.put(nextState.get(),nodeDepth.get(current.get())+1);
            }
        }
        this.success = false;
        long endTime   = System.nanoTime();
        runningtime = endTime - startTime;
    }

    @Override
    public void GetPath(){
        if(!success){
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

    @Override
    public void DisplayPath(){
        if(!success)
            return;
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

    @Override
    public int Cost(){
        int c = Path.size()-1;
        return c;
    }

    @Override
    public int NodesExpanded(){
        return visited.size();
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
    
