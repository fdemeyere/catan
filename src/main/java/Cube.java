import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;

import java.awt.image.BufferedImage;

import java.util.Arrays;

public class Cube {
    public int RADIUS = 66;

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

    private int[] productionByPlayerID;

    // Distance between centers horizontally
    public int horizontalSpacing = (int) Math.round(this.RADIUS * Math.sqrt(3) / 2);

    public int verticalSpacing = (int) Math.round(this.RADIUS * 1.5); // Distance between centers vertically

    private boolean robber = false;
    private Color robberColor = new Color(0, 0, 0);

    private int number;
    private String resource;

    Cube(int x, int y, int z, Vertex a, Vertex b, Vertex c, Vertex d, Vertex e, Vertex f, int numberOfPlayers) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;

        this.productionByPlayerID = new int[numberOfPlayers];

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
            g2d.setColor(this.robberColor); // Set a color for the robber
            g2d.fillOval(x2D - 40, y2D - 40, 20, 20); // Draw a small rectangle as the robber
        }
    }

    public boolean hasRobber() {
        return this.robber;
    }

    public void set2dCoordinates(int screenWidth, int screenHeight) {
        this.x2D = screenWidth / 2 - this.horizontalSpacing * this.x + this.horizontalSpacing * this.y;
        this.y2D = screenHeight / 2 + this.verticalSpacing * (this.x + this.y);
    }

    public void drawHexagon(Graphics2D g2d, Color color, BufferedImage resourceImage) {
        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            // Adjusted angle calculation for pointy-up orientation
            double angle = 2 * Math.PI / 6 * i - Math.PI / 2;
            xPoints[i] = (int) Math.round(this.x2D + this.RADIUS * Math.cos(angle));
            yPoints[i] = (int) (this.y2D + this.RADIUS * Math.sin(angle));
        }

        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, 6);

        BasicStroke bs = new BasicStroke(7);
        g2d.setStroke(bs);
        g2d.setColor(new Color(252, 234, 192));
        g2d.drawPolygon(xPoints, yPoints, 6);
        g2d.drawImage(resourceImage, this.x2D - 20, this.y2D - this.RADIUS / 2, 30, 30, null);

    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return this.resource;
    }

    public void drawNumber(Graphics2D g2d) {
        g2d.setFont(new Font("Courier New", Font.BOLD, 40));
        g2d.setColor(new Color(26, 26, 26));
        if (this.hasRedNumber()) {
            g2d.setColor(new Color(185, 28, 26));
        }

        g2d.drawString("" + (this.number != 0 ? this.number : ""), this.x2D - 20,
                this.y2D + 40);

    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public boolean hasRedNumber() {
        return this.number == 6 || this.number == 8;
    }

    public void increaseProductionByPlayerID(int id) {
        this.productionByPlayerID[id]++;
    }

    public void drawProduction(Graphics2D g2d) {
        g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
        g2d.setColor(new Color(26, 26, 26));
        g2d.drawString(Arrays.toString(this.productionByPlayerID), this.x2D - this.RADIUS / 2, this.y2D + 5);
    }

    public int[] getProduction() {
        return this.productionByPlayerID;
    }

}
