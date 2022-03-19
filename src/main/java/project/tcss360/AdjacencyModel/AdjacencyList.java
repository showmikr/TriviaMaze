/*
 * AdjacencyList.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.AdjacencyModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Creates an AdjacencyList based of the AdjacencyModel Interface
 */
public class AdjacencyList implements AdjacencyModel, Serializable {
    /** ID for serialization*/
    @Serial
    private static final long serialVersionUID = 5920193865047564329L;
    /** Stores the adjacency list*/
    private final List<Set<Integer>> myAdjacencyList;
    /** Stores the row size*/
    private final int myRowSize;
    /** Stores the column size*/
    private final int myColSize;

    /**
     * Creates an adjacency List bases on the parameters passed into it
     * @param theRowSize the row size of the adjacency list
     * @param theColSize the column size of the adjacency list
     */
    public AdjacencyList(final int theRowSize, final int theColSize) {
        myRowSize = theRowSize;
        myColSize = theColSize;
        final int totalNodes = theRowSize * theColSize;
        final int maxConnectionsPerNode = 4;
        myAdjacencyList = new ArrayList<>(totalNodes);
        for (int i = 0; i < totalNodes; i++) {
            myAdjacencyList.add(new HashSet<>(maxConnectionsPerNode));
        }
        makeGridConnections(theRowSize, theColSize);
    }

    /**
     * Connects the columns and rows
     * @param theRowSize the row size
     * @param theColSize the column size
     */
    private void makeGridConnections(final int theRowSize,
                                     final int theColSize) {
        drawHorizontalConnections(theRowSize, theColSize);
        drawVerticalConnections(theRowSize, theColSize);
    }

    /**
     * Connects the rows together
     * @param theRowSize the row size
     * @param theColSize the column size
     */
    private void drawHorizontalConnections(final int theRowSize,
                                           final int theColSize) {
        // Connect each row of rooms together
        for (int i = 0; i < theColSize; i++) {
            for (int j = 0; j < theRowSize - 1; j++) {
                // Get horizontally adjacent room numbers
                final int address = (theRowSize * i) + j;
                final int rightAdjAddress = address + 1;
                connectNodes(address, rightAdjAddress);
            }
        }
    }

    /**
     * Connects the columns together
     * @param theRowSize the row size
     * @param theColSize the column size
     */
    private void drawVerticalConnections(final int theRowSize,
                                         final int theColSize) {
        // Connect each column of rooms together
        for (int i = 0; i < theRowSize; i++) {
            for (int j = 0; j < theColSize - 1; j++) {
                // Get vertically adjacent room numbers
                final int address = (theRowSize * j) + i;
                final int btmAdjAddress = address + theRowSize;
                connectNodes(address, btmAdjAddress);
            }
        }
    }

    /**
     * Updates the adjacency list to show two nodes as connected
     * @param theAddress the first node to connect
     * @param theOtherAddress the second node to connect
     */
    private void connectNodes(final int theAddress, final int theOtherAddress) {
        myAdjacencyList.get(theAddress).add(theOtherAddress);
        myAdjacencyList.get(theOtherAddress).add(theAddress);
    }

    /**
     * Checks to see if a node is connected to other nodes
     * @param theNode the first node to check
     * @param theOtherNode the second node to check
     * @return boolean expression that shows if the nodes are connected or not
     */
    @Override
    public boolean isConnected(final int theNode,
                               final int theOtherNode) {
        final boolean isLinked = myAdjacencyList.get(theNode).contains(theOtherNode);
        final boolean isLinkedReverse = myAdjacencyList.get(theOtherNode).contains(theNode);
        return isLinked && isLinkedReverse;
    }

    /**
     * Disconnects a node from a different node
     * @param theNode the first node to disconnect
     * @param theOtherNode the second node to disconnect
     */
    @Override
    public void disconnect(int theNode, int theOtherNode) {
        myAdjacencyList.get(theNode).remove((Integer)theOtherNode);
        myAdjacencyList.get(theOtherNode).remove((Integer)theNode);
    }

    /**
     * Checks if a path between two nodes exists.
     * @param theSource the source node
     * @param theTarget the target node
     * @return a boolean expression that shows if the nodes have a path between them
     */
    @Override
    public boolean doesPathExistBetween(int theSource, int theTarget) {
        boolean isTargetFound = false;
        final Deque<Integer> searchStack = new ArrayDeque<>();
        searchStack.push(theSource);
        final Set<Integer> visitedNodes = new HashSet<>();
        while (!searchStack.isEmpty()) {
            final int currentNode = searchStack.pop();
            visitedNodes.add(currentNode);
            if (currentNode == theTarget) {
                isTargetFound = true;
                break;
            }
            final Set<Integer> adjacentNodes = myAdjacencyList.get(currentNode);
            for (int nextNode : adjacentNodes) {
                if (!visitedNodes.contains(nextNode))
                    searchStack.push(nextNode);
            }
        }
        return isTargetFound;
    }

    /**
     * Gets the connected nodes from the parameter node
     * @param theSource The source node to check
     * @return a List of the connected neighboring nodes
     */
    @Override
    public List<Integer> getConnectedNeighbors(int theSource) {
        final List<Integer> connectedNeighbors = new ArrayList<>();
        for (Integer nodeAddress : myAdjacencyList.get(theSource)) {
            connectedNeighbors.add(nodeAddress);
        }
        return connectedNeighbors;
    }

    /**
     * Gets the row size of the nodes
     * @return the number of rows
     */
    @Override
    public int getRowSize() {
        return myRowSize;
    }

    /**
     * Gets the column size of the nodes
     * @return the number of columns
     */
    @Override
    public int getColSize() {
        return myColSize;
    }
}
