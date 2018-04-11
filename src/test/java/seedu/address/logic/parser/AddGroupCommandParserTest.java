//@@author jas5469
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.testutil.GroupBuilder;

public class AddGroupCommandParserTest {
    private AddGroupCommandParser parser = new AddGroupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withInformation(VALID_INFORMATION).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_INFORMATION,
                new AddGroupCommand(expectedGroup));

        // valid content
        assertParseSuccess(parser, VALID_INFORMATION,
                new AddGroupCommand(expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGroupCommand.MESSAGE_USAGE);

        // missing content
        assertParseFailure(parser, PREAMBLE_WHITESPACE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid content
        assertParseFailure(parser, INVALID_INFORMATION,
                Information.MESSAGE_INFORMATION_CONSTRAINTS);
    }
}
