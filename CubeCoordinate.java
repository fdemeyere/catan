import java.util.Objects;

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

    @Override
    public String toString() {
        return "" + x + ", " + y + ", " + z;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CubeCoordinate that = (CubeCoordinate) obj;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public CubeCoordinate add(CubeCoordinate coord) {
        return new CubeCoordinate(this.x + coord.x, this.y + coord.y, this.z + coord.z);
    }
}