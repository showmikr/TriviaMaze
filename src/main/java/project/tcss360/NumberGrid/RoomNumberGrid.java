/*
 * RoomNumberGrid.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.NumberGrid;

import project.tcss360.Maze.Direction;

import java.io.Serial;
import java.io.Serializable;

/**
 * A grid of room numbers based off of NumberGrid
 */
public class RoomNumberGrid implements NumberGrid, Serializable {
    /** ID for serialization*/
    @Serial
    private static final long serialVersionUID = 9201398470923759873L;
    /** A 2d array to store the number grid*/
    private final int[][] myNumberGrid;
    /** Stores the number of columns*/
    private final int myColSize;
    /** Stores the number of rows*/
    private final int myRowSize;

    /**
     * Creates a number grid based on the rows and columns passed in
     * @param theRowSize the row size
     * @param theColSize the column size
     */
    public RoomNumberGrid(final int theRowSize, final int theColSize) {
        myColSize = theColSize;
        myRowSize = theRowSize;
        myNumberGrid = makeNumberGrid(theColSize, theRowSize);
    }

    /**
     * Gets a copy of the grid
     * @return A copy of the grid
     */
    public int[][] getGrid() {
        // Return defensive copy of Number Grid
        return makeNumberGrid(myColSize, myRowSize);
    }

    /**
     * Adds numbers to a 2D array and returns it
     * @param theRowSize the row size
     * @param theColSize the column size
     * @return a 2D array with numbers filled in
     */
    private int[][] makeNumberGrid(final int theRowSize, final int theColSize) {
        final int[][] rooms = new int[theColSize][theRowSize];
        int roomNum = 0;
        for (int i = 0; i < theColSize; i++) {
            for (int j = 0; j < theRowSize; j++) {
                rooms[i][j] = roomNum;
                roomNum++;
            }
        }
        return rooms;
    }

    /**
     * Returns the column size
     * @return the column size
     */
    @Override
    public int getColSize() {
        return myColSize;
    }

    /**
     * Returns the row size
     * @return the row size
     */
    @Override
    public int getRowSize() {
        return myRowSize;
    }

    /**
     * Gets the row of the node
     * @param theNodeAddress the node we want the row of
     * @return the row the node is on
     */
    @Override
    public int getRow(final int theRoomNumber) {
        return theRoomNumber / myRowSize;
    }

    /**
     * Gets the column of the node
     * @param theNodeAddress the node we want the column of
     * @return the column the node is on
     */
    @Override
    public int getCol(final int theRoomNumber) {
        return theRoomNumber % myRowSize;
    }

    /**
     * Checks if the move is out of bounds of the Grid
     * @param theNode the current node
     * @param theDirection the direction chosen from current node
     * @return boolean check if it is out of bounds
     */
    @Override
    public boolean isMoveOutOfBounds(final int theNode,
                                      final Direction theDirection) {
        boolean flag = false;
        int updatedRow = getRow(theNode);
        int updatedCol = getCol(theNode);
        switch (theDirection) {
            case up -> updatedRow--;
            case down -> updatedRow++;
            case left -> updatedCol--;
            case right -> updatedCol++;
        }
        final int rowRange = myColSize - 1;
        final int colRange = myRowSize - 1;
        if (updatedRow > rowRange || updatedRow < 0
                || updatedCol > colRange || updatedCol < 0)
            flag = true;
        return flag;
    }

    /**
     * Gets the node in the specified direction
     * @param theNode the current node
     * @param theDirection the direction chosen from the current node
     * @return an int representation of that node
     */
    @Override
    public int getNextNode(final int theNode, final Direction theDirection) {
        int updatedRow = getRow(theNode);
        int updatedCol = getCol(theNode);
        final int nextNodeAddress;
        switch (theDirection) {
            case up -> updatedRow--;
            case down -> updatedRow++;
            case left -> updatedCol--;
            case right -> updatedCol++;
        }
        nextNodeAddress = myNumberGrid[updatedRow][updatedCol];
        return nextNodeAddress;
    }

}
