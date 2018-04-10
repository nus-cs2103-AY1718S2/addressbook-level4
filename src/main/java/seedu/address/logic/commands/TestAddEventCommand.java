package seedu.address.logic.commands;
//@@author crizyli
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Authentication;
import seedu.address.logic.CreateNewCalendar;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.notification.Notification;
import seedu.address.model.notification.exceptions.DuplicateTimetableEntryException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author crizyli
/**
 * Adds an event to a person.
 */
public class TestAddEventCommand extends Command {

    public static final String COMMAND_WORD = "addEvent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add an event to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_TITLE + "TITLE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_STARTTIME + "STARTTIME (must follow given format) "
            + PREFIX_ENDTIME + "ENDTIME (must follow given format)\n"
            + "Example: " + COMMAND_WORD + " 1 title/Project Meeting loca/NUS, Singapore stime/2017-03-19T08:00:00"
            + " etime/2017-03-19T10:00:00 descrip/discuss about v1.4 milestone";

    public static final String MESSAGE_SUCCESS = "Event added!";
    public static final String MESSAGE_FAILURE = "Unable to add event, please try again later.";



    private final Index targetIndex;
    private final String title;
    private final String location;
    private final String startTime;
    private final String endTime;
    private final String description;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public TestAddEventCommand(Index index, String title, String location, String startTime,
                               String endTime, String description) {
        this.title = title;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.targetIndex = index;
    }



    @Override
    public CommandResult execute() throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToAddEvent = lastShownList.get(targetIndex.getZeroBased());

        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = Authentication.getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Event event = new Event()
                .setSummary(title)
                .setLocation(location)
                .setDescription(description);

        String startTimeFormat = startTime + "+08:00";
        DateTime startDateTime = new DateTime(startTimeFormat);
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Asia/Singapore");
        event.setStart(start);

        String endTimeFormat = endTime + "+08:00";
        DateTime endDateTime = new DateTime(endTimeFormat);
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Asia/Singapore");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=1"};
        //event.setRecurrence(Arrays.asList(recurrence));

        /*EventAttendee[] attendees = new EventAttendee[] {
                new EventAttendee().setEmail("jjjsss@example.com"),
                new EventAttendee().setEmail("dzzzssss@example.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[] {
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);*/

        String calendarId = personToAddEvent.getCalendarId();
        Logger logger = LogsCenter.getLogger(TestAddEventCommand.class);
        //@@author IzHoBX
        if (calendarId == null || calendarId.equals("") || calendarId.equals("null")) {
            logger.info("calendarId null, attempting to create calendar");
            try {
                calendarId = CreateNewCalendar.execute(personToAddEvent.getName().fullName);
                logger.info("calendar created successfully");
            } catch (IOException e) {
                e.printStackTrace();
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
                logger.info("Unable to find original person in model manager");
                return new CommandResult(MESSAGE_FAILURE);
            } catch (DuplicatePersonException e) {
                logger.info("newly created person (with calendarId) is same as original person");
                return new CommandResult(MESSAGE_FAILURE);
            }
        }
        //@@author crizyli

        try {
            event = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("failed to add event to calendarId");
            return new CommandResult(MESSAGE_FAILURE);
        }

        //@@author IzHoBX
        Notification notification = new Notification(title, calendarId, event.getId(), event.getEnd().toString(),
                model.getPerson(targetIndex.getZeroBased()).getId().toString());
        try {
            model.addNotification(notification);
        } catch (DuplicateTimetableEntryException e) {
            throw new CommandException("Duplicated event");
        }
        //@@author crizyli

        System.out.printf("Event created: %s\n", event.getHtmlLink());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TestAddEventCommand // instanceof handles nulls
                && targetIndex.equals(((TestAddEventCommand) other).targetIndex)
                && title.equals(((TestAddEventCommand) other).title)
                && location.equals(((TestAddEventCommand) other).location)
                && startTime.equals(((TestAddEventCommand) other).startTime)
                && endTime.equals(((TestAddEventCommand) other).endTime)
                && description.equals(((TestAddEventCommand) other).description));
    }
}
