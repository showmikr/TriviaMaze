package project.tcss360;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import project.tcss360.Maze.SinglePlayerMaze;
import project.tcss360.Question.MultipleChoiceQuestion;
import project.tcss360.Question.Question;
import java.net.URL;
import java.util.*;

public class MazeViewController {

    /** Main Menu Pane */
    @FXML StackPane mainMenuPane;

    /** New Game Button in Main Menu */
    @FXML Button newGameBtn;

    /** Tutorial Button in Main Menu */
    @FXML Button tutorialBtn;

    /** Exit Button in Main Menu */
    @FXML Button exitBtn;

    /** Maze View Pane */
    @FXML GridPane myMazePane;

    /** Primary Pane that holds every pane including the maze pane and question
     * panes.
     */
    @FXML VBox primaryContainer;

    /** True/False Question Pane */
    @FXML VBox trueFalsePane;

    /** True Button in True/False Question Pane */
    @FXML Button trueBtn;

    /** False Button in True/False Question Pane */
    @FXML Button falseBtn;

    /** Question Prompt for True/False Question pane */
    @FXML Label trueFalseLabel;

    /** Volume slider on toolbar */
    @FXML Slider volumeSlider;

    /** Save Button on toolbar */
    @FXML MenuItem saveBtn;

    /** Load Menu Button on toolbar */
    @FXML Menu loadMenu;

    /** Multiple Choice Question Pane */
    @FXML VBox multiChoicePane;

    /** Question prompt for Multiple Choice Question Pane */
    @FXML Label multiChoicePrompt;

    /** First answer option button for Multiple Choice Question Pane */
    @FXML Button optionOneBtn;

    /** Second answer option button for Multiple Choice Question Pane */
    @FXML Button optionTwoBtn;

    /** Third answer option button for Multiple Choice Question Pane */
    @FXML Button optionThreeBtn;

    /** Fourth answer option button for Multiple Choice Question Pane */
    @FXML Button optionFourBtn;

    /** Label for answer one in Multiple Choice Question Pane */
    @FXML Label optionLabelOne;

    /** Label for answer two in Multiple Choice Question Pane */
    @FXML Label optionLabelTwo;

    /** Label for answer three in Multiple Choice Question Pane */
    @FXML Label optionLabelThree;

    /** Label for answer four in Multiple Choice Question Pane */
    @FXML Label optionLabelFour;

    /** Short Answer Question Pane */
    @FXML VBox shortAnsPane;

    /** Question prompt for Short Answer Question Pane */
    @FXML Label shortAnsPrompt;

    /** Answer button for answering a question in Short Answer Question */
    @FXML Button shortAnsEnterBtn;

    /** Text field for getting user inputted answer for Short Answer Question */
    @FXML TextField shortAnsTextField;

    /** Pane for showing game over state (be it a winning or losing state) */
    @FXML VBox gameOverPane;

    /** Button for returning the to main menu after reaching game over state */
    @FXML Button returnMainMenuBtn;

    /** Button for resuming the trivia maze game */
    @FXML Button resumeBtn;

    /** Image shown when you lose the game */
    private Image myLoseImg = new Image("file:src/main/resources/project/tcss360/background_GameOver.png");

    /** Image shown when you win the game */
    private Image myWinImg = new Image("file:src/main/resources/project/tcss360/winScreen.png");

    /** Image shown when you open up the tutorial */
    private Image myTutorialImg = new Image("file:src/main/resources/project/tcss360/tutorial.png");

    /** Map of the options in a given multiple choice question */
    private Map<Integer, Label> myOptionLabels;

    /** Map of the buttons of answer options in a given multiple choice question */
    private Map<Integer, Button> myOptions;

    /** List of available save slots to show in the toolbar */
    private List<MenuItem> saveSlotList;

    /** Image of uncleared pipe */
    private final Image pipeImg = new Image("file:src/main/resources/project/tcss360/pipe.png");

    /** Image of mario on a pipe */
    private final Image mario = new Image("file:src/main/resources/project/tcss360/pipeMarioRed.png");

