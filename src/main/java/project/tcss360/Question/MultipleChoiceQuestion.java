/*
 * MultipleChoiceQuestion.java
 *
 * TCSS 360 - Triva Maze
 */
package project.tcss360.Question;

import java.util.ArrayList;
import java.util.List;

import static project.tcss360.Question.QuestionType.multChoice;

/**
 * Multiple choice question class that inherits from Question
 */
public class MultipleChoiceQuestion extends Question {

    /**
     * List object that contains the possible options for the multiple choice test
     */
    private final List<String> myOptions = new ArrayList<>();

    /**
     * Multiple Choice Question constructor
     * @param thePrompt The prompt for the question
     * @param theAnswer The correct Answer, as a String
     */
    public MultipleChoiceQuestion(String thePrompt, String theAnswer) {
        super(thePrompt, theAnswer);
    }

    /**
     * Add extra options for the multiple choice questions, held as a list
     * @param possibleAnswer String of possible questions
     */
    public void addOption(final String possibleAnswer) {
        myOptions.add(possibleAnswer);
    }

    /**
     * Getter to get the list Object containing the possible answers
     * @return the List object of possible options
     */
    public List<String> getOptions() {
        return myOptions;
    }

    /**
     * Getter method to return multiple choice question type
     * @return the question type enum(multChoice)
     */
    @Override
    public QuestionType getType() {
        return multChoice;
    }
}
