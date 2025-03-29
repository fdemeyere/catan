import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.RenderingHints;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Board extends JFrame implements MouseListener {

    private HexGrid grid;

    public JPanel boardPanel;

    private Graphics2D g2d;

    private boolean robberToMove = false;

    private Cube robberPlacement = null;

    private GameState gameState;

    private String[] resourcePanelOrder = { "lumber", "brick", "wool", "grain", "ore" };

    private String gameFeedback = "";

    private Color brickColor = new Color(208, 105, 56);
    private Color woolColor = new Color(149, 179, 57);
    private Color lumberColor = new Color(67, 145, 70);
    private Color grainColor = new Color(230, 185, 69);
    private Color oreColor = new Color(162, 166, 162);
    private Color nothingColor = new Color(206, 199, 146);
    private Color lightSandColor = new Color(252, 234, 192);

    private int xControlPanel;
    private int yControlPanel;
    private int controlPanelHeight = 120;
    private int controlPanelButtonWidth = 120;
    private int controlPanelPadding = 20;

    private int resourcePanelWidth = 320;
    private int resourceCardWidth = (this.resourcePanelWidth - 120) / 5;
    private int resourceCardHeight = this.controlPanelHeight - 60;

    private List<ControlPanelButton> controlPanelButtons;

    public ControlPanelButton endTurnButton;
    public ControlPanelButton cityButton;
    public ControlPanelButton settlementButton;
    public ControlPanelButton roadButton;
    public ControlPanelButton devCardButton;
    public ControlPanelButton tradeButton;

    private int arcLength = 20;

    public boolean mustPlaceSettlement = false;

    public boolean mustPlaceRoad = false;

    private BufferedImage boardCache;

    public Board(HexGrid grid, GameState gameState) {
        this.grid = grid;
        this.xControlPanel = (int) Math.round(this.grid.getScreenHeight() * 0.06);
        this.yControlPanel = (int) Math.round(this.grid.getScreenHeight() * 0.75);

        this.setSize(grid.getScreenWidth(), grid.getScreenHeight());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setLayout(null);
        this.gameState = gameState;

        List<String> resources = this.getRandomResourceColorOrder();
        List<Integer> blackNumbers = this.getRandomBlackNumberOrder();
        grid.assignResourcesAndNumbers(resources, blackNumbers);
        initializeControlPanelButtons();

        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g2d = (Graphics2D) g;
                if (boardCache != null) {
                    g2d.drawImage(boardCache, 0, 0, boardCache.getWidth(), boardCache.getHeight(), null);
                }
                drawDynamicElements(g2d);
            }
        };

        boardPanel.setBounds(0, 0, grid.getScreenWidth(), grid.getScreenHeight());
        boardPanel.setLayout(null);
        boardPanel.addMouseListener(this);
        add(boardPanel);

        this.updateBoardCache();
        this.setVisible(true);
    }

    private void drawDynamicElements(Graphics2D g2d) {
        DoubleDice.drawResult(g2d);
        this.highlightCurrentPlayer(g2d);
        drawResourceCards(g2d);

        for (Edge edge : grid.getEdges()) {
            if (edge.hasRoad())
                edge.drawRoad(g2d);
            if (roadButton.isActivated &&
                    (gameState.getCurrentPlayer().edgeIsConnectedToRoad(edge)
                            || gameState.getCurrentPlayer().edgeIsConnectedToBuiltVertex(edge))) {
                edge.highlightRoadOption(g2d);
            }

        }

        for (Vertex vertex : grid.getVertices()) {
            if (vertex.isSettlement())
                vertex.drawSettlement(g2d);
            if (vertex.isCity())
                vertex.drawCity(g2d);
            if (settlementButton.isActivated && !vertex.isSettlement() && !vertex.isCity()) {
                vertex.highlightSettlementOption(g2d);
            } else if (cityButton.isActivated && vertex.isSettlement()
                    && vertex.getPlayer() == gameState.getCurrentPlayer()) {
                vertex.highlightSettlementOption(g2d);
            }
        }

        this.drawGameFeedback(g2d);

        for (ControlPanelButton button : controlPanelButtons) {
            button.draw(g2d);
        }
    }

    private void updateBoardCache() {
        int width = boardPanel.getWidth();
        int height = boardPanel.getHeight();
        // Create a new BufferedImage to store the rendered board
        boardCache = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = boardCache.createGraphics();

        // Enable anti-aliasing for smoother rendering
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the background color
        g.setColor(new Color(179, 218, 255));
        g.fillRect(0, 0, grid.getScreenWidth(), grid.getScreenHeight());

        // Draw all cubes
        for (Cube cube : grid.getMap().values()) {
            Color fillColor = getResourceColor(cube.getResource());
            BufferedImage resourceImage = LoadImage.load(cube.getResource() + ".png");

            cube.drawHexagon(g, fillColor, lightSandColor, resourceImage);
            cube.drawNumber(g);
            cube.drawResourceImage(g, resourceImage);
        }

        this.drawResourcePanel(g);

        // g.dispose(); // Dispose of the graphics context to free resources

    }

    private void initializeControlPanelButtons() {
        controlPanelButtons = new ArrayList<>();
        endTurnButton = new ControlPanelButton(
                xControlPanel + resourcePanelWidth + controlPanelPadding * 6 +
                        controlPanelButtonWidth * 5,
                yControlPanel,
                controlPanelButtonWidth,
                controlPanelButtonWidth,
                this.arcLength,
                lightSandColor,
                () -> this.gameState.switchTurn(), null);
        controlPanelButtons.add(endTurnButton);

        settlementButton = new ControlPanelButton(
                xControlPanel + resourcePanelWidth + controlPanelPadding * 4 +
                        controlPanelButtonWidth * 3,
                yControlPanel,
                controlPanelButtonWidth,
                controlPanelButtonWidth,
                this.arcLength,
                lightSandColor,
                () -> this.toggleSettlementButton(), this.nothingColor);
        controlPanelButtons.add(settlementButton);

        cityButton = new ControlPanelButton(
                xControlPanel + resourcePanelWidth + controlPanelPadding * 5 +
                        controlPanelButtonWidth * 4,
                yControlPanel,
                controlPanelButtonWidth,
                controlPanelButtonWidth,
                this.arcLength,
                lightSandColor,
                () -> this.toggleCityButton(), this.nothingColor);
        controlPanelButtons.add(cityButton);

        roadButton = new ControlPanelButton(
                xControlPanel + resourcePanelWidth + controlPanelPadding * 3 +
                        controlPanelButtonWidth * 2,
                yControlPanel,
                controlPanelButtonWidth,
                controlPanelButtonWidth,
                this.arcLength,
                lightSandColor,
                () -> this.toggleRoadButton(), this.nothingColor);
        controlPanelButtons.add(roadButton);

    }

    private void toggleSettlementButton() {
        this.settlementButton.isActivated = !(this.settlementButton.isActivated);
        boardPanel.repaint();

    }

    private void toggleCityButton() {
        this.cityButton.isActivated = !(this.cityButton.isActivated);
    }

    private void toggleRoadButton() {
        this.roadButton.isActivated = !(this.roadButton.isActivated);
        boardPanel.repaint();
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
            handleRobberPlacement(point);
            return;
        }

        if (handleVertexClick(point)) {
            return;
        }

        if (handleEdgeClick(point)) {
            return;
        }

        handleButtonClick(point);
    }

    private void handleRobberPlacement(Point point) {
        Cube clickedCube = getClickedCube(point.getX(), point.getY());
        if (clickedCube != null && !clickedCube.hasRobber()) {
            if (this.robberPlacement != null) {
                this.robberPlacement.removeRobber();
            }
            clickedCube.placeRobber();
            this.robberPlacement = clickedCube;
            boardPanel.repaint();
        }
    }

    private boolean handleVertexClick(Point point) {
        Vertex clickedVertex = getClickedVertex(point.getX(), point.getY());
        if (clickedVertex == null) {
            return false;
        }

        Player currentPlayer = this.gameState.getCurrentPlayer();

        if (!clickedVertex.isSettlement() && !clickedVertex.isCity()) {
            if (this.settlementButton.isActivated) {
                if (currentPlayer.hasEnoughResourcesToBuildSettlement() || this.mustPlaceSettlement) {
                    if (!this.mustPlaceSettlement) {
                        this.grid.withdrawResourcesSettlement(currentPlayer);
                    }
                    clickedVertex.placeSettlement(currentPlayer);
                    this.mustPlaceSettlement = false;
                    this.gameState.getCurrentPlayer().settlements.add(clickedVertex);
                    this.toggleSettlementButton();
                } else {
                    this.gameFeedback = "Not enough resources to build settlement";
                }
                boardPanel.repaint();
            }
        } else if (clickedVertex.isSettlement() && this.cityButton.isActivated) {
            if (clickedVertex.getPlayer() == currentPlayer) {
                if (currentPlayer.hasEnoughResourcesToBuildCity()) {
                    this.grid.withdrawResourcesCity(currentPlayer);
                    clickedVertex.removeSettlement();
                    clickedVertex.placeCity(currentPlayer);
                    this.gameState.getCurrentPlayer().cities.add(clickedVertex);
                    this.toggleCityButton();
                } else {
                    this.gameFeedback = "Not enough resources to build city";
                }
                boardPanel.repaint();
            }
        }

        return true;
    }

    private boolean handleEdgeClick(Point point) {
        if (!this.roadButton.isActivated) {
            return false;
        }

        Edge clickedEdge = getClickedEdge(point.getX(), point.getY());
        if (clickedEdge != null && !clickedEdge.hasRoad()) {
            Player currentPlayer = this.gameState.getCurrentPlayer();
            if (currentPlayer.hasEnoughResourcesToBuildRoad() || this.mustPlaceRoad) {
                if (gameState.getCurrentPlayer().edgeIsConnectedToRoad(clickedEdge)
                        || gameState.getCurrentPlayer().edgeIsConnectedToBuiltVertex(clickedEdge)) {
                    if (!this.mustPlaceRoad) {
                        this.grid.withdrawResourcesRoad(currentPlayer);
                    }
                    clickedEdge.placeRoad(currentPlayer);
                    currentPlayer.addRoad(clickedEdge);
                    this.mustPlaceRoad = false;
                    this.toggleRoadButton();
                }

            } else {
                this.gameFeedback = "Not enough resources to build road";
            }
            boardPanel.repaint();
        }

        return clickedEdge != null;
    }

    private void handleButtonClick(Point point) {
        for (ControlPanelButton button : controlPanelButtons) {
            if (button.isClicked(point.x, point.y)) {
                button.click();
                boardPanel.repaint();
                break;
            }
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

    private void drawResourcePanel(Graphics2D g2d) {
        g2d.setColor(this.lightSandColor);
        g2d.fillRoundRect(this.xControlPanel, this.yControlPanel, this.resourcePanelWidth,
                this.controlPanelHeight,
                this.arcLength, this.arcLength);
    }

    private Color getColorByResource(String resource) {
        switch (resource) {
            case "lumber":
                return this.lumberColor;
            case "wool":
                return this.woolColor;
            case "brick":
                return this.brickColor;
            case "grain":
                return this.grainColor;
            case "ore":
                return this.oreColor;
            default:
                return this.nothingColor;
        }
    }

    private void drawResourceCards(Graphics2D g2d) {
        int width = (this.resourcePanelWidth - 120) / 5;
        Player currentPlayer = this.gameState.getCurrentPlayer();

        for (int i = 1; i <= 5; i++) {
            String resource = this.resourcePanelOrder[i - 1];
            int resourceAmount = currentPlayer.getResourceAmount(resource);
            Color color = this.getColorByResource(resource);
            BufferedImage img = LoadImage.load(resource + ".png");
            g2d.setColor(color);

            int x = this.xControlPanel + 20 * i + width * (i - 1);
            int y = this.yControlPanel + 20;
            g2d.fillRoundRect(x, y, this.resourceCardWidth,
                    this.resourceCardHeight, 10, 10);

            g2d.drawImage(img, x + 5, y + 15, 30, 30, null);

            g2d.setFont(new Font("Courier New", Font.BOLD, 30));
            g2d.setColor(Color.BLACK);
            g2d.drawString("" + resourceAmount, x + this.resourceCardWidth / 4, y + this.resourceCardHeight + 30);
        }

    }

    private void highlightCurrentPlayer(Graphics g2d) {
        g2d.drawString("" + this.gameState.getCurrentPlayer().getID(), 200, 400);
    }

    private void drawGameFeedback(Graphics g2d) {
        g2d.setColor(Color.BLACK);
        g2d.drawString(this.gameFeedback, 300, 40);
    }

}
