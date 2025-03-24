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

    boolean robberToMove = false;

    Cube robberPlacement = null;

    Color lumberColor = new Color(63, 125, 88);
    Color woolColor = new Color(145, 196, 131);
    Color grainColor = new Color(255, 225, 98);
    // Color brickColor = new Color(210, 102, 90);
    Color oreColor = new Color(153, 153, 153);
    Color nothingColor = new Color(229, 208, 172);

    Color brickColor = new Color(207, 139, 100);

    public Board(HexGrid grid) {
        this.grid = grid;
        this.setSize(850, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(null);

        List<String> resources = this.getRandomResourceColorOrder();
        List<Integer> numbers = this.getRandomNumberOrder();
        grid.assignResourcesAndNumbers(resources, numbers);

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;

                if (resources.size() < grid.getMap().size()) {
                    throw new IllegalStateException("Not enough resources for the number of cubes");
                }

                for (Cube cube : grid.getMap().values()) {
                    Color color = getResourceColor(cube.getResource());
                    cube.drawHexagon(g2d, color);
                    cube.drawNumber(g2d);
                    cube.drawRobber(g2d); // Draw the robber if present
                }

                // for (Edge edge : grid.getEdges()) {
                // edge.buildRoad(g2d);
                // }

                for (Vertex vertex : grid.getVertices()) {
                    if (vertex.upgradable)
                        vertex.drawPossibleUpgrade(g2d);
                }

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

                    boardPanel.repaint();
                }

            }
            return;
        }
        Vertex clickedVertex = getClickedVertex(point.getX(), point.getY());
        if (clickedVertex != null) {
            if (clickedVertex.upgradable) {
                clickedVertex.upgrade(g2d);
            }
        }

        boardPanel.repaint();

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

    private Vertex getClickedVertex(double x2d, double y2d) {
        for (Vertex vertex : grid.getVertices()) {
            if (Math.pow(x2d - vertex.getX2D(), 2)
                    + Math.pow(y2d - vertex.getY2D(), 2) <= Math
                            .pow(vertex.getCircleRadius(), 2)) {
                System.out.println("True");
                return vertex;
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

    private List<Integer> getRandomNumberOrder() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 3; i <= 11; i++) {
            if (i == 7)
                continue;
            numbers.add(i);
            numbers.add(i);
        }
        numbers.add(2);
        numbers.add(12);
        Collections.shuffle(numbers);
        return numbers;
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

}
