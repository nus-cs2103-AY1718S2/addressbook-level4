package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PET_PATIENT_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.exceptions.DuplicatePetPatientException;

/**
 * Adds a pet patient to the address book.
 */
public class AddPetPatientCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addpetpatient";
    public static final String COMMAND_ALIAS = "app";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a pet patient to the address book. "
            + "Parameters: "
            + PREFIX_PET_PATIENT_NAME + "NAME "
            + PREFIX_PET_PATIENT_SPECIES + "SPECIES "
            + PREFIX_PET_PATIENT_BREED + "BREED "
            + PREFIX_PET_PATIENT_COLOUR + "COLOUR "
            + PREFIX_PET_PATIENT_BLOODTYPE + "BLOOD_TYPE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PET_PATIENT_NAME + "Jewel "
            + PREFIX_PET_PATIENT_SPECIES + "Cat "
            + PREFIX_PET_PATIENT_BREED + "Persian Ragdoll "
            + PREFIX_PET_PATIENT_COLOUR + "Calico "
            + PREFIX_PET_PATIENT_BLOODTYPE + "AB";

    public static final String MESSAGE_SUCCESS = "New pet patient added: %1$s";
    public static final String MESSAGE_DUPLICATE_PET_PATIENT = "This pet patient already exists in the address book";

    private final PetPatient toAddPetPatient;

    /**
     * Creates an AddCommand to add the specified {@code PetPatient}
     */
    public AddPetPatientCommand(PetPatient petPatient) {
        requireNonNull(petPatient);
        toAddPetPatient = petPatient;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPetPatient(toAddPetPatient);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAddPetPatient));
        } catch (DuplicatePetPatientException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PET_PATIENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAddPetPatient.equals(((AddPetPatientCommand) other).toAddPetPatient));
    }
}
