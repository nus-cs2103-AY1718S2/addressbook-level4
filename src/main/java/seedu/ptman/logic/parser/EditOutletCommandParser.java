package seedu.ptman.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_CONTACT;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_EMAIL;
import static seedu.ptman.logic.parser.CliSyntax.PREFIX_OUTLET_NAME;

import seedu.ptman.commons.exceptions.IllegalValueException;
import seedu.ptman.logic.commands.EditOutletCommand;
import seedu.ptman.logic.parser.exceptions.ParseException;
import seedu.ptman.model.outlet.OperatingHours;
import seedu.ptman.model.outlet.OutletContact;
import seedu.ptman.model.outlet.OutletEmail;
import seedu.ptman.model.outlet.OutletName;

//@@author SunBangjie
/**
 * Parses input arguments and creates a new EditOutletCommand object
 */
public class EditOutletCommandParser implements Parser<EditOutletCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditOutletCommand
     * and returns an EditOutletCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditOutletCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OUTLET_NAME, PREFIX_OPERATING_HOURS,
                        PREFIX_OUTLET_CONTACT, PREFIX_OUTLET_EMAIL);

        OutletName outletName;
        OperatingHours operatingHours;
        OutletContact outletContact;
        OutletEmail outletEmail;

        try {
            outletName = ParserUtil.parseOutletName(argMultimap.getValue(PREFIX_OUTLET_NAME)).isPresent()
                    ? ParserUtil.parseOutletName(argMultimap.getValue(PREFIX_OUTLET_NAME)).get()
                    : null;
            operatingHours = ParserUtil.parseOperatingHours(argMultimap.getValue(PREFIX_OPERATING_HOURS)).isPresent()
                    ? ParserUtil.parseOperatingHours(argMultimap.getValue(PREFIX_OPERATING_HOURS)).get()
                    : null;
            outletContact = ParserUtil.parseOutletContact(argMultimap.getValue(PREFIX_OUTLET_CONTACT)).isPresent()
                    ? ParserUtil.parseOutletContact(argMultimap.getValue(PREFIX_OUTLET_CONTACT)).get()
                    : null;
            outletEmail = ParserUtil.parseOutletEmail(argMultimap.getValue(PREFIX_OUTLET_EMAIL)).isPresent()
                    ? ParserUtil.parseOutletEmail(argMultimap.getValue(PREFIX_OUTLET_EMAIL)).get()
                    : null;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        return new EditOutletCommand(outletName, operatingHours, outletContact, outletEmail);
    }
}
