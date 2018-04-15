//@@author deeheenguyen
package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEWEE_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEW_LOCATION_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.INTERVIEW_TITLE_DESC_SE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEWEE_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_LOCATION_SE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_TITLE_SE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.model.interview.Interview;
import seedu.address.testutil.InterviewBuilder;

public class AddInterviewCommandParserTest {
    private AddInterviewCommandParser parser = new AddInterviewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Interview expectedInterview = new InterviewBuilder()
                .withInterviewTitle(VALID_INTERVIEW_TITLE_SE)
                .withInterviewee(VALID_INTERVIEWEE_SE)
                .withDate(VALID_DATE_SE)
                .withInterviewLocation(VALID_INTERVIEW_LOCATION_SE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INTERVIEW_TITLE_DESC_SE
                + INTERVIEWEE_DESC_SE +  DATE_DESC_SE + INTERVIEW_LOCATION_DESC_SE,
                new AddInterviewCommand(expectedInterview));
    }
}
