import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Board extends JFrame implements MouseListener {

    private HexGrid grid;

    private JPanel boardPanel;

    private Graphics2D g2d;

    private boolean robberToMove = false;

    private Cube robberPlacement = null;

    private GameState gameState;

    Color brickColor = new Color(208, 105, 56);
    Color woolColor = new Color(149, 179, 57);
    Color lumberColor = new Color(67, 145, 70);
    Color grainColor = new Color(230, 185, 69);
    Color oreColor = new Color(162, 166, 162);
    Color nothingColor = new Color(206, 199, 146);

    // Color test = new Color(rgb(61, 184, 54));

    public JLabel label = new JLabel();

    public Board(HexGrid grid, GameState gameState) {
        this.grid = grid;

        this.setSize(grid.getScreenWidth(), grid.getScreenHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(null);
        this.gameState = gameState;

        List<String> resources = this.getRandomResourceColorOrder();
        List<Integer> blackNumbers = this.getRandomBlackNumberOrder();
        grid.assignResourcesAndNumbers(resources, blackNumbers);

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;

                this.setBackground(new Color(0, 204, 255));

                if (resources.size() < grid.getMap().size()) {
                    throw new IllegalStateException("Not enough resources for the number of cubes");
                }

                for (Cube cube : grid.getMap().values()) {
                    Color color = getResourceColor(cube.getResource());

                    BufferedImage resourceImage = LoadImage.load(cube.getResource() + ".png");

                    // boardPanel.add(imageLabel);
                    // }
                    cube.drawHexagon(g2d, color, resourceImage);
                    cube.drawNumber(g2d);
                    // cube.drawProduction(g2d);
                    cube.drawRobber(g2d); // Draw the robber if present
                }

                for (Edge edge : grid.getEdges()) {

                    // edge.highlightRoadOption(g2d);
                    edge.drawRoad(g2d);
                }

                for (Vertex vertex : grid.getVertices()) {
                    if (!vertex.isSettlement() && !vertex.isCity()) {
                        // vertex.highlightSettlementOption(g2d);
                    } else if (vertex.isSettlement())
                        vertex.drawSettlement(g2d);
                    else if (vertex.isCity())
                        vertex.drawCity(g2d);
                }

                DoubleDice.drawResult(g2d);
            }
        };

        boardPanel.setBounds(0, 0, grid.getScreenWidth(), grid.getScreenHeight());
        boardPanel.setLayout(null);
        boardPanel.addMouseListener(this);
        add(boardPanel);

        JButton endTurnButton = this.createEndTurnButton();
        boardPanel.add(endTurnButton);
        this.setVisible(true);

    }

    private JButton createEndTurnButton() {
        int width = 100;
        int height = 100;
        int borderOffset = (int) Math.round(this.grid.getScreenHeight() * 0.1);
        int x = (int) Math.round(this.grid.getScreenWidth() - width - borderOffset);
        int y = (int) Math.round(this.grid.getScreenHeight() - height - borderOffset);
        JButton button = new JButton("End turn");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameState.getCurrentPlayer().printResources();
                gameState.switchTurn();

                grid.giveResources(DoubleDice.DiceRoll().sum);
                boardPanel.repaint();

            }
        });
        button.setBounds(x, y, width,
                height);
        return button;
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
            if (!clickedVertex.isSettlement() && !clickedVertex.isCity()) {
                clickedVertex.placeSettlement(this.gameState.getCurrentPlayer());
                boardPanel.repaint();
                // this.gameState.switchTurn();
            } else if (clickedVertex.isSettlement()) {
                if (clickedVertex.getPlayer() == this.gameState.getCurrentPlayer()) {
                    clickedVertex.removeSettlement();
                    clickedVertex.placeCity(this.gameState.getCurrentPlayer());
                    boardPanel.repaint();
                    // this.gameState.switchTurn();
                }

            }
            return;
        }

        Edge clickedEdge = getClickedEdge(point.getX(), point.getY());
        if (clickedEdge != null && !clickedEdge.hasRoad()) {
            clickedEdge.placeRoad(this.gameState.getCurrentPlayer());
            boardPanel.repaint();
        }

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
                            .pow(vertex.getWidth() / 2, 2)) {
                return vertex;
            }
        }
        return null;
    }

    private Edge getClickedEdge(double x2d, double y2d) {
        for (Edge edge : grid.getEdges()) {
            if (Math.pow(x2d - edge.getX2D(), 2)
                    + Math.pow(y2d - edge.getY2D(), 2) <= Math
                            .pow(edge.getOptionalRoadWidth() / 2, 2)) {
                return edge;
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

    private List<Integer> getRandomBlackNumberOrder() {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 3; i <= 11; i++) {
            if (i == 6 || i == 7 || i == 8)
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

    // public String getResourceFileName(String resource) {
    // switch (resource) {
    // case "wool":
    // return "wool.png";
    // case "brick":
    // return "brick.png";
    // case "grain":
    // return "grain.png";
    // case "ore":
    // return "ore.png";
    // case "lumber":
    // return "lumber.png";
    // default:
    // return "cactus.png";
    // }
    // }

}
