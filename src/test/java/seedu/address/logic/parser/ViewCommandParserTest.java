package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BARCELONA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.team.TeamName;

//@@author jordancjq
public class ViewCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ViewCommand.MESSAGE_USAGE);

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_missingField_failure() {
        // no team name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid team name
        assertParseFailure(parser, INVALID_TEAM_NAME_DESC, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validTeam_success() {
        String userInput = VALID_TEAM_ARSENAL;
        ViewCommand expectedCommand = new ViewCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);

        // with space in name
        userInput = VALID_TEAM_ARSENAL + " " + VALID_TEAM_BARCELONA;
        expectedCommand = new ViewCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
