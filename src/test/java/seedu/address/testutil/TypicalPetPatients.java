package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_NERO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.petpatient.PetPatient;

//@@author chialejing
/**
 * A utility class containing a list of {@code PetPatient} objects to be used in tests.
 */
public class TypicalPetPatients {
    // Pet patient with tags
    public static final PetPatient JEWEL = new PetPatientBuilder()
            .withName("Jewel")
            .withSpecies("Cat")
            .withBreed("Persian Ragdoll")
            .withColour("Calico")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.ALICE.getNric().toString())
            .withTags("Depression", "Test").build();

    // Pet patient with no tags
    public static final PetPatient JOKER = new PetPatientBuilder()
            .withName("Joker")
            .withSpecies("Cat")
            .withBreed("Domestic Shorthair")
            .withColour("Brown and White")
            .withBloodType("O")
            .withOwnerNric(TypicalPersons.BENSON.getNric().toString())
            .withTags(new String[]{}).build();

    // Manually added
    public static final PetPatient KARUPIN = new PetPatientBuilder()
            .withName("Karupin")
            .withSpecies("Cat")
            .withBreed("Himalayan")
            .withColour("Sealpoint")
            .withBloodType("AB")
            .withOwnerNric(TypicalPersons.ELLE.getNric().toString())
            .withTags(new String[]{}).build();

    // Manually added - Pet Patient's details found in {@code CommandTestUtil}
    public static final PetPatient NERO = new PetPatientBuilder()
            .withName(VALID_NAME_NERO)
            .withSpecies(VALID_SPECIES_NERO)
            .withBreed(VALID_BREED_NERO)
            .withColour(VALID_COLOUR_NERO)
            .withBloodType(VALID_BLOODTYPE_NERO)
            .withOwnerNric(VALID_NRIC_BOB)
            .withTags(new String[]{}).build();

    private TypicalPetPatients() {}

    public static List<PetPatient> getTypicalPetPatients() {
        return new ArrayList<>(Arrays.asList(JOKER, JEWEL));
    }
}
