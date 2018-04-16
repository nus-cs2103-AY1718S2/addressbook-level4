package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_NAME;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_ARSENAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_ARSENAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.team.TeamName;

//@@author jordancjq
public class AssignCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AssignCommand.MESSAGE_USAGE);

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, TEAM_DESC_ARSENAL, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid team name
        assertParseFailure(parser, INVALID_TEAM_NAME + INDEX_DESC_1, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, VALID_TEAM_ARSENAL + INVALID_INDEX, ParserUtil.MESSAGE_INVALID_INDEX);

        // multiple index, with 1 invalid
        assertParseFailure(parser, VALID_TEAM_ARSENAL + INDEX_DESC_1 + " -1", ParserUtil.MESSAGE_INVALID_INDEX);

        // multiple invalid values, first invalid value captured
        assertParseFailure(parser, INVALID_TEAM_NAME + INVALID_INDEX, TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // valid team name and 1 index
        TeamName targetTeam = new TeamName(VALID_TEAM_ARSENAL);
        List<Index> targetIndex = Collections.singletonList(INDEX_FIRST_PERSON);
        String userInput = VALID_TEAM_ARSENAL + INDEX_DESC_1;
        AssignCommand expectedCommand = new AssignCommand(targetTeam, targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);

        // valid team name and multiple valid indexes
        targetIndex = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        userInput = VALID_TEAM_ARSENAL + INDEX_DESC_1 + " 2 3";
        expectedCommand = new AssignCommand(targetTeam, targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
