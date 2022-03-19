/*
 * ShortAnswerQuestion.java
 *
 * TCSS 360 - Triva Maze
 */
package project.tcss360.Question;

import static project.tcss360.Question.QuestionType.shortAns;

/**
 * Short answer question class that inherits from Question
 */
public class ShortAnswerQuestion extends Question {

    /**
     * Constructor for short answer questions
     * @param thePrompt the prompt for the question
     * @param theAnswer the answer for the question
     */
    public ShortAnswerQuestion(final String thePrompt, final String theAnswer) {
        super(thePrompt, theAnswer);
    }

    /**
     * Constructor for an empty short answer question
     */
    public ShortAnswerQuestion() {
        super();
    }

    /**
     * Getter for the question Type
     * @return the question type (shortAns)
     */
    @Override
    public QuestionType getType() {
        return shortAns;
    }
}
