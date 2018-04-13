//@@author jas5469
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INFORMATION;
import static seedu.address.storage.XmlAdaptedGroup.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalGroups.GROUP_F;
import static seedu.address.testutil.TypicalGroups.GROUP_I;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.Information;
import seedu.address.testutil.Assert;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.TypicalPersons;

public class XmlAdaptedGroupTest {

    @Test
    public void toModelType_validGroupWithPerson_returnsGroup() throws Exception {
        XmlAdaptedGroup group = new XmlAdaptedGroup(GROUP_F);
        assertEquals(GROUP_F, group.toModelType());
    }

    @Test
    public void toModelType_getPersonList_returnPersonList() throws Exception {
        XmlAdaptedGroup group = new XmlAdaptedGroup(new GroupBuilder().withInformation("Group F").build());
        group.getPersonList().add(new XmlAdaptedPerson(TypicalPersons.ALICE));
        assertEquals(GROUP_F.getPersonList(), group.toModelType().getPersonList());
    }

    @Test
    public void toModelType_addPerson_returnSuccess() throws Exception {
        XmlAdaptedGroup group = new XmlAdaptedGroup(GROUP_F);
        XmlAdaptedPerson personToAdd = new XmlAdaptedPerson(TypicalPersons.BENSON);
        group.getPersonList().add(personToAdd);
        assertEquals(GROUP_I, group.toModelType());
    }

    @Test
    public void toModelType_validGroupDetails_returnsGroup() throws Exception {
        XmlAdaptedGroup group = new XmlAdaptedGroup(GROUP_A);
        assertEquals(GROUP_A, group.toModelType());
    }

    @Test
    public void toModelType_invalidInformation_throwsIllegalValueException() {
        XmlAdaptedGroup group =
                new XmlAdaptedGroup(INVALID_INFORMATION);
        String expectedMessage = Information.MESSAGE_INFORMATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void toModelType_nullInformation_throwsIllegalValueException() {
        XmlAdaptedGroup group = new XmlAdaptedGroup((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Information.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, group::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedGroup groupA = new XmlAdaptedGroup(GROUP_A);
        XmlAdaptedGroup groupB = new XmlAdaptedGroup(GROUP_A);
        assertTrue(groupA.equals(groupA));
        assertFalse(groupA.equals(1));
        assertTrue(groupA.equals(groupB));
    }

}
