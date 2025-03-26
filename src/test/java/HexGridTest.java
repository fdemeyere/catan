//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
class HexGridTest {

    int width = 5;

    // @BeforeEach
    void setUp() {
        try {
            // HexGrid grid = new HexGrid(width, width);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // @Test
    public void VerticesBetweenCorrectCubes() {
        for (int x = -(width - 1) / 2; x <= (width - 1) / 2; x++) {
            for (int y = -(width - 1) / 2; y <= (width - 1) / 2; y++) {
                int z = -x - y;

                if (Math.abs(z) <= (width - 1) / 2) {

                }

            }
        }
    }
}