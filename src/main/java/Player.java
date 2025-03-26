import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Player {
    private final int id;
    private int points;

    private int lumber;
    private int brick;
    private int wool;
    private int grain;
    private int ore;

    private Color color;

    Player(int id, Color color) {
        this.id = id;
        this.color = color;
    }

    List<Vertex> settlements = new ArrayList<>();
    List<Vertex> cities = new ArrayList<>();

    List<String> devCards = new ArrayList<>();

    public int getPoints() {
        return this.points;
    }

    public int getID() {
        return this.id;
    }

    public Color getColor() {
        return this.color;
    }
}
