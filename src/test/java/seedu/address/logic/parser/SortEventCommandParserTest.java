package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortEventCommand;

//@@author x3tsunayh

public class SortEventCommandParserTest {

    private SortEventCommandParser parser = new SortEventCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceArg_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        SortEventCommand expectedSortTitleCommand = new SortEventCommand("TITLE");
        SortEventCommand expectedSortLocationCommand = new SortEventCommand("LOCATION");
        SortEventCommand expectedSortDatetimeCommand = new SortEventCommand("DATETIME");

        //same value
        assertParseSuccess(parser, "TITLE", expectedSortTitleCommand);
        assertParseSuccess(parser, "LOCATION", expectedSortLocationCommand);
        assertParseSuccess(parser, "DATETIME", expectedSortDatetimeCommand);

        //case insensitive
        assertParseSuccess(parser, "tITLe", expectedSortTitleCommand);
        assertParseSuccess(parser, "LOCAtion", expectedSortLocationCommand);
        assertParseSuccess(parser, "DATetimE", expectedSortDatetimeCommand);
    }
}
