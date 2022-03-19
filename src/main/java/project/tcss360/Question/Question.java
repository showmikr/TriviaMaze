/*
 * Question.java
 *
 * TCSS 360 - Triva Maze
 */
package project.tcss360.Question;

import java.io.Serial;
import java.io.Serializable;

import static project.tcss360.Question.QuestionType.general;

/**
 * Class for the question objects that holds questions and its answers
 */
public class Question implements Serializable {

    /**
     * Serializable UID
     */
    @Serial
    private static final long serialVersionUID = 2894381290469487329L;

    /**
     * Prompt for the question
     */
    private final String myPrompt;

    /**
     * Answer for the question
     */
    private final String myAnswer;

    /**
     * checks if the question has been answered correctly
     */
    private boolean isCleared = false;

    /**
     * Constructor Class for the abstract question type
     * @param thePrompt the Question that is being asked
     * @param theAnswer the Answer of the question being inputeted
     */
    public Question(final String thePrompt,
                    final String theAnswer) {
        myPrompt = thePrompt;
        myAnswer = theAnswer;
    }

    /**
     * Empty constructor class for an empty question type
     */
    public Question() {
        myPrompt = null;
        myAnswer = null;
        isCleared = true;
    }

    /**
     * Getter for Answer method
     * @return the answer
     */
    public String getAnswer() {
        return myAnswer;
    }

    /**
     * Getter for the Prompt variable
     * @return the Question Prompt stored
     */
    public String getPrompt() {
        return myPrompt;
    }


    /**
     * Compares the answer inputted to the current
     * @param theAnswer the attempted answer
     */
    public void tryAnswer(String theAnswer) {
        if (theAnswer.equalsIgnoreCase(getAnswer()))
            setAsCleared();
    }

    /**
     * Getter method to check if the question is cleared
     * @return the isCleared variable stored
     */
    public boolean isCleared() {
        return isCleared;
    }

    /**
     * Sets the question as cleared
     */
    protected void setAsCleared() {
        isCleared = true;
    }

    /**
     * Gets the question type
     * @return the question type as enum (general)
     */
    public QuestionType getType() {
        return general;
    }

}
