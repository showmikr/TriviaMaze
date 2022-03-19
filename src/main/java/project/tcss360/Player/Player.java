/*
 * Player.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.Player;

import project.tcss360.Maze.Direction;

/**
 * Interface for Player classes
 */
public interface Player {

    /**
     * Getter Method for the location of the players
     * @return the integer value representing the player
     */
    int getLocation();

    /**
     * Method to move the player in a specified direction
     * @param theDirection the desired direction
     */
    void move(Direction theDirection);

    /**
     * Method to move the player to the set location
     * @param theAddress the desired location
     */
    void setLocation(int theAddress);

    /**
     * Method to backtrack where the player has traveled
     */
    void goBack();

    /**
     * Method to check the player has moved or not
     * @return Whether the player has moved
     */
    boolean hasMoved();

    /**
     * Method to get the previous location that the player was in
     * @return the previous location
     */
    int getPreviousLocation();
}
