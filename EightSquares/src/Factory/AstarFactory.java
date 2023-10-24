package Factory;
import Algorithms.Astar;
import Algorithms.Grid;

public class AstarFactory implements AbstractAlgorithmFactoryInterface {
    private Grid grid;
    private boolean Heuristic;
    public AstarFactory(Grid grid, boolean Heuristic){
        this.grid = grid;
        this.Heuristic = Heuristic;
    }
    @Override
    public AlgorithmInterface CreateAlgorithm(){
        return new Astar(grid,Heuristic);
    }

}
