package seedu.address.logic.parser;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.model.person.Status;

public class StatusCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE);
    private StatusCommandParser parser = new StatusCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No status index specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // No index and no status index specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // Too many arguments specified
        assertParseFailure(parser, "1 2 3", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_validValue_returnsStatusCommand() {
        StatusCommand expectedInterviewCommand =
                new StatusCommand(Index.fromOneBased(2), new Status(5));

        assertParseSuccess(parser, "2 " + "5", expectedInterviewCommand);
    }

}
