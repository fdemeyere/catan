public class Edge {
    VertexID a;
    VertexID b;
    Player owner = null;

    Edge(VertexID a, VertexID b) {
        this.a = a;
        this.b = b;
    }
}
