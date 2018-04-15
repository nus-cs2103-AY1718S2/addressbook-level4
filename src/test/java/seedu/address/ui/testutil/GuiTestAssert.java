package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Client;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        assertEquals(expectedCard.getLocation(), actualCard.getLocation());
        assertEquals(expectedCard.getGrade(), actualCard.getGrade());
        assertEquals(expectedCard.getSubject(), actualCard.getSubject());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Client expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertTagEquals(expectedPerson, actualCard);
        assertEquals(expectedPerson.getLocation().value, actualCard.getLocation());
        assertEquals(expectedPerson.getGrade().value, actualCard.getGrade());
        assertEquals(expectedPerson.getSubject().value, actualCard.getSubject());
    }

    /**
     * Asserts that{@code actualCard} displays the same tag and colour of {@code expectedPerson}
     * @param expectedPerson
     * @param actualCard
     */
    private static void assertTagEquals(Client expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags()
                .stream().map(tag -> tag.tagName)
                .collect(Collectors.toList());

        assertEquals(expectedTags, actualCard.getTags());

        expectedTags.forEach(tag -> assertEquals(Arrays
                .asList(LABEL_STYLE, getTagColorStyleFor(tag)), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that the colour matches the particular tag label.
     */
    private static String getTagColorStyleFor(String tagName) {

        switch(tagName) {

        case "owesMoney":
            return "green";

        case "friends":
            return "yellow";

        case "friend":
            return "green";

        case "husband":
            return "yellow";

        case "colleagues":
            return "purple";

        case "classmates":
            return "green";

        case "neighbours":
            return "gold";

        case "family":
            return "purple";

        case "Urgent":
            return "wheat";

        case "FemaleTutorsOnly":
            return "lightpink";

        case "ExtraCareRequired":
            return "red";

        case "Smart":
            return "blue";

        case "WillingToPay":
            return "lightpink";

        default:
            return "";
        }

    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Client... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Client> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Client[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
