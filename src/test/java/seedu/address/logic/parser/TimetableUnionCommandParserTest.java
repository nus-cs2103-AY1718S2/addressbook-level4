package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalOddEven.ODD;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TimetableUnionCommand;

//@@author AzuraAiR
public class TimetableUnionCommandParserTest {

    private TimetableUnionCommandParser parser = new TimetableUnionCommandParser();

    @Test
    public void parse_validArgs_returnsTimeTableCommand() {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        assertParseSuccess(parser, "Odd 1 2", new TimetableUnionCommand(indexes, ODD));
    }

    @Test
    public void parse_moreThanTwoValidArgs_returnsTimeTableCommand() {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        indexes.add(INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "Odd 1 2 3", new TimetableUnionCommand(indexes, ODD));
    }

    @Test
    public void parse_invalidNumArgs_throwsParseException() {
        assertParseFailure(parser, "Odd 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimetableUnionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "odd1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimetableUnionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_repeatedArgs_throwsParseException() {
        assertParseFailure(parser, "Odd 1 1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TimetableUnionCommand.MESSAGE_USAGE));
    }
}
