package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TeamDisplayHandle;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonCard;
import seedu.address.ui.TeamDisplay;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getPosition(), actualCard.getPosition());
        assertEquals(expectedCard.getRating(), actualCard.getRating());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getTeamName(), actualCard.getTeamName());
        assertEquals(expectedCard.getTags(), actualCard.getTags());

        expectedCard.getTags().forEach(tag ->
                assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualTeamDisplay} displays the details of {@code expectedTeamDisplay}.
     */
    public static void assertTeamDisplayEquals(TeamDisplay expectedTeamDisplay, TeamDisplayHandle actualTeamDisplay) {
        expectedTeamDisplay.getTeams().forEach(team ->
                assertEquals(expectedTeamDisplay.getTeams().toString(), actualTeamDisplay.getTeams().toString()));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getTeamName().toString(), actualCard.getTeamName());
        assertEquals(expectedPerson.getRating().value, actualCard.getRating());
        assertEquals(expectedPerson.getPosition().getPositionName(), actualCard.getPosition());

        assertTagsEqual(expectedPerson, actualCard);
    }

    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * of {@tagColour}
     *
     * @see PersonCard getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
        case "owesMoney":
        case "colleagues":
        case "neighbours":
        case "family":
        case "friend":
        case "friends":
        case "husband":
        case "redCard":
        case "yellowCard":
        case "goodAttitude":
        case "badAttitude":
        case "injured":
            return "teal";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
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
