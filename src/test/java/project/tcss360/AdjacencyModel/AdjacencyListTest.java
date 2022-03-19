package project.tcss360.AdjacencyModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacencyListTest {
    /**
     * Layout should look like
     * 0 - 1 - 2
     * |   |   |
     * 3 - 4 - 5
     */
    private AdjacencyModel adjListA;
    private AdjacencyModel adjListB;
    private AdjacencyModel adjListC;

    @BeforeEach
    public void setUp() {
        adjListA = new AdjacencyList(3, 2);
        adjListB = new AdjacencyList(4, 5);
        adjListC = new AdjacencyList(3, 3);
    }

    @Test
    public void testIsConnectedAdjRightNodesTrue() {
        assertTrue(adjListA.isConnected(0, 1));
    }

    @Test
    public void testIsConnectedAdjBelowTrue() {
        assertTrue(adjListA.isConnected(2, 5));
    }

    @Test
    public void testIsConnectedHorizontalFalse() {
        assertFalse(adjListA.isConnected(3, 5));
    }

    @Test
    public void testIsConnectedVerticalFalse() {
        assertFalse(adjListA.isConnected(1, 3));
    }

    @Test
    public void isEveryRowConnected() {
        for (int i = 0; i < adjListB.getColSize(); i++) {
            for (int j = 0; j < adjListB.getRowSize() - 1; j++) {
                final int address = (adjListB.getRowSize() * i) + j;
                final int rightAdjAddress = address + 1;
                assertTrue(adjListB.isConnected(address, rightAdjAddress));
            }
        }
    }

    @Test
    public void isEveryColumnConnected() {
        for (int i = 0; i < adjListB.getRowSize(); i++) {
            for (int j = 0; j < adjListB.getColSize() - 1; j++) {
                final int address = (adjListB.getRowSize() * j) + i;
                final int btmAdjAddress = address + adjListB.getRowSize();
                assertTrue(adjListB.isConnected(address, btmAdjAddress));
            }
        }
    }

    @Test
    public void testIsConnectedTrue() {
        assertTrue(adjListA.isConnected(1, 4));
        assertTrue(adjListA.isConnected(1, 0));
        assertTrue(adjListA.isConnected(1, 2));
    }

    @Test
    public void testDoesPathExistBetweenTrue() {
        assertTrue(adjListA.doesPathExistBetween(4, 0));
    }

    @Test
    public void testDoesPathExistBetweenTrueDisconnects() {
        adjListA.disconnect(1, 4);
        adjListA.disconnect(3, 4);
        assertTrue(adjListA.doesPathExistBetween(3, 4));
    }

    @Test
    public void testDoesPathExistBetweenFalse() {
        adjListA.disconnect(4, 5);
        adjListA.disconnect(2, 5);
        assertFalse(adjListA.doesPathExistBetween(0, 5));
    }

    @Test
    public void ThreeByThreePathExistsBetweenTrue() {
        adjListC.disconnect(4, 3);
        adjListC.disconnect(4, 1);
        adjListC.disconnect(4, 5);
        assertTrue(adjListC.doesPathExistBetween(0, 4));
    }

}