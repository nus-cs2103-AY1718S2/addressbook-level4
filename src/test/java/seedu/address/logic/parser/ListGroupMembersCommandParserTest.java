package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;


import org.junit.Test;

import seedu.address.logic.commands.ListGroupMembersCommand;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.group.MembersInGroupPredicate;

public class ListGroupMembersCommandParserTest {
    private ListGroupMembersCommandParser parser = new ListGroupMembersCommandParser();


    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,"  ", String.format(Information.MESSAGE_INFORMATION_CONSTRAINTS));
    }

    @Test
    public void parse_validArgs_returnsListGroupMembersCommand() {

        Group group = new Group(new Information("Group A"));
        ListGroupMembersCommand expectedListGroupMembersCommand =
                new ListGroupMembersCommand(new MembersInGroupPredicate(group), group);
        assertParseSuccess(parser, "Group A", expectedListGroupMembersCommand);

    }
    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser,"##!#@", String.format(Information.MESSAGE_INFORMATION_CONSTRAINTS));
    }
}
