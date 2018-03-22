package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT_OWNERONLY = Pattern.compile("-(o)+(?<ownerInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_ALL_NEW = Pattern.compile("-(o)+(?<ownerInfo>.*)"
            + "-(p)+(?<petInfo>.*)-(a)+(?<apptInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_OWNER = Pattern.compile("-(o)+(?<ownerInfo>.*)-(p)+(?<petInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_PET = Pattern.compile("-(p)+(?<petInfo>.*)-(o)+(?<ownerNRIC>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_APPT = Pattern.compile("-(a)+(?<apptInfo>.*)-(o)(?<ownerNRIC>.*)"
            + "-(p)+(?<petName>.*)");
    /**
     * Parses the given {@code String} of arguments in the context of the Person class
     * and returns an Person object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Person parsePerson(String ownerInfo) throws ParseException {
        ArgumentMultimap argMultimapOwner =
                ArgumentTokenizer.tokenize(ownerInfo, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_NRIC, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimapOwner, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_NRIC)
                || !argMultimapOwner.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name ownerName = ParserUtil.parseName(argMultimapOwner.getValue(PREFIX_NAME)).get();
            Phone phone = ParserUtil.parsePhone(argMultimapOwner.getValue(PREFIX_PHONE)).get();
            Email email = ParserUtil.parseEmail(argMultimapOwner.getValue(PREFIX_EMAIL)).get();
            Address address = ParserUtil.parseAddress(argMultimapOwner.getValue(PREFIX_ADDRESS)).get();
            Nric nric = ParserUtil.parseNric(argMultimapOwner.getValue(PREFIX_NRIC)).get();
            Set<Tag> ownerTagList = ParserUtil.parseTags(argMultimapOwner.getAllValues(PREFIX_TAG));

            Person owner = new Person(ownerName, phone, email, address, nric, ownerTagList);

            return owner;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    public AddCommand parse_NewOwner_NewPet_NewAppt(String ownerInfo, String petInfo, String apptInfo)
            throws ParseException{
        System.out.println("I AM PARSING 3 NOW");
        Person owner = parsePerson(ownerInfo);
        PetPatient petPatient = new AddPetPatientCommandParser().parse(petInfo, owner);
        Appointment appt = new AddAppointmentCommandParser().parse(apptInfo, owner, petPatient);
        return new AddCommand(owner, petPatient, appt);
    }

    public AddCommand parseNewOwnerAndPet(String ownerInfo, String petInfo) throws ParseException {
        Person owner = parsePerson(ownerInfo);
        PetPatient petPatient = new AddPetPatientCommandParser().parse(petInfo, owner);
        return new AddCommand(owner, petPatient);
    }

    public AddCommand parseNewOwnerOnly(String ownerInfo) throws ParseException {
        Person owner = parsePerson(ownerInfo);
        return new AddCommand(owner);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    public AddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        Matcher matcher = ADD_COMMAND_FORMAT_ALL_NEW.matcher(trimmedArgs);
        if (matcher.matches()) {
            String ownerInfo = matcher.group("ownerInfo");
            String petInfo = matcher.group("petInfo");
            String apptInfo = matcher.group("apptInfo");
            return parse_NewOwner_NewPet_NewAppt(ownerInfo, petInfo, apptInfo);
        }

        matcher = ADD_COMMAND_FORMAT_OWNER.matcher(trimmedArgs);
        if (matcher.matches()) {
            String ownerInfo = matcher.group("ownerInfo");
            String petInfo = matcher.group("petInfo");
            return parseNewOwnerAndPet(ownerInfo, petInfo);
        }

        matcher = ADD_COMMAND_FORMAT_PET.matcher(trimmedArgs);
        if (matcher.matches()) {
            String petInfo = matcher.group("petInfo");
            String ownerNRIC = matcher.group("ownerNRIC");
            //return new AddPetPatientCommandParser().parse(petInfo, ownerNRIC);
        }

        matcher = ADD_COMMAND_FORMAT_OWNERONLY.matcher(trimmedArgs);
        if (matcher.matches()) {
            String ownerInfo = matcher.group("ownerInfo");
            return parseNewOwnerOnly(ownerInfo);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }

}
