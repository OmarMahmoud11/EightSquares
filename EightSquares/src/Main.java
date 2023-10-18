import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static char[][] readGrid(Scanner scanner){
        char[][] grid = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                grid[i][j] = Integer.toString(scanner.nextInt()).charAt(0);
            }
        }
        scanner.nextLine();
        return grid;
    }
    public static void printGrid(char[][] grid){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static long charArrayToGrid(char[][] arr){
        long grid = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                grid |= (3*(long)i+(long)j) << 4 * Integer.parseInt(arr[i][j]+"");
            }
        }
        return grid;
    }
    public static char[][] gridToCharArray(long grid){
        char[][] c = new char[3][3];
        for(int i = 0; i < 9; i++){
            c[i/3][i%3] = Integer.toString(getNumberAtPosition(i, grid)).toCharArray()[0];
        }
        return c;
    }
    /*
    - grid <key, value> = <no, place>
    -Grid contains places of all no and the blank square.
    -Keys (which represent numbers) start from the lsb.
    -The least significant byte represents the place of the blank square.
    -The next byte represents 1's place and so on.
    -ex: (876543210)_16 represents the goal ordered grid.
    -ex: the grid:
        3 6 5
        2 _ 4
        7 8 1
        is represented as (761250384)_16.
    -Grid places indices:
    0 1 2
    3 4 5
    6 7 8
    */

    public static int getPositionOf(int n, long grid){
        return (int)((grid >> n * 4) & 0x000FL);
    }

    public static int getNumberAtPosition(int i, long grid){
        int n = 0;
        while(grid != 0){
            if((grid & 0x000F) == i) return n;
            grid = grid >> 4;
            n++;
        }
        return -1;
    }

    public static long swapNumbers(int i, int j, long grid){
        //i,j are numbers not squares
        if(i > j) return swapNumbers(j, i, grid);
        System.out.println("bef: " + Long.toHexString(grid));
        long mask = (((0x000FL << 4*i) & grid) << 4*(j-i));
        mask = (mask ^ grid) & (0x000FL << 4*j);
        grid = grid ^ mask;
        mask = mask >> 4*(j-i);
        grid = grid ^ mask;
        System.out.println("after: "+ Long.toHexString(grid));
        return grid;
    }

    public static boolean isValidMove(int curr, int dir){
        int next = curr+dir;
        if(next < 0 || next >= 9) return false;
        int row = curr / 3;
        int col = curr % 3;
        //row: 0->up 1->middle 2->down
        //col: 0->left 1->middle 2->right
        //dir: -3->up 3->down -1->left 1->right
        return (dir == -3 && row != 0) ||
                (dir == 3 && row != 2) ||
                (dir == -1 && col != 0) ||
                (dir == 1 && col != 2);
    }

    //goal test
    public static boolean isGoal(long grid){
        return grid == 0x876543210L;
    }

    public static long nextState(long grid, int dir){
        //N S E W 3 -3 1 -1
        int curr = getPositionOf(0, grid);
        //swapNumbers takes numbers
        return swapNumbers(0, getNumberAtPosition(curr+dir, grid), grid);
    }

    public static void printNextStates(long grid){
        int[] dirs = {-3,3,-1,1};
        System.out.println("---------------------------------");
        for(int dir: dirs){
            if(isValidMove(getPositionOf(0, grid), dir)){
                System.out.println("position of zero is at "+getPositionOf(0,grid));
                System.out.println("move: "+dir);
                printGrid(gridToCharArray(nextState(grid, dir)));
                System.out.println("---------------------------------");
            }
        }
    }

    public static void dfs(HashSet<Long> explored, Grid curr, HashMap<Grid, Grid> parents){
        explored.add(curr.get());

        if(Grid.isGoal(curr.get())){return;}

        for(Grid next: curr.getNextStates()){
            if(!explored.contains(next.get())){
                parents.put(next, curr);
                dfs(explored, next, parents);
            }
        }
    }

    public static void printPath(HashMap<Grid, Grid> parents){
//        long curr = Grid.GOAL;
//        while(curr != null){
//
//        }
    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ans = "";
        while(true){
            System.out.println("Enter grid:");
            //long grid = charArrayToGrid(readGrid(scanner));
            Grid grid = Grid.fromCharArray(readGrid(scanner));
            System.out.println("you entered: "+grid);
            //System.out.println(Long.toHexString(grid));
            //printNextStates(grid);
            ArrayList<Grid> nextLst = grid.getNextStates();
            System.out.println("---------------------------------");
            for(Grid g: nextLst){
                printGrid(g.toCharArray());
                System.out.println(Long.toHexString(g.get()));
                System.out.println("---------------------------------");
            }

            System.out.println("Enter t to exit, any other key to continue");
            ans = scanner.nextLine();
            if(ans.equals("t")) break;

        }
        scanner.close();

    }
}