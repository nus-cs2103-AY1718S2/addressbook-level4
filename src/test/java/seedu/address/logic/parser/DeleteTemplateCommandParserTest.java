package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PURPOSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURPOSE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTemplateCommand;

//@@author ng95junwei

public class DeleteTemplateCommandParserTest {
    private DeleteTemplateCommandParser parser = new DeleteTemplateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String purpose = PURPOSE_DESC;
        assertParseSuccess(parser,  PREAMBLE_WHITESPACE + PURPOSE_DESC,
                new DeleteTemplateCommand(VALID_PURPOSE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
    }
}
//@@author
