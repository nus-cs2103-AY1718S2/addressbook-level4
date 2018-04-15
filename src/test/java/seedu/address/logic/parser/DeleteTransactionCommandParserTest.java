package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTransactionCommand;
import seedu.address.logic.commands.exceptions.CommandException;
//@@author ongkc
public class DeleteTransactionCommandParserTest {

    private DeleteTransactionCommandParser parser = new DeleteTransactionCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() throws CommandException {
        assertParseSuccess(parser, "1", new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws CommandException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTransactionCommand.MESSAGE_USAGE));
    }
}
