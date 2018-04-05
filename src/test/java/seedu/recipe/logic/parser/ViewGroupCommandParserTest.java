//@@author hoangduong1607
package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recipe.logic.commands.ViewGroupCommand;
import seedu.recipe.model.recipe.GroupName;
import seedu.recipe.model.recipe.GroupPredicate;

public class ViewGroupCommandParserTest {

    private ViewGroupCommandParser parser = new ViewGroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_nonEmptyArgs_success() {
        String groupNameString = "My best";
        GroupName groupName = new GroupName(groupNameString);
        ViewGroupCommand expectedViewGroupCommand = new ViewGroupCommand(new GroupPredicate(groupName), groupName);
        assertParseSuccess(parser, groupNameString, expectedViewGroupCommand);
    }

}
