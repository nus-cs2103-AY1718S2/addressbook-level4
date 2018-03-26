package seedu.address.ui;

import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertBrowserDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStubStudent;
    private PersonPanelSelectionChangedEvent selectionChangedEventStubTutor;
    //private PersonPanelSelectionChangedEvent selectionChangedEventStubPersonOnlyNameSpecified;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    //private Person personOnlyNameSpecified;

    @Before
    public void setUp() {
        /*personOnlyNameSpecified  = new PersonBuilder().withName("Hilda Lim")
                .withAddress(null).withEmail(null).withPhone(null)
                .withPrice(null).withSubject(null).withStatus(null).withLevel(null)
                .withTags(new String[0]).build();*/

        selectionChangedEventStubStudent = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        selectionChangedEventStubTutor = new PersonPanelSelectionChangedEvent(new PersonCard(BENSON, 0));
        //selectionChangedEventStubPersonOnlyNameSpecified =
        //        new PersonPanelSelectionChangedEvent(new PersonCard(personOnlyNameSpecified, 0));


        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() {
        // student
        Person student = ALICE;
        postNow(selectionChangedEventStubStudent);
        assertBrowserDisplay(student);

        // tutor
        Person tutor = BENSON;
        postNow(selectionChangedEventStubTutor);
        assertBrowserDisplay(tutor);

        // person with only name specified
        //postNow(selectionChangedEventStubTutor);
        //assertBrowserDisplay(personOnlyNameSpecified);
    }

    /**
     * Asserts that {@code browserPanel} displays the details of {@code expectedPerson} correctly.
     */
    private void assertBrowserDisplay(Person expectedPerson) {
        guiRobot.pauseForHuman();

        // verify person details are displayed correctly
        assertBrowserDisplaysPerson(expectedPerson, browserPanelHandle);
    }
}
