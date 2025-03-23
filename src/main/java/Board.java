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
                    cube.drawHexagon(g2d);
                    cube.drawRobber(g2d);
                }

                for (Vertex vertex : grid.getVertices()) {
                    vertex.upgrade(g2d);
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
}
