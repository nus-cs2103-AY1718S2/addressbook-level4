package seedu.address.testutil;

import seedu.address.model.petpatient.PetPatient;
import seedu.address.model.petpatient.PetPatientName;

/**
 * A utility class containing a list of {@code PetPatient} objects to be used in tests.
 */
public class TypicalPetPatients {

    public static final PetPatient JOKER = new PetPatient(new PetPatientName("joker"), "cat",
            "domestic shorthair", "brown and white", "O", TypicalPersons.BOB, null);
}
