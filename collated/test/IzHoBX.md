# IzHoBX
###### \java\seedu\address\logic\commands\NotificationSystemTest.java
``` java
package seedu.address.logic.commands;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.NotificationCard;
import seedu.address.ui.NotificationCenter;
import seedu.address.ui.testutil.NotificationCardStub;

public class NotificationSystemTest {

    @Test
    public void run() {
        //object instantiation
        NotificationCenterStub notificationCenterStub = new NotificationCenterStub();
        ModelStub modelStub = new ModelStub();
        modelStub.setNotificationCenter(notificationCenterStub);
        AddressbookStub addressbookStub = new AddressbookStub();

        //setup initial data
        Person person1 = null;
        Person person2 = null;
        try {
            person1 = new Person(new Name("Alex"), new Phone("12344"), new Email("h@b.c"), new Address("ascada"),
                    ParserUtil.parseTags(new LinkedList<>()), "asasdasd");
            person1.setId(2);
            person2 = new Person(new Name("David"), new Phone("12344"), new Email("h@b.c"), new Address("ascada"),
                    ParserUtil.parseTags(new LinkedList<>()), "asasdasd");
            person2.setId(5);
        } catch (IllegalValueException e) {
            assertTrue(false);
        }

        try {
            addressbookStub.addPerson(person1);
            addressbookStub.addPerson(person2);
        } catch (DuplicatePersonException e) {
            assertTrue(false);
        }
        assertEquals(addressbookStub.getPersonList().size(), 2);
        modelStub.setAddressBook(addressbookStub);
        assertEquals(modelStub.getAddressBook().getPersonList().size(), 2);

        //adding notification test
        Notification notification1 = new Notification("TEST1", person1.getCalendarId(), "ascca",
                "wasccas", person1.getId() + "");
        Notification notification2 = new Notification("TEST2", person2.getCalendarId(), "ascca",
                "wasccas", person2.getId() + "");
        Notification notification3 = new Notification("TEST3", person2.getCalendarId(), "ascca",
                "wasccas", person2.getId() + "");
        Notification notification4 = new Notification("TEST4", person2.getCalendarId(), "ascca",
                "wasccas", person2.getId() + "");
        addressbookStub.addNotification(notification1);
        addressbookStub.addNotification(notification2);
        addressbookStub.addNotification(notification3);
        addressbookStub.addNotification(notification4);

        notificationCenterStub.add(new NotificationCardStub(notification1.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "Alex", notification1.getEndDate(), notification1.getOwnerId(), true,
                notification1.getEventId()));
        notificationCenterStub.add(new NotificationCardStub(notification2.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "David", notification1.getEndDate(), notification2.getOwnerId(), true,
                notification2.getEventId()));
        notificationCenterStub.add(new NotificationCardStub(notification3.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "David", notification1.getEndDate(), notification3.getOwnerId(), true,
                notification3.getEventId()));
        notificationCenterStub.add(new NotificationCardStub(notification4.getTitle(), (
                notificationCenterStub.getTotalUndismmissedNotificationCards() + 1) + "",
                "David", notification1.getEndDate(), notification4.getOwnerId(), true,
                notification4.getEventId()));
        assertTrue(notificationCenterStub.getTotalUndismmissedNotificationCards() == 4);

        //email command test
        EmailCommandStub ec = new EmailCommandStub(Index.fromOneBased(1));
        ec.setData(modelStub);
        try {
            ec.preprocessUndoableCommand();
        } catch (CommandException e) {
            assertTrue(false);
        }
        assertEquals(ec.getPerson().getEmail(), person1.getEmail());
    }
}

class NotificationCenterStub extends NotificationCenter {

    protected LinkedList<NotificationCardStub> notificationCardCopy;

    public NotificationCenterStub() {
        notificationCardCopy = new LinkedList<>();
        notificationCardCopy.add(null);
    }

    public void add(NotificationCardStub newNotificationCard) {
        notificationCardCopy.add(newNotificationCard);
    }

    @Override
    public int getTotalUndismmissedNotificationCards() {
        return notificationCardCopy.size() - 1;
    }

    public NotificationCardStub getNotificationCardStub(Index targetIndex) {
        return notificationCardCopy.get(targetIndex.getOneBased());
    }

    public String getOwnerIdByIndex(Index index) {
        return notificationCardCopy.get(index.getOneBased()).getOwnerId();
    }
}

class ModelStub extends ModelManager {

    private NotificationCenterStub notificationCenter;
    private AddressBook addressBook;
    private LinkedList<Person> persons = new LinkedList<>();

    @Override
    public void addNotification(Notification e) {

    }

    @Override
    public void addPerson(Person person) throws DuplicatePersonException {
        persons.add(person);
    }

    @Override
    public void deletePerson(Person target) throws PersonNotFoundException {
        fail("This method should not be called.");
    }

    @Override
    public void deleteNotification(String id, boolean deleteFromAddressBookOnly)
            throws NotificationNotFoundException {

    }

    public void setNotificationCenter(NotificationCenterStub notificationCenter) {
        this.notificationCenter = notificationCenter;
    }

    @Override
    public NotificationCenterStub getNotificationCenter() {
        return notificationCenter;
    }

    @Override
    public NotificationCard deleteNotificationByIndex(Index targetIndex) throws NotificationNotFoundException {
        return null;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }
}

class AddressbookStub extends AddressBook {
    private LinkedList<Notification> notifications;
    private Semaphore semaphore;

    public AddressbookStub() {
        this.notifications = new LinkedList<>();
        semaphore = new Semaphore(1);
    }
}

class EmailCommandStub {
    private final Index targetIndex;
    private ModelStub model;
    private Person owner;

    public EmailCommandStub(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    public void setData(ModelStub model) {
        this.model = model;
    }

    /**
     * Mimics the behavior of preprocessUndoableCommand() in EmailCommand
     */
    public void preprocessUndoableCommand() throws CommandException {
        if (targetIndex.getZeroBased() >= model.getNotificationCenter().getTotalUndismmissedNotificationCards() - 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX);
        }
        NotificationCardStub notificationCard = model.getNotificationCenter().getNotificationCardStub(targetIndex);
        String ownerId = model.getNotificationCenter().getOwnerIdByIndex(targetIndex);
        owner = model.getAddressBook().findPersonById(Integer.parseInt(ownerId));
    }

    public Person getPerson() {
        return owner;
    }
}

```
###### \java\seedu\address\logic\commands\RateCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class RateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().withRating("1").withEmail("alice@example.com")
                .withReviews("supervisor@example.com\nLazy")
                .build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRating("1").build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code descriptor}
     */
    private RateCommand prepareCommand(Index index, EditCommand.EditPersonDescriptor descriptor) {
        RateCommand rateCommand = new RateCommand(index, descriptor);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
```
###### \java\seedu\address\logic\parser\RateCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RateCommand;

public class RateCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_oneIntegerArg_failure() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonIntegerArg_failure() {
        assertParseFailure(parser, "a b", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 b", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "b 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexZeroOrLess_failure() {
        assertParseFailure(parser, "0 5", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1 5", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_ratingOutOfBound_failure() {
        assertParseFailure(parser, "1 6", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 -1", MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\address\model\notification\NotificationTest.java
``` java
package seedu.address.model.notification;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Person;

public class NotificationTest {
    private static final String SAMPLE_STRING = "123";

    @Test
    public void notification_nullTitle_fail() {
        try {
            Notification n = new Notification(null, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_idNull_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, null, SAMPLE_STRING, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_endDateNull_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, null, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_ownerIdNull_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, null);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_ownerIdUninitialised_fail() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING,
                    Person.UNINITIALISED_ID + "");
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assertTrue(false);
    }

    @Test
    public void notification_nonEssentialParameterNull_success() {
        try {
            Notification n = new Notification(SAMPLE_STRING, null, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(false);
            return;
        }
        assertTrue(true);
    }

    @Test
    public void notification_allParameterNonNull_success() {
        try {
            Notification n = new Notification(SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING, SAMPLE_STRING,
                    SAMPLE_STRING);
        } catch (Error e) {
            assertTrue(false);
            return;
        }
        assertTrue(true);
    }
}
```
###### \java\seedu\address\model\notification\NotificationTimeTest.java
``` java
package seedu.address.model.notification;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.junit.Test;

public class NotificationTimeTest {

    @Test
    public void isTodayTest_pastDate_fail() {
        assertFalse((new NotificationTime(2014, 2, 1, 10, 12, 13)).isToday());
    }

    @Test
    public void isTodayTest_todayDate_success() {
        assertTrue((new NotificationTime(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                12, 12, 12)).isToday());
    }

    @Test
    public void isTodayTest_futureDate_fail() {
        assertFalse((new NotificationTime(2024, 2, 1, 10, 12, 13)).isToday());
    }

}
```
###### \java\seedu\address\storage\NotificationTimeParserUtilTest.java
``` java
package seedu.address.storage;

import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

public class NotificationTimeParserUtilTest {
    private static final String NO_INTEGER_INVALID = "assvvbdgdtdyhdhdhbdfhdfhdhfhdghfhghfhjfjfjjjfjfhdgdgsdfsfsfsgghd";
    private static final String NULL_STRING_INVALID = "";
    private static final String SHORTER_THAN_SECOND_END_INDEX_INVALID = "20180419T1220";
    private static final String ACCEPTABLE_FORMAT_VALID = "2018-04-19T12:20:00";
    private static final String ACCEPTABLE_FORMAT_WITHOUT_SEPARATION_INVALID = "20180419122000";
    private static final String DEFAULT_FORMAT_VALID =
            "{\"dateTime\":\"2018-12-20T13:35:00.000+08:00timeZone:\"Asia/Singapore\"}";
    private static final String DEFAULT_FORMAT_WITHOUT_QUOTE_VALID =
            "{dateTime:2018-12-20T13:35:00.000+08:00timeZone:Asia/Singapore}";

    @Test
    public void parseTimeTest_invalidInput_fail() {
        assertError(NO_INTEGER_INVALID);
        assertError(NULL_STRING_INVALID);
        assertError(SHORTER_THAN_SECOND_END_INDEX_INVALID);
        assertError(ACCEPTABLE_FORMAT_WITHOUT_SEPARATION_INVALID);
    }

    @Test
    public void parseTimeTest_validInput_success() {
        assertSuccess(ACCEPTABLE_FORMAT_VALID);
        assertSuccess(DEFAULT_FORMAT_VALID);
        assertSuccess(DEFAULT_FORMAT_WITHOUT_QUOTE_VALID);
    }

    /**
     * Passes if there is error thrown, vice versa.
     */
    private void assertError(String input) {
        try {
            NotificationTimeParserUtil.parseTime(input);
        } catch (Error e) {
            assertTrue(e instanceof AssertionError);
            return;
        }
        assert(false);
    }

    /**
     * Passes if there is no error thrown, vice versa.
     */
    private void assertSuccess(String input) {
        try {
            NotificationTimeParserUtil.parseTime(input);
        } catch (AssertionError e) {
            assertTrue(false);
            return;
        }
        assertTrue(true);
    }
}
```
###### \java\seedu\address\ui\testutil\NotificationCardStub.java
``` java
package seedu.address.ui.testutil;

import java.util.LinkedList;

/**
 * A stub that mimics the behavior of Notification Card by removing the JavaFX components.
 */
public class NotificationCardStub {

    protected boolean isFirstStage;
    private String title;
    private String index;
    private String ownerName;
    private String endTime;
    private LinkedList<String> content = new LinkedList<>();

    private String ownerId;
    private boolean isForCenter;
    private String id;

    public NotificationCardStub(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, String id) {
        this.index = displayedIndex + ". ";
        this.title = title;
        this.ownerName = ownerName;
        this.endTime = endTime;
        this.ownerId = ownerId;
        this.id = id;

        this.isFirstStage = isFirstStage;
        isForCenter = false;
        setStyle();
    }

    public NotificationCardStub(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, boolean isForCenter, String id) {
        this.index = displayedIndex + ". ";
        this.title = title;
        this.ownerName = ownerName;
        this.endTime = endTime;
        this.ownerId = ownerId;
        this.id = id;

        this.isFirstStage = isFirstStage;
        isForCenter = isForCenter;
        setStyle();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotificationCardStub)) {
            return false;
        }

        // state check
        NotificationCardStub card = (NotificationCardStub) other;
        return index.equals(card.getIndex())
                && title.equals(card.title)
                && ownerName.equals(card.ownerName)
                && endTime.equals(card.endTime);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String getIndex() {
        return index;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setStyle() {

    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Title: " + title + " Owner: " + ownerName;
    }

    /**
     * Decreases the index displayed on notification card.
     */
    public void decreaseIndex(int i) {
        String currIndex = this.index;
        int j;
        for (j = 0; j < currIndex.length(); j++) {
            if (currIndex.charAt(j) == '.') {
                break;
            }
        }
        int currIndexNumeric = Integer.parseInt(currIndex.substring(0, j));
        this.index = (currIndexNumeric - i) + ". ";
    }
}
```
