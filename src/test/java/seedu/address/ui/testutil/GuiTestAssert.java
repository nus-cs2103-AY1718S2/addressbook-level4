package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PatientCardHandle;
import guitests.guihandles.PatientListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.patient.Patient;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PatientCardHandle expectedCard, PatientCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());

        //@@author Kyholmes-reused
        //Reused from https://github.com/se-edu/addressbook-level4/pull/798/files
        expectedCard.getTags().forEach(tag ->
            assertEquals(expectedCard.getTagColors(tag), actualCard.getTagColors(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPatient}.
     */
    public static void assertCardDisplaysPerson(Patient expectedPatient, PatientCardHandle actualCard) {
        assertEquals(expectedPatient.getName().fullName, actualCard.getName());
        assertEquals(expectedPatient.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, Patient... patients) {
        for (int i = 0; i < patients.length; i++) {
            assertCardDisplaysPerson(patients[i], patientListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code patientListPanelHandle} displays the details of {@code patients} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PatientListPanelHandle patientListPanelHandle, List<Patient> patients) {
        assertListMatching(patientListPanelHandle, patients.toArray(new Patient[0]));
    }

    /**
     * Asserts the size of the list in {@code patientListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PatientListPanelHandle patientListPanelHandle, int size) {
        int numberOfPeople = patientListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
