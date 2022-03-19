/*
 * TriviaMaze.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.Maze;

import project.tcss360.AdjacencyModel.*;
import project.tcss360.NumberGrid.*;
import project.tcss360.Question.*;
import project.tcss360.StartAndFinishGenerator.RandomStartAndFinishGenerator;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Creates the bare bone for a maze.
 */
public class TriviaMaze implements Serializable, Maze {
    /** ID for serialization*/
    @Serial
    private static final long serialVersionUID = 4012321312439487239L;
    /** Stores the room numbers*/
    private final NumberGrid myNumberGrid;
    /** Stores connections between rooms*/
    private final AdjacencyModel myAdjacencyModel;
    /** Stores the question and room number*/
    private final Map<Integer, Question> myQuestionMap;
    /** Stores the starting room number*/
    private final int myStart;
    /** Stores the ending room number*/
    private final int myFinish;

    /**
     * Construct an n x m grid of rooms for a maze. This implementation
     * really only uses an adjacency matrix to represent the connections
     * between rooms.
     * @param theColSize number of rows in maze
     * @param theRowSize number of columns in maze
     */
    public TriviaMaze(final int theRowSize, final int theColSize) {
        final RoomNumberGrid roomNumbers = new RoomNumberGrid(theRowSize, theColSize);
        myNumberGrid = new RoomNumberGrid(theRowSize, theColSize);
        myAdjacencyModel = new AdjacencyList(theRowSize, theColSize);
        final RandomStartAndFinishGenerator startFinishGenerator =
                new RandomStartAndFinishGenerator(
                        ((RoomNumberGrid)myNumberGrid).getGrid());
        myStart = startFinishGenerator.getRandomStartLocation();
        myFinish = startFinishGenerator.getFinishLocation(myStart);
        myQuestionMap = new HashMap<Integer, Question>();
    }

    /**
     * Gets the Adjacency Model
     * @return the Adjacency Model
     */
    protected AdjacencyModel getAdjacencyModel() {
        return myAdjacencyModel;
    }

    /**
     * Gets the numbers grid
     * @return the Number Grid
     */
    public NumberGrid getNumberGrid() {
        return myNumberGrid;
    }

    /**
     * Gets the starting room of the maze
     * @return the starting room for the maze
     */
    @Override
    public int getStartRoom() {
        return myStart;
    }

    /**
     * Gets the ending room of the maze
     * @return the ending room for the maze
     */
    @Override
    public int getFinishRoom() {
        return myFinish;
    }

    /**
     * Gets the size of the maze
     * @return the size of the maze
     */
    @Override
    public int getSize() {
        return myNumberGrid.getColSize() * myNumberGrid.getRowSize();
    }

    /**
     * Fills a map with questions for each room
     * @param theQuestions the set of questions
     */
    @Override
    public void fillWithQuestions(final Set<Question> theQuestions) {
        final int colSize = myNumberGrid.getColSize();
        final int rowSize = myNumberGrid.getRowSize();
        final int maxQuestions = rowSize * colSize;
        List<Integer> roomNumberList = IntStream.range(0, maxQuestions)
                .boxed().collect(Collectors.toList());
        roomNumberList.remove(myStart); // Don't place any question in start room.
        final Random randomNumber = new Random();
        int roomNumber;
        final Iterator<Question> questionIterator = theQuestions.iterator();
        while (myQuestionMap.size() < maxQuestions - 1) {
            int randomIndex = randomNumber.nextInt(roomNumberList.size());
            roomNumber = roomNumberList.get(randomIndex);
            roomNumberList.remove(randomIndex);
            myQuestionMap.put(roomNumber, questionIterator.next());
        }
        clearStartQuestion();
    }

    /**
     * Removes the question from the starting room
     */
    private void clearStartQuestion() {
        // Set player starting room as cleared.
        myQuestionMap.remove(myStart);
        final Question dummyQuestion = new ShortAnswerQuestion();
        myQuestionMap.put(myStart, dummyQuestion);
    }

    /**
     * checks to see if the maze can still be completed
     * @param theCurrentRoom the room the player currently is in
     * @return boolean identifying if the finish is reachable
     */
    @Override
    public boolean isFinishReachable(final int theCurrentRoom) {
        return myAdjacencyModel.doesPathExistBetween(theCurrentRoom, myFinish);
    }

    /**
     * Checks if the maze is won
     * @return returns if the maze is won or not
     */
    @Override
    public boolean isWon() {
        final Question finalQuestion = myQuestionMap.get(myFinish);
        return finalQuestion.isCleared();
    }

    /**
     * Gets the question for the corresponding room
     * @param theRoomNumber the room that you want the question for
     * @return returns the questions for the input room
     */
    @Override
    public Question getQuestionInRoom(final int theRoomNumber) {
        return myQuestionMap.get(theRoomNumber);
    }

    /**
     * Checks if the move is valid
     * @param theCurrentRoom the current room
     * @param theDirection the selected direction
     * @return a boolean if the move is valid or not
     */
    @Override
    public boolean isMoveValid(final int theCurrentRoom, Direction theDirection) {
        final boolean flag;
        if (myNumberGrid.isMoveOutOfBounds(theCurrentRoom, theDirection)) {
            flag = false;
        }
        else {
            final int nextRoom = getNextRoom(theCurrentRoom, theDirection);
            flag = myAdjacencyModel.isConnected(theCurrentRoom, nextRoom);
        }
        return flag;
    }

    /**
     * Gets the next room based on the current room and direction selected
     * @param theCurrentRoom the current room
     * @param theDirection the selected direction
     * @return the integer value of the next room
     */
    @Override
    public int getNextRoom(int theCurrentRoom, Direction theDirection) {
        return myNumberGrid.getNextNode(theCurrentRoom, theDirection);
    }

    /**
     * Checks if the rooms are connected to each other
     * @param theRoom the current room
     * @param theOtherRoom the next room
     * @return a boolean if they are connect or not
     */
    public boolean isConnected(final int theRoom, final int theOtherRoom) {
        return myAdjacencyModel.isConnected(theRoom, theOtherRoom);
    }

    /**
     * Gets the connected rooms of the current room
     * @param theCurrentRoom the current room
     * @return a list of the connected rooms
     */
    public List<Integer> getConnectedRooms(final int theCurrentRoom) {
        return myAdjacencyModel.getConnectedNeighbors(theCurrentRoom);
    }
}
