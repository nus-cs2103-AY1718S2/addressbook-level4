package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.OPTION_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.OPTION_OWNER;
import static seedu.address.logic.parser.CliSyntax.OPTION_PETPATIENT;
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
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateNricException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;

//@@author aquarinte
/**
 * Adds a Person, Petpatient and/or Appointment to Medeina.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = "To add a new contact: "
            + COMMAND_WORD + " " + OPTION_OWNER + " " + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "To add a new pet patient: "
            + COMMAND_WORD + " " + OPTION_PETPATIENT + " " + PREFIX_NAME + "PET_NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]... " + OPTION_OWNER + " " + PREFIX_NRIC + "NRIC\n"
            + "To add a new appointment: "
            + COMMAND_WORD + " " + OPTION_APPOINTMENT + " " + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT... " + OPTION_OWNER + " " + PREFIX_NRIC + "NRIC "
            + OPTION_PETPATIENT + " " + PREFIX_NAME + " PET_NAME\n"
            + "To add all new: " + COMMAND_WORD + " " + OPTION_OWNER + " " + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]... " + OPTION_PETPATIENT + " " + PREFIX_NAME + "PET_NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]... " + OPTION_APPOINTMENT + " "  + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT...";

    public static final String MESSAGE_ERROR_PERSON = "option " + OPTION_OWNER + "\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + OPTION_OWNER + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567Q "
            + PREFIX_TAG + "supplier";

    public static final String MESSAGE_ERROR_APPOINTMENT = "option " + OPTION_APPOINTMENT + "\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_TAG + "TYPE OF APPOINTMENT...\n"
            + "Example: " + OPTION_APPOINTMENT + " "
            + PREFIX_DATE + "2018-12-31 12:30 "
            + PREFIX_REMARK + "nil "
            + PREFIX_TAG + "checkup "
            + PREFIX_TAG + "vaccination";

    public static final String MESSAGE_ERROR_PETPATIENT = "option " + OPTION_PETPATIENT + "\n"
            + "Parameters: "
            + PREFIX_NAME + "PET_NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + OPTION_PETPATIENT + " "
            + PREFIX_NAME + "Jewel "
            + PREFIX_SPECIES + "Cat "
            + PREFIX_BREED + "Persian Ragdoll "
            + PREFIX_COLOUR + "calico "
            + PREFIX_BLOODTYPE + "AB "
            + PREFIX_TAG + "underweight";

    public static final String MESSAGE_SUCCESS_PERSON = "New contact added: %1$s\n";
    public static final String MESSAGE_SUCCESS_PETPATIENT = "New pet patient added: %1$s \n"
            + "under contact: %2$s";
    public static final String MESSAGE_SUCCESS_APPOINTMENT = "New appointment made: %1$s\n"
            + "under contact: %2$s\n"
            + "for pet patient: %3$s";
    public static final String MESSAGE_SUCCESS_EVERYTHING = MESSAGE_SUCCESS_PERSON
            + "New pet patient added: %2$s\n"
            + "New appointment made: %3$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This contact already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_NRIC = "There is already a contact with this NRIC.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This particular appointment already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_DATETIME = "This date time is already taken by another appointment.";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in Medeina";
    public static final String MESSAGE_INVALID_NRIC = "The specified NRIC does not belong to anyone in Medeina."
            + " Please add a new contact.";
    public static final String MESSAGE_INVALID_PET_PATIENT = "The specified pet cannot be found under the specified "
            + "contact in Medeina. Please add a new pet patient.";
    public static final String MESSAGE_MISSING_NRIC_PREFIX = "option -o\n"
            + "Missing prefix nr/ for NRIC.";
    public static final String MESSAGE_MISSING_PET_PATIENT_NAME_PREFIX = "option -p\n"
            + "Missing prefix n/ for pet patient name.";
    public static final String MESSAGE_CONCURRENT_APPOINTMENT = "Appointment cannot be concurrent with other "
            + "appointments.";
    public static final String MESSAGE_PAST_APPOINTMENT = "Appointment cannot be created with past DateTime.";

    private Person person;
    private PetPatient petPatient;
    private Appointment appt;
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private int type;

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
    }

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person owner) {
        requireNonNull(owner);
        person = owner;
        type = 4;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            switch (type) {
            case 1: return addAllNew();
            case 2: return addNewAppointment();
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
        } catch (ConcurrentAppointmentException e) {
            throw new CommandException(MESSAGE_CONCURRENT_APPOINTMENT);
        } catch (PastAppointmentException e) {
            throw new CommandException(MESSAGE_PAST_APPOINTMENT);
        }
    }

    private CommandResult addNewPerson() throws DuplicatePersonException, DuplicateNricException {
        model.addPerson(person);
        return new CommandResult(String.format(MESSAGE_SUCCESS_PERSON, person));
    }

    /**
     * Adds a new pet patient under an existing person.
     */
    private CommandResult addNewPetPatient() throws DuplicatePetPatientException, CommandException {
        person = getValidOwner(ownerNric);
        model.addPetPatient(petPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS_PETPATIENT, petPatient, person));
    }

    /**
     * Adds a new appointment for an existing pet patient, under an existing person.
     */
    private CommandResult addNewAppointment() throws CommandException, DuplicateAppointmentException,
            DuplicateDateTimeException, ConcurrentAppointmentException, PastAppointmentException {
        person = getValidOwner(ownerNric);
        petPatient = getValidPetPatient(ownerNric, petPatientName);
        model.addAppointment(appt);
        return new CommandResult(String.format(MESSAGE_SUCCESS_APPOINTMENT, appt, person, petPatient));
    }

    /**
     * Adds a new appointment for a new pet patient under a new person.
     */
    private CommandResult addAllNew() throws DuplicatePersonException, DuplicateNricException,
            DuplicatePetPatientException, DuplicateAppointmentException, DuplicateDateTimeException,
        ConcurrentAppointmentException, PastAppointmentException {
        model.addPerson(person);
        model.addPetPatient(petPatient);
        model.addAppointment(appt);
        return new CommandResult(String.format(MESSAGE_SUCCESS_EVERYTHING, person, petPatient, appt));
    }

    /**
     * Returns a person object that exists in Medeina.
     */
    private Person getValidOwner(Nric ownerNric) throws CommandException {
        Person validOwner = model.getPersonWithNric(ownerNric);
        if (validOwner == null) {
            throw new CommandException(MESSAGE_INVALID_NRIC);
        }
        return validOwner;
    }

    /**
     * Returns a petpatient object that exists in Medeina.
     */
    private PetPatient getValidPetPatient(Nric ownerNric, PetPatientName petPatientName) throws CommandException {
        PetPatient validPatient  = model.getPetPatientWithNricAndName(ownerNric, petPatientName);
        if (validPatient == null) {
            throw new CommandException(MESSAGE_INVALID_PET_PATIENT);
        }
        return validPatient;
    }

    /**
     * Checks if two objects are the same for equals() method.
     *
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
}
