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
            + ": Adds an event to the employee identified by the index number used in the last employees listing.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + PREFIX_TITLE + "TITLE "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_STARTTIME + "STARTTIME (must follow given format) "
            + PREFIX_ENDTIME + "ENDTIME (must follow given format)\n"
            + "Example: " + COMMAND_WORD + " 1 title/Project Meeting loca/NUS, Singapore stime/2017-03-19T08:00:00"
            + " etime/2017-03-19T10:00:00 descrip/Discuss about product launch";

    public static final String MESSAGE_SUCCESS = "Event added!";
    public static final String MESSAGE_FAILURE = "Unable to add event, please try again later.";


    private final Index targetIndex;
    private final String title;
    private final String location;
    private final String startTime;
    private final String endTime;
    private final String description;
    private final Logger logger = LogsCenter.getLogger(TestAddEventCommand.class);

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
        com.google.api.services.calendar.Calendar service = null;
        try {
            service = Authentication.getCalendarService();
        } catch (IOException e) {
            logger.warning("Couldn't authenticate Google Calendar Service");
        }

        //Solution below adpated from https://developers.google.com/calendar/quickstart/java
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

        String calendarId = personToAddEvent.getCalendarId();
        Logger logger = LogsCenter.getLogger(TestAddEventCommand.class);
        //@@author IzHoBX
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
        //@@author crizyli

        assert calendarId.endsWith("@group.calendar.google.com");
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
        model.addNotification(notification);
        //@@author crizyli

        logger.info("Event created: " + event.getHtmlLink());

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
