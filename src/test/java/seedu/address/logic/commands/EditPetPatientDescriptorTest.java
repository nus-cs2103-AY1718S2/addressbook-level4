package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JOKER;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BREED_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COLOUR_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_FION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SPECIES_HAZEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FIV;

import org.junit.Test;

import seedu.address.logic.descriptors.EditPetPatientDescriptor;
import seedu.address.testutil.EditPetPatientDescriptorBuilder;

//@@author chialejing
public class EditPetPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPetPatientDescriptor descriptorWithSameValues = new EditPetPatientDescriptor(DESC_JOKER);
        assertTrue(DESC_JOKER.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_JOKER.equals(DESC_JOKER));

        // null -> returns false
        assertFalse(DESC_JOKER.equals(null));

        // different types -> returns false
        assertFalse(DESC_JOKER.equals(0));

        // different values -> returns false
        assertFalse(DESC_JOKER.equals(DESC_NERO));

        // different name -> returns false
        EditPetPatientDescriptor editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER)
                .withName(VALID_NAME_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different species -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withSpecies(VALID_SPECIES_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different breed -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withBreed(VALID_BREED_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different colour -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withColour(VALID_COLOUR_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different bloodType -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withBloodType(VALID_BLOODTYPE_HAZEL).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different owner -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withOwnerNric(VALID_NRIC_FION).build();
        assertFalse(DESC_JOKER.equals(editedJoker));

        // different tags -> returns false
        editedJoker = new EditPetPatientDescriptorBuilder(DESC_JOKER).withTags(VALID_TAG_FIV).build();
        assertFalse(DESC_JOKER.equals(editedJoker));
    }

}