    /** Image of maze finish location */
    private final Image finish = new Image("file:src/main/resources/project/tcss360/pipeFlag.png");

    /** Image of what a cleared pipe looks like (it's just a red pipe)" */
    private final Image clearedPipe = new Image("file:src/main/resources/project/tcss360/redpipe.png");

    /** Background Image of Tutorial Pane (This background IS the tutorial) */

    BackgroundImage myTutorialBG = new BackgroundImage(myTutorialImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(1.0, 1.0, true, true, false, false));

    /** Background music media player */
    private MediaPlayer bgMusic;

    /** Controls how transparent unconnected pipes (relative to the player position)
     * should be.
     */
    private static final double myTransparencyLevel = 0.5;

    /** Maze Model for GUI */
    private SinglePlayerMaze myMaze = new SinglePlayerMaze(6, 6);

    /** List of "pipes" which are really buttons for each traversable location
     * on the maze.
     */
    private final List<Button> myPipeList = new ArrayList<>();

    /** List of pipes that are immediately accessible to the player */
    private List<Button> connectedPipes;


    /**
     * Sets up behavior when clicking "New Game" button in menu. Will load new
     * game.
     */
    @FXML
    private void onPlayNewGame() {
        resetGameBoard();
        disableAndHide(mainMenuPane);
        enableAndShow(myMazePane);
    }

    /**
     * Sets up behavior when clicking "Tutorial" button. Will show tutorial
     * pane.
     */
    @FXML
    private void onClickTutorial() {
        disableAndHide(mainMenuPane);
        loadBackground(myTutorialImg, gameOverPane);
        enableAndShow(gameOverPane);
    }

    @FXML
    private void onResume() {
        disableAndHide(mainMenuPane);
        enableAndShow(myMazePane);
    }

    /**
     * Sets up behavior when clicking "Exit" button in main menu. Will quit
     * application.
     */
    @FXML
    private void onExit() {
        Platform.exit();
    }

    /**
     * Sets up behavior when clicking "Save" button in toolbar. Will save
     * state of game when clicked.
     */
    @FXML
    private void onClickSave() {
        if (myMazePane.isVisible() && !myMazePane.isDisabled()) {
            myMaze.saveGame();
            updateLoadMenuView();
        }
    }

    /**
     * Runs full initialization setup for the GUI application.
     */
    @FXML
    private void initialize() {
        constructMazePane();
        disableAndHide(myMazePane);
        disableAndHide(gameOverPane);
        hideQuestionPanes();
        setUpTrueFalsePane();
        setUpMultiChoicePane();
        setUpSaveSlotButtons();
        setUpShortAnsPane();
        updateLoadMenuView();
        setUpBackgrounds();
        coolHacks();
        startBGM();
        resumeBtn.setVisible(false);
        resumeBtn.setDisable(true);
    }

    /**
     * Sets up the multiple choice pane options for filling with options.
     * The labels for each option are placed into a map that can be accessed
     * later when we need to update what these labels show. Also, this method
     * adds functionality for option buttons to glow on mouse hover.
     */
    private void setUpMultiChoicePane() {
        myOptions = new HashMap<>();
        myOptions.put(0, optionOneBtn);
        myOptions.put(1,optionTwoBtn);
        myOptions.put(2, optionThreeBtn);
        myOptions.put(3, optionFourBtn);
        for (int i = 0; i < 4; i++) {
            Button questionBlock = myOptions.get(i);
            questionBlock.hoverProperty().addListener((observable, oldVal, newVal) -> {
                if (newVal)
                    questionBlock.setEffect(new Bloom(0.5));
                else
                    questionBlock.effectProperty().set(null);
            });
        }
        myOptionLabels = new HashMap<>();
        myOptionLabels.put(0, optionLabelOne);
        myOptionLabels.put(1, optionLabelTwo);
        myOptionLabels.put(2, optionLabelThree);
        myOptionLabels.put(3, optionLabelFour);
    }

