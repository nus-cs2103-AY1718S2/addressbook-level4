# IzHoBX
###### \java\seedu\address\commons\events\logic\AddressBookUnlockedEvent.java
``` java
package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to toggle Notification Center
 */
public class AddressBookUnlockedEvent extends BaseEvent {
    public AddressBookUnlockedEvent() {
        super();
    }

    @Override
    public String toString() {
        return "Employees Tracker unlocked!";
    }
}
```
###### \java\seedu\address\commons\events\logic\RequestToDeleteNotificationEvent.java
``` java
package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates timetable entry added/removed*/
public class RequestToDeleteNotificationEvent extends BaseEvent {

    public final String id;
    public final boolean deleteFromAddressbookOnly;

    public RequestToDeleteNotificationEvent(String id, boolean deleteFromAddressbookOnly) {
        this.id = id;
        this.deleteFromAddressbookOnly = deleteFromAddressbookOnly;
    }

    @Override
    public String toString() {
        return "timetable entry deleted: " + id;
    }
}
```
###### \java\seedu\address\commons\events\model\NotificationAddedEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.notification.Notification;

/**
 * Indicates timetable entry added/removed*/
public class NotificationAddedEvent extends BaseEvent {

    public final Notification notification;

    public NotificationAddedEvent(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "timetable entry added: " + notification.toString();
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowNotificationEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.notification.Notification;

/**
 * This event is raised when we need to display notification in Windows 10 notification tray
 */
public class ShowNotificationEvent extends BaseEvent {
    private String ownerName;
    private Notification notification;
    private boolean isFirstSatge;

    public ShowNotificationEvent(String ownerName, Notification notification) {
        this.ownerName = ownerName;
        this.notification = notification;
        isFirstSatge = false;
    }

    public ShowNotificationEvent(String ownerName, Notification notification, boolean isFirstSatge) {
        this.ownerName = ownerName;
        this.notification = notification;
        this.isFirstSatge = isFirstSatge;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Notification getNotification() {
        return notification;
    }

    @Override
    public String toString() {
        return "ShowNotificationEvent: " + notification.toString();
    }

    public boolean isFirstSatge() {
        return isFirstSatge;
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowSuggestionEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a new suggestion is available.
 */
public class ShowSuggestionEvent extends BaseEvent {
    private String suggestion;

    public ShowSuggestionEvent(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    @Override
    public String toString() {
        return null;
    }
}
```
###### \java\seedu\address\commons\events\ui\ToggleNotificationCenterEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to toggle Notification Center
 */
public class ToggleNotificationCenterEvent extends BaseEvent {
    public ToggleNotificationCenterEvent() {
        super();
    }

    @Override
    public String toString() {
        return "Toggling Notification Center";
    }
}
```
###### \java\seedu\address\logic\commands\DismissCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.notification.exceptions.NotificationNotFoundException;
import seedu.address.ui.NotificationCard;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DismissCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "dismiss";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Dismisses the notification card identified by the index number used in Notification Center.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DISMISS_SUCCESS = "Dismissed notification card: %1$s";
    public static final String MESSAGE_ERROR = "Error occurred. Please try again later.";

    private final Index targetIndex;

    public DismissCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        NotificationCard toDelete;
        try {
            toDelete = model.deleteNotificationByIndex(targetIndex);
        } catch (NotificationNotFoundException e) {
            //should not happen, because id is obtained from NotificationCenter
            LogsCenter.getLogger(DismissCommand.class).info("No local copy of notification in AddressBook");
            return new CommandResult(MESSAGE_ERROR);
        }
        return new CommandResult(String.format(MESSAGE_DISMISS_SUCCESS, toDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (targetIndex.getZeroBased() >= model.getNotificationCenter().getTotalUndismmissedNotificationCards() - 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DismissCommand // instanceof handles nulls
                && this.targetIndex.equals(((DismissCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }
```
###### \java\seedu\address\logic\commands\EmailAllCommand.java
``` java
package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;

/**
 * Email to the person associated with the notification card identified using index in Notification Center.
 */
public class EmailAllCommand extends Command {

    public static final String COMMAND_WORD = "emailall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails to remind about all notification cards.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Directing to email app...";
    public static final String MESSAGE_ERROR = "Error occurred. Please try again later.";

    public static final String EMAIL_TEMPLATE = "Dear%20all,"
            + "%0D%0A%0D%0AThis%20email%20is%20to%20remind%20you%20that%20you%20have%20a%20task%20expired.%0D%0A%0D%0A";

    private String allEmails = "";

    @Override
    public CommandResult execute() {
        //notificationCards in notification center is 1-based
        for (int i = 1; i < model.getNotificationCenter().getTotalUndismmissedNotificationCards(); i++) {
            Index targetIndex = Index.fromOneBased(i);
            String ownerId = model.getNotificationCenter().getOwnerIdByIndex(targetIndex);
            allEmails += model.getAddressBook().findPersonById(Integer.parseInt(ownerId)).getEmail().toString() + ",";
        }

        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:" + allEmails + "?subject=" + "Reminder%20for%20Task%20Expiry"
                + "&body=" + EMAIL_TEMPLATE;
        URI uri = URI.create(message);
        try {
            desktop.mail(uri);
        } catch (IOException e) {
            LogsCenter.getLogger(EmailAllCommand.class).info("IOException");
            return new CommandResult(MESSAGE_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommand.java
``` java
package seedu.address.logic.commands;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.ui.NotificationCard;

/**
 * Email to the person associated with the notification card identified using index in Notification Center.
 */
public class EmailCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Emails to remind the corresponding employee "
            + "about the notification card identified by the index number "
            + "used in Notification Center.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Directing to email app...";
    public static final String MESSAGE_ERROR = "Error occurred. Please try again later.";

    public static final String[] EMAIL_TEMPLATE = {"Dear%20",
        ",%0D%0A%0D%0AThis%20email%20is%20to%20remind%20you%20about%20",
        "%20happening%20at%20",
        ".%0D%0A%0D%0A"};

    private final Index targetIndex;

    private Person owner;

    private NotificationCard notificationCard;

    public EmailCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:" + owner.getEmail().toString() + "?subject=" + "Reminder%20for%20"
                + replaceSpaceWithHexa(notificationCard.getTitle()) + "&body="
                + EMAIL_TEMPLATE[0] + replaceSpaceWithHexa(notificationCard.getOwnerName())
                + EMAIL_TEMPLATE[1] + replaceSpaceWithHexa(notificationCard.getTitle())
                + EMAIL_TEMPLATE[2] + replaceSpaceWithHexa(notificationCard.getEndTime())
                + EMAIL_TEMPLATE[3];
        URI uri = URI.create(message);
        try {
            desktop.mail(uri);
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        if (targetIndex.getZeroBased() >= model.getNotificationCenter().getTotalUndismmissedNotificationCards() - 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX);
        }
        notificationCard = model.getNotificationCenter().getNotificationCard(targetIndex);
        String ownerId = model.getNotificationCenter().getOwnerIdByIndex(targetIndex);
        owner = model.getAddressBook().findPersonById(Integer.parseInt(ownerId));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.targetIndex.equals(((EmailCommand) other).targetIndex)); // state check
    }

    /**
     * Replaces all space characters with URI space character
     */
    public String replaceSpaceWithHexa(String input) {
        String[] parts = input.split(" ");
        String toReturn = "";
        for (int i = 0; i < parts.length; i++) {
            toReturn += parts[i];
            toReturn += "%20";
        }
        return toReturn;
    }
}
```
###### \java\seedu\address\logic\commands\NotiCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleNotificationCenterEvent;

/**
 * Toggles the notification center.
 */
public class NotiCommand extends Command {

    public static final String COMMAND_WORD = "noti";

    public static final String MESSAGE_SUCCESS = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Toggles the notification center, or you can double press SHIFT.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ToggleNotificationCenterEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\RateCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rating;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Updates the rating of an existing person in the address book.
 */
public class RateCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rate";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Rates the employee identified "
            + "by the index number used in the last employees listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX must be positive integer "
            + "RATING (must be 1, 2, 3, 4, or 5) \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "5";

    public static final String MESSAGE_RATE_PERSON_SUCCESS = "Rated employee: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This employee already exists in Employees Tracker.";

    private final Index index;
    private final EditCommand.EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public RateCommand(Index index, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditCommand.EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target employee cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RATE_PERSON_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
                                             EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Rating updatedRating = editPersonDescriptor.getRating().orElse(new Rating());

```
###### \java\seedu\address\logic\commands\RateCommand.java
``` java

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RateCommand)) {
            return false;
        }

        // state check
        RateCommand e = (RateCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }
}
```
###### \java\seedu\address\logic\commands\TestAddEventCommand.java
``` java
        if (calendarId == null || calendarId.equals("") || calendarId.equals("null")) {
            logger.info("calendarId null, attempting to create calendar");
            try {
                calendarId = CreateNewCalendar.execute(personToAddEvent.getName().fullName);
                logger.info("calendar created successfully");
            } catch (IOException e) {
                logger.info("unable to create calendar");
                return new CommandResult(MESSAGE_FAILURE);
            }
            Person newWithCalendar = new Person(personToAddEvent.getName(),
                    personToAddEvent.getPhone(),
                    personToAddEvent.getEmail(),
                    personToAddEvent.getAddress(),
                    personToAddEvent.getTags(),
                    calendarId);
            //retain the oldId
            newWithCalendar.setId(personToAddEvent.getId());

            try {
                model.updatePerson(personToAddEvent, newWithCalendar);
            } catch (PersonNotFoundException e) {
                logger.info("Unable to find original employee in model manager");
                return new CommandResult(MESSAGE_FAILURE);
            } catch (DuplicatePersonException e) {
                logger.info("newly created employee (with calendarId) is same as original employee");
                return new CommandResult(MESSAGE_FAILURE);
            }
        }
```
###### \java\seedu\address\logic\commands\TestAddEventCommand.java
``` java
        Notification notification = new Notification(title, calendarId, event.getId(), event.getEnd().toString(),
                model.getPerson(targetIndex.getZeroBased()).getId().toString());
        model.addNotification(notification);
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Subscribe
    private void handleTimetableEntryAddedEvent(NotificationAddedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        NotificationTime parsedTime = NotificationTimeParserUtil.parseTime(event.notification.getEndDate());
        Calendar c = Calendar.getInstance();
        c.set(parsedTime.getYear(), parsedTime.getMonth(), parsedTime.getDate(), parsedTime.getHour(),
                parsedTime.getMinute());
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (timetableEntriesStatus.get(this)) {
                    System.out.println("An event ended at: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format
                            (Calendar.getInstance().getTimeInMillis()));
                } else {
                    System.out.println("A cancelled event ended at: " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                            .format(Calendar.getInstance().getTimeInMillis()));
                }
                Notification notification = timerTaskToTimetableEntryMap.get(this);
                String ownerName;
                try {
                    ownerName = ((ModelManager) model).getNameById(notification.getOwnerId());
                    raise(new ShowNotificationEvent(ownerName, notification));
                } catch (NullPointerException e) {
                    logger.info("Corresponding employee is deleted. Ignoring this notification");
                    raise(new RequestToDeleteNotificationEvent(notification.getEventId(), true));
                }
            }
        };
        timetableEntriesStatus.put(task, true);
        scheduledTimerTasks.put(event.notification.getEventId(), task);
        timerTaskToTimetableEntryMap.put(task, event.notification);
        System.out.println("An event scheduled at " + c.getTime() + " " + (c.getTimeInMillis() - System
                .currentTimeMillis()));
        long duration = c.getTimeInMillis() - System.currentTimeMillis();
        if (duration >= 0) {
            if (parsedTime.isToday()) {
                String ownerName = ((ModelManager) model).getNameById(event.notification.getOwnerId());
                raise(new ShowNotificationEvent(ownerName, event.notification, true));
            }
            timer.schedule(task, duration);
        } else {
            task.run();
        }
    }

    @Subscribe
    private void handleTimetableEntryDeletedEvent(RequestToDeleteNotificationEvent event) {
        TimerTask associatedTimerTask = scheduledTimerTasks.get(event.id);
        timetableEntriesStatus.put(associatedTimerTask, false);
        scheduledTimerTasks.remove(event.id);
    }

    public void setNotificationCenter(NotificationCenter notificationCenter) {
        this.model.setNotificationCenter(notificationCenter);
    }

```
###### \java\seedu\address\logic\parser\DismissCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NOTIFICATION_CARD_INDEX_NON_POSITIVE;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DismissCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DismissCommand object
 */
public class DismissCommandParser implements Parser<DismissCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DismissCommand
     * and returns an DismissCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DismissCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DismissCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(MESSAGE_INVALID_NOTIFICATION_CARD_INDEX_NON_POSITIVE);
        }
    }
}
```
###### \java\seedu\address\logic\parser\EmailCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DismissCommand object
 */
public class EmailCommandParser implements Parser<EmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EmailCommand
     * and returns an EmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EmailCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EmailCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\RateCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Scanner;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Rating;

/**
 * Parses input arguments and creates a new RateCommand object
 */
public class RateCommandParser implements Parser<RateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RateCommand
     * and returns an RateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Scanner sc = new Scanner(args);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }
        Integer rating = sc.nextInt();

        if (!Rating.isValidInputRating(rating)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE));
        }

        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        editPersonDescriptor.setRating(new Rating(rating.toString()));

        return new RateCommand(index, editPersonDescriptor);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setNotificationsList(LinkedList<Notification> notifications) {
        this.notifications = notifications;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
        if (!person.isInitialized()) {
            person.setId(nextId);
            nextId++;
        }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    // timetable entry level operations
    /**
     * Adds a notification to the address book.
     */
    public void addNotification(Notification notification) {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifications.add(notification);
        semaphore.release();
    }

    /**
     * Removes a timetable entry from the address book.
     */
    public void deleteNotification(String notificationId) throws NotificationNotFoundException {
        boolean found = false;
        LinkedList<Notification> toDelete = new LinkedList<>();
        for (Notification n: notifications) {
            if (n.getEventId().equals(notificationId)) {
                toDelete.add(n);
                found = true;
            }
        }
        if (found) {
            for (Notification n: toDelete) {
                notifications.remove(n);
            }
        } else {
            throw new NotificationNotFoundException();
        }
    }

    /**
     * Removes all notifications associated with a person id.
     */
    private void removeNotificationsWithOwnerId (Integer id) {
        LinkedList<Notification> toDelete = new LinkedList<>();
        for (Notification n: notifications) {
            if (n.getOwnerId().equals(id + "")) {
                toDelete.add(n);
            }
        }
        for (Notification n: toDelete) {
            notifications.remove(n);
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Returns a person with the given id.
     *
     * @param id must be a valid id.
     */
    public Person findPersonById(int id) {
        for (Person p: persons) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java

}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Deletes a timetable entry given its id. */
    void deleteNotification(String id, boolean deleteFromAddressBookOnly) throws NotificationNotFoundException;

    /** Adds the given person */
    void addNotification(Notification e);
```
###### \java\seedu\address\model\Model.java
``` java

```
###### \java\seedu\address\model\ModelManager.java
``` java

    /**
     * Ensures Notification Center is non-null. Attempts to assign notification center if so.
     */
    private void ensureNotificationCenterNonNull() {
        if (notificationCenter == null) {
            raise(new RequestForNotificationCenterEvent());
        }
    }
    @Override
    public synchronized void deleteNotification(String id, boolean deleteFromAddressBookOnly) throws
            NotificationNotFoundException {
        try {
            addressBook.deleteNotification(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!deleteFromAddressBookOnly) {
            try {
                ensureNotificationCenterNonNull();
                notificationCenter.deleteNotification(id);
            } catch (NullPointerException e) {
                logger.info("NullPointerException encountered when deleting notification for deleted employee");
            }
        }
        indicateAddressBookChanged();
    }

    @Override
    public NotificationCard deleteNotificationByIndex(Index targetIndex) throws NotificationNotFoundException {
        ensureNotificationCenterNonNull();
        addressBook.deleteNotification(notificationCenter.getIdByIndex(targetIndex));
        indicateAddressBookChanged();
        NotificationCard toDelete = notificationCenter.deleteNotificationByIndex(targetIndex);
        return toDelete;
    }

    private void indicateNotificationAdded(Notification e) {
        raise(new NotificationAddedEvent(e));
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void addNotification(Notification e) {
        addressBook.addNotification(e);
        indicateAddressBookChanged();
        indicateNotificationAdded(e);
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    public String getNameById(String id) {
        return addressBook.findPersonById(Integer.parseInt(id)).getName().toString();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Subscribe
    private void handleRequestToDeleteNotificationEvent(RequestToDeleteNotificationEvent event) {
        try {
            deleteNotification(event.id, event.deleteFromAddressbookOnly);
        } catch (NotificationNotFoundException e) {
            e.printStackTrace();
            logger.info("Notification is not stored locally");
        }
    }

    @Override
    public void findAllSavedNotifications() {

        //schedule all notification
        for (Notification n: getAddressBook().getNotificationsList()) {
            System.out.println("Scheduling all notification");
            indicateNotificationAdded(n);
        }
    }

    public void setNotificationCenter(NotificationCenter notificationCenter) {
        assert(notificationCenter != null);
        this.notificationCenter = notificationCenter;
    }

    public NotificationCenter getNotificationCenter() {
        return  notificationCenter;
    }

```
###### \java\seedu\address\model\notification\exceptions\NotificationNotFoundException.java
``` java
package seedu.address.model.notification.exceptions;

/**
 * Signals that the operation is unable to find the specified person.
 */
public class NotificationNotFoundException extends Exception {}
```
###### \java\seedu\address\model\notification\Notification.java
``` java
package seedu.address.model.notification;

import seedu.address.model.person.Person;

/**
 * Represents a timetable entry added to Employees Tracker
 */
public class Notification {
    private String title;
    private String calendarId;
    private String eventId;
    private String endDate;
    private String ownerId;

    public Notification(String title, String calendarId, String eventId, String endDate, String ownerId) {
        assert(title != null && eventId != null && endDate != null && ownerId != null && !ownerId.equals(Person
                .UNINITIALISED_ID + ""));
        this.title = title;
        this.calendarId = calendarId;
        this.eventId = eventId;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title;
    }

    public String toDisplayString() {
        return "Event " + getTitle() + " ended at " + getEndDateDisplay();
    }

    public String getEndDateDisplay() {
        return getEndDate().substring(13, 23) + " " + getEndDate().substring(24, 32);
    }

    @Override
    public String toString() {
        return title;
    }
}
```
###### \java\seedu\address\model\notification\NotificationTime.java
``` java
package seedu.address.model.notification;

import java.util.Calendar;

/**
 * Container for different time fields
 */
public class NotificationTime {
    private int year;
    private int month;
    private int date;
    private int hour;
    private int minute;
    private int seconds;

    public NotificationTime(int year, int month, int date, int hour, int minute, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.seconds = seconds;

    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSeconds() {
        return seconds;
    }

    /**
     * Checks if the date contained refers to today's date
     */
    public boolean isToday() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return this.year == year && this.month == month && this.date == day;
    }
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    public Rating getRating() {
        return rating;
    }

    public String getRatingDisplay() {
        return rating.getRatingDisplay();
    }
```
###### \java\seedu\address\model\person\Rating.java
``` java
package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import com.vdurmont.emoji.EmojiParser;

/**
 * Represents a Person's rating in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {
    public static final String MESSAGE_RATING_CONSTRAINTS = "Rating must be 1, 2, 3, 4 or 5";
    public static final String RATING_VALIDATION_REGEX = "-?\\d*";
    public static final int DEFAULT_NULL_RATING = -1;
    public static final String INVALID_RATING_DISPLAY = "-";
    public static final String RATING_DISPLAY = ":star2: ";
    public static final int MAX_RATING = 5;

    public final Integer value;

    /**
     * Constructs a {@code Rating} for a new person who hasn't been assigned a rating.
     */
    public Rating() {
        value = DEFAULT_NULL_RATING;
    }

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        checkArgument(isValidRating(rating), MESSAGE_RATING_CONSTRAINTS);
        value = Integer.parseInt(rating);
    }

    /**
     * Returns true if a given string is a valid person rating.
     */
    public static boolean isValidRating(String test) {
        return test.matches(RATING_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid input of person rating.
     */
    public static boolean isValidInputRating(int test) {
        return test > 0 && test <= MAX_RATING;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof Rating) // instanceof handles nulls
                && this.value == ((Rating) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public String getRatingDisplay() {
        if (value == -1) {
            return INVALID_RATING_DISPLAY;
        } else {
            return convertRatingToStars(value);
        }
    }

    /**
     * Converts numerical rating into respective number of stars
     */
    private String convertRatingToStars(int rating) {
        StringBuilder sb = new StringBuilder();
        while (rating-- > 0) {
            sb.append(RATING_DISPLAY);
        }
        return EmojiParser.parseToUnicode(sb.toString());
    }
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the notification list.
     */
    LinkedList<Notification> getNotificationsList();

    int getNextId();
```
###### \java\seedu\address\storage\NotificationTimeParserUtil.java
``` java
package seedu.address.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.notification.NotificationTime;

/**
 * Parses timetable endtime to separated fields of time.
 */
public class NotificationTimeParserUtil {
    private static final int YEAR_BEGIN_INDEX = 0;
    private static final int MONTH_BEGIN_INDEX = 5;
    private static final int DAY_BEGIN_INDEX = 8;
    private static final int HOUR_BEGIN_INDEX = 11;
    private static final int MINUTE_BEGIN_INDEX = 14;
    private static final int SECOND_BEGIN_INDEX = 17;

    private static final int YEAR_END_INDEX = 4;
    private static final int MONTH_END_INDEX = 7;
    private static final int DAY_END_INDEX = 10;
    private static final int HOUR_END_INDEX = 13;
    private static final int MINUTE_END_INDEX = 16;
    private static final int SECOND_END_INDEX = 19;

    //the menu of Month in Calendar is zero based
    private static final int MONTH_INDEX_OFFSET = -1;
    private static final int TIMEZONE_HOUR_OFFSET = 0;

    /**
     * Parses the input time string into time fields.
     *
     * @params input that a string containing time information in the format:
     * Default format: {"dateTime":"YYYY-MM-DDTHH:MM:SS.000+08:00timeZone:"Asia/Singapore"}
     * Acceptable format: YYYY-MM-DDTHH:MM:SS
     *
     * @return NotificationTime containing the parsed time fields.
     */
    public static NotificationTime parseTime(String input) {
        int firstIntegerOffset = findFirstIntegerOffset(input);
        assert(firstIntegerOffset != -1 && SECOND_END_INDEX + firstIntegerOffset <= input.length());
        try {
            NotificationTime tet = new NotificationTime(Integer.parseInt(input.substring(YEAR_BEGIN_INDEX
                            + firstIntegerOffset,
                    YEAR_END_INDEX + firstIntegerOffset)),
                    Integer.parseInt(input.substring(MONTH_BEGIN_INDEX + firstIntegerOffset, MONTH_END_INDEX
                            + firstIntegerOffset)) + MONTH_INDEX_OFFSET,
                    Integer.parseInt(input.substring(DAY_BEGIN_INDEX + firstIntegerOffset,
                            DAY_END_INDEX + firstIntegerOffset)),
                    Integer.parseInt(input.substring(HOUR_BEGIN_INDEX + firstIntegerOffset,
                            HOUR_END_INDEX + firstIntegerOffset)) + TIMEZONE_HOUR_OFFSET,
                    Integer.parseInt(input.substring(MINUTE_BEGIN_INDEX + firstIntegerOffset,
                            MINUTE_END_INDEX + firstIntegerOffset)),
                    Integer.parseInt(input.substring(SECOND_BEGIN_INDEX + firstIntegerOffset,
                            SECOND_END_INDEX + firstIntegerOffset)));
            return tet;
        } catch (NumberFormatException e) {
            LogsCenter.getLogger(NotificationTimeParserUtil.class).info("Time format in notification mismatch");
            assert(false);
        }
        //should not be returned
        return null;
    }

    /**
     * Finds the position of the first integer character
     */
    private static int findFirstIntegerOffset(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) - '0' >= 0 && input.charAt(i) - '0' <= 9) {
                return i;
            }
        }
        return -1;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedNotification.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.notification.Notification;

/**
 * JAXB-friendly adapted version of the Notification.
 */
public class XmlAdaptedNotification {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String calendarId;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String ownerId;
    @XmlElement(required = true)
    private String eventId;

    /**
     * Constructs an XmlAdaptedNotification.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedNotification() {}

    /**
     * Constructs a {@code XmlAdaptedNotification} with the given timetable entry details.
     */
    public XmlAdaptedNotification(String title, String calendarId, String eventId, String endDate, String ownerId) {
        this.title = title;
        this.calendarId = calendarId;
        this.eventId = eventId;
        this.endDate = endDate;
        this.ownerId = ownerId;
    }

    /**
     * Converts a given Notification into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedNotification(Notification source) {
        this.title = title;
        this.calendarId = source.getCalendarId();
        this.endDate = source.getEndDate();
        this.ownerId = source.getOwnerId();
        this.eventId = source.getEventId();
    }

    /**
     * Converts this jaxb-friendly adapted timetable entry object into the model's Notification object.
     *
     */
    public Notification toModelType() {
        return new Notification(title, calendarId, eventId, endDate, ownerId);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        if (this.rating == null) {
            this.rating = (new Rating()).toString();
        }
        if (!Rating.isValidRating(this.rating)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }

        final Rating rating = new Rating(this.rating);
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        addressBook.setNextId(nextId);
        addressBook.setPassword(password);
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }

        XmlSerializableAddressBook otherAb = (XmlSerializableAddressBook) other;
        return persons.equals(otherAb.persons)
                && tags.equals(otherAb.tags)
                && photos.equals(otherAb.photos)
                && notifications.equals(otherAb.notifications);
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                raiseSuggestionEventIfMatchesCommandWord(newValue);
            }
        });
    }

    /**
     * Raises a ShowSuggestionEvent if the {@param newValue } matches one of command word.
     */
    private void raiseSuggestionEventIfMatchesCommandWord(String newValue) {
        try {
            newValue = (new Scanner(newValue)).next();
        } catch (NoSuchElementException e) {
            if (LogicManager.isLocked()) {
                raise(new ShowSuggestionEvent(ResultDisplay.WELCOME_MESSAGE));
            } else {
                raise(new ShowSuggestionEvent(ResultDisplay.SUGGEST_HELP_MESSAGE));
            }
            return;
        }
        boolean found = false;
        for (int i = 0; i < allCommandsWord.length; i++) {
            if (newValue.equals(allCommandsWord[i])) {
                raise(new ShowSuggestionEvent(allCommandsUsage[i]));
                found = true;
                break;
            }
        }
        if (!found) {
            raise(new ShowSuggestionEvent(""));
        }
    }
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case SHIFT:
            registerShiftPressed(keyEvent);
            if (consecutiveShiftPressed.size() == 2) {
                resetWaitForSecondShift();
                raise(new ToggleNotificationCenterEvent());
            }
            break;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
    /**
     * Records SHIFT key has been registered and waits for the next SHIFT.
     */
    private void registerShiftPressed(KeyEvent keyEvent) {
        consecutiveShiftPressed.offer(keyEvent);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                resetWaitForSecondShift();
            }
        }, DOUBLE_KEY_TOLERANCE);
    }

    /**
     * Stops the wait for the second consecutive SHIFT (for double SHIFT keyEvent) and reset the metadata
     */
    private void resetWaitForSecondShift() {
        for (KeyEvent ke: consecutiveShiftPressed) {
            ke.consume();
        }
        consecutiveShiftPressed.clear();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        shownNotificationCards = new LinkedList<>();
        notificationCenter = new NotificationCenter(notificationCardsBox, notificationCenterPlaceHolder);
        logic.setNotificationCenter(notificationCenter);
        mainStage.getChildren().remove(notificationCenterPlaceHolder);
        notificationCenterStatus = HIDE;
        semaphore = new Semaphore(1);
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        detailPanel = new DetailPanel();
        browserPlaceholder.getChildren().add(detailPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the default size based on user preferences.
     */
    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    /**
     * Returns the current size and the position of the main Window.
     */
    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), Theme.getTheme());
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Show in-app notification
     */
    public void showNewNotification(ShowNotificationEvent event) {
        logger.info("Preparing in app notification");

        //metadata update
        NotificationCard x = new NotificationCard(event.getNotification().getTitle(),
                notificationCenter.getTotalUndismmissedNotificationCards() + "",
                event.getOwnerName(),
                event.getNotification().getEndDateDisplay(),
                event.getNotification().getOwnerId(), event.isFirstSatge(), event.getNotification().getEventId());
        Region notificationCard = x.getRoot();
        notificationCard.setMaxHeight(NOTIFICATION_CARD_HEIGHT);
        notificationCard.setMaxWidth(NOTIFICATION_CARD_WIDTH);
        notificationCenter.add(x);

        //hides notificationCard away from screen
        notificationCard.setTranslateX(NOTIFICATION_CARD_WIDTH);
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notificationCard.setTranslateY(UP * shownNotificationCards.size() * NOTIFICATION_CARD_HEIGHT);
        shownNotificationCards.add(notificationCard);
        semaphore.release();

        //enter animation
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mainStage.getChildren().add(notificationCard);
                animateHorizontally(notificationCard, NOTIFICATION_CARD_WIDTH, ENTER);

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        //it should be the first notification card to exit first
                        try {
                            semaphore.acquire();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Region firstNotificationCard = shownNotificationCards.removeFirst();

                        //cards are reused later in notification center
                        animateHorizontally(firstNotificationCard, NOTIFICATION_CARD_WIDTH, EXIT);
                        moveAllNotificationCardsDown();
                        semaphore.release();
                    }
                };
                timer.schedule(timerTask, NOTIFICATION_CARD_SHOW_TIME);
            }
        });
    }

    private void moveAllNotificationCardsDown() {
        for (Region r: shownNotificationCards) {
            animateVertically(r, NOTIFICATION_CARD_HEIGHT, DOWN);
        }
    }

    /**
     * Animates any Region object vertically according to predefined style.
     */
    private void animateVertically(Region r, int distanceToMove, int direction) {
        TranslateTransition enterAnimation = new TranslateTransition(Duration.millis(250), r);
        enterAnimation.setByY(direction * 0.25 * distanceToMove);
        enterAnimation.play();
        TranslateTransition enterAnimation1 = new TranslateTransition(Duration.millis(250), r);
        enterAnimation1.setByY(direction * 0.75 * distanceToMove);
        enterAnimation1.play();
        TranslateTransition enterAnimation2 = new TranslateTransition(Duration.millis(250), r);
        enterAnimation2.setByY(direction * distanceToMove);
        enterAnimation2.play();
    }

    /**
     * Animates any Region object horizontally according to predefined style.
     */
    private void animateHorizontally(Region component, double width, int direction) {
        TranslateTransition enterAnimation = new TranslateTransition(Duration.millis(250), component);
        enterAnimation.setByX(direction * 0.25 * width);
        enterAnimation.play();
        TranslateTransition enterAnimation1 = new TranslateTransition(Duration.millis(250), component);
        enterAnimation1.setByX(direction * 0.75 * width);
        enterAnimation1.play();
        TranslateTransition enterAnimation2 = new TranslateTransition(Duration.millis(250), component);
        enterAnimation2.setByX(direction * width);
        enterAnimation2.play();
    }

    /**
     * Show the notification panel with an animation
     */
    public void toggleNotificationCenter() {
        if (notificationCenterStatus == SHOW) {
            animateHorizontally(notificationCenterPlaceHolder, NOTIFICATION_PANEL_WIDTH, EXIT);
            mainStage.getChildren().remove(notificationCenterPlaceHolder);
            notificationCenterStatus = HIDE;
        } else { //shows
            assert(notificationCenterStatus == HIDE);
            notificationCenterPlaceHolder = notificationCenter.getNotificationCenter();
            mainStage.getChildren().add(notificationCenterPlaceHolder);
            animateHorizontally(notificationCenterPlaceHolder, NOTIFICATION_PANEL_WIDTH, ENTER);
            notificationCenterStatus = SHOW;
        }
    }

    public void deleteNotificationCard(String id) {
        notificationCenter.deleteNotification(id);
    }

    @Subscribe
    protected void provideNotificationCenter(RequestForNotificationCenterEvent event) {
        logic.setNotificationCenter(notificationCenter);
    }
```
###### \java\seedu\address\ui\NotificationCard.java
``` java
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * An UI component that displays information of a {@code Notification}.
 */
public class NotificationCard extends UiPart<Region> {


    public static final int NOTIFICATION_CARD_X_OFFSET = 15;
    public static final int NOTIFICATION_CARD_Y_OFFSET = 15;
    public static final int NOTIFICATION_CARD_WIDTH = 300 + NOTIFICATION_CARD_X_OFFSET;
    public static final int NOTIFICATION_CARD_HEIGHT = 100 + NOTIFICATION_CARD_Y_OFFSET;
    private static final String FXML = "NotificationCard.fxml";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    protected boolean isFirstStage;
    @FXML
    private Label title;
    @FXML
    private Label index;
    @FXML
    private Label ownerName;
    @FXML
    private Label endTime;
    @FXML
    private VBox xOffset;
    @FXML
    private VBox yOffset;
    @FXML
    private GridPane content;

    private String ownerId;
    private boolean isForCenter;
    private String id;

    public NotificationCard(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, String id) {
        super(FXML);
        this.index.setText(displayedIndex + ". ");
        this.title.setText(title);
        this.ownerName.setText(ownerName);
        this.endTime.setText(endTime);
        this.ownerId = ownerId;
        this.id = id;

        xOffset.setMaxWidth(NOTIFICATION_CARD_X_OFFSET);
        yOffset.setMaxWidth(NOTIFICATION_CARD_Y_OFFSET);
        this.isFirstStage = isFirstStage;
        isForCenter = false;
        setStyle();
    }

    public NotificationCard(String title, String displayedIndex, String ownerName, String endTime, String ownerId,
                            boolean isFirstStage, boolean isForCenter, String id) {
        super(FXML);
        this.index.setText(displayedIndex);
        this.title.setText(title);
        this.ownerName.setText(ownerName);
        this.endTime.setText(endTime);
        this.ownerId = ownerId;
        this.id = id;

        xOffset.setMaxWidth(NOTIFICATION_CARD_X_OFFSET);
        yOffset.setMaxWidth(NOTIFICATION_CARD_Y_OFFSET);
        this.isFirstStage = isFirstStage;
        this.isForCenter = isForCenter;
        setStyle();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NotificationCard)) {
            return false;
        }

        // state check
        NotificationCard card = (NotificationCard) other;
        return index.getText().equals(card.index.getText())
                && title.equals(((NotificationCard) other).title)
                && ownerName.equals(((NotificationCard) other).ownerName)
                && endTime.equals(((NotificationCard) other).endTime);
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getTitle() {
        return title.getText();
    }

    public String getIndex() {
        return index.getText();
    }

    public String getOwnerName() {
        return ownerName.getText();
    }

    public String getEndTime() {
        return endTime.getText();
    }

    public void setStyle() {
        if (!isForCenter) {
            if (isFirstStage) {
                content.getStyleClass().add("notification-card-first-stage");
            } else {
                content.getStyleClass().add("notification-card-second-stage");
            }
        } else {
            if (isFirstStage) {
                content.getStyleClass().add("notification-card-notification-center-first-stage");
            } else {
                content.getStyleClass().add("notification-card-notification-center-second-stage");
            }
        }
    }

    public NotificationCard getCopyForCenter() {
        return new NotificationCard(this.getTitle(), this.getIndex(), this.getOwnerName(), this.getEndTime(), this
                .getOwnerId(), isFirstStage, true, id);
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Title: " + title.getText() + " Owner: " + ownerName.getText();
    }

    /**
     * Decreases the index displayed on notification card.
     */
    public void decreaseIndex(int i) {
        String currIndex = this.index.getText();
        int j;
        for (j = 0; j < currIndex.length(); j++) {
            if (currIndex.charAt(j) == '.') {
                break;
            }
        }
        int currIndexNumeric = Integer.parseInt(currIndex.substring(0, j));
        this.index.setText((currIndexNumeric - i) + ". ");
    }
}
```
###### \java\seedu\address\ui\NotificationCenter.java
``` java
package seedu.address.ui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;

/**
 * Encapsulates all the information and functionalities required for Notification Center.
 */
public class NotificationCenter {
    private static final int NOTIFICATION_CENTER_WIDTH = NotificationCard.NOTIFICATION_CARD_WIDTH
            + NotificationCard.NOTIFICATION_CARD_X_OFFSET * 3;
    private static final int NOTIFICATION_CARD_HEIGHT_IN_CENTER = NotificationCard.NOTIFICATION_CARD_HEIGHT;
    private static final int NOTIFICATION_CARD_WIDTH_IN_CENTER = NotificationCard.NOTIFICATION_CARD_WIDTH;
    protected LinkedList<javafx.scene.layout.Region> notificationCards;
    protected LinkedList<NotificationCard> notificationCardCopy;
    protected HashMap<String, LinkedList<javafx.scene.layout.Region>> idToCard;

    @FXML
    private VBox notificationCardsBox;

    @FXML
    private ScrollPane notificationCenterPlaceHolder;

    public NotificationCenter() {

    }

    public NotificationCenter(VBox notificationCardsBox,
                              javafx.scene.control.ScrollPane notificationCenterPlaceHolder) {
        notificationCards = new LinkedList<>();
        idToCard = new HashMap<>();
        notificationCards.add(null);
        //for 1 based index
        notificationCardCopy = new LinkedList<>();
        notificationCardCopy.add(null);
        this.notificationCardsBox = notificationCardsBox;
        this.notificationCenterPlaceHolder = notificationCenterPlaceHolder;
        setWidth();
        hideNotificationCenter();
        setPadding();
    }

    private void hideNotificationCenter() {
        notificationCenterPlaceHolder.setTranslateX(NOTIFICATION_CENTER_WIDTH);
    }

    private void setWidth() {
        notificationCenterPlaceHolder.setMaxWidth(NOTIFICATION_CENTER_WIDTH);
        notificationCardsBox.setMaxWidth(NOTIFICATION_CENTER_WIDTH);
    }

    private void setPadding() {
        notificationCardsBox.setPadding(new Insets(NotificationCard.NOTIFICATION_CARD_Y_OFFSET, 0, 0,
                NotificationCard.NOTIFICATION_CARD_X_OFFSET));
    }

    public int getWidth() {
        return NOTIFICATION_CENTER_WIDTH;
    }

    public LinkedList<javafx.scene.layout.Region> getNotificationCards() {
        return notificationCards;
    }

    public int getTotalUndismmissedNotificationCards() {
        return notificationCards.size();
    }

    /**
     * Adds a notification to the Notification center
     */
    public void add(NotificationCard newNotificationCard) {
        NotificationCard forCenter = newNotificationCard.getCopyForCenter();
        javafx.scene.layout.Region notificationCard = forCenter.getRoot();
        LinkedList<javafx.scene.layout.Region> cards;
        if (idToCard.get(forCenter.getId()) == null) {
            cards = new LinkedList<>();
        } else {
            cards = idToCard.get(forCenter.getId());
        }
        cards.add(notificationCard);
        idToCard.put(forCenter.getId(), cards);
        notificationCards.add(notificationCard);
        notificationCardCopy.add(forCenter);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                notificationCardsBox.getChildren().add(notificationCard);
            }
        });
    }

    public ScrollPane getNotificationCenter() {
        return notificationCenterPlaceHolder;
    }

    /**
     * Deletes all notification records associated with the given eventId
     */
    public void deleteNotification(String id) throws NullPointerException {
        if (!idToCard.containsKey(id)) {
            return;
        }
        for (javafx.scene.layout.Region nc: idToCard.get(id)) {
            notificationCardsBox.getChildren().remove(nc);
            notificationCards.remove(nc);
        }
        idToCard.remove(id);
        Iterator<NotificationCard> iterator = notificationCardCopy.iterator();
        iterator.next();
        //to bypass the null at index 0
        int toDecrement = 0;
        while (iterator.hasNext()) {
            NotificationCard curr = iterator.next();
            if (curr.getId().equals(id)) {
                iterator.remove();
                toDecrement++;
            } else if (toDecrement > 0) {
                curr.decreaseIndex(toDecrement);
            }
        }
    }

    public String getIdByIndex(Index index) {
        return notificationCardCopy.get(index.getOneBased()).getId();
    }

    public String getOwnerIdByIndex(Index index) {
        return notificationCardCopy.get(index.getOneBased()).getOwnerId();
    }


    public NotificationCard getNotificationCard(Index targetIndex) {
        return notificationCardCopy.get(targetIndex.getOneBased());
    }

    /**
    * Delete the notification card at the given index
    */
    public NotificationCard deleteNotificationByIndex(Index targetIndex) {
        notificationCardsBox.getChildren().remove(targetIndex.getZeroBased());
        idToCard.remove(notificationCards.get(targetIndex.getOneBased()).getId());
        notificationCards.remove(targetIndex.getOneBased());
        Iterator<NotificationCard> iterator = notificationCardCopy.iterator();
        for (int i = 0; i < notificationCardCopy.size(); i++) {
            if (i <= targetIndex.getOneBased()) {
                iterator.next();
            } else {
                NotificationCard curr = iterator.next();
                curr.decreaseIndex(1);
            }
        }
        return notificationCardCopy.remove(targetIndex.getOneBased());
    }

    /**
     * Removes all notification cards associated with the given ownerId
     */
    public void removeNotificationForPerson(int targetId) {
        System.out.println("TargetId: targetId");
        int totalRemoved = 0;
        LinkedList<NotificationCard> toDelete = new LinkedList<>();
        for (NotificationCard nc: notificationCardCopy) {
            if (nc != null && nc.getOwnerId().equals(targetId + "")) {
                toDelete.add(nc);
                notificationCards.remove(nc.getRoot());
                notificationCardsBox.getChildren().remove(nc.getRoot());
                totalRemoved++;
            } else if (totalRemoved > 0) {
                nc.decreaseIndex(totalRemoved);
            }
        }
        for (NotificationCard nc: toDelete) {
            notificationCardCopy.remove(nc);
        }
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
package seedu.address.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
    private static final String[] TAG_COLOR_STYLES = {"red", "yellow", "blue", "orange", "brown", "green"};
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label rating;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView photo;

```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    @Subscribe
    private void handleShowSuggestionEvent(ShowSuggestionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setStyleForSuggestion();
        Platform.runLater(() -> displayed.setValue(event.getSuggestion()));
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    /**
     * Sets the {@code ResultDisplay} style to use the suggestion style.
     */
    private void setStyleForSuggestion() {
        if (!resultDisplay.getStyleClass().contains(SUGGESTION_STYLE_CLASS)) {
            resultDisplay.getStyleClass().add(SUGGESTION_STYLE_CLASS);
        }
    }

    /**
     * Sets the {@code ResultDisplay} style to use the suggestion style.
     */
    private void removeStyleForSuggestion() {
        resultDisplay.getStyleClass().remove(SUGGESTION_STYLE_CLASS);
    }
```
###### \java\seedu\address\ui\UiManager.java
``` java
        primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                System.out.println("minimized:" + t1.booleanValue());
                isWindowMinimized = t1;
                if (!isWindowMinimized && !LogicManager.isLocked()) {
                    showDelayedNotifications();
                }
            }
        });
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Subscribe
    private void handleShowNotificationEvent(ShowNotificationEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (LogicManager.isLocked()) {
            delayedNotifications.offer(event);
        } else {
            if (isWindowMinimized) {
                showNotificationOnWindows(event);
                delayedNotifications.offer(event);
            } else {
                showNotificationInApp(event);
            }
        }
    }

    @Subscribe
    private void handleToggleNotificationEvent(ToggleNotificationCenterEvent event) {
        if (!LogicManager.isLocked()) {
            mainWindow.toggleNotificationCenter();
        }
    }

    @Subscribe
    private void handleAddressBookUnlockedEvent(AddressBookUnlockedEvent event) {
        showDelayedNotifications();
    }

    private void showNotificationInApp(ShowNotificationEvent event) {
        mainWindow.showNewNotification(event);
    }

    private void showDelayedNotifications() {
        while (!delayedNotifications.isEmpty()) {
            showNotificationInApp(delayedNotifications.poll());
        }
    }

    /**
     * Shows notification on Windows System Tray
     */
    private void showNotificationOnWindows(ShowNotificationEvent event) {
        SystemTray tray = SystemTray.getSystemTray();
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage(ICON_APPLICATION);
        TrayIcon trayIcon = new TrayIcon(image, "E.T. timetable entry ended");
        trayIcon.setImageAutoSize(true);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        trayIcon.displayMessage("Task ended", event.getOwnerName() + " has " + event.getNotification().getTitle()
                + " ended at " + event.getNotification().getEndDateDisplay(), TrayIcon.MessageType.INFO);

    }
```
###### \resources\view\BrightTheme.css
``` css
.notification-scroll-pane > .viewport {
    -fx-background-color: #3366cc;
}

.notification-scroll-pane {
    -fx-background-color: #3366cc;
}

.notification-card-first-stage {
    -fx-background-color: #3399ff;
    -fx-text-fill: #000000;
    -fx-opacity: 0.8;
}

.notification-card-second-stage {
    -fx-background-color: #cc3300;
    -fx-text-fill: #000000;
    -fx-opacity: 0.8;
}

.notification-card-notification-center-first-stage {
    -fx-background-color: #3399ff;
    -fx-text-fill: #000000;
    -fx-opacity: 1;
}

.notification-card-notification-center-second-stage {
    -fx-background-color: #cc3300;
    -fx-text-fill: #000000;
    -fx-opacity: 1;
}
```
###### \resources\view\BrightTheme.css
``` css
.cell_small_label_rating {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #FFFF00;
}
```
###### \resources\view\DarkTheme.css
``` css
.notification-scroll-pane > .viewport {
    -fx-background-color: #3366cc;
}

.notification-scroll-pane {
    -fx-background-color: #3366cc;
}

.notification-card-first-stage {
    -fx-background-color: #3399ff;
    -fx-text-fill: #000000;
    -fx-opacity: 0.8;
}

.notification-card-second-stage {
    -fx-background-color: #cc3300;
    -fx-text-fill: #000000;
    -fx-opacity: 0.8;
}

.notification-card-notification-center-first-stage {
    -fx-background-color: #3399ff;
    -fx-text-fill: #000000;
    -fx-opacity: 1;
}

.notification-card-notification-center-second-stage {
    -fx-background-color: #cc3300;
    -fx-text-fill: #000000;
    -fx-opacity: 1;
}
```
###### \resources\view\DarkTheme.css
``` css
.cell_small_label_rating {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #FFFF00;
}
```
###### \resources\view\Extensions.css
``` css
.suggestion {
    -fx-text-fill: darkred !important; /* The error class should always override the default text-fill style */
}
```
###### \resources\view\NotificationCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<HBox xmlns:fx="http://javafx.com/fxml/1">
    <VBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
      <GridPane fx:id="content" alignment="CENTER" prefHeight="100" opacity="0.5" HBox.hgrow="ALWAYS">
        <columnConstraints>

          <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="300" />
        </columnConstraints>
          <padding>
              <Insets bottom="0" left="0" right="0" top="-10" />
          </padding>
        <VBox alignment="CENTER_LEFT" minHeight="75" GridPane.columnIndex="0">
          <padding>
            <Insets bottom="5" left="15" right="5" top="0" />
          </padding>
          <HBox alignment="CENTER_LEFT" spacing="5">
            <Label fx:id="index" text="a" styleClass="cell_big_label">
              <minWidth>
                <!-- Ensures that the label text is never truncated -->
                <Region fx:constant="USE_PREF_SIZE" />
              </minWidth>
                   <font>
                      <Font size="20.0" />
                   </font>
            </Label>
            <Label fx:id="title" text="\$title" styleClass="cell_big_label">
                   <font>
                      <Font size="20.0" />
                   </font></Label>
          </HBox>
          <Label fx:id="ownerName" text="\$ownerName" styleClass="cell_big_label">
            <padding>
                <Insets top="5" />
            </padding>
          </Label>
          <Label fx:id="endTime" text="\$endTime" styleClass="cell_big_label">
            <padding>
                <Insets top="5" />
            </padding>
          </Label>
        </VBox>
          <rowConstraints>
             <RowConstraints />
          </rowConstraints>
      </GridPane>
        <VBox fx:id="yOffset" maxHeight="15" prefHeight="15" opacity="0"></VBox>
    </VBox>
    <VBox fx:id="xOffset" maxWidth="15" prefWidth="15" opacity="0"></VBox>
</HBox>
```
###### \resources\view\PersonListCard.fxml
``` fxml
        <Label fx:id="rating" styleClass="cell_small_label_rating" text="\$rating" />
```
