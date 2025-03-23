import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Board extends JFrame implements MouseListener {

    HexGrid grid;

    private JPanel boardPanel;

    Graphics2D g2d;

    boolean robberToMove = true;

    Cube robberPlacement = null;

    Color lumberColor = new Color(63, 125, 88);
    Color woolColor = new Color(145, 196, 131);
    Color grainColor = new Color(255, 225, 98);
    Color brickColor = new Color(255, 100, 100);
    Color oreColor = new Color(153, 153, 153);
    Color nothingColor = new Color(255, 255, 255);

    private List<String> resources;

    private List<Cube> lumberCubes = new ArrayList<>();
    private List<Cube> woolCubes = new ArrayList<>();
    private List<Cube> grainCubes = new ArrayList<>();
    private List<Cube> brickCubes = new ArrayList<>();
    private List<Cube> oreCubes = new ArrayList<>();

    public Board(HexGrid grid) {
        this.grid = grid;
        this.setSize(850, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(null);
        this.resources = getRandomResourceColorOrder();

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;

                if (resources.size() < grid.getMap().size()) {
                    throw new IllegalStateException("Not enough resources for the number of cubes");
                }

                int resourceIndex = 0;
                for (Cube cube : grid.getMap().values()) {
                    String resource = resources.get(resourceIndex);
                    cube.setResource(resource);
                    categorizeCubeByResource(cube);
                    Color color = getResourceColor(resource);
                    cube.drawHexagon(g2d, color);
                    resourceIndex++;
                    cube.drawRobber(g2d);
                }

                // for (Edge edge : grid.getEdges()) {
                // edge.buildRoad(g2d);
                // }

                // for (Vertex vertex : grid.getVertices()) {
                // vertex.upgrade(g2d);
                // }

            }
        };

        boardPanel.setBounds(0, 0, 850, 850);
        boardPanel.setBackground(Color.WHITE);
        boardPanel.addMouseListener(this);
        add(boardPanel);
        this.setVisible(true);

    }

    public void mouseClicked(MouseEvent e) {
        return;
    }

    public void mousePressed(MouseEvent e) {
        return;
    }

    public void mouseReleased(MouseEvent e) {
        Point point = e.getPoint();

        if (robberToMove) {
            Cube clickedCube = getClickedCube(point.getX(), point.getY());
            if (clickedCube != null) {
                if (!clickedCube.hasRobber()) {
                    if (this.robberPlacement != null) {
                        this.robberPlacement.removeRobber();
                    }
                    clickedCube.placeRobber();
                    this.robberPlacement = clickedCube;
                    // robberToMove = false;
                }

            }

        }

        boardPanel.repaint(); // Trigger a repaint to update the drawing
    }

    public void mouseEntered(MouseEvent e) {
        return;
    }

    public void mouseExited(MouseEvent e) {
        return;
    }

    private Cube getClickedCube(double x2d, double y2d) {
        for (Cube cube : grid.getMap().values()) {
            if (cube.cubeContains2DCoord(x2d, y2d)) {
                return cube;
            }
        }
        return null;
    }

    private List<String> getRandomResourceColorOrder() {
        List<String> resourceColors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            resourceColors.add("lumber");
            resourceColors.add("wool");
            resourceColors.add("grain");
        }
        for (int i = 0; i < 3; i++) {
            resourceColors.add("brick");
            resourceColors.add("ore");
        }
        resourceColors.add("nothing");
        Collections.shuffle(resourceColors);
        return resourceColors;
    }

    private Color getResourceColor(String resource) {
        switch (resource) {
            case "lumber":
                return this.lumberColor;
            case "wool":
                return this.woolColor;
            case "grain":
                return this.grainColor;
            case "brick":
                return this.brickColor;
            case "ore":
                return this.oreColor;
            default:
                return this.nothingColor;
        }
    }

    private void categorizeCubeByResource(Cube cube) {
        switch (cube.getResource()) {
            case "lumber":
                this.lumberCubes.add(cube);
            case "wool":
                this.woolCubes.add(cube);
            case "grain":
                this.grainCubes.add(cube);
            case "brick":
                this.brickCubes.add(cube);
            case "ore":
                this.oreCubes.add(cube);
        }
    }
}
