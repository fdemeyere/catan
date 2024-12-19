import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        try {
            HexGrid grid = new HexGrid(5, 5);
//            for(CubeCoordinate coord : grid.map.keySet()) {
//                System.out.println(coord.toString());
//            }
            System.out.println(grid.getVertices().size());
            System.out.println(grid.getMap().size());
            System.out.println(grid.edges.size());

            System.out.println(grid.getVertexByID(grid.vectors.get("topleft"), grid.vectors.get("topright"), new CubeCoordinate(0, 0, 0)));


//            Cube current = grid.map.get(new CubeCoordinate(0, 0, 0));
//            System.out.println(current.toString());
//            System.out.println(grid.getCube(0, -2, 2).c == grid.getCube(0, -1, 1).a);
//            System.out.println(grid.getCube(0, -1, 1).c == grid.getCube(0, 0, 0).a);
//            System.out.println(grid.getCube(0, 0, 0).c == grid.getCube(0, 1, -1).a);
//            System.out.println(grid.getCube(0, 1, -1).c == grid.getCube(0, 2, -2).a);

//            for(Vertex v : grid.vertices) {
//                System.out.println(v.id);
//            }

        }
        catch(Exception e) {
            System.out.println(e);
        }




    }
}

