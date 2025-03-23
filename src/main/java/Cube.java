import java.awt.Graphics2D;
import java.awt.Color;

public class Cube {
    int RADIUS = 50;

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

    int x2D;
    int y2D;

    private boolean robber = false;

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
        return new Vertex[] { this.a, this.b, this.c, this.d, this.e, this.f };
    }

    public Boolean cubeContains2DCoord(double xClicked, double yClicked) {
        // return coord2DLeftSide(xClicked, yClicked) && coord2DLowerHalf(xClicked,
        // yClicked);
        if (coord2DLeftSide(xClicked, yClicked)) {
            if (coord2DLowerHalf(xClicked, yClicked))
                return coord2DBottomLeft(xClicked, yClicked);

        }
        return false;
    }

    private Boolean coord2DLeftSide(double xClicked, double yClicked) {
        double xDiff = this.x2D - xClicked;
        if (xClicked <= this.x2D && xDiff < RADIUS * Math.sqrt(3) / 2)
            return true;

        return false;
    }

    private Boolean coord2DLowerHalf(double xClicked, double yClicked) {
        double yDiff = yClicked - this.y2D;
        if (yClicked >= this.y2D && yDiff < RADIUS)
            return true;

        return false;
    }

    private Boolean coord2DBottomLeft(double xClicked, double yClicked) {
        if (!coord2DLeftSide(xClicked, yClicked) || !coord2DLowerHalf(xClicked, yClicked))
            return false;
        double k = ((this.RADIUS / 2) / (this.RADIUS * Math.sqrt(3) / 2));
        double m = (this.y2D + this.RADIUS) - k * this.x2D;

        return yClicked <= k * xClicked + m;

    }

    public void placeRobber() {
        this.robber = true; // Update the state to indicate the robber is placed
    }

    public void removeRobber() {
        this.robber = false;
    }

    public void drawRobber(Graphics2D g2d) {
        if (this.robber) {
            g2d.setColor(Color.RED); // Set a color for the robber
            g2d.fillRect(x2D - 10, y2D - 30, 20, 20); // Draw a small rectangle as the robber
        }
    }

    public boolean hasRobber() {
        return this.robber;
    }

}
