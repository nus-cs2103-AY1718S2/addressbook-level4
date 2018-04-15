package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Client;
import seedu.address.testutil.ClientBuilder;

//@@author shookshire
public class ClientCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Client personWithNoTags = new ClientBuilder().withTags(new String[0]).build();
        ClientCard clientCard = new ClientCard(personWithNoTags, 1);
        uiPartRule.setUiPart(clientCard);
        assertCardDisplay(clientCard, personWithNoTags, 1);

        // with tags
        Client personWithTags = new ClientBuilder().build();
        clientCard = new ClientCard(personWithTags, 2);
        uiPartRule.setUiPart(clientCard);
        assertCardDisplay(clientCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Client person = new ClientBuilder().build();
        ClientCard personCard = new ClientCard(person, 0);

        // same person, same index -> returns true
        ClientCard copy = new ClientCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Client differentPerson = new ClientBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new ClientCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new ClientCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ClientCard clientCard, Client expectedClient, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(clientCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedClient, personCardHandle);
    }
}
