package seedu.address.logic.parser;

//@@author lohtianwei
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TogglePrivacyCommand;
import seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EditPersonPrivacyBuilder;

public class TogglePrivacyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format
            (MESSAGE_INVALID_COMMAND_FORMAT, TogglePrivacyCommand.MESSAGE_USAGE);

    private static final String MESSAGE_NO_FIELDS = String.format(TogglePrivacyCommand.MESSAGE_NO_FIELDS);

    private TogglePrivacyCommandParser parser = new TogglePrivacyCommandParser();

    @Test
    public void parseInvalidIndex() {
        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        //negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        //invalid index
        assertParseFailure(parser, "1 random", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseMissingField_fail() {
        // no prefix specified
        assertParseFailure(parser, "1", MESSAGE_NO_FIELDS);
        //no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
        //nothing specified after command word
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidPrefix_fail() {
        assertParseFailure(parser, "1" + " " + PREFIX_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TogglePrivacyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseOneField_success() throws ParseException {
        Index target = INDEX_FIRST_PERSON;
        String input = target.getOneBased() + " " + PREFIX_PHONE;

        EditPersonPrivacy epp = new EditPersonPrivacyBuilder().setPhonePrivate("false").build();
        TogglePrivacyCommand expected = new TogglePrivacyCommand(target, epp);

        TogglePrivacyCommand actual = parser.parse(input);

        compareTpCommand(expected, actual);
    }

    @Test
    public void parseValidFollowedbyInvalid_success() throws ParseException {
        Index target = INDEX_FIRST_PERSON;
        String input = target.getOneBased() + " " + PREFIX_PHONE + " " + PREFIX_NAME;

        EditPersonPrivacy epp = new EditPersonPrivacyBuilder().setPhonePrivate("false").build();
        TogglePrivacyCommand expected = new TogglePrivacyCommand(target, epp);

        TogglePrivacyCommand actual = parser.parse(input);

        compareTpCommand(expected, actual);
    }

    /**
     * Checks if two TP commands are equal
     * @param expected
     * @param actual
     */
    private void compareTpCommand(TogglePrivacyCommand expected, TogglePrivacyCommand actual) {
        assertEquals(expected.getIndex(), actual.getIndex());
        assertEquals(expected.getEpp().getPrivateRemark(), actual.getEpp().getPrivateRemark());
        assertEquals(expected.getEpp().getPrivateAddress(), actual.getEpp().getPrivateAddress());
        assertEquals(expected.getEpp().getPrivateRating(), actual.getEpp().getPrivateRating());
        assertEquals(expected.getEpp().getPrivatePhone(), actual.getEpp().getPrivatePhone());
        assertEquals(expected.getEpp().getPrivateEmail(), actual.getEpp().getPrivateEmail());
    }
}
