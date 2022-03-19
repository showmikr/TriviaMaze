/*
 * Maze.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.Maze;

import project.tcss360.Question.Question;

import java.util.Set;

/**
 * An interface that is implemented in SinglePlayerMaze
 */
public interface Maze {
    /**
     * Fills a map with questions for each room
     * @param theQuestions the set of questions
     */
    void fillWithQuestions(final Set<Question> theQuestions);

    /**
     * checks to see if the maze can still be completed
     * @param theCurrentRoom the room the player currently is in
     * @return boolean identifying if the finish is reachable
     */
    boolean isFinishReachable(final int theCurrentRoom);

    /**
     * Checks if the maze is won
     * @return returns if the maze is won or not
     */
    boolean isWon();

    /**
     * Gets the question for the corresponding room
     * @param theRoomNumber the room that you want the question for
     * @return returns the questions for the input room
     */
    Question getQuestionInRoom(final int theRoomNumber);

    /**
     * Checks if the move is valid
     * @param theCurrentRoom the current room
     * @param theDirection the selected direction
     * @return a boolean if the move is valid or not
     */
    boolean isMoveValid(final int theCurrentRoom, final Direction theDirection);

    /**
     * Gets the next room based on the current room and direction selected
     * @param theCurrentRoom the current room
     * @param theDirection the selected direction
     * @return the integer value of the next room
     */
    int getNextRoom(final int theCurrentRoom, final Direction theDirection);

    /**
     * Gets the starting room of the maze
     * @return the starting room of the maze
     */
    int getStartRoom();

    /**
     * Gets the finishing room of the maze
     * @return the ending room of the maze
     */
    int getFinishRoom();

    /**
     * Gets the size of the maze
     * @return the size of the maze
     */
    int getSize();
}
