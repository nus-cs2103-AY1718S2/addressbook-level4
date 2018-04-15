package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLOSED_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLOSED_TUTORS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TUTORS;

import seedu.address.ui.util.ListPanelController;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_SUCCESS = "Listed all clients";

    @Override
    public CommandResult execute() {
        if (ListPanelController.isCurrentDisplayActiveList()) {
            model.updateFilteredTutorList(PREDICATE_SHOW_ALL_TUTORS);
            model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        } else {
            assert(!ListPanelController.isCurrentDisplayActiveList());
            model.updateFilteredClosedTutorList(PREDICATE_SHOW_ALL_CLOSED_TUTORS);
            model.updateFilteredClosedStudentList(PREDICATE_SHOW_ALL_CLOSED_STUDENTS);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
