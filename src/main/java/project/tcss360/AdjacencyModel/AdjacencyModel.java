/*
 * AdjacencyModel.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.AdjacencyModel;

import java.util.List;

/**
 * An interface that is implemented in AdjacencyList
 */
public interface AdjacencyModel {
    /**
     * Checks to see if a node is connected to other nodes
     * @param theNode the first node to check
     * @param theOtherNode the second node to check
     * @return boolean expression that shows if the nodes are connected or not
     */
    boolean isConnected(final int theNode, final int theOtherNode);

    /**
     * Disconnects a node from a different node
     * @param theNode the first node to disconnect
     * @param theOtherNode the second node to disconnect
     */
    void disconnect(final int theNode, final int theOtherNode);

    /**
     * Checks if a path between two nodes exists.
     * @param theSource the source node
     * @param theTarget the target node
     * @return a boolean expression that shows if the nodes have a path between them
     */
    boolean doesPathExistBetween(final int theSource, final int theTarget);

    /**
     * Gets the connected nodes from the parameter node
     * @param theSource The source node to check
     * @return a List of the connected neighboring nodes
     */
    List<Integer> getConnectedNeighbors(final int theSource);

    /**
     * Gets the row size of the nodes
     * @return the number of rows
     */
    int getRowSize();

    /**
     * Gets the column size of the nodes
     * @return the number of columns
     */
    int getColSize();
}
