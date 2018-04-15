package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLOSED_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLOSED_TUTORS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClientListSwitchEvent;
import seedu.address.ui.util.ListPanelController;

//@@author olimhc
/**
 * Represents a switch command to enable user to switch between closed and active client list
 * All active students and tutors or closed students and tutors will be shown after switching
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_SUCCESS = "Switched to ";
    public static final String MESSAGE_CLOSED_DISPLAY_LIST = "closed client list.\n";
    public static final String MESSAGE_ACTIVE_DISPLAY_LIST = "active client list.\n";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ClientListSwitchEvent());
        listPanelController.switchDisplay();
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            model.updateFilteredClosedTutorList(PREDICATE_SHOW_ALL_CLOSED_TUTORS);
            model.updateFilteredClosedStudentList(PREDICATE_SHOW_ALL_CLOSED_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS + MESSAGE_CLOSED_DISPLAY_LIST);
        } else {
            model.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
            return new CommandResult(MESSAGE_SUCCESS + MESSAGE_ACTIVE_DISPLAY_LIST);
        }
    }
}
