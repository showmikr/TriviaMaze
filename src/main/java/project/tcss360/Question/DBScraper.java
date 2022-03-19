/*
 * DBScraper.java
 *
 * TCSS 360 - Triva Maze
 */
package project.tcss360.Question;

import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to process data from the database file and returns the data itself.
 */
public class DBScraper {

    /**
     * Reads and loads the question database from nintendo.db into a set and returns it
     * @return the set of questionf from nintendo.db
     */
    public static Set<Question> scrapeQuestions() {
        final Set<Question> questionSet = new HashSet<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/project/tcss360/nintendo.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);


            ResultSet rs = statement.executeQuery("select * from multiplechoicequestions");
            while (rs.next()) {
                final Question question = new MultipleChoiceQuestion(rs.getString("Question"),
                        rs.getString("CorrectAnswer"));
                for (int i = 1; i < 5; i++) {
                    ((MultipleChoiceQuestion)question).addOption(rs.getString("Answer_" + i));
                }
                questionSet.add(question);
            }
            rs = statement.executeQuery("select * from shortanswer");
            while (rs.next()) {
                final Question question = new ShortAnswerQuestion(rs.getString("Question"),
                        rs.getString("Answer"));
                questionSet.add(question);
            }
            rs = statement.executeQuery("select * from truefalse");
            while (rs.next()) {
                final Question question = new TrueFalseQuestion(rs.getString("Question"),
                        rs.getString("Answer"));
                questionSet.add(question);
            }
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (connection!=null){
                    connection.close();
                }
            }
            catch (SQLException e){
                System.err.println(e);
            }
        }
        return questionSet;
    }

}
