import java.util.Random;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

public class DoubleDice {
    public static class DiceRollResult {
        public static int die1;
        public static int die2;
        public static int sum;

        public DiceRollResult(int die1, int die2, int sum) {
            DoubleDice.DiceRollResult.die1 = die1;
            DoubleDice.DiceRollResult.die2 = die2;
            DoubleDice.DiceRollResult.sum = sum;
        }

    }

    public static DiceRollResult DiceRoll() {
        Random random = new Random();

        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        int sum = die1 + die2;
        return new DiceRollResult(die1, die2, sum);
    }

    public static void drawResult(Graphics2D g2d) {
        g2d.setFont(new Font("Courier New", Font.BOLD, 40));
        g2d.setColor(new Color(26, 26, 26));
        g2d.drawString("" + DiceRollResult.die1 + ", " + DiceRollResult.die2, 10, 40);

    }

}
