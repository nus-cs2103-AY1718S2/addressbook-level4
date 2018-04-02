package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.OPTION_OWNER;
import static seedu.address.logic.commands.CommandTestUtil.OPTION_PET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BREED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.petpatient.PetPatient;

//@@author aquarinte
/**
 * A utility class for Pet Patient.
 */
public class PetPatientUtil {
    /**
     * Returns an add command string for adding the {@code petpatient}.
     */
    public static String getAddCommand(PetPatient petPatient, Nric ownerNric) {
        return AddCommand.COMMAND_WORD + OPTION_PET + " " + getPetPatientDetails(petPatient)
                + OPTION_OWNER + " " + PREFIX_NRIC + ownerNric.toString();
    }

    /**
     * Returns an add command string for adding the {@code petpatient}.
     */
    public static String getAddCommandAlias(PetPatient petPatient, Nric ownerNric) {
        return AddCommand.COMMAND_ALIAS + " " + OPTION_PET + " " + getPetPatientDetails(petPatient)
                + " " + OPTION_OWNER + " " + PREFIX_NRIC + ownerNric.toString();
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPetPatientDetails(PetPatient petPatient) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + petPatient.getName().fullName + " ");
        sb.append(PREFIX_SPECIES + petPatient.getSpecies() + " ");
        sb.append(PREFIX_BREED + petPatient.getBreed() + " ");
        sb.append(PREFIX_COLOUR + petPatient.getColour() + " ");
        sb.append(PREFIX_BLOODTYPE + petPatient.getColour() + " ");
        petPatient.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }
}
