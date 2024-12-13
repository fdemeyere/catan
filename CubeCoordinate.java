public class CubeCoordinate {
    public final int x;
    public final int y;
    public final int z;

    // Constructor
    public CubeCoordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "" + x + ", " + y + ", " + z;
    }
}