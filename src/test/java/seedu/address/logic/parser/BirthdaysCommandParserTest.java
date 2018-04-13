package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.BirthdaysCommand;

//@@author AzuraAiR
public class BirthdaysCommandParserTest {

    private BirthdaysCommandParser parser = new BirthdaysCommandParser();

    @Test
    public void parse_todaysFieldMissing_success() {
        assertParseSuccess(parser, "", new BirthdaysCommand(false));
    }

    @Test
    public void parse_todaysFieldPresent_success() {
        assertParseSuccess(parser, "today", new BirthdaysCommand(true));
    }

    @Test
    public void parse_todaysFieldinvalid_failure() {
        assertParseFailure(parser, "tomorrow", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                BirthdaysCommand.MESSAGE_USAGE));
    }
}
