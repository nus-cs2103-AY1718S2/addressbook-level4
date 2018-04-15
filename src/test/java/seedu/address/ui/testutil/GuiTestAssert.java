package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.CalendarEntryCardHandle;
import guitests.guihandles.CalendarEntryListPanelHandle;
import guitests.guihandles.OrderCardHandle;
import guitests.guihandles.OrderListPanelHandle;
import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;
import seedu.address.ui.PersonCard;

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
        expectedCard.getGroups().forEach(tag ->
             assertEquals(expectedCard.getGroupTagStyleClasses(tag), actualCard.getGroupTagStyleClasses(tag)));
        expectedCard.getPreferences().forEach(tag ->
             assertEquals(expectedCard.getPreferenceTagStyleClasses(tag),
                     actualCard.getPreferenceTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertGroupsEqual(expectedPerson, actualCard);
        assertPreferencesEqual(expectedPerson, actualCard);

    }
    //@@author AJZ1995
    /**
     * Adopted from CS2103T AB4 Appendix A
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see PersonCard#getGroupTagColorStyleFor(String)
     */
    private static String getGroupTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
        case "owesMoney":
            return "teal";

        case "twitter":
            return "blue";

        case "family":
        case "friend":
            return "orange";
        case "friends":
            return "brown";

        case "colleagues":
        case "neighbours":
            return "yellow";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see PersonCard#getPrefTagColorStyleFor(String)
     */
    private static String getPrefTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "cosmetics":
        case "computers":
            return "teal";

        case "necklaces":
            return "brown";

        case "notebooks":
            return "green";

        case "photobooks":
        case "skirts":
            return "orange";

        case "shoes":
            return "green";

        case "videoGames":
            return "black";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertGroupsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getGroupTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getGroups());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getGroupTagColorStyleFor(tag)),
                        actualCard.getGroupTagStyleClasses(tag)));
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertPreferencesEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getPreferenceTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getPreferences());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getPrefTagColorStyleFor(tag)),
                        actualCard.getPreferenceTagStyleClasses(tag)));
    }
    //@@author

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedOrder}.
     */
    public static void assertCardDisplaysOrder(Order expectedOrder, OrderCardHandle actualCard) {
        assertEquals(expectedOrder.getOrderInformation().toString(), actualCard.getOrderInformation());

        double priceValue = Double.valueOf(expectedOrder.getPrice().toString());
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        String expectedPriceAndQuantity = "S$" + String.valueOf(decimalFormat.format(priceValue))
                + " X " + expectedOrder.getQuantity().toString();

        assertEquals(expectedPriceAndQuantity, actualCard.getPriceAndQuantity());

        int quantityValue = Integer.valueOf(expectedOrder.getQuantity().toString());
        double totalPrice = priceValue * quantityValue;

        String expectedTotalPrice = "Total: S$" + String.valueOf(decimalFormat.format(totalPrice));

        assertEquals(expectedTotalPrice, actualCard.getTotalPrice());

        assertEquals("Deliver By: " + expectedOrder.getDeliveryDate().toString(), actualCard.getDeliveryDate());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEntry}.
     */
    public static void assertCardDisplaysEntry(CalendarEntry expectedEntry, CalendarEntryCardHandle actualCard) {
        assertEquals(expectedEntry.getEntryTitle().toString(), actualCard.getEntryTitle());

        String expectedStartDate = "From: " + expectedEntry.getStartDate().toString();
        assertEquals(expectedStartDate, actualCard.getStartDate());

        String expectedEndDate = "To: " + expectedEntry.getEndDate().toString();

        assertEquals(expectedEndDate, actualCard.getEndDate());

        String expectedTimeDuration = "Between " + expectedEntry.getStartTime().toString()
                + " and " + expectedEntry.getEndTime().toString();
        assertEquals(expectedTimeDuration, actualCard.getTimeDuration());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertPersonListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertPersonListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertPersonListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    //@@author SuxianAlicia-reused
    /**
     * Asserts that the list in {@code orderListPanelHandle} displays the details of {@code orders} correctly and
     * in the correct order.
     */
    public static void assertOrderListMatching(OrderListPanelHandle orderListPanelHandle, Order... orders) {
        for (int i = 0; i < orders.length; i++) {
            assertCardDisplaysOrder(orders[i], orderListPanelHandle.getOrderCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code orderListPanelHandle} displays the details of {@code orders} correctly and
     * in the correct order.
     */
    public static void assertOrderListMatching(OrderListPanelHandle orderListPanelHandle, List<Order> orders) {
        assertOrderListMatching(orderListPanelHandle, orders.toArray(new Order[0]));
    }

    /**
     * Asserts that the list in {@code calendarEntryListPanelHandle} displays the details of {@code entries} correctly
     * and in the correct order.
     */
    public static void assertCalendarEntryListMatching(
            CalendarEntryListPanelHandle calendarEntryListPanelHandle, CalendarEntry... entries) {
        for (int i = 0; i < entries.length; i++) {
            assertCardDisplaysEntry(entries[i], calendarEntryListPanelHandle.getCalendarEntryCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code calendarEntryListPanelHandle} displays the details of {@code entries} correctly
     * and in the correct order.
     */
    public static void assertCalendarEntryListMatching(
            CalendarEntryListPanelHandle calendarEntryListPanelHandle, List<CalendarEntry> entries) {
        assertCalendarEntryListMatching(calendarEntryListPanelHandle, entries.toArray(new CalendarEntry[0]));
    }
    //@@author

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertPersonListSize(PersonListPanelHandle personListPanelHandle, int size) {
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
