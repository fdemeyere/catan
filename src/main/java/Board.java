import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Board extends JFrame {

    private JPanel boardPanel;

    public Board(HexGrid grid) {
        this.setSize(850, 850);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                for (Cube cube : grid.getMap().values()) {
                    drawHexagon(cube, g2d);

                }

            }
        };

        // boardPanel.addMouseListener(new MouseAdapter() {
        // @Override
        // public void mouseClicked(MouseEvent e) {
        // int x = e.getX();
        // int y = e.getY();
        // for (Hexagon hexagon : hexagons) {
        // if (hexagon.isClicked(x, y)) {
        // hexagon.toggleSelected();
        // boardPanel.repaint();
        // break;
        // }
        // }
        // }
        // });

        boardPanel.setBounds(0, 0, 850, 850);
        boardPanel.setBackground(Color.WHITE);
        add(boardPanel);
        this.setVisible(true);

    }

    private void drawHexagon(Cube cube, Graphics2D g2d) {
        // Draw a hexagon

        int radius = 80;
        double horizontalSpacing = 1.15 * radius * Math.sqrt(3) / 2; // Distance between centers horizontally
        double verticalSpacing = 1.15 * radius * 1.5; // Distance between centers vertically

        int centerX = (int) (400 - horizontalSpacing * cube.x + horizontalSpacing * cube.y);
        int centerY = (int) (400 - verticalSpacing * (cube.x + cube.y));

        int[] xPoints = new int[6];
        int[] yPoints = new int[6];

        for (int i = 0; i < 6; i++) {
            // Adjusted angle calculation for pointy-up orientation
            double angle = 2 * Math.PI / 6 * i - Math.PI / 2;
            xPoints[i] = (int) (centerX + radius * Math.cos(angle));
            yPoints[i] = (int) (centerY + radius * Math.sin(angle));
        }

        g2d.setColor(Color.BLACK); // Set the color for the hexagon outline
        g2d.drawPolygon(xPoints, yPoints, 6);
    }

}
