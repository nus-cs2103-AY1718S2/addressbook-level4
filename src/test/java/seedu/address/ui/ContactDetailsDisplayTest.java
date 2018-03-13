package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ContactDetailsDisplayHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ContactDetailsDisplayTest extends GuiUnitTest {

    private ContactDetailsDisplayHandle contactDetailsDisplayHandle;

    @Before
    public void setUp() {
        ContactDetailsDisplay contactDetailsDisplay = new ContactDetailsDisplay();
        uiPartRule.setUiPart(contactDetailsDisplay);

        contactDetailsDisplayHandle = new ContactDetailsDisplayHandle(contactDetailsDisplay.getRoot());
    }

    @Test
    public void display() {
        // default result text
        assertEquals("", contactDetailsDisplayHandle.getName());
        assertEquals("", contactDetailsDisplayHandle.getAddress());
        assertEquals("", contactDetailsDisplayHandle.getPhone());
        assertEquals("", contactDetailsDisplayHandle.getEmail());

        // new result received
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        postNow(new PersonPanelSelectionChangedEvent(personCard));
        guiRobot.pauseForHuman();

        assertEquals(person.getName().toString(), contactDetailsDisplayHandle.getName());
        assertEquals(person.getAddress().toString(), contactDetailsDisplayHandle.getAddress());
        assertEquals(person.getPhone().toString(), contactDetailsDisplayHandle.getPhone());
        assertEquals(person.getEmail().toString(), contactDetailsDisplayHandle.getEmail());
    }
}
