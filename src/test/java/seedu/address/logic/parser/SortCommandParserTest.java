package seedu.address.logic.parser;

//@@author lohtianwei
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.SortCommand.BY_ASCENDING;
//import static seedu.address.logic.commands.SortCommand.BY_DESCENDING;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

//import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void noArguments_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArguments_failure() {
        //more than 1 field entered
        assertParseFailure(parser, "name" + " " + "address" + " " + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //invalid field entered
        assertParseFailure(parser, "invalid" + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //invalid sort order entered
        assertParseFailure(parser, "name" + " " + "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //no field entered
        assertParseFailure(parser, "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //no order entered
        assertParseFailure(parser, "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
    /*
    @Test
    public void parse_validArguments_success() {
        //valid input for sort by name in asc order
        assertParseSuccess(parser, "name" + " " + BY_ASCENDING,
                new SortCommand("name", BY_ASCENDING));

        //valid input for sort by name in desc order
        assertParseSuccess(parser, "name" + " " + BY_DESCENDING,
                new SortCommand("name", BY_DESCENDING));

        //valid input for sort by address in asc order
        assertParseSuccess(parser, "address" + " " + BY_ASCENDING,
                new SortCommand("address", BY_ASCENDING));

        //valid input for sort by address in desc order
        assertParseSuccess(parser, "address" + " " + BY_DESCENDING,
                new SortCommand("address", BY_DESCENDING));

        //valid input for sort by phone in asc order
        assertParseSuccess(parser, "phone" + " " + BY_ASCENDING,
                new SortCommand("phone", BY_ASCENDING));

        //valid input for sort by phone in desc order
        assertParseSuccess(parser, "phone" + " " + BY_DESCENDING,
                new SortCommand("phone", BY_DESCENDING));

        //valid input for sort by email in asc order
        assertParseSuccess(parser, "email" + " " + BY_ASCENDING,
                new SortCommand("email", BY_ASCENDING));

        //valid input for sort by email in desc order
        assertParseSuccess(parser, "email" + " " + BY_DESCENDING,
                new SortCommand("email", BY_DESCENDING));
    }
    */
}
