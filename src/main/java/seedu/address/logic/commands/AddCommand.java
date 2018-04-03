package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;

//@@author aquarinte
/**
 * Adds a Person, Petpatient and/or Appointment to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = "To add a new person: "
            + COMMAND_WORD + " -o " + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To add a new pet patient: "
            + COMMAND_WORD + " -p " + PREFIX_NAME + "NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]... -o " + PREFIX_NRIC + "OWNER_NRIC\n"
            + "To add a new appointment: "
            + COMMAND_WORD + " -a " + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + "[" + PREFIX_TAG + "TYPE OF APPOINTMENT]... -o " + PREFIX_NRIC + "OWNER_NRIC -p "
            + PREFIX_NAME + " PET_NAME\n"
            + "To add all new: " + COMMAND_WORD + " -o " + PREFIX_NAME + "OWNER_NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]... -p " + PREFIX_NAME + "PET_NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]... -a "  + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT...";

    public static final String MESSAGE_PERSON = "Option -o : Person details. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + "-o "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567Q "
            + PREFIX_TAG + "medical supplier";

    public static final String MESSAGE_APPOINTMENT = "Option -a : Appointment details. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT...\n"
            + "Example: " + "-a "
            + PREFIX_DATE + "2018-12-31 12:30 "
            + PREFIX_REMARK + "nil "
            + PREFIX_TAG + "checkup "
            + PREFIX_TAG + "vaccination";

    public static final String MESSAGE_PETPATIENT = COMMAND_WORD + " -p : Pet Patient details. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + "-p "
            + PREFIX_NAME + "Jewel "
            + PREFIX_SPECIES + "Cat "
            + PREFIX_BREED + "Persian Ragdoll "
            + PREFIX_COLOUR + "Calico "
            + PREFIX_BLOODTYPE + "AB";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s\n";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_NRIC = "This is already someone with this NRIC.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This particular appointment already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_DATETIME = "This date time is already taken by another appointment.";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in Medeina";
    public static final String MESSAGE_INVALID_NRIC = "The specified NRIC does not belong to anyone in Medeina."
            + " Please add a new person.";
    public static final String MESSAGE_MISSING_NRIC_PREFIX = "Missing prefix \"nr/\" for NRIC after -o option";
    public static final String MESSAGE_INVALID_PET_PATIENT = "The specified pet cannot be found under the specified "
            + "owner in Medeina. Please add a new pet patient.";

    private Person person;
    private PetPatient petPatient;
    private Appointment appt;
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private int type;
    private String message = "New person added: %1$s\n";

    /**
     * Creates an AddCommand to add the specified {@code Person} and {@code PetPatient} and {@code Appointment}.
     */
    public AddCommand(Person person, PetPatient petPatient, Appointment appt) {
        requireNonNull(person);
        requireNonNull(petPatient);
        requireNonNull(appt);
        this.person = person;
        this.petPatient = petPatient;
        this.appt = appt;
        type = 1;
        message += "New pet patient added: %2$s\nNew appointment made: %3$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code Appointment} if an existing Person object getNric() is
     * equivalent to {@code Nric}, and an existing PetPatient object getOwner() is equivalent to {@code ownerNric}
     * and getName() equivalent to {@code PetPatientName}.
     */
    public AddCommand(Appointment appt, Nric ownerNric, PetPatientName petPatientName) {
        requireNonNull(appt);
        requireNonNull(ownerNric);
        requireNonNull(petPatientName);
        this.appt = appt;
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        type = 2;
        message = "New appointment made: %1$s\nunder owner: %2$s\nfor pet patient: %3$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code PetPatient} if an existing Person object getNric() is
     * equivalent to {@code Nric}.
     */
    public AddCommand(PetPatient petPatient, Nric ownerNric) {
        requireNonNull(petPatient);
        requireNonNull(ownerNric);
        this.petPatient = petPatient;
        this.ownerNric = ownerNric;
        type = 3;
        message = "New pet patient added: %1$s \nunder owner: %2$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person owner) {
        requireNonNull(owner);
        person = owner;
        type = 4;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            switch (type) {
            case 1: return addAllNew();
            case 2: return addNewAppt();
            case 3: return addNewPetPatient();
            case 4: return addNewPerson();
            default: throw new CommandException(MESSAGE_USAGE);
            }

        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (DuplicateNricException e) {
            throw new CommandException(MESSAGE_DUPLICATE_NRIC);
        } catch (DuplicatePetPatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PET_PATIENT);
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (DuplicateDateTimeException e) {
            throw new CommandException(MESSAGE_DUPLICATE_DATETIME);
        }
    }

    private CommandResult addNewPerson() throws DuplicatePersonException, DuplicateNricException {
        model.addPerson(person);
        return new CommandResult(String.format(message, person));
    }

    /**
     * Add a new pet patient under an existing person.
     */
    private CommandResult addNewPetPatient() throws DuplicatePetPatientException, CommandException {
        person = model.getPersonWithNric(ownerNric);
        if (person != null) {
            model.addPetPatient(petPatient);
            return new CommandResult(String.format(message, petPatient, person));
        }
        throw new CommandException(MESSAGE_INVALID_NRIC);
    }

    /**
     * Add a new appointment for an existing pet patient under an existing person.
     */
    private CommandResult addNewAppt() throws CommandException, DuplicateAppointmentException,
            DuplicateDateTimeException {
        person = model.getPersonWithNric(ownerNric);
        petPatient = model.getPetPatientWithNricAndName(ownerNric, petPatientName);

        if (person == null) {
            throw new CommandException(MESSAGE_INVALID_NRIC);
        }

        if (petPatient == null) {
            throw new CommandException(MESSAGE_INVALID_PET_PATIENT);
        }

        model.addAppointment(appt);
        return new CommandResult(String.format(message, appt, person, petPatient));
    }

    /**
     * Add a new appointment, a new pet patient and a new person.
     * (New appointment for the new patient under a new person).
     */
    private CommandResult addAllNew() throws DuplicatePersonException, DuplicateNricException,
            DuplicatePetPatientException, DuplicateAppointmentException, DuplicateDateTimeException {
        model.addPerson(person);
        model.addPetPatient(petPatient);
        model.addAppointment(appt);
        return new CommandResult(String.format(message, person, petPatient, appt));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;

        boolean personSame = isTheSame(person, otherAddCommand.person);
        boolean petPatientSame = isTheSame(petPatient, otherAddCommand.petPatient);
        boolean appointmentSame = isTheSame(appt, otherAddCommand.appt);

        if (personSame && petPatientSame && appointmentSame) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if both objects are the same.
     * Returns true if both objects are equivalent.
     * Returns true if both objects are null.
     */
    public boolean isTheSame(Object one, Object two) {
        if (one != null && two != null) {
            if (one.equals(two)) {
                return true;
            }
        }

        if (one == null && two == null) {
            return true;
        }

        return false;
    }
}
