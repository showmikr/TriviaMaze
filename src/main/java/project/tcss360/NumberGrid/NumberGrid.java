/*
 * NumberGrid.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.NumberGrid;

import project.tcss360.Maze.Direction;

/**
 * An interface that is implemented in RoomNumberGrid
 */
public interface NumberGrid {
    /**
     * Returns the row size
     * @return the row size
     */
    int getRowSize();

    /**
     * Returns the column size
     * @return the column size
     */
    int getColSize();

    /**
     * Checks if the move is out of bounds of the Grid
     * @param theNode the current node
     * @param theDirection the direction chosen from current node
     * @return boolean check if it is out of bounds
     */
    boolean isMoveOutOfBounds(final int theNode, final Direction theDirection);

    /**
     * Gets the node in the specified direction
     * @param theNode the current node
     * @param theDirection the direction chosen from the current node
     * @return an int representation of that node
     */
    int getNextNode(final int theNode, final Direction theDirection);

    /**
     * Gets the row of the node
     * @param theNodeAddress the node we want the row of
     * @return the row the node is on
     */
    int getRow(final int theNodeAddress);

    /**
     * Gets the column of the node
     * @param theNodeAddress the node we want the column of
     * @return the column the node is on
     */
    int getCol(final int theNodeAddress);
}
