public class Edge {
    VertexID a;
    VertexID b;

    Player owner = null;

//    Vertex id a, b
    Edge(VertexID a, VertexID b) {
        this.a = a;
        this.b = b;
    }
}
