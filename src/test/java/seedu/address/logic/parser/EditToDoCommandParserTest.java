package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TODO;

import seedu.address.logic.commands.EditToDoCommand.EditToDoDescriptor;


import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditToDoCommand;
import seedu.address.model.todo.Content;
import seedu.address.testutil.EditToDoDescriptorBuilder;

public class EditToDoCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE);

    private EditToDoCommandParser parser = new EditToDoCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CONTENT, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditToDoCommand.MESSAGE_NOT_EDITED_TODO);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CONTENT_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + CONTENT_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_CONTENT_DESC, Content.MESSAGE_CONTENT_CONSTRAINTS); // invalid content
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TODO;
        String userInput = targetIndex.getOneBased() + CONTENT_DESC;

        EditToDoDescriptor descriptor = new EditToDoDescriptorBuilder().withContent(VALID_CONTENT).build();
        EditToDoCommand expectedCommand = new EditToDoCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
