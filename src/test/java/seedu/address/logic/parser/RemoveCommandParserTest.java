package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemoveCommand;
import seedu.address.model.person.Category;
import seedu.address.model.person.Subject;
//@@author shookshire
public class RemoveCommandParserTest {

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveCommand() {
        assertParseSuccess(parser, "1 c/s s/math",
                new RemoveCommand(INDEX_FIRST_PERSON, new Subject("math"), new Category("s")));
        assertParseSuccess(parser, "1 c/t s/math",
                new RemoveCommand(INDEX_FIRST_PERSON, new Subject("math"), new Category("t")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Missing index
        assertParseFailure(parser, "c/s s/math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        // Missing subject
        assertParseFailure(parser, "1 c/s", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        // Missing category
        assertParseFailure(parser, "1 s/math",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
    }
}
