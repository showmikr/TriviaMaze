/*
 * MazeApp.java
 *
 * TCSS 360 - Trivia Maze
 */
package project.tcss360;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sets up the GUI for launch
 */
public class MazeApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Nintendo Karuta Trivia Maze");
        Parent root = FXMLLoader.load(getClass().getResource("main-menu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.getIcons().add(new Image("file:src/main/resources/project/tcss360/gameIcon.png"));
        stage.show();
    }

    /**
     * Launches the GUI application
     * @param args the arguments for launch
     */
    public static void main(String[] args) {
        launch(args);
    }

}