import java.util.Objects;
import java.awt.Graphics2D;
import java.awt.Color;

public class Vertex {
    private HexGrid grid;

    private CubeCoordinate[] neighborCubes = new CubeCoordinate[3];

    public boolean buildable = true;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Vertex that = (Vertex) obj;
        return ((Objects.equals(neighborCubes[0], that.neighborCubes[0]))
                && (Objects.equals(neighborCubes[1], that.neighborCubes[1]))
                && (Objects.equals(neighborCubes[2], that.neighborCubes[2])));
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighborCubes[0], neighborCubes[1], neighborCubes[2]);
    }

    public void setNeighborCubeByVertexPosition(int position, Cube cube) {
        neighborCubes[getCubePosition(position)] = cube.getCubeCoordinate();
    }

    public void setNeighborCubeByVertexPostion(int position, CubeCoordinate coord) {
        neighborCubes[getCubePosition(position)] = coord;
    }

    Vertex(HexGrid grid, CubeCoordinate coord1, CubeCoordinate coord2, CubeCoordinate coord3) {
        this.grid = grid;
        neighborCubes[0] = coord1;
        neighborCubes[1] = coord2;
        neighborCubes[2] = coord3;
    }

    Vertex(HexGrid grid) {
        this.grid = grid;
    }

    private int getCubePosition(int vertexIndex) {
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
    public String toString() {
        String result = "Neighbor 0: (" + handleCubeCoordinatesToString(0) + " )\n";
        result += "Neighbor 1: (" + handleCubeCoordinatesToString(1) + " )\n";
        result += "Neighbor 2: (" + handleCubeCoordinatesToString(2) + " )\n";
        return result;
    }

    private String handleCubeCoordinatesToString(int index) {

        return neighborCubes[index] == null ? "null" : neighborCubes[index].toString();
    }

    public boolean isPointyTopConfig() throws IllegalStateException {
        int firstNeighborPosition = getFirstNeighborPosition();
        if (firstNeighborPosition == 0) {
            // The 4th vertex of the first occurring neighbor should be
            // the current vertex
            if (this == grid.getVertexByNeighbor(this.getNeighbor(firstNeighborPosition), 3))
                return true;
            return false;
        } else if (firstNeighborPosition == 1) {
            if (this == grid.getVertexByNeighbor(this.getNeighbor(firstNeighborPosition), 5))
                return true;
            return false;
        } else if (firstNeighborPosition == 2) {
            if (this == grid.getVertexByNeighbor(this.getNeighbor(firstNeighborPosition), 1))
                return true;
            return false;
        } else
            throw new IllegalStateException("Vertex is neither in pointy-top or pointy-bottom configuration");

    }

    public void upgrade(Graphics2D g2d) {
        int width = 20;
        int height = 20;
        int x = 0;
        int y = 0;
        if (this.isPointyTopConfig()) {
            // CubCoordinate -> Cube
            int firstNeighborPosition = getFirstNeighborPosition();
            Cube cube = this.getNeighbor(firstNeighborPosition);
            if (firstNeighborPosition == 0) {
                x = cube.x2D;
                y = cube.y2D + cube.RADIUS - height / 2;
            } else if (firstNeighborPosition == 1) {
                x = cube.x2D - cube.horizontalSpacing;
                y = cube.y2D - height / 2 - cube.RADIUS / 2;
            } else {
                x = cube.x2D + cube.horizontalSpacing;
                y = cube.y2D - height / 2 - cube.RADIUS / 2;
            }

            g2d.setColor(Color.ORANGE);
            g2d.fillRect(x - width / 2, y, width, height);
        } else {
            // CubCoordinate -> Cube
            int firstNeighborPosition = getFirstNeighborPosition();
            Cube cube = this.getNeighbor(firstNeighborPosition);
            if (firstNeighborPosition == 0) {
                x = cube.x2D - cube.horizontalSpacing;
                y = cube.y2D - height / 2 + cube.RADIUS / 2;
            } else if (firstNeighborPosition == 1) {
                x = cube.x2D;
                y = cube.y2D - height / 2 - cube.RADIUS;
            } else {
                x = cube.x2D + cube.horizontalSpacing;
                y = cube.y2D - height / 2 + cube.RADIUS / 2;
            }

            g2d.setColor(Color.ORANGE);
            g2d.fillRect(x - width / 2, y, width, height);
        }
    }

    private Cube getNeighbor(int position) throws IllegalArgumentException {
        if (position < 0 || position > 2)
            throw new IllegalArgumentException("Neighbors exist at position 0, 1, 2. Input: " + position);
        return grid.getMap().get(this.neighborCubes[position]);
    }

    private Integer getFirstNeighborPosition() throws IllegalStateException {
        for (int i = 0; i < 3; i++) {
            Cube cube = this.getNeighbor(i);
            if (cube != null) {
                return i;
            }
        }
        throw new IllegalStateException("Vertex has no neighbor cubes");

    }

}
