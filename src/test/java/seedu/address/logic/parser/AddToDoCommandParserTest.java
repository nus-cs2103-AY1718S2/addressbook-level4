package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;
import seedu.address.testutil.ToDoBuilder;

public class AddToDoCommandParserTest {
    private AddToDoCommandParser parser = new AddToDoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ToDo expectedToDo = new ToDoBuilder().withContent(VALID_CONTENT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_CONTENT,
                new AddToDoCommand(expectedToDo));

        // valid content
        assertParseSuccess(parser, VALID_CONTENT,
                new AddToDoCommand(expectedToDo));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToDoCommand.MESSAGE_USAGE);

        // missing content
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid content
        assertParseFailure(parser, INVALID_CONTENT,
                Content.MESSAGE_CONTENT_CONSTRAINTS);
    }
}
