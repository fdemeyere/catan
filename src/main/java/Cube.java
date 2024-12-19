public class Cube {
    int x;
    int y;
    int z;

    Vertex a;
    Vertex b;
    Vertex c;
    Vertex d;
    Vertex e;
    Vertex f;

    Edge e1;
    Edge e2;
    Edge e3;
    Edge e4;
    Edge e5;
    Edge e6;

    Cube(int x, int y, int z, Vertex a, Vertex b, Vertex c, Vertex d, Vertex e, Vertex f) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;

    }

    @Override
    public String toString() {
        return "" + x + ", " + y + ", " + z;
    }

    public CubeCoordinate getCubeCoordinate() {
        return new CubeCoordinate(x, y, z);
    }

    public Vertex[] getVertexArray() {
        return new Vertex[]{this.a, this.b, this.c, this.d, this.e, this.f};
    }
}
