public class Main {

    HexGrid grid;
    Board board;

    public static void main(String[] args) {
        Main main = new Main();
        try {
            main.grid = new HexGrid(5, 5);
            main.board = new Board(main.grid);

        } catch (Exception e) {
            System.out.println(e);
        }
        main.printSetupInfo();
    }

    public void printSetupInfo() {
        System.out.println("---------------------------------------");
        System.out.println("Number of vertices: " + grid.getVertices().size());
        System.out.println("Number of tiles: " + grid.getMap().size());
        System.out.println("Number of edges: " + grid.getEdges().size());
    }
}
