package project.tcss360.Maze;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriviaMazeTestTest {

    @Test
    public void testGetRandomCornerRoomNumber() {
        final List<Integer> expectedRoomNums = Arrays.asList(0, 3, 8, 11);
        final List<Integer> results = new ArrayList<>();
        while (results.size() < 4) {
            TriviaMaze triviaMaze = new TriviaMaze(3, 4);
            int startLocation = triviaMaze.getStartRoom();
            if (!results.contains(startLocation))
                results.add(startLocation);
        }
        Collections.sort(results);
        for (int i = 0; i < 4; i++) {
            final int expected = expectedRoomNums.get(i);
            final int result = results.get(i);
            assertEquals(expected, result,
                    "Expected " + expected + ", Got " + result);
        }
    }

}