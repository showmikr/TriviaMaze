package project.tcss360.NumberGrid;

import org.junit.jupiter.api.Test;
import project.tcss360.Maze.Direction;

import static org.junit.jupiter.api.Assertions.*;
import static project.tcss360.Maze.Direction.*;

class RoomNumberGridTest {

    @Test
    public void testGeRowSize() {
        final RoomNumberGrid rooms = new RoomNumberGrid(3, 10);
        final int expected = 3;
        assertEquals(expected, rooms.getRowSize(), "getNumRows() incorrect");
    }

    @Test
    public void testGetColSize() {
        final RoomNumberGrid rooms = new RoomNumberGrid(3, 10);
        final int expected = 10;
        assertEquals(expected, rooms.getColSize(), "getNumCols() incorrect");
    }

    @Test
    public void testGetNumberGrid() {
        final RoomNumberGrid rooms = new RoomNumberGrid(3, 3);
        final int[][] expected = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        for (int i = 0; i < 3; i++) {
            assertArrayEquals(expected[i], rooms.getGrid()[i]);
        }
    }

    @Test
    public void testGetNextNodeRight() {
        final NumberGrid rooms = new RoomNumberGrid(3, 3);
        final int nextAddress = rooms.getNextNode(0, right);
        assertEquals(1, nextAddress);
    }


}