    /**
     * Sets up behavior for when user clicks any given answer option. Correct
     * answer will update maze to player's new location. Incorrect answer will
     * block player from the question they tried answering.
     */
    private void addMultiChoiceActionHandlers() {
        final List<String> answerOptions = ((MultipleChoiceQuestion)myMaze.getQuestionInRoom(myMaze.getPlayer().getLocation())).getOptions();
        for (int i = 0; i < myOptions.size(); i++) {
            final int count = i;
            Button questionBlock = myOptions.get(i);
            questionBlock.setOnAction(e -> {
                attemptQuestion(answerOptions.get(count));
            });
        }
    }

    /**
     * Updates the labels to reflect the answer options for the question the
     * player is supposed to tackle.
     */
    private void updateMultipleChoicePane() {
        final int playerLocation = myMaze.getPlayer().getLocation();
        final Question q = myMaze.getQuestionInRoom(playerLocation);
        multiChoicePrompt.setText(q.getPrompt());
        final List<String> questionList =  ((MultipleChoiceQuestion)q).getOptions();
        for (int i = 0; i < questionList.size(); i++) {
            myOptionLabels.get(i).setText(questionList.get(i));
        }
    }

    /**
     * Sets up the background images used for each pane in the GUI.
     */
    private void setUpBackgrounds() {
        Image cartridge = new Image("file:src/main/resources/project/tcss360/background_intro.png");
        loadBackground(cartridge, mainMenuPane);

        Image hills = new Image("file:src/main/resources/project/tcss360/background_maze.png");
        loadBackground(hills, myMazePane);

        Image brickCity = new Image("file:src/main/resources/project/tcss360/background_TFChoice.png");
        loadBackground(brickCity, trueFalsePane);

        Image fortress = new Image("file:src/main/resources/project/tcss360/background_MC.png");
        loadBackground(fortress, multiChoicePane);

        Image sea = new Image("file:src/main/resources/project/tcss360/background_FIB.png");
        loadBackground(sea, shortAnsPane);
    }

    /**
     * loads up a background image on a pane using an image and the target pane
     * as input.
     * @param theImage Input Image
     * @param thePane Target Pane for placing image as background
     */
    private void loadBackground(final Image theImage, final Pane thePane) {
        BackgroundImage theImageBG = new BackgroundImage(theImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,new BackgroundSize(1.0, 1.0, true, true, false, false));
        thePane.setBackground(new Background(theImageBG));
    }

    /**
     * Adds in the correct number of save slot buttons for the "Load" button
     * in the toolbar based on how many saves the player has.
     */
    private void setUpSaveSlotButtons() {
        saveSlotList = new ArrayList<>();
        for (int i = 0; i < myMaze.getMaxSaveSlots(); i++) {
            final MenuItem saveSlot = new MenuItem("Save Slot " + (i+1));
            saveSlot.setVisible(false);
            saveSlotList.add(saveSlot);
        }
        for (MenuItem saveSlot : saveSlotList) {
            loadMenu.getItems().add(saveSlot);
        }
        addSaveSlotActionEvents();
    }

    /**
     * Sets up behavior for when player clicks a save slot button. When clicked,
     * the GUI will be updated to reflect the state of the game from the save
     * slot desired.
     */
    private void addSaveSlotActionEvents() {
        for (int i = 0; i < saveSlotList.size(); i++) {
            final int slotNumber = i + 1;
            final MenuItem saveSlot = loadMenu.getItems().get(i);
            saveSlot.setOnAction(e -> {
                showPostLoadMazeView(slotNumber);
            });
        }
    }

    /**
     * Updates the view of the maze once you load a save slot.
     * @param theSaveSlot the save slot that is loaded
     */
    private void showPostLoadMazeView(final int theSaveSlot) {
        disableAndHide(mainMenuPane);
        disableAndHide(gameOverPane);
        hideQuestionPanes();
        clearMazeView();
        final int playerLocationBeforeLoad = myMaze.getPlayer().getLocation();
        myMaze = myMaze.loadGame(theSaveSlot);
        updateMazeView(playerLocationBeforeLoad);
    }

