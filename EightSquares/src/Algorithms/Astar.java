package Algorithms;

import Factory.AlgorithmInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
public class Astar implements AlgorithmInterface {

    class parentPair{
        long parent;
        int cost;

        public parentPair(long parent, int cost) {
            this.parent = parent;
            this.cost = cost;
        }

        public long getParent() {
            return parent;
        }

        public int getCost() {
            return cost;
        }

        public void setParent(long parent) {
            this.parent = parent;
        }
        public void setCost(int cost) {
            this.cost = cost;
        }

    }

    class FrontierPair implements Comparable{
        Long state;
        Double sum;

        public FrontierPair(Long state, double sum) {
            this.state = state;
            this.sum = sum;
        }

        public Long getState() {
            return state;
        }

        public Double getSum() {
            return sum;
        }


        @Override
        public boolean equals(Object obj) {
            FrontierPair f =  (FrontierPair) obj;
            return this.state.equals(f.state);
        }

        @Override
        public int compareTo(Object o) {
            FrontierPair f = (FrontierPair) o;
            return this.sum.compareTo(f.sum);
        }
    }

    /**
     * parents (child,parent,cost)
     * frontier(state,cost+heuristic)
     * visited(state)
     *
     */
    private PriorityQueue<FrontierPair> frontier;
    private HashSet<Long> visited;
    private HashMap<Long,parentPair> parentSet;
    private Grid startGrid;
    private boolean success;
    public int maxDepth;

    public ArrayList<Grid> Path;

    private boolean heuristic;

    private HashSet<Long> inFrontier ;
    private long runningtime;

    public Astar(Grid startGrid,boolean heuristic) {
        this.frontier = new PriorityQueue<>();
        this.visited = new HashSet<>();
        this.parentSet = new HashMap<>();
        this.startGrid = startGrid;
        this.success = false;
        this.maxDepth = 0;
        this.Path = new ArrayList<>();
        this.inFrontier = new HashSet<>();
        this.heuristic = heuristic;
        runningtime=0;
    }

    @Override
    public void DisplayAlgorithm(){
        long startTime = System.nanoTime();
        frontier.add(new FrontierPair(startGrid.get(),0.0));
        inFrontier.add(startGrid.get());
        parentSet.put(startGrid.get(),new parentPair(startGrid.get(), 0));

        while (!frontier.isEmpty()){
            FrontierPair current = frontier.poll();
            inFrontier.remove(current.state);
            Grid currentGrid = new Grid(current.getState());
            int depth = parentSet.get(currentGrid.get()).cost;
            maxDepth = Math.max(maxDepth,depth);
            if(visited.contains(currentGrid.get())){continue;}
            visited.add(currentGrid.get());
            if(currentGrid.isGoal()){
                this.success = true;
                long endTime   = System.nanoTime();
                runningtime = endTime - startTime;
                return;
            }
            for (Grid nextState : currentGrid.getNextStates()) {
                if(!visited.contains(nextState.get()) && !parentSet.containsKey(nextState.get())){
                    frontier.add(new FrontierPair(nextState.get(), depth+1+nextState.heuristic(this.heuristic)));
                    inFrontier.add(nextState.get());
                    parentSet.put(nextState.get(),new parentPair(currentGrid.get(),depth+1));
                }
                else if(inFrontier.contains(nextState.get())){
                    if(depth+1 <  parentSet.get(nextState.get()).cost ){
                        frontier.add(new FrontierPair(nextState.get(), depth+1+nextState.heuristic(this.heuristic)));
                        parentSet.get(nextState.get()).setParent(currentGrid.get());
                        parentSet.get(nextState.get()).setCost(depth+1);
                    }
                }
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
        Grid curr = new Grid(Grid.GOAL);
        while(!curr.get().equals(startGrid.get())){
            Path.add(curr);
            System.out.println(parentSet.get(curr.get()).parent);
            curr = new Grid(parentSet.get(curr.get()).parent);

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
