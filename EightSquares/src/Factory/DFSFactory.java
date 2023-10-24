package Factory;

import Algorithms.DFS;
import Algorithms.Grid;

public class DFSFactory implements AbstractAlgorithmFactoryInterface {
    private Grid grid;
    public DFSFactory(Grid grid){
        this.grid = grid;
    }
    @Override
    public AlgorithmInterface CreateAlgorithm() {
        return new DFS(grid);
    }
}
