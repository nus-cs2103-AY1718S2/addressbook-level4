package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_APPOINTMENT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_NERO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_FION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_VACCINATION;

import org.junit.Test;

import seedu.address.logic.descriptors.EditAppointmentDescriptor;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

//@@author chialejing
public class EditAppointmentDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditAppointmentDescriptor descriptorWithSameValues = new EditAppointmentDescriptor(DESC_APPOINTMENT_ONE);
        assertTrue(DESC_APPOINTMENT_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_APPOINTMENT_ONE.equals(DESC_APPOINTMENT_ONE));

        // null -> returns false
        assertFalse(DESC_APPOINTMENT_ONE.equals(null));

        // different types -> returns false
        assertFalse(DESC_APPOINTMENT_ONE.equals(0));

        // different values -> returns false
        assertFalse(DESC_APPOINTMENT_ONE.equals(DESC_APPOINTMENT_TWO));

        // different owner -> returns false
        EditAppointmentDescriptor editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withOwnerNric(VALID_NRIC_FION).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different pet patient name -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withPetPatientName(VALID_NAME_NERO).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different remark -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withRemark(VALID_REMARK_TWO).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different local date time -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withLocalDateTime(VALID_DATE_TWO).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));

        // different tags -> returns false
        editedAppointment = new EditAppointmentDescriptorBuilder(DESC_APPOINTMENT_ONE)
                .withTags(VALID_TAG_VACCINATION).build();
        assertFalse(DESC_APPOINTMENT_ONE.equals(editedAppointment));
    }
}
