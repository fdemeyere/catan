import java.util.List;
import java.util.ArrayList;

public class Resource {
    private List<Cube> cubes = new ArrayList<>();

    String type;

    Resource(String type) {
        this.type = type;
    }

    public List<Cube> getCubes() {
        return cubes;
    }

    public void addCube(Cube cube) {
        cubes.add(cube);
    }
}
