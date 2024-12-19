import java.util.List;
import java.util.ArrayList;

public class Vertex {

    VertexID id = new VertexID();

    List<Cube> cubes = new ArrayList<>();

    boolean buildable = true;

    Vertex(){

    }



    public void addCube(Cube cube) {
        if (!cubes.contains(cube)) {
            cubes.add(cube);
        }
    }


}
