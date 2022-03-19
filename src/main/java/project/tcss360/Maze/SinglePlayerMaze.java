/*
 * SinglePlayerMaze.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360.Maze;

import project.tcss360.Player.MazePlayer;
import project.tcss360.Player.Player;
import project.tcss360.Question.DBScraper;
import project.tcss360.Question.Question;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Creates a maze based off of TriviaMaze
 */
public class SinglePlayerMaze extends TriviaMaze implements Serializable {
    /** ID for serialization*/
    @Serial
    private static final long serialVersionUID = 6192836588759834743L;
    /** Stores the maximum amount of save slots*/
    private final int maxSaveSlots = 4;
    /** Stores the current number of save slots*/
    private int mySaveSlotCount;
    /** Stores the rooms that have been solved*/
    private final Set<Integer> mySolvedQuestionAddresses;
    /** Stores a player that is in the maze*/
    private final Player myPlayer;

    /**
     * Construct an n x m grid of rooms for a maze. This implementation
     * really only uses an adjacency matrix to represent the connections
     * between rooms.
     *
     * @param theRowSize number of columns in maze
     * @param theColSize number of rows in maze
     */
    public SinglePlayerMaze(int theRowSize, int theColSize) {
        super(theRowSize, theColSize);
        myPlayer = new MazePlayer(this);
        this.fillWithQuestions(DBScraper.scrapeQuestions());
        mySolvedQuestionAddresses = new HashSet<>();
        mySolvedQuestionAddresses.add(this.getStartRoom());
        setUpSaveDirectory();
        mySaveSlotCount = currentSaveCount();
    }

    /**
     * Gets the player
     * @return the player
     */
    public Player getPlayer() {
        return myPlayer;
    }

    /**
     * Gets the maximum number of allowed saved
     * @return the maximum number of saves
     */
    public int getMaxSaveSlots() {
        return maxSaveSlots;
    }

    /**
     * Gets the current amount of saves
     * @return the current amount of saves
     */
    public int currentSaveCount() {
        int saveCount = -1;
        try (Stream<Path> files = Files.list(Paths.get("src/saves/"))) {
            saveCount = (int)files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (saveCount < 0) {
            saveCount = mySaveSlotCount;
        }
        return saveCount;
    }

    /**
     * Creates a directory for the saves
     */
    private void setUpSaveDirectory() {
        final String path = "src/saves";
        final File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Stores the current state of the game
     */
    public void saveGame() {
        try {
            mySaveSlotCount = mySaveSlotCount % maxSaveSlots;
            final String fileName = getSaveSlotName();
            mySaveSlotCount++;
            final FileOutputStream file = new FileOutputStream(fileName);
            final ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the amount of saves
     * @return the amount of saves
     */
    public int getSaveSlotCount() {
        return mySaveSlotCount;
    }

    /**
     *
     * @return
     */
    private String getSaveSlotName() {
        return "src/saves/save" + (mySaveSlotCount + 1) + ".ser";
    }

    /**
     * Loads a singlePlayer maze from a saved slot
     * @param theSaveSlot an integer representation of the save slot
     * @return a single player maze from the saved slot
     */
    public SinglePlayerMaze loadGame(final int theSaveSlot) {
        SinglePlayerMaze loadedMaze = null;
        final String fileName = "src/saves/save" + theSaveSlot + ".ser";
        try {
            final FileInputStream file = new FileInputStream(fileName);
            final ObjectInputStream in = new ObjectInputStream(file);
            loadedMaze = (SinglePlayerMaze) in.readObject();
            in.close();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedMaze;
    }

    /**
     * Attempts to answer the question from the passed parameter
     * @param theAnswer the input answer from the user
     * @return an identifier that shows if the attempt was passed or failed
     */
    public boolean attemptQuestion(final String theAnswer) {
        boolean flag = true;
        final Question q = getQuestionInRoom(myPlayer.getLocation());
        q.tryAnswer(theAnswer);
        if (!q.isCleared()) {
            flag = false;
            final int attemptedRoom = myPlayer.getLocation();
            myPlayer.goBack();
            final int previousLocation = myPlayer.getLocation();
            super.getAdjacencyModel().disconnect(attemptedRoom, previousLocation);
        }
        if (flag) {
            mySolvedQuestionAddresses.add(myPlayer.getLocation());
        }
        return flag;
    }

    /**
     * Gets the question of the solved address
     * @return the solved question's address
     */
    public Set<Integer> getSolveQuestionAddresses() {
        return mySolvedQuestionAddresses;
    }

    /**
     * Skips the question but passing in the answer of the question
     */
    public void skipQuestion() {
        attemptQuestion(getQuestionInRoom(myPlayer.getLocation()).getAnswer());
    }
}
