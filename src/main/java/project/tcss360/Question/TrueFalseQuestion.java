/*
 * TrueFalseQuestion.java
 *
 * TCSS 360 - Triva Maze
 */
package project.tcss360.Question;

import static project.tcss360.Question.QuestionType.trueFalse;

/**
 * True False Question class that inherits from Question
 */
public class TrueFalseQuestion extends Question {

    /**
     * Constructor for True/False Questions
     * @param thePrompt the prompt for the question
     * @param theAnswer The correct Answer, either T or F
     */
    public TrueFalseQuestion(final String thePrompt, final String theAnswer) {
        super(thePrompt, theAnswer);
    }

    /**
     * Empty Constructor Class for True/False Questions
     */
    public TrueFalseQuestion() {
        super();
    }

    /**
     * Getter for the question type
     * @return the Question Type Enum (trueFalse)
     */
    @Override
    public QuestionType getType() {
        return trueFalse;
    }
}
