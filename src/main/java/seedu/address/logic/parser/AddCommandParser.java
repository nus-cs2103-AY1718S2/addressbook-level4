package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Pattern ADD_COMMAND_FORMAT_OWNER_ONLY = Pattern.compile("-(o)+(?<ownerInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_ALL_NEW = Pattern.compile("-(o)+(?<ownerInfo>.*)"
            + "-(p)+(?<petInfo>.*)-(a)+(?<apptInfo>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_NEW_PET_EXISTING_OWNER = Pattern.compile("-(p)+(?<petInfo>.*)"
            + "-(o)+(?<ownerNric>.*)");
    private static final Pattern ADD_COMMAND_FORMAT_NEW_APPT_EXISTING_OWNER_PET = Pattern.compile("-(a)+(?<apptInfo>.*)"
            + "-(o)(?<ownerNric>.*)" + "-(p)+(?<petName>.*)");
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

    /**
     * Parses the given {@code String} of arguments in the context of the Appointment class
     * and returns an Appointment object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Appointment parseAppointment(String apptInfo) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(apptInfo, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_REMARK, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_APPOINTMENT));
        }

        try {
            LocalDateTime localDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATE)).get();
            Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK)).get();
            Set<Tag> type = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            Appointment appointment = new Appointment(remark, localDateTime, type);

            return appointment;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the PetPatient class
     * and returns an PetPatient object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PetPatient parsePetPatient(String petInfo) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        petInfo,
                        PREFIX_NAME,
                        PREFIX_PET_PATIENT_SPECIES,
                        PREFIX_PET_PATIENT_BREED,
                        PREFIX_PET_PATIENT_COLOUR,
                        PREFIX_PET_PATIENT_BLOODTYPE,
                        PREFIX_TAG);

        if (!arePrefixesPresent(
                argMultimap,
                PREFIX_NAME,
                PREFIX_PET_PATIENT_BREED,
                PREFIX_PET_PATIENT_SPECIES,
                PREFIX_PET_PATIENT_COLOUR,
                PREFIX_PET_PATIENT_BLOODTYPE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_PETPATIENT));
        }

        try {
            PetPatientName name = ParserUtil.parsePetPatientName(argMultimap.getValue(PREFIX_NAME)).get();
            String species = ParserUtil.parseSpecies(argMultimap.getValue(PREFIX_PET_PATIENT_SPECIES)).get();
            String breed = ParserUtil.parseBreed(argMultimap.getValue(PREFIX_PET_PATIENT_BREED)).get();
            String color = ParserUtil.parseColour(argMultimap.getValue(PREFIX_PET_PATIENT_COLOUR)).get();
            String bloodType = ParserUtil.parseBloodType(argMultimap.getValue(PREFIX_PET_PATIENT_BLOODTYPE)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            PetPatient petPatient = new PetPatient(name, species, breed, color, bloodType, tagList);

            return petPatient;
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand createNewOwnerPetAppt(String ownerInfo, String petInfo, String apptInfo)
            throws ParseException {
        Person owner = parsePerson(ownerInfo);

        PetPatient petPatient = parsePetPatient(petInfo);
        petPatient.setOwnerNric(owner.getNric());

        Appointment appt = parseAppointment(apptInfo);
        appt.setOwnerNric(owner.getNric());
        appt.setPetPatientName(petPatient.getName());

        return new AddCommand(owner, petPatient, appt);
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand createNewApptforExistingOwnerAndPet(String apptInfo, String ownerNric, String petName)
            throws ParseException {
        Appointment appt = parseAppointment(apptInfo);
        return new AddCommand(appt, new Nric(ownerNric.trim()), new PetPatientName(petName.trim()));
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand createNewPetForExistingPerson(String petInfo, String ownerNric) throws ParseException {
        PetPatient petPatient = parsePetPatient(petInfo);
        return new AddCommand(petPatient, new Nric(ownerNric.trim()));
    }

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
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

    /**
     * Parses the given {@code String} of arguments in the context of AddCommand
     * and returns an AddCommand object.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        //to add a new person (owner), new pet patient, and a new appointment
        Matcher matcher = ADD_COMMAND_FORMAT_ALL_NEW.matcher(trimmedArgs);
        if (matcher.matches()) {
            String ownerInfo = matcher.group("ownerInfo");
            String petInfo = matcher.group("petInfo");
            String apptInfo = matcher.group("apptInfo");
            return createNewOwnerPetAppt(ownerInfo, petInfo, apptInfo);
        }
        //add a new appointment for existing person and pet patient
        matcher = ADD_COMMAND_FORMAT_NEW_APPT_EXISTING_OWNER_PET.matcher(trimmedArgs);
        if (matcher.matches()) {
            String apptInfo = matcher.group("apptInfo");
            String ownerNric = matcher.group("ownerNric");
            String petName = matcher.group("petName");
            return createNewApptforExistingOwnerAndPet(apptInfo, ownerNric, petName);
        }

        //add a new patient to an existing owner
        matcher = ADD_COMMAND_FORMAT_NEW_PET_EXISTING_OWNER.matcher(trimmedArgs);
        if (matcher.matches()) {
            String petInfo = matcher.group("petInfo");
            String ownerNric = matcher.group("ownerNric");
            return createNewPetForExistingPerson(petInfo, ownerNric);
        }

        //add a new person
        matcher = ADD_COMMAND_FORMAT_OWNER_ONLY.matcher(trimmedArgs);
        if (matcher.matches()) {
            String ownerInfo = matcher.group("ownerInfo");
            return parseNewOwnerOnly(ownerInfo);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

    }

}
