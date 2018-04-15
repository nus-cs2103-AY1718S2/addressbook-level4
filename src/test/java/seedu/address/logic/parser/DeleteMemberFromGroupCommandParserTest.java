//@@author jas5469
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDGROUPMEMBER_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDGROUPMEMBER_INFORMATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDGROUPMEMBER_NO_GROUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INFORMATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteMemberFromGroupCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.testutil.TypicalGroups;


public class DeleteMemberFromGroupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMemberFromGroupCommand.MESSAGE_USAGE);

    private DeleteMemberFromGroupCommandParser parser = new DeleteMemberFromGroupCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name of person specified
        assertParseFailure(parser, VALID_INFORMATION, MESSAGE_INVALID_FORMAT);


        // no  field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_compulsoryInvalidGroupField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMemberFromGroupCommand.MESSAGE_USAGE);

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

    @Test
    public void parse_validInformation_success() {

        //correct input
        Index index = INDEX_FIRST_PERSON;
        String correctInput = index.getOneBased() + " " + PREFIX_GROUP + "Group A";
        Group group = TypicalGroups.getTypicalGroups().get(0);

        assertParseSuccess(parser, correctInput, new DeleteMemberFromGroupCommand(index, group));
    }
}