    /**
     * Updates the number of save slot buttons the "Load" menu button will
     * reveal upon clicking it. If you have only 1 save, clicking "Load" will
     * only reveal 1 save slot button whereas having 3 saves will make "Load"
     * reveal 3 save slot buttons.
     */
    private void updateLoadMenuView() {
        final int currentSaves = myMaze.currentSaveCount();
        for (int i = 0; i < currentSaves; i++) {
            loadMenu.getItems().get(i).setVisible(true);
        }
    }

    /**
     * Sets up and starts the game background music. Furthermore, it binds
     * the background music volume to the volume slider on the toolbar.
     */
    private void startBGM() {
        URL resource = getClass().getResource("Mii.mp3");
        Media theMedia = new Media(resource.toString());
        bgMusic = new MediaPlayer(theMedia);
        bgMusic.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                bgMusic.seek(Duration.ZERO);
            }
        });
        bgMusic.setVolume(0.3);
        bgMusic.play();
        bindVolumeSlider();
    }

    /**
     * Binds the game background music volume to the volume slider on the toolbar.
     */
    private void bindVolumeSlider() {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (!oldValue.equals(newValue)) {
                    bgMusic.setVolume((double)newValue/100);
                }
            }
        });
    }

    /**
     * Runs all the necessary operations for building out the view of the
     * maze on game startup.
     */
    private void constructMazePane() {
        myMazePane.prefHeightProperty().bind(primaryContainer.heightProperty());

        setUpPipeGrid();
        establishAdjacentPipes();
        applyStartAndFinishEffects();
        updateConnectedPipes();
        makePipesGlowOnHover();
        setUpPipeClickEventHandlers();
    }

    /**
     * Builds out the grid of pipes in the maze view with some initial look
     * settings for the pipes.
     */
    private void setUpPipeGrid() {
        for (int i = 0; i < myMaze.getSize(); i++) {
            final Button pipe = new Button();
            pipe.setGraphic(new ImageView(pipeImg));
            pipe.setBackground(null);
            pipe.setOpacity(myTransparencyLevel);
            pipe.setDisable(true);
            myPipeList.add(pipe);
        }
        for (int i = 0; i < myPipeList.size(); i++) {
            final Button pipeButton = myPipeList.get(i);
            myMazePane.add(pipeButton,
                    myMaze.getNumberGrid().getCol(i),
                    myMaze.getNumberGrid().getRow(i), 1, 1);
        }
    }

    /**
     * Highlight and enable pipes that the player is adjacent to at the start
     * of a new game.
     */
    private void establishAdjacentPipes() {
        connectedPipes = new ArrayList<>();
        final List<Integer> adjacentRooms = myMaze.getConnectedRooms(myMaze.getStartRoom());
        for (Integer address : adjacentRooms) {
            connectedPipes.add(myPipeList.get(address));
        }
    }

    /**
     * Make the finish location glow and the start location show mario.
     */
    private void applyStartAndFinishEffects() {
        Button marioButton = myPipeList.get(myMaze.getStartRoom());
        marioButton.setGraphic(new ImageView(mario));
        marioButton.setOpacity(1);
        makeFinishGlow();
    }

    /**
     * Makes the finish room glow in the maze view.
     */
    private void makeFinishGlow() {
        final Button finishPipe = myPipeList.get(myMaze.getFinishRoom());
        finishPipe.setGraphic(new ImageView(finish));
        finishPipe.setEffect(new Glow(1));
        finishPipe.setOpacity(1);
    }

    /**
     * This is run just before we update the maze view. We disable the current
     * pipes adjacent to and accessible to the player and clear them before
     * grabbing and highlighting the new set of pipes accessible to the player.
     */
    private void disableCurrentConnectedPipes() {
        for (Button pipe : connectedPipes) {
            pipe.setOpacity(myTransparencyLevel);
            pipe.setDisable(true);
        }
        connectedPipes.clear();
    }

    /**
     * Updates the maze view to highlight the pipes accessible to the player
     * after they move.
     */
    private void updateConnectedPipes() {
        final int playerPosition = myMaze.getPlayer().getLocation();
        final List<Integer> connectedPipePositions =
                myMaze.getConnectedRooms(playerPosition);
        for (Integer position : connectedPipePositions) {
            Button pipe = myPipeList.get(position);
            pipe.setDisable(false);
            pipe.setOpacity(1);
            connectedPipes.add(pipe);
        }
        //Make the pipe at mario's location opaque as well
        myPipeList.get(playerPosition).setOpacity(1);
    }

    /**
     * Enables accessible pipes (from the player's position) to glow when the
     * mouse hovers over them.
     */
    private void makePipesGlowOnHover() {
        for (Button pipe : myPipeList) {
            pipe.hoverProperty().addListener((ChangeListener<Boolean>)
                    (observable, oldVal, newVal) -> {
                        if (newVal)
                            pipe.setEffect(new Bloom(0.5));
                        else
                            pipe.effectProperty().set(null);
                    });
        }
    }

    /**
     * This is the crux of how player movement is handled. This method
     * sets up every pipe in the maze view to activate the question mapped to
     * it. For each pipe, we will display the question associated with it.
     */
    private void setUpPipeClickEventHandlers() {
        for (Button pipe : myPipeList) {
            final int pipeAddress = myPipeList.indexOf(pipe);
            pipe.setOnMouseClicked(e -> {
                myMaze.getPlayer().setLocation(pipeAddress);
                final Question question = myMaze.getQuestionInRoom(pipeAddress);
                final String qType = question.getClass().getSimpleName();
                if (!question.isCleared()) {
                    disableAndHide(myMazePane);
                    switch (qType) {
                        case "TrueFalseQuestion" -> {
                            trueFalseLabel.setText(question.getPrompt());
                            enableAndShow(trueFalsePane);
                        }
                        case "MultipleChoiceQuestion" -> {
                            addMultiChoiceActionHandlers();
                            updateMultipleChoicePane();
                            enableAndShow(multiChoicePane);
                        }
                        case "ShortAnswerQuestion" -> {
                            enableAndShow(shortAnsPane);
                            shortAnsTextField.clear();
                            shortAnsPrompt.setText(question.getPrompt());
                        }
                    }
                } else {
                    updateMazeView();
                }
            });
        }
    }

    /**
     * Sets up the buttons in the true/false pane to glow on mouse hover.
     */
    private void setUpTrueFalsePane() {
       final List<Button> buttons = new ArrayList<>();
       buttons.add(trueBtn);
       buttons.add(falseBtn);
        for (Button b : buttons) {
            b.hoverProperty().addListener((ChangeListener<Boolean>)
                    (observable, oldVal, newVal) -> {
                        if (newVal)
                            b.setEffect(new Bloom(0.5));
                        else
                            b.effectProperty().set(null);
                    });
        }
    }

    /**
     * disables and hides a given pane from being viewed.
     * @param thePane the target pane to disable and hide
     */
    private void disableAndHide(final Pane thePane) {
        thePane.setVisible(false);
        thePane.setDisable(true);
    }

    /**
     * Sets up behavior for when player clicks "True" option in true/false question.
     * When "true" option is clicked the model is asked if "T" is the correct answer.
     */
    @FXML
    private void onTrueClick() {
        attemptQuestion("T");
    }

    /**
     * Sets up behavior for when player clicks "False" option in true/false question.
     * When "false" option is clicked the model is asked if "F" is the correct answer.
     */
    @FXML
    private void onFalseClick() {
        attemptQuestion("F");
    }

    /**
     * Attempts the question at the player's current location with a given answer.
     * After question attempt we update the maze view to reflect whether the player
     * got the question correct or not.
     * @param theAnswer the answer to try
     */
    private void attemptQuestion(final String theAnswer) {
        myMaze.attemptQuestion(theAnswer);
        runQuestionCleanup();
    }

    /**
     * Updates the maze view after the player answers a question.
     */
    private void runQuestionCleanup() {
        hideQuestionPanes();
        updateMazeView();
        runGameCompleteCheck();
    }

    /**
     * Runs a check to see if the player has won or failed the game.
     * If either of these conditions is met, we show a fail state pane or a
     * win state pane.
     */
    private void runGameCompleteCheck() {
        if (myMaze.isWon()) {
            loadBackground(myWinImg, gameOverPane);
            resetAndGoToGameOverPane();
        } else if (!myMaze.isFinishReachable(myMaze.getPlayer().getLocation())) {
            loadBackground(myLoseImg, gameOverPane);
            resetAndGoToGameOverPane();
        }
    }

    /**
     * Resets the game and shows us the game over pane which could either be
     * a game win or loss.
     */
    private void resetAndGoToGameOverPane() {
        disableAndHide(myMazePane);
        enableAndShow(gameOverPane);
    }

    /**
     * resets the entire maze view to reflect a new game
     */
    private void resetGameBoard() {
        clearMazeView();
        myMaze = new SinglePlayerMaze(6, 6);
        updateMazeView();
        applyStartAndFinishEffects();
        resumeBtn.setDisable(true);
        resumeBtn.setVisible(false);
    }

    /**
     * Clears out the view of the maze while also updating (if needed)
     * the view of the locations of the finish and start locations.
     */
    private void clearMazeView() {
        List<Integer> pipeAddresses = new ArrayList<>();
        pipeAddresses.add(myMaze.getStartRoom());
        pipeAddresses.add(myMaze.getFinishRoom());
        for (Integer address : pipeAddresses) {
            myPipeList.get(address).setEffect(null);
            myPipeList.get(address).setGraphic(new ImageView(pipeImg));
            myPipeList.get(address).setOpacity(myTransparencyLevel);
        }
        wipeCurrentSolvedPipes();
        erasePlayerFromPosition(myMaze.getPlayer().getLocation());
    }

    /**
     * Hides and disables all the question panes in the GUI view.
     */
    private void hideQuestionPanes() {
        disableAndHide(trueFalsePane);
        disableAndHide(multiChoicePane);
        disableAndHide(shortAnsPane);
    }


    /**
     * Updates maze view, but requires previous location of player. The player
     * model code already provides us with a method for getting their previous
     * position, but it isn't usable when we load up a new save. So, to erase
     * the previous pipe the player was on after loading a new save, we need
     * to manually supply this method with player's previous location before
     * loading a new save.
     *
     */
    private void updateMazeView(final int lastKnownPlayerLocation) {
        erasePlayerFromPosition(lastKnownPlayerLocation);
        makeFinishGlow();
        disableCurrentConnectedPipes();
        updateConnectedPipes();
        updateSolvedPipes();
        spawnPlayerInView();
        enableAndShow(myMazePane);
    }

    /**
     * Updates maze view based on player's previous location after they've moved.
     */
    private void updateMazeView() {
        updateMazeView(myMaze.getPlayer().getPreviousLocation());
    }

    /**
     * Erases the player's position from the maze view.
     * @param theAddress the player's current location
     */
    private void erasePlayerFromPosition(final int theAddress) {;
        final Button pipeToWipe = myPipeList.get(theAddress);
        pipeToWipe.setGraphic(null);
        pipeToWipe.setOpacity(myTransparencyLevel);
        pipeToWipe.setGraphic(new ImageView(pipeImg));
    }

    /**
     * Draws the player onto the maze view using the player's current location
     * in the maze model.
     */
    private void spawnPlayerInView() {
        final int playerPosition = myMaze.getPlayer().getLocation();
        myPipeList.get(playerPosition).setGraphic(new ImageView(mario));
    }

    /**
     * Enables and makes visible the given pane.
     * @param thePane the target pane to enable and show
     */
    private void enableAndShow(final Pane thePane) {
        thePane.setVisible(true);
        thePane.setDisable(false);
    }

    /**
     * Generates a simple cheat where player can skip a question by pressing
     * the COMMA button on their keyboard.
     */
    private void coolHacks() {
        primaryContainer.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.BACK_QUOTE)) {
                myMaze.skipQuestion();
                hideQuestionPanes();
                runQuestionCleanup();
            }
        });
    }

    /**
     * resets view of pipes that player has already cleared back to their
     * original uncleared state.
     */
    private void wipeCurrentSolvedPipes() {
        for (Integer address : myMaze.getSolveQuestionAddresses()) {
            myPipeList.get(address).setGraphic(new ImageView(pipeImg));
        }
    }

    /**
     * Updates a pipe to look "cleared" if a player has correctly answered the
     * question inside it.
     */
    private void updateSolvedPipes() {
        for (Integer address : myMaze.getSolveQuestionAddresses()) {
            myPipeList.get(address).setGraphic(new ImageView(clearedPipe));
        }
    }

    /**
     * Sets up the short answer pane view.
     */
    private void setUpShortAnsPane() {
        shortAnsEnterBtn.setDisable(true);
        shortAnsEnterBtn.setOpacity(0.3);
        bindShortAnsBlockToTextField();
        makeShortAnsBlockGlowOnHover();
    }

    /**
     * Binds the opacity of the short answer pane answer button to whether
     * if the player has entered any text in the answer text field or not.
     * If the player types in text, the button will become enabled and opaque.
     * If no text is in the answer field, the button will remain disabled and
     * slightly transparent.
     */
    private void bindShortAnsBlockToTextField() {
        shortAnsTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue.trim().length() > 0) {
                    shortAnsEnterBtn.setDisable(false);
                    shortAnsEnterBtn.setOpacity(1);
                } else {
                    shortAnsEnterBtn.setDisable(true);
                    shortAnsEnterBtn.setOpacity(0.5);
                }
            }
        });
    }

    /**
     * Makes the answer button glow on the short answer question pane when
     * the mouse hovers over it, but only when the button is enabled (i.e, when
     * the answer field isn't empty)
     */
    private void makeShortAnsBlockGlowOnHover() {
        shortAnsEnterBtn.hoverProperty().addListener((ChangeListener<Boolean>)
                (observable, oldVal, newVal) -> {
            if (newVal && !shortAnsEnterBtn.isDisabled()) {
                shortAnsEnterBtn.setEffect(new Bloom(0.5));
            } else
                shortAnsEnterBtn.setEffect(null);
                });
    }

    /**
     * Makes it so that player can answer short answer questions by pressing
     * ENTER key on keyboard after typing their answer.
     * @param key the key pressed
     */
    @FXML
    private void onShortAnsEnterKeyPressed(KeyEvent key) {
        if (key.getCode().equals(KeyCode.ENTER) && !shortAnsEnterBtn.isDisabled()) {
            onShortAnsMouseClicked();
        }
    }

    /**
     * Makes it so that player can answer question by clicking on answer button
     * in short answer questions.
     */
    @FXML
    private void onShortAnsMouseClicked() {
        final String playerAnswer = shortAnsTextField.getText().trim();
        attemptQuestion(playerAnswer);
    }

    /**
     * Hides all other panes from GUI view and shows only the main menu.
     */
    private void displayMainMenu() {
        disableAndHide(myMazePane);
        disableAndHide(gameOverPane);
        hideQuestionPanes();
        enableAndShow(mainMenuPane);
    }

    /**
     * Returns player to main menu screen when player clicks "Main Menu" button
     * after game over screen is presented. Also handles whether to show the
     * "Resume" button on the menu based on if there is a game in progress.
     */
    @FXML
    private void onMainMenuClick() {
        displayMainMenu();
        if (!gameOverPane.getBackground().getImages().get(0).equals(myTutorialBG)) {
            resumeBtn.setDisable(true);
            resumeBtn.setVisible(false);
        }
    }

    /**
     * Returns player to main menu screen after clicking "Main Menu" on the
     * toolbar. Ensures that resume button only displays while game is in progress.
     */
    @FXML
    private void onToolBarMainMenuClick() {
        final boolean isGameInProgress = myMaze.isFinishReachable(myMaze.getPlayer().getLocation());
        if (isGameInProgress && !mainMenuPane.isVisible()) {
            resumeBtn.setDisable(false);
            resumeBtn.setVisible(true);
        }
        if (shortAnsPane.isVisible() || multiChoicePane.isVisible() || trueFalsePane.isVisible()) {
            myMaze.getPlayer().setLocation(myMaze.getPlayer().getPreviousLocation());
        }
        displayMainMenu();
    }

}
