import java.awt.Graphics2D;
import java.awt.Color;

public class Cube {
    public int RADIUS = 80;

    public int x;
    public int y;
    public int z;

    public Vertex a;
    public Vertex b;
    public Vertex c;
    public Vertex d;
    public Vertex e;
    public Vertex f;

    public Edge e1;
    public Edge e2;
    public Edge e3;
    public Edge e4;
    public Edge e5;
    public Edge e6;

    public int x2D;
    public int y2D;

    // Distance between centers horizontally
    public int horizontalSpacing = (int) Math.round(this.RADIUS * Math.sqrt(3) / 2);

    public int verticalSpacing = (int) Math.round(this.RADIUS * 1.5); // Distance between centers vertically

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

    public void drawHexagon(Graphics2D g2d) {

        // int centerX = (int) (400 - horizontalSpacing * cube.x + horizontalSpacing *
        // cube.y);
        // int centerY = (int) (400 + verticalSpacing * (cube.x + cube.y));

        this.x2D = 400 - this.horizontalSpacing * this.x + this.horizontalSpacing * this.y;
        this.y2D = 400 + this.verticalSpacing * (this.x + this.y);

        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            // Adjusted angle calculation for pointy-up orientation
            double angle = 2 * Math.PI / 6 * i - Math.PI / 2;
            xPoints[i] = (int) Math.round(this.x2D + this.RADIUS * Math.cos(angle));
            yPoints[i] = (int) (this.y2D + this.RADIUS * Math.sin(angle));
        }

        g2d.setColor(Color.BLACK); // Set the color for the hexagon outline
        g2d.drawPolygon(xPoints, yPoints, 6);

        g2d.drawString(this.toString(), this.x2D - 20, this.y2D + 5);

        this.drawRobber(g2d); // Draw the robber if present
    }

}
