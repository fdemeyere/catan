import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;


public class HexGrid {

    private Map<CubeCoordinate, Cube> map;
    int width;

    List<Vertex> vertices = new ArrayList<>();

    Map<String, CubeCoordinate> vectors = new HashMap<>(Map.of(
            "topleft", new CubeCoordinate(0, -1, 1),
            "topright", new CubeCoordinate(-1, 0, 1),
            "right", new CubeCoordinate(-1, 1, 0),
            "bottomright", new CubeCoordinate(0, 1, -1),
            "bottomleft", new CubeCoordinate(1, 0, -1),
            "left", new CubeCoordinate(1, -1, 0)
    ));

    HexGrid(int width, int height) throws Exception {
        if(width < 5) throw new Exception("Board width must be bigger or equal to 5");
        if(width % 2 != 1) throw new Exception("Board width must be an odd number");
        if(width != height) throw new Exception("Board width and height must be equal");

        map = new HashMap<>();
        this.width = width;

        for(int x = -(width - 1)/2; x <= (width - 1)/2; x++) {
            for(int y = -(width - 1)/2; y <= (width - 1)/2; y++) {
                int z = -x - y;

                if(Math.abs(z) <= (width - 1)/2) {
                    Cube newCube = new Cube(x, y, z, null, null, null, null, null, null);
                    map.put(new CubeCoordinate(x, y, z), newCube);
                    setVertices(newCube);

                }


            }
        }


    }







//    private void setVertices(Cube cube) {
//
//
//        //      Set a-vertex
//        if(cube.a == null) {
//
//
//            Cube topleft = map.get(cube.getCubeCoordinate().add(vectors.get("topleft")));
//            Cube topright = map.get(cube.getCubeCoordinate().add(vectors.get("topright")));
//
//            Vertex sharedVertex = null;
//            if (topleft != null && topleft.c != null) {
//                sharedVertex = topleft.c;
//            } else if (topright != null && topright.e != null) {
//                sharedVertex = topright.e;
//            } else {
//                sharedVertex = new Vertex();
//
//                vertices.add(sharedVertex);
//            }
//
//            cube.a = sharedVertex;
//            sharedVertex.addCube(cube);
//
//            if (topleft != null) {
//                topleft.c = sharedVertex;
//                sharedVertex.addCube(topleft);
//            }
//            if (topright != null) {
//                topright.e = sharedVertex;
//                sharedVertex.addCube(topright);
//            }
//
//        }
//
//
//        //      Set b-vertex
//        if(cube.b == null) {
//            Cube topright = map.get(cube.getCubeCoordinate().add(vectors.get("topright")));
//            Cube right = map.get(cube.getCubeCoordinate().add(vectors.get("right")));
//
//            Vertex sharedVertex = null;
//            if (topright != null && topright.d != null) {
//                sharedVertex = topright.d;
//            } else if (right != null && right.f != null) {
//                sharedVertex = right.f;
//            } else {
//                sharedVertex = new Vertex();
//                vertices.add(sharedVertex);
//            }
//
//            cube.b = sharedVertex;
//            sharedVertex.addCube(cube);
//
//            if (topright != null) {
//                topright.e = sharedVertex;
//                sharedVertex.addCube(topright);
//            }
//            if (right != null) {
//                right.f = sharedVertex;
//                sharedVertex.addCube(right);
//            }
//        }
//
//        //      Set c-vertex
//        if(cube.c == null) {
//
//
//
//            Cube right = map.get(cube.getCubeCoordinate().add(vectors.get("right")));
//            Cube bottomright = map.get(cube.getCubeCoordinate().add(vectors.get("bottomright")));
//
//            Vertex sharedVertex = null;
//            if (right != null && right.e != null) {
//                sharedVertex = right.e;
//            } else if (bottomright != null && bottomright.a != null) {
//                sharedVertex = bottomright.a;
//            } else {
//                sharedVertex = new Vertex();
//                vertices.add(sharedVertex);
//            }
//
//            cube.c = sharedVertex;
//            sharedVertex.addCube(cube);
//
//            if (right != null) {
//                right.e = sharedVertex;
//                sharedVertex.addCube(right);
//            }
//            if (bottomright != null) {
//                bottomright.a = sharedVertex;
//                sharedVertex.addCube(bottomright);
//            }
//        }
//
//        //      Set d-vertex
//        if(cube.d == null) {
//
//
//            Cube bottomright = map.get(cube.getCubeCoordinate().add(vectors.get("bottomright")));
//            Cube bottomleft = map.get(cube.getCubeCoordinate().add(vectors.get("bottomleft")));
//
//            Vertex sharedVertex = null;
//            if (bottomright != null && bottomright.f != null) {
//                sharedVertex = bottomright.f;
//            } else if (bottomleft != null && bottomleft.b != null) {
//                sharedVertex = bottomleft.b;
//            } else {
//                sharedVertex = new Vertex();
//                vertices.add(sharedVertex);
//            }
//
//            cube.d = sharedVertex;
//            sharedVertex.addCube(cube);
//
//            if (bottomright != null) {
//                bottomright.f = sharedVertex;
//                sharedVertex.addCube(bottomright);
//            }
//            if (bottomleft != null) {
//                bottomleft.b = sharedVertex;
//                sharedVertex.addCube(bottomleft);
//            }
//
//        }
//
//
//        //      Set e-vertex
//        if(cube.e == null) {
//
//
//            Cube bottomleft = map.get(cube.getCubeCoordinate().add(vectors.get("bottomleft")));
//            Cube left = map.get(cube.getCubeCoordinate().add(vectors.get("left")));
//
//            Vertex sharedVertex = null;
//            if (bottomleft != null && bottomleft.a != null) {
//                sharedVertex = bottomleft.a;
//            } else if (left != null && left.c != null) {
//                sharedVertex = left.c;
//            } else {
//                sharedVertex = new Vertex();
//                vertices.add(sharedVertex);
//            }
//
//            cube.e = sharedVertex;
//            sharedVertex.addCube(cube);
//
//            if (bottomleft != null) {
//                bottomleft.a = sharedVertex;
//                sharedVertex.addCube(bottomleft);
//            }
//            if (left != null) {
//                left.c = sharedVertex;
//                sharedVertex.addCube(left);
//            }
//        }
//
//        //      Set f-vertex
//        if(cube.f == null) {
//
//
//            Cube left = map.get(cube.getCubeCoordinate().add(vectors.get("left")));
//            Cube topleft = map.get(cube.getCubeCoordinate().add(vectors.get("topleft")));
//
//
//            Vertex sharedVertex = null;
//            if (left != null && left.b != null) {
//                sharedVertex = left.b;
//            } else if (topleft != null && topleft.d != null) {
//                sharedVertex = topleft.d;
//            } else {
//                sharedVertex = new Vertex();
//                vertices.add(sharedVertex);
//            }
//
//            cube.f = sharedVertex;
//            sharedVertex.addCube(cube);
//
//            if (left != null) {
//                left.b = sharedVertex;
//                sharedVertex.addCube(left);
//            }
//            if (topleft != null) {
//                topleft.d = sharedVertex;
//                sharedVertex.addCube(topleft);
//            }
//        }
//
//
//    }

