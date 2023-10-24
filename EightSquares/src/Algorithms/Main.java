package Algorithms;

import Algorithms.BFS;
import Algorithms.DFS;
import Algorithms.Grid;
import Factory.*;

import java.util.ArrayList;
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

    public static void PrintPath(ArrayList<Grid> Path){
        Scanner scanner = new Scanner(System.in);
        int i=0;
        printGrid(Path.get(i).toCharArray());
        while(true){
            System.out.println("To next state enter y ,For previous state enter n ,To stop and exit enter any other character:");
            char next = scanner.next().charAt(0);
            if(next=='y' && i<Path.size()-1){
                i++;
                printGrid(Path.get(i).toCharArray());
            }
            else if(next=='y' && i==Path.size()-1){
                System.out.println("Error, We reach the Goal !");
            }
            else if(next=='n' && i>0){
                i--;
                printGrid(Path.get(i).toCharArray());
            }
            else if(next=='n' && i==0){
                System.out.println("Error, It is the first state!");
            }
            else{
                break;
            }
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
            /*****************************************************************************************/
            System.out.println("Choose the algorithm: ");
            System.out.println("1. BFS");
            System.out.println("2. DFS");
            System.out.println("3. A*");
            String A = scanner.nextLine();
            AbstractAlgorithmFactoryInterface Algorithm;
            if(A.equalsIgnoreCase("BFS"))
                Algorithm = new BFSFactory(grid);
            else if(A.equalsIgnoreCase("DFS"))
                Algorithm = new DFSFactory(grid);
            else if(A.equalsIgnoreCase("Astar")){
                System.out.println("Choose heuristic type:");
                System.out.println("1. Manhattan");
                System.out.println("2. Euclidean");
                String heuristic = scanner.nextLine();
                if(heuristic.equalsIgnoreCase("Manhattan")){
                    Algorithm = new AstarFactory(grid,true);
                }
                else if(heuristic.equalsIgnoreCase("Euclidean")){
                    Algorithm = new AstarFactory(grid,false);
                }
                else {
                    System.out.println("Error the input invalid !");
                    continue;
                }
            }
            else{
                System.out.println("Error the input invalid !");
                continue;
            }
            AlgorithmInterface algorithm = Algorithm.CreateAlgorithm();
            algorithm.DisplayAlgorithm();
            if(!algorithm.IsThereAPath()){
                System.out.println("Failed !");
                while (true) {
                    System.out.println("Choose the number of the operations:");
                    System.out.println("1. Nodes Expanded");
                    System.out.println("2. Search Depth");
                    System.out.println("3. Running Time");
                    System.out.println("4. Exit");

                    int x = scanner.nextInt();
                    if(x==1){
                        System.out.println("Nodes expanded "+algorithm.NodesExpanded());
                    }
                    else if(x==2){
                        System.out.println("Search Depth: "+algorithm.SearchDepth());
                    }
                    else if(x==3){
                        System.out.println("Running Time: "+algorithm.RunningTime()+" NanoSeconds");
                    }
                    else if(x==4){
                        break;
                    }
                    else {
                        System.out.println("Invalid Input!");
                    }
                }
            }
            else{
                System.out.println("Success !");
                while (true) {
                    System.out.println("Choose the number of the operations:");
                    System.out.println("1. Path To Goal");
                    System.out.println("2. Cost Of The Path");
                    System.out.println("3. Nodes Expanded");
                    System.out.println("4. Search Depth");
                    System.out.println("5. Running Time");
                    System.out.println("6. Print Path Step By Step");
                    System.out.println("7. Exit");

                    int x = scanner.nextInt();
                    scanner.nextLine();
                    if(x==1){
                        algorithm.DisplayPath();
                    }
                    else if(x==2){
                        System.out.println("The cost of the path: "+algorithm.Cost());
                    }
                    else if(x==3){
                        System.out.println("Nodes expanded "+algorithm.NodesExpanded());
                    }
                    else if(x==4){
                        System.out.println("Search Depth: "+algorithm.SearchDepth());
                    }
                    else if(x==5){
                        System.out.println("Running Time: "+algorithm.RunningTime()+" NanoSeconds");
                    }
                    else if(x==6){
                        PrintPath(algorithm.Path());
                    }
                    else if(x==7){
                        break;
                    }
                    else {
                        System.out.println("Invalid Input!");
                    }
                }
            }

            /**********************************************************************************/
            System.out.println("Enter t to exit, any other key to continue");
            ans = scanner.nextLine();
            if(ans.equals("t")) break;

        }
        scanner.close();

    }
}