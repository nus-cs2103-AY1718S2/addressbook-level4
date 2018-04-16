package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_CHELSEA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_CHELSEA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RenameCommand;
import seedu.address.model.team.TeamName;

//@@author jordancjq
public class RenameCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RenameCommand.MESSAGE_USAGE);

    private RenameCommandParser parser = new RenameCommandParser();

    @Test
    public void parse_missingField_failure() {
        // no field
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no new team name specified
        assertParseFailure(parser, VALID_TEAM_ARSENAL, MESSAGE_INVALID_FORMAT);

        // no specified team to rename
        assertParseFailure(parser, TEAM_DESC_ARSENAL, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_extraNewTeamName_failure() {
        // two new team name specified
        assertParseFailure(parser, VALID_TEAM_ARSENAL + TEAM_DESC_CHELSEA + TEAM_DESC_CHELSEA,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_sameNameAsSpecified_failure() {
        // new team name is same as specified team team
        assertParseFailure(parser, VALID_TEAM_ARSENAL + TEAM_DESC_ARSENAL, RenameCommand.MESSAGE_NO_CHANGE);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid new team name
        assertParseFailure(parser, VALID_TEAM_ARSENAL + INVALID_TEAM_NAME_DESC,
                TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);

        // invalid given team name
        assertParseFailure(parser, "&-team" + TEAM_DESC_ARSENAL,
                TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validTeamName_success() {
        String userInput = VALID_TEAM_ARSENAL + TEAM_DESC_CHELSEA;
        RenameCommand expectedCommand =
                new RenameCommand(new TeamName(VALID_TEAM_ARSENAL), new TeamName(VALID_TEAM_CHELSEA));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
