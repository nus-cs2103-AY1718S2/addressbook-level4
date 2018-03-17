package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PersonBuilder;

public class PatientCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Patient patientWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(patientWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, patientWithNoTags, 1);

        // with tags
        Patient patientWithTags = new PersonBuilder().build();
        personCard = new PersonCard(patientWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, patientWithTags, 2);
    }

    @Test
    public void equals() {
        Patient patient = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(patient, 0);

        // same patient, same index -> returns true
        PersonCard copy = new PersonCard(patient, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different patient, same index -> returns false
        Patient differentPatient = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPatient, 0)));

        // same patient, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(patient, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPatient} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Patient expectedPatient, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify patient details are displayed correctly
        assertCardDisplaysPerson(expectedPatient, personCardHandle);
    }
}
