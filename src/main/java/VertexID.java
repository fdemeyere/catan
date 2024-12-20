import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class VertexID {

    private CubeCoordinate[] neighborCubes = new CubeCoordinate[3];

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VertexID that = (VertexID) obj;
        return ((Objects.equals(neighborCubes[0], that.neighborCubes[0]))
            && (Objects.equals(neighborCubes[1], that.neighborCubes[1]))
            && (Objects.equals(neighborCubes[2], that.neighborCubes[2]))
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighborCubes[0], neighborCubes[1], neighborCubes[2]);
    }


    public void setNeighborCubeByVertexPosition(int position, Cube cube) {
        neighborCubes[getVertex(position)] = cube.getCubeCoordinate();
    }

    public void setNeighborCubeByVertexPostion(int position, CubeCoordinate coord) {
        neighborCubes[getVertex(position)] = coord;
    }

    VertexID(CubeCoordinate coord1, CubeCoordinate coord2, CubeCoordinate coord3) {
        neighborCubes[0] = coord1;
        neighborCubes[1] = coord2;
        neighborCubes[2] = coord3;
    }

    VertexID() {

    }

    private int getVertex(int vertexIndex) {
        switch (vertexIndex) {
            case 0:
            case 5:
                return 1;
            case 1:
            case 2:
                return 2;
            case 3:
            case 4:
                return 0;
            default:
                throw new IllegalStateException("Unexpected value: " + vertexIndex);
        }

    }

    @Override
    public String toString(){
        String result = "Neighbor 0: (" +  handleCubeCoordinatesToString(0) + " )\n";
        result += "Neighbor 1: (" +  handleCubeCoordinatesToString(1) + " )\n";
        result += "Neighbor 2: (" +  handleCubeCoordinatesToString(2) + " )\n";
        return result;
    }

    private String handleCubeCoordinatesToString(int index) {

        return neighborCubes[index] == null ? "null" : neighborCubes[index].toString();
    }
}
