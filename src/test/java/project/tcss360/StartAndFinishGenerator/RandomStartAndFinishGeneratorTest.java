package project.tcss360.StartAndFinishGenerator;

import org.junit.jupiter.api.Test;
import project.tcss360.NumberGrid.RoomNumberGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomStartAndFinishGeneratorTest {

    private final int[][] testGridA =
            new RoomNumberGrid(3, 4).getGrid();
    private final RandomStartAndFinishGenerator gen = new
            RandomStartAndFinishGenerator(testGridA);

    @Test
    public void testGetRandomStartLocation() {
        // Assume we are using a 3 row, 4 column number grid
        final RoomNumberGrid rooms = new RoomNumberGrid(3, 4);
        final int[][] testGrid = rooms.getGrid();

        final List<Integer> expectedCornerNumbers = Arrays.asList(0, 3, 8, 11);
        final List<Integer> results = new ArrayList<>();
        while (results.size() < 4) {
            final RandomStartAndFinishGenerator generator =
                    new RandomStartAndFinishGenerator(testGrid);
            final int start = generator.getRandomStartLocation();
            if (!results.contains(start))
                results.add(start);
        }
        Collections.sort(results);
        for (int i = 0; i < 4; i++) {
            final int expected = expectedCornerNumbers.get(i);
            final int result = results.get(i);
            assertEquals(expected, result,
                    "Expected " + expected + ", Got " + result);
        }
    }

    @Test
    public void testGetFinishLocationTopLeftStart() {
        // Assume we are using a 3 row, 4 column number grid
        final int start = 0;
        final int expectedFinish = 11;
        final int resultFinish = gen.getFinishLocation(start);
        assertEquals(expectedFinish, resultFinish, "No match");
    }

    @Test
    public void testGetFinishLocationTopRightStart() {
        // Assume we are using a 3 row, 4 column number grid
        final int start = 3;
        final int expectedFinish = 8;
        final int resultFinish = gen.getFinishLocation(start);
        assertEquals(expectedFinish, resultFinish, "No match");
    }

    @Test
    public void testGetFinishLocationBottomLeftStart() {
        // Assume we are using a 3 row, 4 column number grid
        final int start = 8;
        final int expectedFinish = 3;
        final int resultFinish = gen.getFinishLocation(start);
        assertEquals(expectedFinish, resultFinish, "No match");
    }

    @Test
    public void testGetFinishLocationBottomRightStart() {
        // Assume we are using a 3 row, 4 column number grid
        final int start = 8;
        final int expectedFinish = 3;
        final int resultFinish = gen.getFinishLocation(start);
        assertEquals(expectedFinish, resultFinish, "No match");
    }

}