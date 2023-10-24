package View;
import Algorithms.Grid;
import Factory.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Content extends JPanel{
    public Content(int x, int y, int width, int height){
        this.setBounds(x,y,width,height);
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(width,height));
    }
}

class BorderPanel extends JPanel{
    public BorderPanel(int x, int y, int width, int height){
        this.setBounds(x, y, width, height);
        this.setPreferredSize(new Dimension(width, height));
    }
}

class BorderLayoutPanel extends JPanel{

    private JPanel content;
    private JPanel top;
    private JPanel left;
    private JPanel bottom;
    private JPanel right;
    public BorderLayoutPanel(Color bgColor){
        this.setLayout(new BorderLayout(10,10));

//        this.add(content, BorderLayout.CENTER);

        this.left = new BorderPanel(0,0,100,100);
        this.add(left, BorderLayout.EAST);
        left.setBackground(bgColor);

        this.right = new BorderPanel(700,0, 100, 100);
        this.add(right, BorderLayout.WEST);
        right.setBackground(bgColor);

        this.top = new BorderPanel(0,0,100,100);
        this.add(top, BorderLayout.NORTH);
        top.setBackground(bgColor);

        this.bottom = new BorderPanel(0,700,100,100);
        this.add(bottom, BorderLayout.SOUTH);
        bottom.setBackground(bgColor);

    }

    public void addContentToBottom(Component comp, String place){
        this.bottom.add(comp, place);
    }

    public void addContentToTop(Component comp, String place){
        this.top.add(comp, place);
    }

    public void addContentToLeft(Component comp, String place){
        this.left.add(comp, place);
    }

    public void addContentToRight(Component comp, String place){
        this.right.add(comp, place);
    }

    public void addContent(Component comp){
        this.add(comp, BorderLayout.CENTER);
    }
}

class GridLayoutPanel extends JPanel{

    public GridLayoutPanel(){
        this.setLayout(new GridLayout(3,3,5,5));
        this.setBounds(0,0,300,300);
    }

}

class GridSquare extends JButton{
    public GridSquare(char n, Color bgColor, Color fgColor){
        this.setText(String.valueOf(n));
        this.setFont(new Font("Segoe UI", Font.BOLD, 32));
        this.setBackground(bgColor);
        this.setForeground(fgColor);
        this.setFocusable(false);
    }
}

class GridField extends JTextField{

    private GridInput parent;
    public GridField(GridInput parent){

        this.parent = parent;

        this.setFont(new Font("Segoe UI", Font.BOLD, 25));
        this.setHorizontalAlignment(JTextField.CENTER);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if(!parent.getFreeNumSet().contains(c)){
                    e.consume();
                }else{
                    setText("");
                }
            }
        });
    }
}
class GridInput extends JFrame{

    private MainFrame parent;
    private GridField[][] fields;
    private BorderLayoutPanel panel;
    private HashSet<Character> freeNumSet;

    HashSet<Character> DOMAIN = new HashSet<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8'));

    public GridInput(MainFrame parent){

        this.freeNumSet = new HashSet<>(DOMAIN);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(400, 400);
        this.parent = parent;

        this.panel = new BorderLayoutPanel(Color.DARK_GRAY);

        GridLayoutPanel textGridPanel = new GridLayoutPanel();

        this.panel.add(textGridPanel);

        this.fields = new GridField[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.fields[i][j] = new GridField(this);
                textGridPanel.add(this.fields[i][j]);
            }
        }

        JButton submitB = new JButton();
        submitB.setText("Confirm");
        submitB.setFont(new Font("Segeo UI", Font.BOLD,18));
        submitB.addActionListener(e->{
            for(GridField[] row: this.fields){
                for(GridField f: row){
                    if(f.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"Please enter all numbers.");
                        return;
                    }
                }
            }
            returnInputGrid();
            this.dispose();
        });
        panel.addContentToBottom(submitB, BorderLayout.CENTER);

        this.add(panel);
        this.setVisible(true);
    }

    private void returnInputGrid() {
        char[][] arr = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                arr[i][j] = this.fields[i][j].getText().charAt(0);
            }
        }
        this.parent.setStartState(arr);
        this.parent.drawStartState();

    }

    public Set<Character> getFreeNumSet(){
        updateFreeNumSet();
        return this.freeNumSet;
    }
    private void updateFreeNumSet(){
        this.freeNumSet = new HashSet<>(DOMAIN);
        for(GridField[] row: this.fields){
            for(GridField f: row) {
                //remove number from freeNumSet if found used
                if (!f.getText().isEmpty()) {
                    this.freeNumSet.remove(f.getText().charAt(0));
                }
            }
        }
    }


}

