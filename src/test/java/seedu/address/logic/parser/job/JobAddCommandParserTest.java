package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUMBER_OF_POSITIONS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EXCEL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_EXCEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_DEVELOPER_INTERN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.job.JobAddCommand;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.JobBuilder;

public class JobAddCommandParserTest {
    private JobAddCommandParser parser = new JobAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Job expectedJob = new JobBuilder().withPosition(VALID_POSITION_DEVELOPER_INTERN)
                .withTeam(VALID_TEAM_DEVELOPER_INTERN).withLocation(VALID_LOCATION_DEVELOPER_INTERN)
                .withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN).withTags(VALID_TAG_JAVASCRIPT)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POSITION_DESC_DEVELOPER_INTERN
                + TEAM_DESC_DEVELOPER_INTERN + LOCATION_DESC_DEVELOPER_INTERN
                + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT, new JobAddCommand(expectedJob));

        // multiple positions - last position accepted
        assertParseSuccess(parser, POSITION_DESC_INTERN + POSITION_DESC_DEVELOPER_INTERN
                + TEAM_DESC_DEVELOPER_INTERN + LOCATION_DESC_DEVELOPER_INTERN
                + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT, new JobAddCommand(expectedJob));

        // multiple teams - last team accepted
        assertParseSuccess(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_INTERN
                + TEAM_DESC_DEVELOPER_INTERN + LOCATION_DESC_DEVELOPER_INTERN
                + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT, new JobAddCommand(expectedJob));

        // multiple locations - last location accepted
        assertParseSuccess(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_INTERN + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN
                + TAG_DESC_JAVASCRIPT, new JobAddCommand(expectedJob));

        // multiple numberOfPositions - last numberOfPositions accepted
        assertParseSuccess(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN
                + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT, new JobAddCommand(expectedJob));

        // multiple tags - all accepted
        Job expectedJobWithMultipleTags = new JobBuilder().withPosition(VALID_POSITION_DEVELOPER_INTERN)
                .withTeam(VALID_TEAM_DEVELOPER_INTERN).withLocation(VALID_LOCATION_DEVELOPER_INTERN)
                .withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN).withTags(VALID_TAG_JAVASCRIPT,
                    VALID_TAG_EXCEL).build();
        assertParseSuccess(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT
                + TAG_DESC_EXCEL, new JobAddCommand(expectedJob));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE);

        // missing position prefix
        assertParseFailure(parser, VALID_POSITION_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                expectedMessage);

        // missing team prefix
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + VALID_TEAM_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                expectedMessage);

        // missing location prefix
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + VALID_LOCATION_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                expectedMessage);

        // missing numberOfLocations prefix
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                expectedMessage);

        // missing tags prefix
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + VALID_TAG_JAVASCRIPT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid position
        assertParseFailure(parser, INVALID_POSITION_DESC + TEAM_DESC_DEVELOPER_INTERN
                        + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                Position.MESSAGE_POSITION_CONSTRAINTS);

        // invalid team
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + INVALID_TEAM_DESC
                        + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                Team.MESSAGE_TEAM_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                        + INVALID_LOCATION_DESC + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + TAG_DESC_JAVASCRIPT,
                Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid numberOfPositions
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                        + LOCATION_DESC_DEVELOPER_INTERN + INVALID_NUMBER_OF_POSITIONS_DESC + TAG_DESC_JAVASCRIPT,
                NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

}