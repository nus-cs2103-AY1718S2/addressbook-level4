//@@author LeonidAgarth
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalGroups.GROUP_C;
import static seedu.address.testutil.TypicalGroups.GROUP_G;
import static seedu.address.testutil.TypicalGroups.GROUP_H;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.TimetableChangedEvent;
import seedu.address.database.module.Module;
import seedu.address.database.module.Schedule;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.WeeklyEvent;
import seedu.address.model.group.Group;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ScheduleGroupCommand}.
 */
public class ScheduleGroupCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validGroup_success() {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        ArrayList<WeeklyEvent> freeA = new ArrayList<>();
        Module mod = new Module("Free", "", null);
        for (String day : daysOfWeek) {
            for (int s = 800; s < 1800; s += 100) {
                Schedule sch = new Schedule("", "", "", day, "" + s, "" + (s + 100), "");
                freeA.add(new WeeklyEvent(mod, sch));
            }
        }
        assertExecutionSuccess(GROUP_A, freeA);

        ArrayList<WeeklyEvent> freeH = new ArrayList<>();
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "1600", "1700", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Monday", "1700", "1800", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Tuesday", "1400", "1500", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Tuesday", "1500", "1600", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1200", "1300", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1300", "1400", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1600", "1700", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Wednesday", "1700", "1800", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "800", "900", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "900", "1000", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1300", "1400", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1400", "1500", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Thursday", "1500", "1600", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Friday", "1200", "1300", "")));
        freeH.add(new WeeklyEvent(mod, new Schedule("", "", "", "Friday", "1300", "1400", "")));
        assertExecutionSuccess(GROUP_H, freeH);
    }

    @Test
    public void execute_groupNotFound_failure() {
        assertExecutionFailure(GROUP_C,
                String.format(ScheduleGroupCommand.MESSAGE_GROUP_NOT_FOUND, GROUP_C.getInformation()));
        assertExecutionFailure(GROUP_G,
                String.format(ScheduleGroupCommand.MESSAGE_GROUP_NOT_FOUND, GROUP_G.getInformation()));
    }

    @Test
    public void equals() {
        ScheduleGroupCommand groupACommand = new ScheduleGroupCommand(GROUP_A);
        ScheduleGroupCommand groupHCommand = new ScheduleGroupCommand(GROUP_H);

        // same object -> returns true
        assertTrue(groupACommand.equals(groupACommand));

        // same values -> returns true
        ScheduleGroupCommand selectFirstCommandCopy = new ScheduleGroupCommand(GROUP_A);
        assertTrue(groupACommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(groupACommand.equals(1));

        // null -> returns false
        assertFalse(groupACommand.equals(null));

        // different person -> returns false
        assertFalse(groupACommand.equals(groupHCommand));
    }

    /**
     * Executes a {@code ScheduleGroupCommand} with the given {@code index},
     * and checks that {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Group group, ArrayList<WeeklyEvent> freeSlots) {
        ScheduleGroupCommand scheduleGroupCommand = prepareCommand(group);

        try {
            CommandResult commandResult = scheduleGroupCommand.execute();
            assertEquals(String.format(ScheduleGroupCommand.MESSAGE_SUCCESS, group.getInformation()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        TimetableChangedEvent lastEvent = (TimetableChangedEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(lastEvent != null);
        assertTrue(lastEvent.timetable.equals(freeSlots));
    }

    /**
     * Executes a {@code ScheduleGroupCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Group group, String expectedMessage) {
        ScheduleGroupCommand scheduleGroupCommand = prepareCommand(group);

        try {
            scheduleGroupCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code ScheduleGroupCommand} with parameters {@code index}.
     */
    private ScheduleGroupCommand prepareCommand(Group group) {
        ScheduleGroupCommand scheduleGroupCommand = new ScheduleGroupCommand(group);
        scheduleGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return scheduleGroupCommand;
    }
}