public class MainFrame extends JFrame{

    private JLabel label;
    private JButton button;
    private JPanel buttonContainer;
    private GridSquare[][] squares;
    private JButton nextButton;
    private JButton backButton;
    private JButton inputButton;
    private ArrayList<char[][]> path;
    private int currIndex;
    private char[][] startState;
    private JButton solveBFS;
    private JButton solveDFS;
    private JButton solveManhattanA;
    private JButton solveEuclideanA;


    public MainFrame(){
        this.setTitle("GUI Tutorial");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.darkGray);
        this.setLayout(new BorderLayout(10,10));

        this.currIndex = 0;

        //TODO at first time the ordered grid is drawn
        //TODO take input from user, then display it
        this.startState = new char[][]{{'0','1','2'},{'3','4','5'},{'6','7','8'}};

        this.path = new ArrayList<>();
        path.add(this.startState);


        GridLayoutPanel gridPanel = new GridLayoutPanel();
        BorderLayoutPanel panel = new BorderLayoutPanel(Color.DARK_GRAY);
        panel.addContent(gridPanel);

        this.squares = new GridSquare[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                GridSquare s = new GridSquare(
                        ' ',
                        Color.BLACK,
                        Color.WHITE
                );
                gridPanel.add(s);
                squares[i][j] = s;
            }
        }

        drawGrid(this.startState);

        this.nextButton = new JButton();
        this.nextButton.setText("Next");
        this.nextButton.setFocusable(false);
        panel.addContentToBottom(this.nextButton, BorderLayout.EAST);
        this.nextButton.addActionListener(e->{
            goNext();
            drawGrid(path.get(this.currIndex));
        });

        this.backButton = new JButton();
        this.backButton.setText("Back");
        this.backButton.setFocusable(false);
        this.backButton.setEnabled(false);
        panel.addContentToBottom(this.backButton, BorderLayout.WEST);
        this.backButton.addActionListener(e->{
            goBack();
            drawGrid(path.get(this.currIndex));
        });

        this.inputButton = new JButton();
        this.inputButton.setText("Enter a grid");
        this.inputButton.setFocusable(false);
        panel.addContentToTop(this.inputButton, BorderLayout.WEST);
        this.inputButton.addActionListener(e->{
            new GridInput(this);
            drawGrid(this.startState);
        });

        this.solveBFS = new JButton();
        this.solveBFS.setText("Solve BFS");
        this.solveBFS.setFocusable(false);
        panel.addContentToTop(this.solveBFS, BorderLayout.EAST);
        this.solveBFS.addActionListener(e->{
            //TODO
            AbstractAlgorithmFactoryInterface algoFactory;
            algoFactory = new BFSFactory(Grid.fromCharArray(this.startState));
            AlgorithmInterface algo = algoFactory.CreateAlgorithm();
            algo.DisplayAlgorithm();
            resetWindow();

            //must be called first
            algo.GetPath();
            String msg = "";
            if(!algo.IsThereAPath()){
                msg += "This grid is unsolvable!\n";
            }else{
                msg += "Path found!\n"+
                        "Cost: "+algo.Cost()+"\n";
            }

            msg += "Number of expanded nodes: "+
                    algo.NodesExpanded()+"\n"+
                    "Time elapsed: "+algo.RunningTime();

            JOptionPane.showMessageDialog(null, msg);


            this.path = new ArrayList<>();
            algo.Path().forEach(g->this.path.add(g.toCharArray()));
        });

        this.solveDFS = new JButton();
        this.solveDFS.setText("Solve DFS");
        this.solveDFS.setFocusable(false);
        panel.addContentToTop(this.solveDFS, BorderLayout.EAST);
        this.solveDFS.addActionListener(e->{
            //TODO
            AbstractAlgorithmFactoryInterface algoFactory;
            algoFactory = new DFSFactory(Grid.fromCharArray(this.startState));
            AlgorithmInterface algo = algoFactory.CreateAlgorithm();
            algo.DisplayAlgorithm();
            algo.GetPath();
            resetWindow();

            String msg = "";
            if(!algo.IsThereAPath()){
                msg += "This grid is unsolvable!\n";
            }else{
                msg += "Path found!\n"+
                        "Cost: "+algo.Cost()+"\n";
            }

            msg += "Number of expanded nodes: "+
                    algo.NodesExpanded()+"\n"+
                    "Time elapsed: "+algo.RunningTime();

            JOptionPane.showMessageDialog(null, msg);


            this.path = new ArrayList<>();
            algo.Path().forEach(g->this.path.add(g.toCharArray()));
        });

        this.solveManhattanA = new JButton();
        this.solveManhattanA.setText("Solve Manhattan A*");
        this.solveManhattanA.setFocusable(false);
        panel.addContentToTop(this.solveManhattanA, BorderLayout.EAST);
        this.solveManhattanA.addActionListener(e->{
            //TODO
            AbstractAlgorithmFactoryInterface algoFactory;
            algoFactory = new AstarFactory(
                    Grid.fromCharArray(this.startState),
                    false);
            AlgorithmInterface algo = algoFactory.CreateAlgorithm();
            algo.DisplayAlgorithm();
            algo.GetPath();
            resetWindow();

            String msg = "";
            if(!algo.IsThereAPath()){
                msg += "This grid is unsolvable!\n";
            }else{
                msg += "Path found!\n"+
                        "Cost: "+algo.Cost()+"\n";
            }

            msg += "Number of expanded nodes: "+
                    algo.NodesExpanded()+"\n"+
                    "Time elapsed: "+algo.RunningTime();

            JOptionPane.showMessageDialog(null, msg);


            this.path = new ArrayList<>();
            algo.Path().forEach(g->this.path.add(g.toCharArray()));
        });

        this.solveEuclideanA = new JButton();
        this.solveEuclideanA.setText("Solve Euclidean A*");
        this.solveEuclideanA.setFocusable(false);
        panel.addContentToTop(this.solveEuclideanA, BorderLayout.EAST);
        this.solveEuclideanA.addActionListener(e->{
            AbstractAlgorithmFactoryInterface algoFactory;
            algoFactory = new AstarFactory(
                    Grid.fromCharArray(this.startState),
                    true);
            AlgorithmInterface algo = algoFactory.CreateAlgorithm();
            algo.DisplayAlgorithm();
            algo.GetPath();
            resetWindow();


            String msg = "";
            if(!algo.IsThereAPath()){
                msg += "This grid is unsolvable!\n";
            }else{
                msg += "Path found!\n"+
                        "Cost: "+algo.Cost()+"\n";
            }

            msg += "Number of expanded nodes: "+
                    algo.NodesExpanded()+"\n"+
                    "Time elapsed: "+algo.RunningTime();

            JOptionPane.showMessageDialog(null, msg);


            this.path = new ArrayList<>();
            algo.Path().forEach(g->this.path.add(g.toCharArray()));
        });




        this.add(panel);


        this.setVisible(true);
    }

    public void drawGrid(char[][] charGrid){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(charGrid[i][j] == '0'){
                    this.squares[i][j].setEnabled(false);
                    this.squares[i][j].setText("");
                    this.squares[i][j].setBackground(this.getBackground());
                    this.squares[i][j].setBorderPainted(false);
                }else{
                    this.squares[i][j].setEnabled(true);
                    this.squares[i][j].setText(String.valueOf(charGrid[i][j]));
                    this.squares[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }

    public void goNext(){
        if(this.path.size() == 1){return;}
        if(this.currIndex < this.path.size()-1){
            this.currIndex++;
            this.backButton.setEnabled(true);
        }
        if(this.currIndex >= this.path.size()-1){
            this.nextButton.setEnabled(false);
        }
    }

    public void goBack(){
        if(this.currIndex > 0){
            this.currIndex--;
            this.nextButton.setEnabled(true);
        }
        if(this.currIndex <= 0){
            this.backButton.setEnabled(false);
        }
    }

    public void setStartState(char[][] state){
        for(int i = 0; i < 3; i++){
            this.startState[i] = Arrays.copyOf(state[i], 3);
        }
    }

    public void resetNavigationButtons(){
        this.currIndex = 0;
        this.backButton.setEnabled(false);
        this.nextButton.setEnabled(true);
    }

    public void resetWindow(){
        this.resetNavigationButtons();
        this.drawStartState();
    }
    public void drawStartState() {
        drawGrid(this.startState);
        resetNavigationButtons();
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}


