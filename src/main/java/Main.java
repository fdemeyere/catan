import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.UIManager;

import java.awt.Dimension;

public class Main {
    int NUMBER_OF_PLAYERS = 2; // 2-4 players

    HexGrid grid;
    Board board;

    Player[] players = new Player[NUMBER_OF_PLAYERS];

    GameState gameState;

    Color[] playerColors = new Color[] { new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255),
            new Color(255, 255, 0) };

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
            this.grid = new HexGrid(5, 5, this.screenSize, this.players);
            this.gameState = new GameState(players, this.grid);
            // this.board = new Board(this.grid, this.gameState);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startGame() {
        try {
            this.board = new Board(grid, gameState);
        } catch (Exception e) {
            e.printStackTrace(); // Print the exception to identify the issue
        }
        this.roundOne();
        DoubleDice.DiceRoll();
        this.grid.giveResources(DoubleDice.DiceRollResult.sum);
    }

    private void createPlayers() {
        for (int i = 0; i < this.NUMBER_OF_PLAYERS; i++) {
            players[i] = new Player(i, this.playerColors[i]);
        }
    }

    private Player[] getRoundOnePlayerOrder() {
        Player[] roundOnePlayerOrder = new Player[this.NUMBER_OF_PLAYERS * 2];

        for (int i = 0; i < this.players.length; i++) {
            roundOnePlayerOrder[i] = this.players[i];
            roundOnePlayerOrder[this.players.length * 2 - 1 - i] = this.players[i];
        }

        return roundOnePlayerOrder;
    }

    private void roundOne() {
        Player[] playerOrder = getRoundOnePlayerOrder();
        placeSettlementForPlayer(playerOrder, 0); // Start with the first player
    }

    private void placeSettlementForPlayer(Player[] playerOrder, int index) {
        if (index >= playerOrder.length) {
            return; // All players have placed their settlements and roads
        }

        Player player = playerOrder[index];
        gameState.setCurrentPlayer(player);
        board.settlementButton.click();
        board.mustPlaceSettlement = true;

        // Listen for settlement placement event
        board.setSettlementPlacedListener(() -> {
            board.roadButton.click();
            board.mustPlaceRoad = true;

            // Listen for road placement event
            board.setRoadPlacedListener(() -> {
                // Move to the next player after both settlement &s road are placed
                placeSettlementForPlayer(playerOrder, index + 1);
            });
        });
    }

}
