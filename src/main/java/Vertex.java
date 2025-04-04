import java.util.Objects;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

public class Vertex {
    private HexGrid grid;

    private CubeCoordinate[] neighborCubes = new CubeCoordinate[3];

    public boolean settlement = false;

    public boolean city = false;

    private int WIDTH = 30;

    private int x2D;
    private int y2D;

    private Player player;

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

    public void set2dCoordinates() {
        int firstNeighborPosition = getFirstNeighborPosition();
        Cube cube = this.getNeighbor(firstNeighborPosition);

        if (this.isPointyTopConfig()) {
            switch (firstNeighborPosition) {
                case 0:
                    x2D = cube.x2D;
                    y2D = cube.y2D + cube.RADIUS;
                    break;
                case 1:
                    x2D = cube.x2D - cube.horizontalSpacing;
                    y2D = cube.y2D - cube.RADIUS / 2;
                    break;
                case 2:
                    x2D = cube.x2D + cube.horizontalSpacing;
                    y2D = cube.y2D - cube.RADIUS / 2;
                    break;
            }
        } else {
            switch (firstNeighborPosition) {
                case 0:
                    x2D = cube.x2D - cube.horizontalSpacing;
                    y2D = cube.y2D + cube.RADIUS / 2;
                    break;
                case 1:
                    x2D = cube.x2D;
                    y2D = cube.y2D - cube.RADIUS;
                    break;
                case 2:
                    x2D = cube.x2D + cube.horizontalSpacing;
                    y2D = cube.y2D + cube.RADIUS / 2;
                    break;
            }
        }
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
        int vertexIndexToCheck;

        switch (firstNeighborPosition) {
            case 0:
                vertexIndexToCheck = 3;
                break;
            case 1:
                vertexIndexToCheck = 5;
                break;
            case 2:
                vertexIndexToCheck = 1;
                break;
            default:
                throw new IllegalStateException("Vertex is not in a known configuration");
        }

        return this == grid.getVertexByNeighbor(this.getNeighbor(firstNeighborPosition), vertexIndexToCheck);
    }

    public void highlightSettlementOption(Graphics2D g2d) {
        BasicStroke bs = new BasicStroke(1);
        g2d.setStroke(bs);
        g2d.setColor(new Color(119, 205, 255, 128));
        g2d.fillOval(this.x2D - this.WIDTH / 2, this.y2D - this.WIDTH / 2, this.WIDTH / 2 * 2,
                this.WIDTH / 2 * 2);
        g2d.setColor(new Color(0, 0, 0, 128));

        g2d.drawOval(this.x2D - this.WIDTH / 2, this.y2D - this.WIDTH / 2, this.WIDTH, this.WIDTH);

    }

    public void drawSettlement(Graphics2D g2d) {
        g2d.setColor(this.player.getColor());
        g2d.fillOval(this.x2D - this.WIDTH / 2, this.y2D - this.WIDTH / 2, this.WIDTH, this.WIDTH);

    }

    public void drawCity(Graphics2D g2d) {
        g2d.setColor(this.player.getColor());
        g2d.fillRect(this.x2D - this.WIDTH / 2, this.y2D - this.WIDTH / 2, this.WIDTH, this.WIDTH);
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

    public int getX2D() {
        return this.x2D;
    }

    public int getY2D() {
        return this.y2D;
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public void placeSettlement(Player player) {
        this.settlement = true;
        this.addProductionPointForNeighborCubes(player.getID());
        this.player = player;
    }

    public void removeSettlement() {
        this.settlement = false;
    }

    public boolean isSettlement() {
        return this.settlement;
    }

    public void placeCity(Player player) {
        this.city = true;
        this.addProductionPointForNeighborCubes(player.getID());
    }

    public void removeCity() {
        this.city = false;
    }

    public boolean isCity() {
        return this.city;
    }

    public void addProductionPointForNeighborCubes(int currentPlayerID) {
        for (CubeCoordinate vector : this.neighborCubes) {
            if (vector != null) {
                Cube neighbor = this.grid.getCube(vector);
                neighbor.increaseProductionByPlayerID(currentPlayerID);
            }
        }
    }

    public Player getPlayer() {
        return this.player;
    }

}
