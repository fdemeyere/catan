import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        try {
            HexGrid grid = new HexGrid(5, 5);
//            for(CubeCoordinate coord : grid.map.keySet()) {
//                System.out.println(coord.toString());
//            }
            System.out.println("---------------------------------------");
            System.out.println("Number of vertices: " + grid.getVertices().size());
            System.out.println("Number of tiles: " + grid.getMap().size());
            System.out.println("Number of edges: " + grid.getEdges().size());

            System.out.println("Number of vertexID: " + grid.idToVertex.values().size());


            System.out.println(grid.getVertexByID(grid.vectors.get("topright"), grid.vectors.get("null"), grid.vectors.get("topleft")).id.toString());
//            // Create some sample coordinates
//            CubeCoordinate c1 = new CubeCoordinate(0, -1, 1);
//            CubeCoordinate c2 = new CubeCoordinate(-1, 0, 1);
//            CubeCoordinate c3 = new CubeCoordinate(0, 0, 0);
//
//            // Insert a vertex
//            VertexID vertexID = new VertexID(c1, c2, c3);
//            Vertex vertex = new Vertex(vertexID);
//            grid.idToVertex.put(vertexID, vertex);
//
//            Vertex retrievedVertex = grid.getVertexByID(c1, c2, c3);
//            System.out.println("Retrieved Vertex: " + retrievedVertex.id.toString());
        }
        catch(Exception e) {
            System.out.println(e);
        }




    }
}

