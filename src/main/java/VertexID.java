public class VertexID {

    private CubeCoordinate[] neighborCubes = new CubeCoordinate[3];

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VertexID that = (VertexID) obj;
        return neighborCubes[0] == that.neighborCubes[0] && neighborCubes[1] == that.neighborCubes[1] && neighborCubes[2] == that.neighborCubes[2];
    }

    public void setNeighborCube(int index, Cube cube) {
        neighborCubes[getVertex(index)] = cube.getCubeCoordinate();
    }

    public void setNeighborCube(int index, CubeCoordinate coord) {
        neighborCubes[getVertex(index)] = coord;
    }

    VertexID(CubeCoordinate coord1, CubeCoordinate coord2, CubeCoordinate coord3) {
        setNeighborCube(0, coord1);
        setNeighborCube(1, coord2);
        setNeighborCube(2, coord3);
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
}
