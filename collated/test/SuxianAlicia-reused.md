# SuxianAlicia-reused
###### /java/seedu/address/logic/parser/DeleteEntryCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.Test;

import seedu.address.logic.commands.DeleteEntryCommand;

public class DeleteEntryCommandParserTest {

    private DeleteEntryCommandParser parser = new DeleteEntryCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteEntryCommand(INDEX_FIRST_ENTRY));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteEntryCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/DeleteGroupCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalGroups.FRIENDS;

import org.junit.Test;

import seedu.address.logic.commands.DeleteGroupCommand;

public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        assertParseSuccess(parser, "friends", new DeleteGroupCommand(FRIENDS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "fr!ends", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteGroupCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/DeletePreferenceCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;

import org.junit.Test;

import seedu.address.logic.commands.DeletePreferenceCommand;

public class DeletePreferenceCommandParserTest {

    private DeletePreferenceCommandParser parser = new DeletePreferenceCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePreferenceCommand() {
        assertParseSuccess(parser, "computers", new DeletePreferenceCommand(COMPUTERS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Comp&ters", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePreferenceCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
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
```
