package project.tcss360.Question;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DBScraperTest {

    @Test
    public void testConnectionEstablished() {
        int counter = 0;
        final Set<Question> questionSet = DBScraper.scrapeQuestions();
        for (Question q : questionSet) {
            System.out.println(q.getPrompt());
            if (q.getType().equals(QuestionType.multChoice)) {
                final List<String> options =
                        ((MultipleChoiceQuestion)q).getOptions();
                for (int i = 0; i < options.size(); i++) {
                    System.out.print("Option " + (i + 1) + ": " + options.get(i));
                    System.out.println();
                }
            }
            System.out.println("Answer: " + (q.getAnswer()));
            System.out.println();
            counter++;
        }

        assertEquals(48, counter);
    }


}