package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDates.DATE_FIRST_JAN;
import static seedu.address.testutil.TypicalDates.INVALID_DATE_DESC;
import static seedu.address.testutil.TypicalDates.VALID_DATE_DESC;
import static seedu.address.testutil.TypicalTags.INVALID_TAG_DESC;
import static seedu.address.testutil.TypicalTags.TAG_SET_FRIEND;
import static seedu.address.testutil.TypicalTags.TAG_SET_OWES_MONEY_FRIEND;
import static seedu.address.testutil.TypicalTags.VALID_TAG_DESC_FRIEND;
import static seedu.address.testutil.TypicalTags.VALID_TAG_DESC_OWES_MONEY;

import org.junit.Test;

import seedu.address.logic.commands.DeleteBeforeCommand;
import seedu.address.model.person.DateAdded;
import seedu.address.model.tag.Tag;

public class DeleteBeforeCommandParserTest {

    private DeleteBeforeCommandParser parser = new DeleteBeforeCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        // one tag - accepted
        assertParseSuccess(parser, VALID_DATE_DESC + VALID_TAG_DESC_FRIEND,
                new DeleteBeforeCommand(DATE_FIRST_JAN, TAG_SET_FRIEND));

        // multiple tags - all accepted
        assertParseSuccess(parser, VALID_DATE_DESC + VALID_TAG_DESC_OWES_MONEY + VALID_TAG_DESC_FRIEND,
                new DeleteBeforeCommand(DATE_FIRST_JAN, TAG_SET_OWES_MONEY_FRIEND));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // invalid date - fail
        assertParseFailure(parser, INVALID_DATE_DESC + VALID_TAG_DESC_FRIEND,
                DateAdded.MESSAGE_DATE_CONSTRAINTS);

        // invalid tag - fail
        assertParseFailure(parser, VALID_DATE_DESC + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBeforeCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, VALID_TAG_DESC_FRIEND, expectedMessage);

        // missing tags prefix
        assertParseFailure(parser, VALID_DATE_DESC, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }
}
