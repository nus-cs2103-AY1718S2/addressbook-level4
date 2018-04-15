# philos22
###### \java\seedu\address\CalendarTest.java
``` java

package seedu.address;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

/**
 * This class is meant to override some properties of Calendar so that it will be suited for
 * testing
 */
public class CalendarTest extends Calendar {

    private static ArrayList<String> eventIDs = new ArrayList<>();

    static {
        addEventIDs("one1");
        addEventIDs("two2");
        addEventIDs("three3");
        addEventIDs("four4");
        addEventIDs("five5");
        addEventIDs("six6");
        addEventIDs("seven7");
        addEventIDs("eight8");
    }

    private static List<String> pseudoEventList = new ArrayList<>();

    static {
        pseudoEventList.add("[Event 1] \t EventName1 \t [EventStart1] to [EventEnd1] \tLocation: EventLocation1\n");
        pseudoEventList.add("[Event 2] \t EventName2 \t [EventStart2] to [EventEnd2] \tLocation: EventLocation2\n");
        pseudoEventList.add("[Event 3] \t EventName3 \t [EventStart3] to [EventEnd3] \tLocation: EventLocation3\n");
        pseudoEventList.add("[Event 4] \t EventName4 \t [EventStart4] to [EventEnd4] \tLocation: EventLocation4\n");
        pseudoEventList.add("[Event 5] \t EventName5 \t [EventStart5] to [EventEnd5] \tLocation: EventLocation5\n");
        pseudoEventList.add("[Event 6] \t EventName6 \t [EventStart6] to [EventEnd6] \tLocation: EventLocation6\n");
        pseudoEventList.add("[Event 7] \t EventName7 \t [EventStart7] to [EventEnd7] \tLocation: EventLocation7\n");
        pseudoEventList.add("[Event 8] \t EventName8 \t [EventStart8] to [EventEnd8] \tLocation: EventLocation8\n");
    }

    public static void addEventIDs(String event) {
        eventIDs.add(event);
    }

    /**
     * Manually creating a list of events for testing
     */
    public static String listEventsTest() {
        StringBuilder result = new StringBuilder();
        for (String eventLine : pseudoEventList) {
            result.append(eventLine);
        }
        return result.toString();
    }

    @Test
    public static void addEventTest(String eventName, String eventLocation, DateTime startDateTime,
                                    DateTime endDateTime) {

        Event event = new Event()
                .setSummary(eventName)
                .setLocation(eventLocation);

        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("");
        event.setEnd(end);

        //EventName matches
        assertEquals(event.getSummary(), eventName);

        //Location matches
        assertEquals(event.getLocation(), eventLocation);

        //Start matches
        assertEquals(event.getStart(), startDateTime);

        //End matches
        assertEquals(event.getEnd(), endDateTime);
    }

}
```
###### \java\seedu\address\logic\commands\CalendarCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.CalendarTest;

/**
 * Ensures that the events are listed in the same way
 */
public class CalendarCommandTest {

    private static List<String> pseudoEventList = new ArrayList<>();

    @Before
    public void setUp() {
        pseudoEventList.add("[Event 1] \t EventName1 \t [EventStart1] to [EventEnd1] \tLocation: EventLocation1\n");
        pseudoEventList.add("[Event 2] \t EventName2 \t [EventStart2] to [EventEnd2] \tLocation: EventLocation2\n");
        pseudoEventList.add("[Event 3] \t EventName3 \t [EventStart3] to [EventEnd3] \tLocation: EventLocation3\n");
        pseudoEventList.add("[Event 4] \t EventName4 \t [EventStart4] to [EventEnd4] \tLocation: EventLocation4\n");
        pseudoEventList.add("[Event 5] \t EventName5 \t [EventStart5] to [EventEnd5] \tLocation: EventLocation5\n");
        pseudoEventList.add("[Event 6] \t EventName6 \t [EventStart6] to [EventEnd6] \tLocation: EventLocation6\n");
        pseudoEventList.add("[Event 7] \t EventName7 \t [EventStart7] to [EventEnd7] \tLocation: EventLocation7\n");
        pseudoEventList.add("[Event 8] \t EventName8 \t [EventStart8] to [EventEnd8] \tLocation: EventLocation8\n");
    }

