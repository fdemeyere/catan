import java.util.HashMap;

public class Main {
    static HashMap<CubeCoordinate, Cube> grid = new HashMap<>();

    public static void main(String[] args) {

//        Cube cube = new Cube(0, 0, 0, null, null, null, null, null, null);
        for(int i = -2; i <= 2; i++) {
            for(int j = -2; j <= 2; j++) {
                int z = -i - j;
//                System.out.println("------------------------");
//                System.out.println("x: " + i + ", y: " + j + ", z: " + z);
                if(Math.abs(z) <= 2)
                    grid.put(new CubeCoordinate(i, j, z), new Cube(i, j, z, null, null, null, null, null, null));

            }
        }
//        for(CubeCoordinate coord : grid.keySet()) {
//            System.out.println(coord.toString());
//        }
//        System.out.println(grid.keySet().size());
    }
}
