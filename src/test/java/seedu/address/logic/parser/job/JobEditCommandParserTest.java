package seedu.address.logic.parser.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUMBER_OF_POSITIONS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_ALGORITHMS;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_EXCEL;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_POSITIONS_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_ALGORITHMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_EXCEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_INTERN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.job.JobEditCommand;
import seedu.address.logic.commands.job.JobEditCommand.EditJobDescriptor;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.EditJobDescriptorBuilder;

public class JobEditCommandParserTest {

    private static final String SKILL_EMPTY = " " + PREFIX_SKILL;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobEditCommand.MESSAGE_USAGE);

    private JobEditCommandParser parser = new JobEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_POSITION_INTERN, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", JobEditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + POSITION_DESC_INTERN, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + POSITION_DESC_INTERN, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid position
        assertParseFailure(parser, "1" + INVALID_POSITION_DESC, Position.MESSAGE_POSITION_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TEAM_DESC, Team.MESSAGE_TEAM_CONSTRAINTS); // invalid team
        // invalid location
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC, Location.MESSAGE_LOCATION_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_NUMBER_OF_POSITIONS_DESC,
                NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_SKILL_DESC, Skill.MESSAGE_SKILL_CONSTRAINTS); // invalid skill

        // invalid team followed by valid location
        assertParseFailure(parser, "1" + INVALID_TEAM_DESC + LOCATION_DESC_INTERN,
                Team.MESSAGE_TEAM_CONSTRAINTS);

        // valid team followed by invalid team. The test case for invalid team followed by valid team
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + TEAM_DESC_DEVELOPER_INTERN + INVALID_TEAM_DESC,
                Team.MESSAGE_TEAM_CONSTRAINTS);

        // while parsing {@code PREFIX_SKILL} alone will reset the SKILLs of the {@code Job} being edited,
        // parsing it together with a valid skill results in error
        assertParseFailure(parser, "1" + SKILL_DESC_JAVASCRIPT + SKILL_DESC_EXCEL + SKILL_EMPTY,
                Skill.MESSAGE_SKILL_CONSTRAINTS);
        assertParseFailure(parser, "1" + SKILL_DESC_JAVASCRIPT + SKILL_EMPTY + SKILL_DESC_EXCEL,
                Skill.MESSAGE_SKILL_CONSTRAINTS);
        assertParseFailure(parser, "1" + SKILL_EMPTY + SKILL_DESC_JAVASCRIPT + SKILL_DESC_EXCEL,
                Skill.MESSAGE_SKILL_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_POSITION_DESC + INVALID_LOCATION_DESC + VALID_TEAM_INTERN,
                Position.MESSAGE_POSITION_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + TEAM_DESC_DEVELOPER_INTERN + SKILL_DESC_ALGORITHMS
                + LOCATION_DESC_INTERN + POSITION_DESC_INTERN + SKILL_DESC_JAVASCRIPT + NUMBER_OF_POSITIONS_DESC_INTERN;

        EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withPosition(VALID_POSITION_INTERN)
                .withTeam(VALID_TEAM_DEVELOPER_INTERN).withLocation(VALID_LOCATION_INTERN)
                .withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_INTERN)
                .withSkills(VALID_SKILL_JAVASCRIPT, VALID_SKILL_ALGORITHMS).build();
        JobEditCommand expectedCommand = new JobEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + TEAM_DESC_DEVELOPER_INTERN + LOCATION_DESC_INTERN;

        EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withTeam(VALID_TEAM_DEVELOPER_INTERN)
                .withLocation(VALID_LOCATION_INTERN).build();
        JobEditCommand expectedCommand = new JobEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // position
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + POSITION_DESC_INTERN;
        EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withPosition(VALID_POSITION_INTERN).build();
        JobEditCommand expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // team
        userInput = targetIndex.getOneBased() + TEAM_DESC_INTERN;
        descriptor = new EditJobDescriptorBuilder().withTeam(VALID_TEAM_INTERN).build();
        expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_INTERN;
        descriptor = new EditJobDescriptorBuilder().withLocation(VALID_LOCATION_INTERN).build();
        expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // number of Positions
        userInput = targetIndex.getOneBased() + NUMBER_OF_POSITIONS_DESC_INTERN;
        descriptor = new EditJobDescriptorBuilder().withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_INTERN).build();
        expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // skills
        userInput = targetIndex.getOneBased() + SKILL_DESC_JAVASCRIPT;
        descriptor = new EditJobDescriptorBuilder().withSkills(VALID_SKILL_JAVASCRIPT).build();
        expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased()  + TEAM_DESC_INTERN  + LOCATION_DESC_INTERN
                + VALID_SKILL_JAVASCRIPT + TEAM_DESC_INTERN + LOCATION_DESC_INTERN + SKILL_DESC_JAVASCRIPT
                + TEAM_DESC_DEVELOPER_INTERN + LOCATION_DESC_DEVELOPER_INTERN + SKILL_DESC_EXCEL;

        EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withTeam(VALID_TEAM_DEVELOPER_INTERN)
                .withLocation(VALID_LOCATION_DEVELOPER_INTERN).withSkills(VALID_SKILL_JAVASCRIPT, VALID_SKILL_EXCEL)
                .build();
        JobEditCommand expectedCommand = new JobEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_TEAM_DESC + TEAM_DESC_DEVELOPER_INTERN;
        EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withTeam(VALID_TEAM_DEVELOPER_INTERN).build();
        JobEditCommand expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + LOCATION_DESC_DEVELOPER_INTERN + INVALID_TEAM_DESC
                + TEAM_DESC_DEVELOPER_INTERN;
        descriptor = new EditJobDescriptorBuilder().withTeam(VALID_TEAM_DEVELOPER_INTERN)
                .withLocation(VALID_LOCATION_DEVELOPER_INTERN).build();
        expectedCommand = new JobEditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetSkills_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + SKILL_EMPTY;

        EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withSkills().build();
        JobEditCommand expectedCommand = new JobEditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
