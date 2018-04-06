package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CreateCommand;
import seedu.address.model.team.Team;
import seedu.address.model.team.TeamName;

public class CreateCommandParserTest {

    private static final String TEAM_NAME_EMPTY = "";

    private CreateCommandParser parser = new CreateCommandParser();

    @Test
    public void parse_fieldPresent_success() {
        // with team name
        CreateCommand expectedCommand = new CreateCommand(new Team(new TeamName(VALID_TEAM_ARSENAL)));
        String userInput = VALID_TEAM_ARSENAL;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE);

        // missing team name
        assertParseFailure(parser, TEAM_NAME_EMPTY, expectedMessage);
    }
}
