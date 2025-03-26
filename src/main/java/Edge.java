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

    private Player player;

    Edge(Vertex a, Vertex b) {
        this.a = a;
        this.b = b;
    }

    public void placeRoad(Player player) {
        this.road = true;
        this.player = player;
    }

    public void drawRoad(Graphics2D g2d) {
        if (this.road) {
            g2d.setColor(this.player.getColor());
            BasicStroke bs = new BasicStroke(7);
            g2d.setStroke(bs);
            g2d.drawLine(a.getX2D(), a.getY2D(), b.getX2D(), b.getY2D());

        }

    }

    public void highlightRoadOption(Graphics2D g2d) {
        if (!this.road) {
            BasicStroke bs = new BasicStroke(1);
            g2d.setStroke(bs);
            this.x2D = (a.getX2D() + b.getX2D()) / 2;
            this.y2D = (a.getY2D() + b.getY2D()) / 2;
            g2d.setColor(new Color(119, 205, 255, 128));
            g2d.fillOval(this.x2D - this.optionalRoadWidth / 2, this.y2D - this.optionalRoadWidth / 2,
                    this.optionalRoadWidth,
                    this.optionalRoadWidth);
            g2d.setColor(new Color(0, 0, 0, 128));
            g2d.drawOval(this.x2D - this.optionalRoadWidth / 2, this.y2D - this.optionalRoadWidth / 2,
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

    public Player getPlayer() {
        return this.player;
    }
}
