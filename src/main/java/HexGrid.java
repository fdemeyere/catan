import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import java.util.Collection;

public class HexGrid {

    private final Map<CubeCoordinate, Cube> map;
    private final int width;

    public final Map<VertexID, Vertex> idToVertex;

    private final List<Edge> edges = new ArrayList<>();

    private List<Vertex> vertices = new ArrayList<>();

    public Map<String, CubeCoordinate> vectors = new HashMap<>(Map.of(
            "topleft", new CubeCoordinate(0, -1, 1),
            "topright", new CubeCoordinate(-1, 0, 1),
            "right", new CubeCoordinate(-1, 1, 0),
            "bottomright", new CubeCoordinate(0, 1, -1),
            "bottomleft", new CubeCoordinate(1, 0, -1),
            "left", new CubeCoordinate(1, -1, 0),
            "null", new CubeCoordinate(0, 0, 0)
    ));

    HexGrid(int width, int height) throws Exception {
        if(width < 5) throw new Exception("Board width must be bigger or equal to 5");
        if(width % 2 != 1) throw new Exception("Board width must be an odd number");
        if(width != height) throw new Exception("Board width and height must be equal");

        this.map = new HashMap<>();
        this.idToVertex = new HashMap<>();
        this.width = width;

        for(int x = -(width - 1)/2; x <= (width - 1)/2; x++) {
            for(int y = -(width - 1)/2; y <= (width - 1)/2; y++) {
                int z = -x - y;

                if(Math.abs(z) <= (width - 1)/2) {
                    Cube newCube = new Cube(x, y, z, null, null, null, null, null, null);
                    this.map.put(new CubeCoordinate(x, y, z), newCube);
                    this.setVertices(newCube);

                }


            }
        }

        for(Cube cube : map.values()) {
            this.setEdges(cube);
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

        Edge[] cubeEdges = {cube.e1, cube.e2, cube.e3, cube.e4, cube.e5, cube.e6};

        for(int i = 0; i < 6; i++) {
            if(cubeEdges[i] == null) {
                Cube neighbor = map.get(cube.getCubeCoordinate().add(vectors.get(directions[i])));

                Edge sharedEdge = null;
                if(neighbor != null) {
                    sharedEdge = getEdgeByNeighbor(neighbor, (i + 3) % 6);
                }

                if (sharedEdge == null) {
                    sharedEdge = new Edge(cubeVertices[0].id, cubeVertices[1].id);
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
            case 0: return neighbor.e1;
            case 1: return neighbor.e2;
            case 2: return neighbor.e3;
            case 3: return neighbor.e4;
            case 4: return neighbor.e5;
            case 5: return neighbor.e6;
            default: return null;
        }

    }

    private void setVertices(Cube cube) {

        // Directions in which vertices can be shared
        String[][] directions = {
                {"topleft", "topright"},    // For vertex 'a'
                {"topright", "right"},      // For vertex 'b'
                {"right", "bottomright"},   // For vertex 'c'
                {"bottomright", "bottomleft"}, // For vertex 'd'
                {"bottomleft", "left"},     // For vertex 'e'
                {"left", "topleft"}         // For vertex 'f'
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

//                if (sharedVertex == null) {
//                    sharedVertex = new Vertex(currentVertexID);
//                    idToVertex.put(currentVertexID, sharedVertex);
//                    currentVertexID++;
//                }

                if (sharedVertex == null) {
                    sharedVertex = new Vertex();
//                    idToVertex.put(sharedVertex.id, sharedVertex);

                    vertices.add(sharedVertex);



                }

                cubeVertices[i] = sharedVertex;
                sharedVertex.id.setNeighborCubeByVertexPosition(i, cube);



//                System.out.println("----------------------------------------------------");
//                System.out.println("Current vertex position: " + i);
//                System.out.println(sharedVertex.id);
            }
        }

        cube.a = cubeVertices[0];
        cube.b = cubeVertices[1];
        cube.c = cubeVertices[2];
        cube.d = cubeVertices[3];
        cube.e = cubeVertices[4];
        cube.f = cubeVertices[5];

        for(Vertex vertex : vertices) {
            idToVertex.put(vertex.id, vertex);
        }
    }

    private Vertex getVertexByNeighbor(Cube neighbor, int vertexIndex) {
        switch (vertexIndex) {
            case 0: return neighbor.a;
            case 1: return neighbor.b;
            case 2: return neighbor.c;
            case 3: return neighbor.d;
            case 4: return neighbor.e;
            case 5: return neighbor.f;
            default: return null;
        }

    }



    public Cube getCube(int x, int y, int z) {
        return map.get(new CubeCoordinate(x, y, z));
    }

    public Map<CubeCoordinate, Cube> getMap() {
        return map;
    }

    public Collection<Vertex> getVertices(){
        return idToVertex.values();
    }

    public Vertex getVertexByID(CubeCoordinate coord1, CubeCoordinate coord2, CubeCoordinate coord3) {
        VertexID idToLookUp = new VertexID(coord1, coord2, coord3);

        return idToVertex.get(idToLookUp);
    }

    public List<Edge> getEdges(){
        return edges;
    }


}
