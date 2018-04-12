//@@author jas5469
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDGROUPMEMBER_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDGROUPMEMBER_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDGROUPMEMBER_NO_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.Test;

import seedu.address.logic.commands.AddMemberToGroupCommand;
import seedu.address.model.group.Information;


public class AddMemberToGroupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberToGroupCommand.MESSAGE_USAGE);

    private AddMemberToGroupCommandParser parser = new AddMemberToGroupCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name of person specified
        assertParseFailure(parser, VALID_INFORMATION, MESSAGE_INVALID_FORMAT);


        // no  field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_compulsoryInvalidGroupField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberToGroupCommand.MESSAGE_USAGE);

        //missing group prefix
        assertParseFailure(parser, INVALID_ADDGROUPMEMBER_NO_GROUP, expectedMessage);

        //missing field
        assertParseFailure(parser , " ", expectedMessage);
    }

    @Test
    public void parse_indexNegative_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_INDEX);

        //negative index
        assertParseFailure(parser, INVALID_ADDGROUPMEMBER_INDEX, expectedMessage);
    }

    @Test
    public void parse_invalidInformation_failure() {
        String expectedMessage = String.format(Information.MESSAGE_INFORMATION_CONSTRAINTS);

        //wrong input for Group Information
        assertParseFailure(parser, INVALID_ADDGROUPMEMBER_INFORMATION, expectedMessage);

    }
}
