import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import java.util.Collection;
import java.util.Collections;

import java.awt.Dimension;

public class HexGrid {

    private final Map<CubeCoordinate, Cube> map;
    private final int width;

    private final List<Edge> edges = new ArrayList<>();

    private List<Vertex> vertices = new ArrayList<>();

    public Map<String, CubeCoordinate> vectors = new HashMap<>(Map.of(
            "topleft", new CubeCoordinate(0, -1, 1),
            "topright", new CubeCoordinate(-1, 0, 1),
            "right", new CubeCoordinate(-1, 1, 0),
            "bottomright", new CubeCoordinate(0, 1, -1),
            "bottomleft", new CubeCoordinate(1, 0, -1),
            "left", new CubeCoordinate(1, -1, 0),
            "null", new CubeCoordinate(0, 0, 0)));

    private List<Cube> lumberCubes = new ArrayList<>();
    private List<Cube> woolCubes = new ArrayList<>();
    private List<Cube> grainCubes = new ArrayList<>();
    private List<Cube> brickCubes = new ArrayList<>();
    private List<Cube> oreCubes = new ArrayList<>();

    private Map<Integer, List<Cube>> numberToCubes = new HashMap<>();

    private final Random random = new Random();

    private Dimension screenSize;

    HexGrid(int width, int height, Dimension screenSize) throws Exception {
        if (width < 5)
            throw new Exception("Board width must be bigger or equal to 5");
        if (width % 2 != 1)
            throw new Exception("Board width must be an odd number");
        if (width != height)
            throw new Exception("Board width and height must be equal");

        this.map = new HashMap<>();
        this.width = width;
        this.screenSize = screenSize;

        for (int x = -(width - 1) / 2; x <= (width - 1) / 2; x++) {
            for (int y = -(width - 1) / 2; y <= (width - 1) / 2; y++) {
                int z = -x - y;

                if (Math.abs(z) <= (width - 1) / 2) {
                    Cube newCube = new Cube(x, y, z, null, null, null, null, null, null);
                    this.map.put(new CubeCoordinate(x, y, z), newCube);
                    this.setVertices(newCube);

                }

            }
        }

        // Initialize numberToCubes map
        for (int i = 2; i <= 12; i++) {
            numberToCubes.put(i, new ArrayList<>());
        }

        for (Cube cube : map.values()) {
            this.setEdges(cube);
            cube.set2dCoordinates(this.getScreenWidth(), this.getScreenHeight());
        }

        for (Vertex vertex : vertices) {
            vertex.set2dCoordinates();
        }

        for (Edge edge : edges) {
            edge.set2dCoordinates();
        }
    }

    private void setEdges(Cube cube) {
        String[] directions = {
                "topright",
                "right",
                "bottomright",
                "bottomleft",
                "left",
                "topleft"
        };

        Vertex[] cubeVertices = cube.getVertexArray();

        Edge[] cubeEdges = { cube.e1, cube.e2, cube.e3, cube.e4, cube.e5, cube.e6 };

        for (int i = 0; i < 6; i++) {
            if (cubeEdges[i] == null) {
                Cube neighbor = map.get(cube.getCubeCoordinate().add(vectors.get(directions[i])));

                Edge sharedEdge = null;
                if (neighbor != null) {
                    sharedEdge = getEdgeByNeighbor(neighbor, (i + 3) % 6);
                }

                if (sharedEdge == null) {
                    sharedEdge = new Edge(cubeVertices[i], cubeVertices[(i + 1) % 6]);
                    edges.add(sharedEdge);
                }

                cubeEdges[i] = sharedEdge;
            }

        }

        cube.e1 = cubeEdges[0];
        cube.e2 = cubeEdges[1];
        cube.e3 = cubeEdges[2];
        cube.e4 = cubeEdges[3];
        cube.e5 = cubeEdges[4];
        cube.e6 = cubeEdges[5];
    }

    private Edge getEdgeByNeighbor(Cube neighbor, int edgeIndex) {
        switch (edgeIndex) {
            case 0:
                return neighbor.e1;
            case 1:
                return neighbor.e2;
            case 2:
                return neighbor.e3;
            case 3:
                return neighbor.e4;
            case 4:
                return neighbor.e5;
            case 5:
                return neighbor.e6;
            default:
                return null;
        }

    }