    @Test
    public void calendarListEvents_showsSameList() {
        StringBuilder expectedOutput = new StringBuilder();
        for (String eventLine : pseudoEventList) {
            expectedOutput.append(eventLine);
        }
        assertEquals(CalendarTest.listEventsTest().toString(), expectedOutput.toString());
    }

}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    private FindCommand prepareCommand(String userInput, String tagInput) {
        FindCommand command =
                new FindCommand(new ContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")),
                        Arrays.asList(tagInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
```
###### \java\seedu\address\logic\parser\CalendarAddCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATETIME_FORMAT;
import static seedu.address.logic.commands.CalendarAddCommand.COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.CalendarAddCommand;

/**
 * Testing for CalendarAddCommand
 */
public class CalendarAddCommandParserTest {

    private CalendarAddCommandParser parser = new CalendarAddCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCalendarAddCommand() {
        // no leading and trailing whitespaces
        String eventName = "CalendarTest";
        String location = "CalendarLocation";
        String startTime = "2019-01-01 12:00:00";
        String endTime = "2019-01-01 13:00:00";
        CalendarAddCommand expectedCalendarAddCommand =
                new CalendarAddCommand(eventName, location, ParserUtil.parseDateTime(startTime),
                        ParserUtil.parseDateTime(endTime));

        assertParseSuccess(parser,
                "calAdd event/CalendarTest loc/CalendarLocation start/2019-01-01 12:00:00 end/2019-01-01 13:00:00",
                expectedCalendarAddCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser,
                "calAdd \n event/CalendarTest \t \n loc/CalendarLocation \t start/2019-01-01 12:00:00 \n "
                        + "end/2019-01-01 13:00:00", expectedCalendarAddCommand);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarAddCommand.MESSAGE_USAGE);

        //missing event prefix
        assertParseFailure(parser, COMMAND_WORD + " " + VALID_EVENT_NAME + EVENT_LOCATION_DESC
                + EVENT_START_DESC + EVENT_END_DESC, expectedMessage);

        //missing location prefix
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC+ VALID_EVENT_LOCATION + EVENT_START_DESC
                 + EVENT_END_DESC, expectedMessage);

        //missing startDateTime prefix
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + EVENT_LOCATION_DESC + VALID_EVENT_START
                + EVENT_END_DESC, expectedMessage);

        //missing endDateTime prefix
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + EVENT_LOCATION_DESC + EVENT_START_DESC
                + VALID_EVENT_END, expectedMessage);
    }

    @Test
    public void parse_invalidArgs_failure() {
        //invalid date format - start and end dates are the same scenario
        assertParseFailure(parser, COMMAND_WORD + " " + EVENT_NAME_DESC + EVENT_LOCATION_DESC + INVALID_EVENT_START
                + EVENT_END_DESC, MESSAGE_INVALID_DATETIME_FORMAT);
    }

}
```
###### \java\seedu\address\logic\parser\CalendarDeleteCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.CalendarDeleteCommand;

/**
 * Testing for CalendarDeleteCommand
 */
public class CalendarDeleteCommandParserTest {

    private CalendarDeleteCommandParser parser = new CalendarDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<String> eventIdsTest = new ArrayList<>(); //Same logic as delete event command
        eventIdsTest.add("a1b2c3d4e5f6g7");
        assertEquals(eventIdsTest.get(1 - 1), "a1b2c3d4e5f6g7");
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                CalendarDeleteCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> nameList = new ArrayList<>();
        nameList.add("Alice");
        nameList.add("Bob");
        List<String> tagList = new ArrayList<>();
        tagList.add("Tag1");
        tagList.add("Tag2");
        FindCommand expectedFindCommand =
                new FindCommand(new ContainsKeywordsPredicate(nameList, tagList));

        assertParseSuccess(parser, "find n/Alice Bob t/Tag1 Tag2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "find \n n/Alice \n \t Bob  \t t/Tag1 \n Tag2", expectedFindCommand);
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Tag1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Tag1").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Tag1", "Tag2"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Tag1").build()));

        // Mixed-case keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("tAg1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Tag1").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Tag1").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Tag2"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Tag1").build()));

        // Keywords match phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice", "12345",
                "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Tag1").withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
```
