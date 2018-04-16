package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_BARCELONA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RemoveCommand;
import seedu.address.model.team.TeamName;

//@@author jordancjq
public class RemoveCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            RemoveCommand.MESSAGE_USAGE);

    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_missingField_failure() {
        // no team name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTeamName_failure() {
        // invalid team name
        assertParseFailure(parser, "&-Team", TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_validTeam_success() {
        String userInput = VALID_TEAM_ARSENAL;
        RemoveCommand expectedCommand = new RemoveCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);

        // with space in name
        userInput = VALID_TEAM_ARSENAL + " " + VALID_TEAM_BARCELONA;
        expectedCommand = new RemoveCommand(new TeamName(userInput));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