    private void setVertices(Cube cube) {

        // Directions in which vertices can be shared
        String[][] directions = {
                { "topleft", "topright" }, // For vertex 'a'
                { "topright", "right" }, // For vertex 'b'
                { "right", "bottomright" }, // For vertex 'c'
                { "bottomright", "bottomleft" }, // For vertex 'd'
                { "bottomleft", "left" }, // For vertex 'e'
                { "left", "topleft" } // For vertex 'f'
        };

        Vertex[] cubeVertices = cube.getVertexArray();

        for (int i = 0; i < 6; i++) {

            if (cubeVertices[i] == null) {
                Cube neighbor1 = map.get(cube.getCubeCoordinate().add(vectors.get(directions[i][0])));
                Cube neighbor2 = map.get(cube.getCubeCoordinate().add(vectors.get(directions[i][1])));

                Vertex sharedVertex = null;

                if (neighbor1 != null) {
                    sharedVertex = getVertexByNeighbor(neighbor1, (i + 2) % 6);
                } else if (neighbor2 != null) {
                    sharedVertex = getVertexByNeighbor(neighbor2, (i + 4) % 6);
                }

                // if (sharedVertex == null) {
                // sharedVertex = new VertexID(currentVertexID);
                // idToVertex.put(currentVertexID, sharedVertex);
                // currentVertexID++;
                // }

                if (sharedVertex == null) {
                    sharedVertex = new Vertex(this);
                    // idToVertex.put(sharedVertex.id, sharedVertex);

                    vertices.add(sharedVertex);

                }

                cubeVertices[i] = sharedVertex;
                sharedVertex.setNeighborCubeByVertexPosition(i, cube);

                // System.out.println("----------------------------------------------------");
                // System.out.println("Current vertex position: " + i);
                // System.out.println(sharedVertex.id);
            }
        }

        cube.a = cubeVertices[0];
        cube.b = cubeVertices[1];
        cube.c = cubeVertices[2];
        cube.d = cubeVertices[3];
        cube.e = cubeVertices[4];
        cube.f = cubeVertices[5];
    }

    public Vertex getVertexByNeighbor(Cube neighbor, int vertexIndex) {
        switch (vertexIndex) {
            case 0:
                return neighbor.a;
            case 1:
                return neighbor.b;
            case 2:
                return neighbor.c;
            case 3:
                return neighbor.d;
            case 4:
                return neighbor.e;
            case 5:
                return neighbor.f;
            default:
                return null;
        }

    }

    public Cube getCube(int x, int y, int z) {
        return map.get(new CubeCoordinate(x, y, z));
    }

    public Map<CubeCoordinate, Cube> getMap() {
        return map;
    }

    public Collection<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void assignResourcesAndNumbers(List<String> resources, List<Integer> blackNumbers) {
        int resourceIndex = 0;
        int numberIndex = 0;

        this.assignRedNumbers();
        for (Cube cube : map.values()) {
            String resource = resources.get(resourceIndex);
            cube.setResource(resource);
            this.categorizeCubeByResource(cube);

            if (!resource.equals("nothing") && cube.getNumber() == 0) {
                cube.setNumber(blackNumbers.get(numberIndex));
                this.categorizeCubeByNumber(cube);
                numberIndex++;
            }

            resourceIndex++;
        }
    }

    private void assignRedNumbers() {
        List<Integer> redNumbers = new ArrayList<>();
        List<Cube> redCubes = new ArrayList<>();
        redNumbers.add(6);
        redNumbers.add(6);
        redNumbers.add(8);
        redNumbers.add(8);

        for (int i : redNumbers) {
            Cube cube = this.getRandomCube();

            while (cube.getNumber() != 0 || this.cubeHasNeighborInList(cube, redCubes)) {
                cube = this.getRandomCube();
            }
            cube.setNumber(i);
            redCubes.add(cube);
        }
    }

    private boolean cubeHasNeighborInList(Cube cube, List<Cube> possibleNeighbors) {
        for (Cube possibleNeighbor : possibleNeighbors) {
            if (this.cubesAreNeighbors(cube, possibleNeighbor))
                return true;
        }
        return false;
    }

    private void categorizeCubeByResource(Cube cube) {
        switch (cube.getResource()) {
            case "lumber":
                this.lumberCubes.add(cube);
            case "wool":
                this.woolCubes.add(cube);
            case "grain":
                this.grainCubes.add(cube);
            case "brick":
                this.brickCubes.add(cube);
            case "ore":
                this.oreCubes.add(cube);
        }
    }

    private void categorizeCubeByNumber(Cube cube) {
        int number = cube.getNumber();
        if (numberToCubes.containsKey(number)) {
            numberToCubes.get(number).add(cube);
        }
    }

    public List<Cube> getCubesByNumber(int number) {
        return numberToCubes.getOrDefault(number, Collections.emptyList());
    }

    public boolean cubesAreNeighbors(Cube cube1, Cube cube2) {
        CubeCoordinate offset = cube1.getCubeCoordinate().subtract(cube2.getCubeCoordinate());
        return this.vectors.values().contains(offset);
    }

    private Cube getRandomCube() {
        if (map.isEmpty()) {
            return null;
        }
        List<Cube> cubeList = new ArrayList<>(map.values());
        int randomIndex = random.nextInt(cubeList.size());
        return cubeList.get(randomIndex);
    }

    public int getScreenWidth() {
        return this.screenSize.width;
    }

    public int getScreenHeight() {
        return this.screenSize.height;
    }

}
