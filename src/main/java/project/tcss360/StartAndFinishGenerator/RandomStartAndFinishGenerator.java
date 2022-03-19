/*
 * RandomStartAndFinishGenerator.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.StartAndFinishGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class meant for generating a random starting room number that is at
 * one of the corners of our maze. The finish (goal room) is set up
 * to always be the room number opposite of our starting corner
 */
public class RandomStartAndFinishGenerator {

    /**
     * 2d Integer Array to map out the number grid
     */
    private final int[][] myNumberGrid;

    /**
     * Integer Array to hold values of the corner integers
     */
    private final int[] myCornerNumbers;

    /**
     * Constructor for the Start and Finish generator
     * @param numberGrid the number grid being used.
     */
    public RandomStartAndFinishGenerator(final int[][] numberGrid) {
        myNumberGrid = numberGrid;
        myCornerNumbers = getCornerRoomNumbers();
    }

    /**
     * gets the corner numbers and returns their values based on the number grid
     * @return the location and values on the number grid for the values for the corners
     */
    private int[] getCornerRoomNumbers() {
        final int numCols = myNumberGrid[0].length;
        final int numRows = myNumberGrid.length;
        final int topLeftCornerRoomNumber = 0;
        final int topRightCornerRoomNumber =
                myNumberGrid[0][numCols - 1];
        final int bottomLeftCornerRoomNumber =
                myNumberGrid[numRows - 1][0];
        final int bottomRightCornerRoomNumber =
                myNumberGrid[numRows - 1][numCols - 1];
        return new int[] {
                topLeftCornerRoomNumber,
                topRightCornerRoomNumber,
                bottomLeftCornerRoomNumber,
                bottomRightCornerRoomNumber
        };
    }

    /**
     * Generates a random start location and gets its value to mark as the starting location
     * @return the generated starting location
     */
    public int getRandomStartLocation() {
        final int[] cornerRoomNumbers = getCornerRoomNumbers();
        final int numberOfCorners = 4;
        final int min = 0;
        int randPosition =
                ThreadLocalRandom.current().nextInt(min, numberOfCorners);
        return cornerRoomNumbers[randPosition];
    }

    /**
     * Generates the finish location based on the starting location
     * @param theStart the location of the starting location
     * @return the value for the finish location based on the corner
     */
    public int getFinishLocation(final int theStart) {
        final int finish;
        if (theStart == myCornerNumbers[0])
            finish = myCornerNumbers[3];
        else if (theStart == myCornerNumbers[3])
            finish = myCornerNumbers[0];
        else if (theStart == myCornerNumbers[1])
            finish = myCornerNumbers[2];
        else // if theStart == myCornerNumbers[2]
            finish = myCornerNumbers[1];
        return finish;
    }

}
