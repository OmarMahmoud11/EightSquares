import java.util.ArrayList;

public class Grid {
    private Long grid;

    private final int[] DIR = {-3,3,-1,1};

    public static final long GOAL = 0x876543210L;
    public Grid(Long grid){this.grid = grid;}

    public void set(Long grid){this.grid = grid;}

    public Long get(){return this.grid;}

    private int getPositionOf(int number){
        return (int)((grid >> number * 4) & 0x000FL);
    }

    private int getNumberAtSquare(int squareNumber){
        int i = 0;
        Long g = this.grid;
        while(g != 0){
            if((g & 0x000FL) == squareNumber) return i;
            g = g >> 4;
            i++;
        }
        return -1;
    }

    private Long swapNumbers(int numA, int numB){
        if(numA > numB) return swapNumbers(numB, numA);

        long g = this.grid;
        //System.out.println("bef: " + Long.toHexString(g));
        long mask = (((0x000FL << 4*numA) & g) << 4*(numB-numA));
        mask = (mask ^ g) & (0x000FL << 4*numB);
        g = g ^ mask;
        mask = mask >> 4*(numB-numA);
        g = g ^ mask;
        //System.out.println("after: "+ Long.toHexString(g));
        return g;
    }

    private boolean isValidMove(int currSquare, int dir){
        int next = currSquare+dir;
        if(next < 0 || next >= 9) return false;
        int row = currSquare / 3;
        int col = currSquare % 3;
        //row: 0->up 1->middle 2->down
        //col: 0->left 1->middle 2->right
        //dir: -3->up 3->down -1->left 1->right
        return (dir == -3 && row != 0) ||
                (dir == 3 && row != 2) ||
                (dir == -1 && col != 0) ||
                (dir == 1 && col != 2);
    }

    //goal test
    public static boolean isGoal(Long g){
        return g == 0x876543210L;
    }

    private Grid nextState(int dir){
        int curr = getPositionOf(0);
        return new Grid(swapNumbers(0,
                getNumberAtSquare(curr+dir)
        ));
    }

    public ArrayList<Grid> getNextStates(){
        ArrayList<Grid> nextLst = new ArrayList<>();
        for(int dir: DIR){
            if(isValidMove(getPositionOf(0), dir)){
                nextLst.add(nextState(dir));
            }
        }
        return nextLst;
    }

    public char[][] toCharArray(){
        char[][] c = new char[3][3];
        for(int i = 0; i < 9; i++){
            c[i/3][i%3] = Integer.toString(getNumberAtSquare(i))
                    .toCharArray()[0];
        }
        return c;
    }

    public static Grid fromCharArray(char[][] arr){
        long grid = 0L;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                grid |= (3*(long)i+(long)j) << 4 * Integer.parseInt(arr[i][j]+"");
            }
        }
        return new Grid(grid);
    }

    public String toString(){
        return Long.toHexString(this.grid);
    }

}
