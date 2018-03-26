package seedu.address.testutil;

import seedu.address.model.petpatient.PetPatient;

/**
 * A utility class containing a list of {@code PetPatient} objects to be used in tests.
 */
public class TypicalPetPatients {

    public static final PetPatient JOKER = new PetPatientBuilder()
            .withName("Joker")
            .withSpecies("Cat")
            .withBreed("Domestic Shorthair")
            .withColour("Brown and White")
            .withBloodType("O")
            .withOwnerNric(TypicalPersons.BOB.getNric().toString())
            .withTags("Injured").build();

    public static final PetPatient JEWEL = new PetPatientBuilder()
            .withName("Jewel")
            .withSpecies("Cat")
            .withBreed("Persian Ragdoll")
            .withColour("Calico")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.ALICE.getNric().toString())
            .withTags(new String[]{}).build();

    private TypicalPetPatients() {}
}
