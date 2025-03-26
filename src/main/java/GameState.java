public class GameState {
    private Player[] players;
    private Player currentPlayer;

    public GameState(Player[] players) {
        this.players = players;
        this.currentPlayer = players[0];
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void switchTurn() {
        currentPlayer = players[(currentPlayer.getID() + 1) % this.players.length];
    }

    public void printPlayers() {
        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i].getColor());
        }
    }
}
