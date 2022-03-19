/*
 * MazePlayer.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.Player;

import project.tcss360.Maze.Direction;
import project.tcss360.Maze.Maze;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The player class that tracks the player and save data
 */
public class MazePlayer implements Player, Serializable {
    @Serial
    private static final long serialVersionUID = 3269485734685758744L;
    private int myAddress;
    private final Maze myMaze;
    private final Deque<Integer> myLocationHistory;

    /**
     * Constructor for the MazePlayer Class
     * @param theMaze the Maze object being used
     */
    public MazePlayer(final Maze theMaze) {
        myMaze = theMaze;
        final int startingRoom = theMaze.getStartRoom();
        myAddress = startingRoom;
        myLocationHistory = new ArrayDeque<>();
        myLocationHistory.push(startingRoom);
    }

    /**
     * Getter method for the location of the player
     * @return the address of the player's location
     */
    @Override
    public int getLocation() {
        return myAddress;
    }

    /**
     * Moves the player in the desired direction
     * @param theDirection the direction the player wants to move
     */
    @Override
    public void move(Direction theDirection) {
        if (myMaze.isMoveValid(myAddress, theDirection)) {
            final int nextAddress =
                    myMaze.getNextRoom(myAddress, theDirection);
            myAddress = nextAddress;
            updateLocationHistory(nextAddress);
        } else {
            updateLocationHistory(myAddress);
        }

    }

    /**
     * Sets the location of the player marker
     * @param theAddress the desired location for the player
     */
    @Override
    public void setLocation(int theAddress) {
        myAddress = theAddress;
        updateLocationHistory(theAddress);
    }

    /**
     * updates the stack of location history that the player has traversed
     */
    private void updateLocationHistory(final int theNextRoom) {
        final int movesToRemember = 2;
        if (myLocationHistory.size() >= movesToRemember) {
            final int temp = myLocationHistory.pop();
            myLocationHistory.pop();
            myLocationHistory.push(temp);
        }
        myLocationHistory.push(theNextRoom);
    }

    /**
     * removes the last move by the player and moves character back
     */
    @Override
    public void goBack() {
        myLocationHistory.pop();
        myAddress = myLocationHistory.peekLast();
    }

    /**
     * Checks if the player has moved
     * @return Whether the player has moved
     */
    @Override
    public boolean hasMoved() {
        boolean flag = myLocationHistory.peekLast() != myAddress;
        return flag;
    }

    /**
     * Gets the previous location that the player has traversed
     * @return the previous location as an integer
     */
    @Override
    public int getPreviousLocation() {
        return myLocationHistory.peekLast();
    }

}
