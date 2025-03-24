import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Edge {
    private Vertex a;
    private Vertex b;
    private boolean road = false;

    private int x2D;
    private int y2D;

    private int optionalRoadWidth = 30;

    Edge(Vertex a, Vertex b) {
        this.a = a;
        this.b = b;
    }

    public void placeRoad() {
        this.road = true;
    }

    public void drawRoad(Graphics2D g2d) {
        if (this.road) {
            g2d.setColor(new Color(255, 0, 0));
            BasicStroke bs = new BasicStroke(7);
            g2d.setStroke(bs);
            g2d.drawLine(a.getX2D(), a.getY2D(), b.getX2D(), b.getY2D());
        }

    }

    public void highlightRoadOption(Graphics2D g2d) {
        if (!this.road) {
            this.x2D = (a.getX2D() + b.getX2D()) / 2;
            this.y2D = (a.getY2D() + b.getY2D()) / 2;
            g2d.setColor(new Color(119, 205, 255, 128));
            g2d.fillOval(this.x2D - this.optionalRoadWidth / 2, this.y2D - this.optionalRoadWidth / 2,
                    this.optionalRoadWidth,
                    this.optionalRoadWidth);
        }

    }

    public boolean hasRoad() {
        return this.road;
    }

    public int getX2D() {
        return this.x2D;
    }

    public int getY2D() {
        return this.y2D;
    }

    public int getOptionalRoadWidth() {
        return this.optionalRoadWidth;
    }

    public void set2dCoordinates() {
        this.x2D = (a.getX2D() + b.getX2D()) / 2;
        this.y2D = (a.getY2D() + b.getY2D()) / 2;
    }
}
