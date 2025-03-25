import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;

public class Main {

    HexGrid grid;
    Board board;

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    Player human = new Player(new Color(255, 0, 0));
    Player aiPlayer = new Player(new Color(0, 0, 255));

    public static void main(String[] args) {
        Main main = new Main();
        main.setup();
        main.printSetupInfo();
        main.startGame();
    }

    public void printSetupInfo() {
        System.out.println("---------------------------------------");
        System.out.println("Screen width:" + this.screenSize.width);
        System.out.println("Screen height:" + this.screenSize.height);
        System.out.println("Number of vertices: " + grid.getVertices().size());
        System.out.println("Number of tiles: " + grid.getMap().size());
        System.out.println("Number of edges: " + grid.getEdges().size());
    }

    private void setup() {
        try {
            this.grid = new HexGrid(5, 5, this.screenSize);
            this.board = new Board(this.grid);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startGame() {
        while (human.getPoints() < 10 && aiPlayer.getPoints() < 10) {
            DoubleDice.DiceRollResult diceRollResult = DoubleDice.DiceRoll();
        }
    }
}
