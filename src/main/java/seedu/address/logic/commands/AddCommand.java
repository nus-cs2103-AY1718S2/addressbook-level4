package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.petpatient.PetPatient;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

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

    public static final String MESSAGE_SUCCESS = "New person added: %1$s\n";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private Person toAddOwner;
    private PetPatient toAddPet;
    private Appointment toAddAppt;
    private String message = "New person added: %1$s\n";

    /**
     * Creates an AddCommand to add the specified {@code Person} and {@code PetPatient} and {@code Appointment}
     */
    public AddCommand(Person owner, PetPatient pet, Appointment appt) {
        requireNonNull(owner);
        requireNonNull(pet);
        requireNonNull(appt);
        toAddOwner = owner;
        toAddPet = pet;
        toAddAppt = appt;
        message += "New pet patient added: " + toAddPet.toString()
                + "\nNew appointment made: " + toAddAppt.toString();
        System.out.println("ADDED ALL THREE");
    }

    /**
     * Creates an AddCommand to add the specified {@code Person} and {@code PetPatient}
     */
    public AddCommand(Person owner, PetPatient pet) {
        requireNonNull(owner);
        requireNonNull(pet);
        toAddOwner = owner;
        toAddPet = pet;
        message += "New pet patient added: " + toAddPet.toString();
    }

    /**
     * Creates an AddCommand to add the specified {@code PetPatient}
     */
    public AddCommand(PetPatient pet) {
        requireNonNull(pet);
        toAddPet = pet;
    }

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person owner) {
        requireNonNull(owner);
        toAddOwner = owner;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAddOwner);
            return new CommandResult(String.format(message, toAddOwner));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAddOwner.equals(((AddCommand) other).toAddOwner));
    }
}
