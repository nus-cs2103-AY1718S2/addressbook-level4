//@@author chenchongsong
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {


    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {

        assertParseSuccess(parser, PREFIX_ADDRESS + SortCommand.SORT_ORDER_ASCENDING,
                new SortCommand(PREFIX_ADDRESS.toString(), SortCommand.SORT_ORDER_ASCENDING));

        assertParseSuccess(parser, PREFIX_PHONE + SortCommand.SORT_ORDER_DESCENDING,
                new SortCommand(PREFIX_PHONE.toString(), SortCommand.SORT_ORDER_DESCENDING));

        assertParseSuccess(parser, PREFIX_EMAIL + SortCommand.SORT_ORDER_ASCENDING,
                new SortCommand(PREFIX_EMAIL.toString(), SortCommand.SORT_ORDER_ASCENDING));

        assertParseSuccess(parser, PREFIX_NAME + SortCommand.SORT_ORDER_DESCENDING,
                new SortCommand(PREFIX_NAME.toString(), SortCommand.SORT_ORDER_DESCENDING));

        assertParseSuccess(parser, PREFIX_MONEY + SortCommand.SORT_ORDER_ASCENDING,
                new SortCommand(PREFIX_MONEY.toString(), SortCommand.SORT_ORDER_ASCENDING));

        assertParseSuccess(parser, PREFIX_TAG + SortCommand.SORT_ORDER_DESCENDING,
                new SortCommand(PREFIX_TAG.toString(), SortCommand.SORT_ORDER_DESCENDING));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // duplicate
        assertParseFailure(parser, "n/asc e/asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // typo
        assertParseFailure(parser, "e/ascc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // typo
        assertParseFailure(parser, "tt/desc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
