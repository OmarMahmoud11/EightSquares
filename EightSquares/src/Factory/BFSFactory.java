package Factory;
import Algorithms.BFS;
import Algorithms.Grid;

public class BFSFactory implements AbstractAlgorithmFactoryInterface{
    private Grid grid;
    public BFSFactory(Grid grid){
        this.grid = grid;
    }
    @Override
    public AlgorithmInterface CreateAlgorithm() {
        return new BFS(grid);
    }
}
