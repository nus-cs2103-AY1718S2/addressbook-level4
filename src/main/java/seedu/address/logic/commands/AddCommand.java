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
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;

/**
 * Adds a Person, Petpatient and/or Appointment to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    //NEED TO UPDATE THE MESSAGE_USAGE (CONSOLIDATE)
    public static final String MESSAGE_USAGE = COMMAND_WORD + " -o : Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_NRIC + "NRIC "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_NRIC + "S1234567Q "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_APPOINTMENT = COMMAND_WORD + " -a : Adds an appointment. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_REMARK + "REMARK "
            + "[" + PREFIX_TAG + "TYPE OF APPOINTMENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2018-12-31 12:30 "
            + PREFIX_REMARK + "nil "
            + PREFIX_TAG + "checkup "
            + PREFIX_TAG + "vaccination";

    public static final String MESSAGE_PETPATIENT = COMMAND_WORD + " -p : Adds a pet patient to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_SPECIES + "SPECIES "
            + PREFIX_BREED + "BREED "
            + PREFIX_COLOUR + "COLOUR "
            + PREFIX_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jewel "
            + PREFIX_SPECIES + "Cat "
            + PREFIX_BREED + "Persian Ragdoll "
            + PREFIX_COLOUR + "Calico "
            + PREFIX_BLOODTYPE + "AB";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s\n";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This particular appointment already exists in Medeina.";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in Medeina";
    public static final String MESSAGE_INVALID_NRIC = "The specified NRIC does not belong to anyone in Medeina."
            + " Please add a new person.";
    public static final String MESSAGE_INVALID_PET_PATIENT = "The specified pet cannot be found under the specified "
            + "owner in Medeina. Please add a new pet patient.";

    private Person toAddOwner;
    private PetPatient toAddPet;
    private Appointment toAddAppt;
    private Nric ownerNric;
    private PetPatientName petPatientName;
    private int type;
    private String message = "New person added: %1$s\n";

    /**
     * Creates an AddCommand to add the specified {@code Person} and {@code PetPatient} and {@code Appointment}.
     */
    public AddCommand(Person owner, PetPatient pet, Appointment appt) {
        requireNonNull(owner);
        requireNonNull(pet);
        requireNonNull(appt);
        toAddOwner = owner;
        toAddPet = pet;
        toAddAppt = appt;
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
        toAddAppt = appt;
        this.ownerNric = ownerNric;
        this.petPatientName = petPatientName;
        type = 2;
        message = "New appointment made: %1$s\nunder owner: %2$s\nfor pet patient: %3$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code PetPatient} if an existing Person object getNric() is
     * equivalent to {@code Nric}.
     */
    public AddCommand(PetPatient pet, Nric ownerNric) {
        requireNonNull(pet);
        requireNonNull(ownerNric);
        toAddPet = pet;
        this.ownerNric = ownerNric;
        type = 3;
        message = "New pet patient added: %1$s \nunder owner: %2$s";
    }

    /**
     * Creates an AddCommand to add the specified {@code Person}.
     */
    public AddCommand(Person owner) {
        requireNonNull(owner);
        toAddOwner = owner;
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
        } catch (DuplicatePetPatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PET_PATIENT);
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    private CommandResult addNewPerson() throws DuplicatePersonException {
        model.addPerson(toAddOwner);
        return new CommandResult(String.format(message, toAddOwner));
    }

    /**
     * Add a new pet patient under an existing person.
     */
    private CommandResult addNewPetPatient() throws DuplicatePetPatientException, CommandException {
        Person p = getPersonWithNric();
        if (p != null) {
            toAddPet.setOwnerNric(ownerNric);
            model.addPetPatient(toAddPet);
            return new CommandResult(String.format(message, toAddPet, p));
        }
        throw new CommandException(MESSAGE_INVALID_NRIC);
    }

    /**
     * Add a new appointment for an existing pet patient under an existing person.
     */
    private CommandResult addNewAppt() throws CommandException, DuplicateAppointmentException {
        Person owner = getPersonWithNric();
        PetPatient pet = getPetPatientWithNricAndName();
        if (owner != null) {
            toAddAppt.setOwnerNric(ownerNric);
        } else {
            throw new CommandException(MESSAGE_INVALID_NRIC);
        }

        if (pet != null) {
            toAddAppt.setPetPatientName(petPatientName);
        } else {
            throw new CommandException(MESSAGE_INVALID_PET_PATIENT);
        }

        toAddAppt.setPetPatientName(petPatientName);
        model.addAppointment(toAddAppt);
        return new CommandResult(String.format(message, toAddAppt, owner, pet));
    }

    /**
     * Add a new appointment, a new pet patient and a new person.
     * (New appointment for the new patient under a new person).
     */
    private CommandResult addAllNew() throws DuplicatePersonException, DuplicatePetPatientException,
            DuplicateAppointmentException {
        model.addPerson(toAddOwner);
        model.addPetPatient(toAddPet);
        model.addAppointment(toAddAppt);
        return new CommandResult(String.format(message, toAddOwner, toAddPet, toAddAppt));
    }

    /**
     * Checks whether a Person object with ownerNric exists.
     * Return the Person object if it exists.
     */
    public Person getPersonWithNric() {
        for (Person p : model.getAddressBook().getPersonList()) {
            if (p.getNric().equals(ownerNric)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Checks whether a PetPatient object with ownerNric and petPatientName exists.
     * Return the PetPatient object if it exists.
     */
    public PetPatient getPetPatientWithNricAndName() {
        for (PetPatient p : model.getAddressBook().getPetPatientList()) {
            if (p.getOwner().equals(ownerNric) && p.getName().equals(petPatientName)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAddOwner.equals(((AddCommand) other).toAddOwner));
    }
}
