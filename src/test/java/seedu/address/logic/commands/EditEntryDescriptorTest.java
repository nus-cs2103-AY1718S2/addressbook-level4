package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GET_STOCKS;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_BOSS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_BOSS;

import org.junit.Test;

import seedu.address.logic.commands.EditEntryCommand.EditEntryDescriptor;
import seedu.address.testutil.EditEntryDescriptorBuilder;

public class EditEntryDescriptorTest {
    @Test
    public void equals() {

        // same values -> returns true
        EditEntryDescriptor descriptorWithSameValues = new EditEntryDescriptor(DESC_GET_STOCKS);
        assertTrue(DESC_GET_STOCKS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GET_STOCKS.equals(DESC_GET_STOCKS));

        // null -> returns false
        assertFalse(DESC_GET_STOCKS.equals(null));

        // different types -> returns false
        assertFalse(DESC_GET_STOCKS.equals(5));

        // different values -> returns false
        assertFalse(DESC_GET_STOCKS.equals(DESC_MEET_BOSS));

        // different entry title -> returns false
        EditEntryDescriptor editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS)
                .withEntryTitle(VALID_ENTRY_TITLE_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different start date -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withStartDate(VALID_START_DATE_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different end date -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withEndDate(VALID_END_DATE_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different start time -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withStartTime(VALID_START_TIME_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));

        // different end time -> returns false
        editedBoss = new EditEntryDescriptorBuilder(DESC_GET_STOCKS).withEndTime(VALID_END_TIME_MEET_BOSS).build();
        assertFalse(DESC_GET_STOCKS.equals(editedBoss));
    }
}
