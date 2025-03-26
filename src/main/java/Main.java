import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.UIManager;

import java.awt.Dimension;

public class Main {
    int NUMBER_OF_PLAYERS = 4; // 2-4 players

    HexGrid grid;
    Board board;

    Player[] players = new Player[NUMBER_OF_PLAYERS];

    GameState gameState;

    Color[] playerColors = new Color[] { new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
            new Color(255, 255, 0) };

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.SystemLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Main main = new Main();
        main.createPlayers();
        main.setup();
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
            this.gameState = new GameState(players);
            // this.gameState.printPlayers();
            this.grid = new HexGrid(5, 5, this.screenSize, this.NUMBER_OF_PLAYERS);
            this.board = new Board(this.grid, this.gameState);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startGame() {

        DoubleDice.DiceRoll();
    }

    private void createPlayers() {
        for (int i = 0; i < this.NUMBER_OF_PLAYERS; i++) {
            players[i] = new Player(i, this.playerColors[i]);

        }
    }
}
