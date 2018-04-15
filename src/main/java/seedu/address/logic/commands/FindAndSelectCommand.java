package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.student.NameContainsKeywordsPredicate;

//@@author chweeee
/**
 * Finds and lists all students in address book whose name contains any of the argument keywords,
 * and Selects the first student of the list.
 * Keyword matching is case sensitive.
 */
public class FindAndSelectCommand extends UndoableCommand {

    public static final String MESSAGE_SELECT_STUDENT_SUCCESS = "Selected Student: %1$s";

    public static final String MESSAGE_STUDENT_NOT_FOUND = "Student to be selected cannot be found.";

    private final NameContainsKeywordsPredicate predicate;

    private Index targetIndex;

    public FindAndSelectCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult executeUndoableCommand() {

        try {
            targetIndex = ParserUtil.parseIndex("1");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_STUDENT_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {

        //look for the student
        model.updateFilteredStudentList(predicate);
        if (model.getFilteredStudentList().size() == 0) {
            throw new CommandException(MESSAGE_STUDENT_NOT_FOUND);
        }
    }
}
//@@author
