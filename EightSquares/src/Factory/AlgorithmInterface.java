package Factory;

import Algorithms.Grid;

import java.util.ArrayList;

public interface AlgorithmInterface {
    void DisplayAlgorithm();
    void DisplayPath();
    int Cost();
    int NodesExpanded();
    int SearchDepth();
    long RunningTime();
    boolean IsThereAPath();

    ArrayList<Grid> Path();
}