    private void setVertices(Cube cube) {
//        System.out.println("--------------------------------------");
//        System.out.println("Cube coordinate: " + cube.toString());
        // Directions in which vertices can be shared
        String[][] directions = {
                {"topleft", "topright"},    // For vertex 'a'
                {"topright", "right"},      // For vertex 'b'
                {"right", "bottomright"},   // For vertex 'c'
                {"bottomright", "bottomleft"}, // For vertex 'd'
                {"bottomleft", "left"},     // For vertex 'e'
                {"left", "topleft"}         // For vertex 'f'
        };

        Vertex[] cubeVertices = {cube.a, cube.b, cube.c, cube.d, cube.e, cube.f};

        for (int i = 0; i < 6; i++) {

            if (cubeVertices[i] == null) {
                Cube neighbor1 = map.get(cube.getCubeCoordinate().add(vectors.get(directions[i][0])));
                Cube neighbor2 = map.get(cube.getCubeCoordinate().add(vectors.get(directions[i][1])));

//                if(i == 0) {
//                    System.out.println(cube.getCubeCoordinate().add(vectors.get(directions[i][0])));
//                    System.out.println("Neighbor 1: " + (neighbor1 == null ? null : neighbor1.toString()));
//                    System.out.println("Neighbor 2: " + (neighbor2 == null ? null : neighbor2.toString()));
//                }


                Vertex sharedVertex = null;

                if (neighbor1 != null) {
                    sharedVertex = getVertexByNeighbor(neighbor1, (i + 2) % 6);
                } else if (neighbor2 != null) {
                    sharedVertex = getVertexByNeighbor(neighbor2, (i + 4) % 6);
                }

                if (sharedVertex == null) {
                    sharedVertex = new Vertex();
                    vertices.add(sharedVertex);
                }

                cubeVertices[i] = sharedVertex;
                sharedVertex.addCube(cube);
            }
        }

        cube.a = cubeVertices[0];
        cube.b = cubeVertices[1];
        cube.c = cubeVertices[2];
        cube.d = cubeVertices[3];
        cube.e = cubeVertices[4];
        cube.f = cubeVertices[5];
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
}
