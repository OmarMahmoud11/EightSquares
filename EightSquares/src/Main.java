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

            System.out.println("Choose the algorithm: ");
            System.out.println("1. BFS");
            System.out.println("2. DFS");
            System.out.println("3. A*");
            int x = scanner.nextInt();
            ArrayList<Grid> Path;
            if(x==1){
                BFS MyBFS = new BFS(grid);
                MyBFS.excute();
                MyBFS.GetPath();
                MyBFS.DisplayPath();
                Path = MyBFS.Path;
                System.out.println("To display the path enter y OR any other character to get other options:");
                char c = scanner.next().charAt(0);
                if(c=='y'){
                    int i=0;
                    printGrid(Path.get(0).toCharArray());
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
                while(true){
                    System.out.println("Choose one of the operations:");
                    System.out.println("1. Cost of the path.");
                    System.out.println("2. Nodes expanded.");
                    System.out.println("3. Running time.");
                    System.out.println("4. Exit.");
                    int nn = scanner.nextInt();
                    if(nn==1){
                        MyBFS.Cost();
                    }
                    else if(nn==2){
                        MyBFS.NodesExpanded();
                    }
                    else if(nn==3){
                        //need implementation
                    }
                    else if(nn==4){
                        break;
                    }
                }
            }
            else if(x==2){
                DFS MyDFS = new DFS(grid);
                MyDFS.DisplayDFS();
                MyDFS.GetPath();
                Path = MyDFS.Path;
                System.out.println("To display the path enter y OR any other character to get other options:");
                char c = scanner.next().charAt(0);
                if(c=='y'){
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
                while(true){
                    System.out.println("Choose one of the operations:");
                    System.out.println("1. Cost of the path.");
                    System.out.println("2. Nodes expanded.");
                    System.out.println("3. Running time.");
                    System.out.println("4. Exit.");
                    int nn = scanner.nextInt();
                    if(nn==1){
                        MyDFS.Cost();
                    }
                    else if(nn==2){
                        MyDFS.NodesExpanded();
                    }
                    else if(nn==3){
                        //need implementation
                    }
                    else if(nn==4){
                        break;
                    }
                }
            }
            else if(x==3){
                //need implementation
            }

            System.out.println("Enter t to exit, any other key to continue");
            ans = scanner.nextLine();
            if(ans.equals("t")) break;

        }
        scanner.close();

    }
}