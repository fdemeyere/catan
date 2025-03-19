import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        try {
            HexGrid grid = new HexGrid(5, 5);
            Board board = new Board(grid);

            System.out.println("---------------------------------------");
            System.out.println("Number of vertices: " + grid.getVertices().size());
            System.out.println("Number of tiles: " + grid.getMap().size());
            System.out.println("Number of edges: " + grid.getEdges().size());

            System.out.println("Number of vertexID: " + grid.idToVertex.values().size());

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
