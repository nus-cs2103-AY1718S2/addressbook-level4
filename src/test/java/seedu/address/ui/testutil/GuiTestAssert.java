package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

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
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getGroups(), actualCard.getGroups());
        assertEquals(expectedCard.getPreferences(), actualCard.getPreferences());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getGroupTags().stream().map(group -> group.tagName).collect(Collectors.toList()),
                actualCard.getGroups());
        assertEquals(expectedPerson.getPreferenceTags().stream().map(pref -> pref.tagName).collect(Collectors.toList()),
                actualCard.getPreferences());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedOrder}.
     */
    public static void assertCardDisplaysOrder(Order expectedOrder, OrderCardHandle actualCard) {
        assertEquals(expectedOrder.getOrderInformation().toString(), actualCard.getOrderInformation());

        String expectedPriceAndQuantity = "S$" + expectedOrder.getPrice().toString() + " X "
                + expectedOrder.getQuantity().toString();
        assertEquals(expectedPriceAndQuantity, actualCard.getPriceAndQuantity());

        String expectedTotalPrice = "Total: S$" + String.valueOf(
                Double.parseDouble(expectedOrder.getPrice().toString())
                        * Integer.parseInt(expectedOrder.getQuantity().toString()));

        assertEquals(expectedTotalPrice, actualCard.getTotalPrice());

        assertEquals("Deliver By: " + expectedOrder.getDeliveryDate().toString(), actualCard.getDeliveryDate());
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
