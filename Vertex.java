import java.util.List;
import java.util.ArrayList;

public class Vertex {

    int id;

    List<Cube> cubes = new ArrayList<>();

    boolean buildable = true;

    Vertex(int id){
        this.id = id;
    }



    public void addCube(Cube cube) {
        if (!cubes.contains(cube)) {
            cubes.add(cube);
        }
    }


}
