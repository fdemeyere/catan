import java.util.HashMap;

public class HexGrid {

    HashMap<CubeCoordinate, Cube> map;

    HexGrid(int width, int height) throws Exception {
        if(width < 5) throw new Exception("Board width must be bigger or equal to 5");
        if(width % 2 != 1) throw new Exception("Board width must be an odd number");
        if(width != height) throw new Exception("Board width and height must be equal");

        map = new HashMap<>();

        for(int i = -(width - 1)/2; i <= (width - 1)/2; i++) {
            for(int j = -(width - 1)/2; j <= (width - 1)/2; j++) {
                int z = -i - j;

                if(Math.abs(z) <= (width - 1)/2)
                    map.put(new CubeCoordinate(i, j, z), new Cube(i, j, z, null, null, null, null, null, null));

            }
        }
    }
}
