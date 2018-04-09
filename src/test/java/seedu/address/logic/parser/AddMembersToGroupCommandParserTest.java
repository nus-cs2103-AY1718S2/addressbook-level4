package seedu.address.logic.parser;

//@@author jas5469

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_NO_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_NO_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddMembersToGroupCommand;


public class AddMembersToGroupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMembersToGroupCommand.MESSAGE_USAGE);

    private AddMembersToGroupCommandParser parser = new AddMembersToGroupCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name of person specified
        assertParseFailure(parser, VALID_INFORMATION, MESSAGE_INVALID_FORMAT);

        // no group specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no  field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMembersToGroupCommand.MESSAGE_USAGE);

        //missing group prefix
        assertParseFailure(parser, INVALID_DESC_NO_GROUP, expectedMessage);

        //missing person name prefix
        assertParseFailure(parser, INVALID_DESC_NO_NAME, expectedMessage);

        //missing field
        assertParseFailure(parser , " ", expectedMessage);
    }
}
