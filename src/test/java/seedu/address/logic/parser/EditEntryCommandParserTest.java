package seedu.address.logic.parser;
//@@author SuxianAlicia
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENTRY_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.model.event.EndDate;
import seedu.address.model.event.EndTime;
import seedu.address.model.event.EntryTitle;
import seedu.address.model.event.StartDate;
import seedu.address.model.event.StartTime;
import seedu.address.testutil.EditEntryDescriptorBuilder;

public class EditEntryCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE);

    private EditEntryCommandParser parser = new EditEntryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ENTRY_TITLE_MEET_BOSS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEntryCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ENTRY_TITLE_DESC_MEET_BOSS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ENTRY_TITLE_DESC_MEET_BOSS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 o/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ENTRY_TITLE_DESC,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS); // invalid entry title

        assertParseFailure(parser, "1"
                + INVALID_START_DATE_DESC, StartDate.MESSAGE_START_DATE_CONSTRAINTS); // invalid start date

        assertParseFailure(parser, "1" + INVALID_END_DATE_DESC,
                EndDate.MESSAGE_END_DATE_CONSTRAINTS); // invalid end date

        assertParseFailure(parser, "1" + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_START_TIME_CONSTRAINTS); // invalid start time

        assertParseFailure(parser, "1" + INVALID_END_TIME_DESC,
                EndTime.MESSAGE_END_TIME_CONSTRAINTS); // invalid end time

        // invalid entry title followed by valid start date
        assertParseFailure(parser, "1" + INVALID_ENTRY_TITLE_DESC + START_DATE_DESC_MEET_BOSS,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);

        // valid start date followed by invalid start date
        assertParseFailure(parser, "1" + START_DATE_DESC_MEET_BOSS + INVALID_START_DATE_DESC,
                StartDate.MESSAGE_START_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_END_DATE_DESC + INVALID_END_TIME_DESC
                + VALID_ENTRY_TITLE_MEET_BOSS, EndDate.MESSAGE_END_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ENTRY;
        String userInput = targetIndex.getOneBased() + ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS
                + END_DATE_DESC_MEET_BOSS + START_TIME_DESC_MEET_BOSS + END_TIME_DESC_MEET_BOSS;

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS)
                .withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_MEET_BOSS)
                .withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_MEET_BOSS).build();
        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_ENTRY;
        String userInput = targetIndex.getOneBased() + ENTRY_TITLE_DESC_GET_STOCKS + END_DATE_DESC_GET_STOCKS;

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS)
                .withEndDate(VALID_END_DATE_GET_STOCKS).build();
        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_ENTRY;

        // entry title
        String userInput = targetIndex.getOneBased() + ENTRY_TITLE_DESC_GET_STOCKS;
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();
        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date
        userInput = targetIndex.getOneBased() + START_DATE_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withStartDate(VALID_START_DATE_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date
        userInput = targetIndex.getOneBased() + END_DATE_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withEndDate(VALID_END_DATE_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start time
        userInput = targetIndex.getOneBased() + START_TIME_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withStartTime(VALID_START_TIME_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end time
        userInput = targetIndex.getOneBased() + END_TIME_DESC_GET_STOCKS;
        descriptor = new EditEntryDescriptorBuilder()
                .withEndTime(VALID_END_TIME_GET_STOCKS).build();
        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_ENTRY;
        String userInput = targetIndex.getOneBased()
                + ENTRY_TITLE_DESC_MEET_BOSS + ENTRY_TITLE_DESC_GET_STOCKS
                + START_DATE_DESC_GET_STOCKS + START_DATE_DESC_MEET_BOSS
                + END_DATE_DESC_MEET_BOSS + END_DATE_DESC_GET_STOCKS
                + START_TIME_DESC_GET_STOCKS + START_TIME_DESC_MEET_BOSS
                + END_TIME_DESC_MEET_BOSS + END_TIME_DESC_GET_STOCKS;

        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEndDate(VALID_END_DATE_GET_STOCKS).withStartTime(VALID_START_TIME_MEET_BOSS)
                .withEndTime(VALID_END_TIME_GET_STOCKS).build();

        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_ENTRY;
        String userInput = targetIndex.getOneBased() + INVALID_ENTRY_TITLE_DESC + ENTRY_TITLE_DESC_GET_STOCKS;
        EditEntryDescriptor descriptor = new EditEntryDescriptorBuilder()
                .withEntryTitle(VALID_ENTRY_TITLE_GET_STOCKS).build();

        EditEntryCommand expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + END_TIME_DESC_MEET_BOSS + INVALID_START_DATE_DESC
                + ENTRY_TITLE_DESC_MEET_BOSS + START_DATE_DESC_MEET_BOSS;

        descriptor = new EditEntryDescriptorBuilder()
                .withEndTime(VALID_END_TIME_MEET_BOSS).withStartDate(VALID_START_DATE_MEET_BOSS)
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS).build();

        expectedCommand = new EditEntryCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
