package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();
    //@@author wynonaK
    @Test
    public void parse_validOwnerArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "-o 1", new DeleteCommand(1, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validForceOwnerArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "-fo 1", new DeleteCommand(4, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validPetArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "-p 1", new DeleteCommand(2, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validForcePetArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "-fp 1", new DeleteCommand(5, INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validAppointmentArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "-a 1", new DeleteCommand(3, INDEX_FIRST_PERSON));
    }
    //@@author
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
