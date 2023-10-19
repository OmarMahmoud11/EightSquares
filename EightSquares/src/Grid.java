import java.util.ArrayList;

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
public class Grid {
    private Long grid;

    private final int[] DIR = {1,-1,3,-3}; //<---

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
        while(i<9){
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
    public boolean isGoal(){
        return this.grid == 0x876543210L;
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
