package project.tcss360.Maze;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.tcss360.Question.Question;
import project.tcss360.Question.ShortAnswerQuestion;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static project.tcss360.Maze.Direction.*;

public class SerializationTest {

    private SinglePlayerMaze mazeA = null;


    @BeforeEach
    public void setUp() {
        mazeA = new SinglePlayerMaze(3, 3);
        final Set<Question> dummyQuestions = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            dummyQuestions.add(new ShortAnswerQuestion());
        }
        dummyQuestions.add(new ShortAnswerQuestion("Bruh", "bruh"));
        mazeA.fillWithQuestions(dummyQuestions);
    }

    @AfterEach
    public void deleteTestSaves() {
        deleteSaves();
    }

    private void deleteSaves() {
        final File dirPath = new File("src/saves/");
        final File[] fileList = dirPath.listFiles();
        assert fileList != null;
        for (File file : fileList) {
            file.delete();
        }
    }

    @Test
    public void testSerializationPlayerPosition() {
        final int start = mazeA.getStartRoom();
        mazeA.getPlayer().move(right);
        final int updatedPlayerPos = mazeA.getPlayer().getLocation();
        mazeA.saveGame();
        final SinglePlayerMaze savedMaze = mazeA.loadGame(1);
        assertEquals(updatedPlayerPos, savedMaze.getPlayer().getLocation());
    }

    @Test public void testSerializationAdjacencyModel() {
        mazeA.saveGame();
        mazeA.getAdjacencyModel().disconnect(0, 3);
        mazeA.saveGame();
        final SinglePlayerMaze savedMaze = mazeA.loadGame(1);
        assertTrue(savedMaze.getAdjacencyModel().isConnected(0, 3));
        final SinglePlayerMaze savedMaze2 = mazeA.loadGame(2);
        assertFalse(savedMaze2.getAdjacencyModel().isConnected(0, 3));
    }

    @Test
    public void testSerializationNumberGraph() {
        mazeA.saveGame();
        final SinglePlayerMaze mazeB = mazeA.loadGame(1);
        assertEquals(mazeB.getNextRoom(0, right),
                mazeA.getNextRoom(0, right));
    }

    @Test
    public void testSerializationMaxSaves() {
        for (int i = 0; i < 20; i++) {
            mazeA.saveGame();
        }
        for (int i = 1; i <= mazeA.getMaxSaveSlots(); i++) {
            assertTrue(Files.exists(Path.of("src/saves/save" + i + ".ser")));
        }
    }
}
