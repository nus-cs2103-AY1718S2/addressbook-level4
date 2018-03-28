package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.model.person.Name;

public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() throws IllegalValueException {

        Name amy = new Name(VALID_NAME_AMY);

        DeleteByNameCommand expected = new DeleteByNameCommand(amy);

        assertParseSuccess(parser, VALID_NAME_AMY, expected);

        //leading whitespaces
        assertParseSuccess(parser, " " + VALID_NAME_AMY, expected);

        //leading and trailing whitespaces
        assertParseSuccess(parser, " " + VALID_NAME_AMY + "  ", expected);

        //trailing whitespaces
        assertParseSuccess(parser, VALID_NAME_AMY + "  ", expected);

        //non alphabatical input
        Name nonAlphabatical = new Name("123");
        assertParseSuccess(parser, "123", new DeleteByNameCommand(nonAlphabatical));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        //blank input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
        //whitespaces as input
        assertParseFailure(parser, "              ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //single special character
        assertParseFailure(parser, "@",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //multiple special characters
        assertParseFailure(parser, "@$%^&*(",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //special character between valid characters
        assertParseFailure(parser, "M@ry J@ne",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Other ascii characters
        assertParseFailure(parser, "ε",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
    }

}
