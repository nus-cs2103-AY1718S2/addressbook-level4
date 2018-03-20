package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MA2108_HOMEWORK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_CS2010_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_CS2010_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CS2010_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TIME_CS2010_QUIZ;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MA2108;

//import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditActivityDescriptor;
import seedu.address.testutil.EditActivityDescriptorBuilder;

public class EditActivityDescriptorTest {

    //TODO: TEST
    /**
     * Test
     */
    public void equals() {
        // same values -> returns true
        EditActivityDescriptor descriptorWithSameValues = new EditActivityDescriptor(DESC_MA2108_HOMEWORK);
        assertTrue(DESC_MA2108_HOMEWORK.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_MA2108_HOMEWORK.equals(DESC_MA2108_HOMEWORK));

        // null -> returns false
        assertFalse(DESC_MA2108_HOMEWORK.equals(null));

        // different types -> returns false
        assertFalse(DESC_MA2108_HOMEWORK.equals(5));

        // different values -> returns false
        assertFalse(DESC_MA2108_HOMEWORK.equals(DESC_CS2010_QUIZ));

        // different name -> returns false
        EditActivityDescriptor editedAmy = new EditActivityDescriptorBuilder(DESC_MA2108_HOMEWORK).withName(VALID_NAME_CS2010_QUIZ).build();
        assertFalse(DESC_MA2108_HOMEWORK.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditActivityDescriptorBuilder(DESC_MA2108_HOMEWORK).withPhone(VALID_DATE_TIME_CS2010_QUIZ).build();
        assertFalse(DESC_MA2108_HOMEWORK.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditActivityDescriptorBuilder(DESC_MA2108_HOMEWORK).withAddress(VALID_REMARK_CS2010_QUIZ).build();
        assertFalse(DESC_MA2108_HOMEWORK.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditActivityDescriptorBuilder(DESC_MA2108_HOMEWORK).withTags(VALID_TAG_MA2108).build();
        assertFalse(DESC_MA2108_HOMEWORK.equals(editedAmy));
    }
}
