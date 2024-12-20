import java.util.List;
import java.util.ArrayList;

public class Vertex {

    VertexID id;

    List<Cube> cubes = new ArrayList<>();

    boolean buildable = true;

    Vertex() {
        this.id = new VertexID();
    }

    Vertex(VertexID id) {
        this.id = id;
    }


    public void addCube(Cube cube) {
        if (!cubes.contains(cube)) {
            cubes.add(cube);
        }
    }

    public void makeIdImmutable() {

    }
}