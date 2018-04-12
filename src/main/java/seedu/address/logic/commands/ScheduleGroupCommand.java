//@@author LeonidAgarth
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.TimetableChangedEvent;
import seedu.address.database.DatabaseManager;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Display the common free time slots of members in a group
 */
public class ScheduleGroupCommand extends Command {

    public static final String COMMAND_WORD = "scheduleGroup";
    public static final String COMMAND_ALIAS = "sG";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Display the common free time slots of members in a group.\n"
            + "Parameters: GROUP_NAME\n"
            + "Example: " + COMMAND_WORD + " CS2103T";

    public static final String MESSAGE_SUCCESS = "Common free time slots are displayed for group %1$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "There is no group named %1$s.";

    private final Group toShow;
    private final ArrayList<WeeklyEvent> occupied;
    private final ArrayList<WeeklyEvent> free;

    /**
     * Creates an ScheduleGroupCommand to schedule the specified {@code Group}
     */
    public ScheduleGroupCommand(Group group) {
        requireNonNull(group);
        toShow = group;
        occupied = new ArrayList<>();
        free = new ArrayList<>();
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        Group groupToShow = new Group(toShow.getInformation());
        for (Group group : model.getFilteredGroupList()) {
            if (toShow.getInformation().equals(group.getInformation())) {
                groupToShow = group;
                break;
            }
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND, toShow.getInformation()));
        }
        fillTimeSlots(groupToShow);
        generateFreeTimeSlots();
        EventsCenter.getInstance().post(new TimetableChangedEvent(FXCollections.observableArrayList(free)));
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupToShow.getInformation()));
    }

    private void fillTimeSlots(Group groupToShow) {
        for (Person member : groupToShow.getPersonList()) {
            ArrayList<WeeklyEvent> moduleList = DatabaseManager.getInstance().parseEvents(member.getTimeTableLink());
            occupied.addAll(moduleList);
        }
    }

    private void generateFreeTimeSlots() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (String day : daysOfWeek) {
            for (int s = 800; s < 1800; s += 100) {
                Module mod = new Module("Free", "", null);
                Schedule sch = new Schedule("", "", "", day, "" + s, "" + (s + 100), "");
                WeeklyEvent freeTimeSlot = new WeeklyEvent(mod, sch);
                if (!moduleClash(freeTimeSlot)) {
                    free.add(freeTimeSlot);
                }
            }
        }
    }

    private boolean moduleClash(WeeklyEvent timeSlot) {
        for (WeeklyEvent mod : occupied) {
            if (mod.clash(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleGroupCommand // instanceof handles nulls
                && toShow.equals(((ScheduleGroupCommand) other).toShow));
    }
}
