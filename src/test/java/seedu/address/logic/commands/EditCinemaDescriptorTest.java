package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SENGKANG;
import static seedu.address.logic.commands.CommandTestUtil.DESC_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TAMPINES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_TAMPINES;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditCinemaDescriptor;
import seedu.address.testutil.EditCinemaDescriptorBuilder;

public class EditCinemaDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCinemaDescriptor descriptorWithSameValues = new EditCinemaDescriptor(DESC_SENGKANG);
        assertTrue(DESC_SENGKANG.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_SENGKANG.equals(DESC_SENGKANG));

        // null -> returns false
        assertFalse(DESC_SENGKANG.equals(null));

        // different types -> returns false
        assertFalse(DESC_SENGKANG.equals(5));

        // different values -> returns false
        assertFalse(DESC_SENGKANG.equals(DESC_TAMPINES));

        // different name -> returns false
        EditCinemaDescriptor editedAmy = new EditCinemaDescriptorBuilder(DESC_SENGKANG)
                .withName(VALID_NAME_TAMPINES).build();
        assertFalse(DESC_SENGKANG.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditCinemaDescriptorBuilder(DESC_SENGKANG).withPhone(VALID_PHONE_TAMPINES).build();
        assertFalse(DESC_SENGKANG.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditCinemaDescriptorBuilder(DESC_SENGKANG).withEmail(VALID_EMAIL_TAMPINES).build();
        assertFalse(DESC_SENGKANG.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditCinemaDescriptorBuilder(DESC_SENGKANG).withAddress(VALID_ADDRESS_TAMPINES).build();
        assertFalse(DESC_SENGKANG.equals(editedAmy));

    }
}
