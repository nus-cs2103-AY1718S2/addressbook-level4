package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MESSAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PURPOSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PURPOSE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MESSAGE;

import org.junit.Test;

import seedu.address.logic.commands.AddTemplateCommand;
import seedu.address.model.email.Template;
import seedu.address.testutil.TemplateBuilder;

//@@author ng95junwei

public class AddTemplateCommandParserTest {
    private AddTemplateCommandParser parser = new AddTemplateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Template expectedTemplate = new TemplateBuilder()
                .withPurpose(VALID_PURPOSE)
                .withSubject(VALID_SUBJECT)
                .withMessage(VALID_MESSAGE)
                .build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PURPOSE_DESC + SUBJECT_DESC + MESSAGE_DESC,
                new AddTemplateCommand(expectedTemplate));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateCommand.MESSAGE_USAGE);

        // missing purpose prefix
        assertParseFailure(parser, SUBJECT_DESC + MESSAGE_DESC,
                expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, PURPOSE_DESC + MESSAGE_DESC,
                expectedMessage);

        // missing message prefix
        assertParseFailure(parser, PURPOSE_DESC + SUBJECT_DESC,
                expectedMessage);
    }
}

//@@author