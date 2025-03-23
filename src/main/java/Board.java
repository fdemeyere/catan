import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public class Board extends JFrame implements MouseListener {

    HexGrid grid;

    private JPanel boardPanel;

    Graphics2D g2d;

    boolean robberToMove = true;

    Cube robberPlacement = null;

    public Board(HexGrid grid) {
        this.grid = grid;
        this.setSize(850, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(null);

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;
                for (Cube cube : grid.getMap().values()) {
                    drawHexagon(cube, g2d);
                    cube.drawRobber(g2d);
                }

            }
        };

        boardPanel.setBounds(0, 0, 850, 850);
        boardPanel.setBackground(Color.WHITE);
        boardPanel.addMouseListener(this);
        add(boardPanel);
        this.setVisible(true);

    }

    private void drawHexagon(Cube cube, Graphics2D g2d) {
        // Draw a hexagon

        double horizontalSpacing = 1.15 * cube.RADIUS * Math.sqrt(3) / 2; // Distance between centers horizontally
        double verticalSpacing = 1.15 * cube.RADIUS * 1.5; // Distance between centers vertically

        // int centerX = (int) (400 - horizontalSpacing * cube.x + horizontalSpacing *
        // cube.y);
        // int centerY = (int) (400 + verticalSpacing * (cube.x + cube.y));

        cube.x2D = (int) (400 - horizontalSpacing * cube.x + horizontalSpacing * cube.y);
        cube.y2D = (int) (400 + verticalSpacing * (cube.x + cube.y));

        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            // Adjusted angle calculation for pointy-up orientation
            double angle = 2 * Math.PI / 6 * i - Math.PI / 2;
            xPoints[i] = (int) (cube.x2D + cube.RADIUS * Math.cos(angle));
            yPoints[i] = (int) (cube.y2D + cube.RADIUS * Math.sin(angle));
        }

        g2d.setColor(Color.BLACK); // Set the color for the hexagon outline
        g2d.drawPolygon(xPoints, yPoints, 6);

        g2d.drawString(cube.toString(), cube.x2D - 20, cube.y2D + 5);

        cube.drawRobber(g2d); // Draw the robber if present
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

    // public void placeRobber(Graphics2D g2d, Cube cube) {
    // cube.placeRobber(g2d);
    // }

    private Cube getClickedCube(double x2d, double y2d) {
        for (Cube cube : grid.getMap().values()) {
            if (cube.cubeContains2DCoord(x2d, y2d)) {
                return cube;
            }
        }
        return null;
    }
}
