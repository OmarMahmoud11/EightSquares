import java.util.*;
public class BFS {
    Queue<Long> buffer;
    HashSet<Long> visited;
    HashMap<Long,Long> parentSet;
    Grid currentState ;
    
    public BFS(Grid state){
        buffer = new LinkedList<>();
        visited = new HashSet<>();
        parentSet = new HashMap<>();
        currentState = state;

    }

    public void excute(){
        buffer.add(currentState.get());
        parentSet.put(currentState.get(), null);
        while(!buffer.isEmpty()){
            Grid current = new Grid(buffer.poll());
            visited.add(current.get());
            if(current.isGoal()){
                System.out.println("success");
                printStack(getParentsOfSol());
                return;}
            for (Grid nextState : current.getNextStates()) {
                if(visited.contains(nextState.get()) || parentSet.containsKey(nextState.get())){continue;}
                buffer.add(nextState.get());
                parentSet.put(nextState.get(), current.get());
            }
        }
        System.out.println("failed");
    }

    public Stack<Grid> getParentsOfSol(){
        Stack<Grid> parentPath = new Stack<>();
        long curr = 0x876543210L;
        parentPath.push(new Grid(curr));
        while(parentSet.get(curr) != null){
            long parent = parentSet.get(curr);
            parentPath.push(new Grid(parent));
            curr = parent;
        }
        return parentPath;
    }

    public void printStack(Stack<Grid> parentPath){
        while(!parentPath.empty()){
            Grid x = parentPath.pop();
            char[][] c = x.toCharArray();
            for (int i = 0; i < c.length; i++) {
                for (int j = 0; j < c[0].length; j++) {
                    System.out.print(c[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println("=========================");

        }
    }

    public static void main(String[] args) {
        Long x = 0x876543210L;
        Grid firststate = new Grid(0x876542103L);
        
        char[][] c = firststate.toCharArray();;
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[0].length; j++) {
                System.out.print(c[i][j]+" ");
            }
            System.out.println();
        }
        
        BFS bss = new BFS(firststate);

        bss.excute();
    }
}
    
