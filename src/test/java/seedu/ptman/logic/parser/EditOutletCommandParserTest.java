package seedu.ptman.logic.parser;

import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OPERATING_HOURS_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OUTLET_CONTACT_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OUTLET_EMAIL_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.INVALID_OUTLET_NAME_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OPERATING_HOURS_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OUTLET_CONTACT_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OUTLET_EMAIL_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.OUTLET_NAME_DESC;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OPERATING_HOURS;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OUTLET_CONTACT;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OUTLET_EMAIL;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_OUTLET_NAME;
import static seedu.ptman.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ptman.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.ptman.logic.commands.EditOutletCommand;
import seedu.ptman.model.outlet.OperatingHours;
import seedu.ptman.model.outlet.OutletContact;
import seedu.ptman.model.outlet.OutletEmail;
import seedu.ptman.model.outlet.OutletName;

//@@author SunBangjie
public class EditOutletCommandParserTest {

    private EditOutletCommandParser parser = new EditOutletCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        String commandInvalidName = EditOutletCommand.COMMAND_WORD + INVALID_OUTLET_NAME_DESC;
        assertParseFailure(parser, commandInvalidName, OutletName.MESSAGE_NAME_CONSTRAINTS);

        String commandInvalidOperatingHours = EditOutletCommand.COMMAND_WORD + INVALID_OPERATING_HOURS_DESC;
        assertParseFailure(parser, commandInvalidOperatingHours,
                OperatingHours.MESSAGE_OPERATING_HOUR_CONSTRAINTS);

        String commandInvalidOutletContact = EditOutletCommand.COMMAND_WORD + INVALID_OUTLET_CONTACT_DESC;
        assertParseFailure(parser, commandInvalidOutletContact, OutletContact.MESSAGE_OUTLET_CONTACT_CONSTRAINTS);

        String commandInvalidOutletEmail = EditOutletCommand.COMMAND_WORD + INVALID_OUTLET_EMAIL_DESC;
        assertParseFailure(parser, commandInvalidOutletEmail, OutletEmail.MESSAGE_OUTLET_EMAIL_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsValid_success() {
        String userInput = EditOutletCommand.COMMAND_WORD
                + OUTLET_NAME_DESC + OPERATING_HOURS_DESC + OUTLET_CONTACT_DESC + OUTLET_EMAIL_DESC;
        OutletName outletName = new OutletName(VALID_OUTLET_NAME);
        OperatingHours operatingHours = new OperatingHours(VALID_OPERATING_HOURS);
        OutletContact outletContact = new OutletContact(VALID_OUTLET_CONTACT);
        OutletEmail outletEmail = new OutletEmail(VALID_OUTLET_EMAIL);
        EditOutletCommand expectedCommand = new EditOutletCommand(outletName, operatingHours,
                outletContact, outletEmail);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
