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



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String ans = "";
        while(true){
            System.out.println("Enter grid:");
            //long grid = charArrayToGrid(readGrid(scanner));
            Grid grid = Grid.fromCharArray(readGrid(scanner));
            System.out.println("you entered: "+grid);

            DFS MyDFS = new DFS(grid);
            MyDFS.DisplayDFS();
            MyDFS.DisplayPath();
            MyDFS.Cost();
            MyDFS.NodesExpanded();
            MyDFS.Depth();

            System.out.println("Enter t to exit, any other key to continue");
            ans = scanner.nextLine();
            if(ans.equals("t")) break;

        }
        scanner.close();

    }
}