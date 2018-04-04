package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_THEATER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.THEATER_DESC_FIVE;
import static seedu.address.logic.commands.CommandTestUtil.THEATER_DESC_THREE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMOFTHEATERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_TAMPINES;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.cinema.Address;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Email;
import seedu.address.model.cinema.Name;
import seedu.address.model.cinema.Phone;
import seedu.address.model.cinema.Theater;
import seedu.address.testutil.CinemaBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Cinema expectedCinema = new CinemaBuilder().withName(VALID_NAME_TAMPINES).withPhone(VALID_PHONE_TAMPINES)
                .withEmail(VALID_EMAIL_TAMPINES).withAddress(VALID_ADDRESS_TAMPINES)
                .withTheater(VALID_NUMOFTHEATERS).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, new AddCommand(expectedCinema));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_SENGKANG + NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, new AddCommand(expectedCinema));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_TAMPINES + PHONE_DESC_SENGKANG + PHONE_DESC_TAMPINES
                + EMAIL_DESC_TAMPINES + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, new AddCommand(expectedCinema));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_SENGKANG
                + EMAIL_DESC_TAMPINES + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, new AddCommand(expectedCinema));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_SENGKANG + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, new AddCommand(expectedCinema));

        // multiple theaters - last theater accepted
        assertParseSuccess(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_FIVE + THEATER_DESC_THREE, new AddCommand(expectedCinema));

        // multiple tags - all accepted
        Cinema expectedCinemaMultipleTags = new CinemaBuilder().withName(VALID_NAME_TAMPINES)
                .withPhone(VALID_PHONE_TAMPINES).withEmail(VALID_EMAIL_TAMPINES).withAddress(VALID_ADDRESS_TAMPINES)
                .withTheater(VALID_NUMOFTHEATERS).build();
        assertParseSuccess(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, new AddCommand(expectedCinemaMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Cinema expectedCinema = new CinemaBuilder().withName(VALID_NAME_SENGKANG).withPhone(VALID_PHONE_SENGKANG)
                .withEmail(VALID_EMAIL_SENGKANG).withAddress(VALID_ADDRESS_SENGKANG)
                .withTheater(VALID_NUMOFTHEATERS).build();
        assertParseSuccess(parser, NAME_DESC_SENGKANG + PHONE_DESC_SENGKANG + EMAIL_DESC_SENGKANG
                + ADDRESS_DESC_SENGKANG + THEATER_DESC_THREE, new AddCommand(expectedCinema));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_TAMPINES + VALID_PHONE_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + VALID_EMAIL_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + VALID_ADDRESS_TAMPINES + THEATER_DESC_THREE, expectedMessage);

        // missing theater prefix
        assertParseFailure(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + VALID_NUMOFTHEATERS, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_TAMPINES + VALID_PHONE_TAMPINES + VALID_EMAIL_TAMPINES
                + VALID_ADDRESS_TAMPINES + VALID_NUMOFTHEATERS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES + ADDRESS_DESC_TAMPINES
                + THEATER_DESC_THREE, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_TAMPINES + INVALID_PHONE_DESC + EMAIL_DESC_TAMPINES + ADDRESS_DESC_TAMPINES
                + THEATER_DESC_THREE, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + INVALID_EMAIL_DESC + ADDRESS_DESC_TAMPINES
                + THEATER_DESC_THREE, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES + INVALID_ADDRESS_DESC
                + THEATER_DESC_THREE, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid theater
        assertParseFailure(parser, NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + INVALID_THEATER_DESC, Theater.MESSAGE_THEATER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + INVALID_ADDRESS_DESC + THEATER_DESC_THREE, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_TAMPINES + PHONE_DESC_TAMPINES + EMAIL_DESC_TAMPINES
                + ADDRESS_DESC_TAMPINES + THEATER_DESC_THREE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
