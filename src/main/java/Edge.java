import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics2D;

public class Edge {
    Vertex a;
    Vertex b;
    Player owner = null;

    Edge(Vertex a, Vertex b) {
        this.a = a;
        this.b = b;
    }

    public void buildRoad(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        BasicStroke bs = new BasicStroke(7);
        g2d.setStroke(bs);
        g2d.drawLine(a.getX2D(), a.getY2D() + a.getHeight() / 2, b.getX2D(), b.getY2D() + a.getHeight() / 2);
    }
}
