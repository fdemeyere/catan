import java.util.HashMap;

public class Main {


    public static void main(String[] args) {
        try {
            HexGrid grid = new HexGrid(7, 7);
            for(CubeCoordinate coord : grid.map.keySet()) {
                System.out.println(coord.toString());
            }
            System.out.println(grid.map.keySet().size());
        }
        catch(Exception e) {
            System.out.println(e);
        }




    }
}
