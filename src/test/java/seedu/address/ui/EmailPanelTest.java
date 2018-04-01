package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EmailPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class EmailPanelTest extends GuiUnitTest {

    private EmailPanelHandle emailPanelHandle;

    @Before
    public void setUp() {
        EmailPanel emailPanel = new EmailPanel();
        uiPartRule.setUiPart(emailPanel);

        emailPanelHandle = new EmailPanelHandle(emailPanel.getRoot());
    }

    @Test
    public void display() {

        // new result received
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        postNow(new PersonPanelSelectionChangedEvent(personCard));
        guiRobot.pauseForHuman();

        assertEquals(person.getEmail().value, emailPanelHandle.getRecipient());
        assertEquals(" Dear " + person.getName().fullName + ", ", emailPanelHandle.getBody());
    }
}
