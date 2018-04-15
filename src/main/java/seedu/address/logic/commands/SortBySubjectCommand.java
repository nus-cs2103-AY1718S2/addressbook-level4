package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInClosedViewException;
import seedu.address.model.person.Category;
import seedu.address.ui.util.ListPanelController;

//@@author olimhc
/**
 *Sort the selected list according to their subject in alphabetical order
 */
public class SortBySubjectCommand extends SortCommand {

    public static final String MESSAGE_SORT_DESC = " their subject in alphabetical order.";

    private Category category;


    public SortBySubjectCommand(Category category) {
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }

        if (category.isTutor()) {
            model.sortBySubjectFilteredClientTutorList();
            return new CommandResult(MESSAGE_SUCCESS_TUTOR + MESSAGE_SORT_DESC);
        } else {
            model.sortBySubjectFilteredClientStudentList();
            return new CommandResult(MESSAGE_SUCCESS_STUDENT + MESSAGE_SORT_DESC);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortBySubjectCommand // instanceof handles nulls
                & this.category.equals(((SortBySubjectCommand) other).category)); // state check // state check
    }
}
