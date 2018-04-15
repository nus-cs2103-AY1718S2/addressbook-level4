package seedu.address.logic.commands;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.CommandNotAvailableInClosedViewException;
import seedu.address.model.person.Category;
import seedu.address.model.person.Client;
import seedu.address.model.person.MatchContainsKeywordsPredicate;
import seedu.address.model.person.MatchContainsPersonsPredicate;
import seedu.address.ui.util.ListPanelController;

//@@author Zhu-Jiahui
/**
 * Match the entered client and lists all clients in address book that has similar attributes to the matched client.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all clients that match all the fields listed by the person entered.\n"
            + "Example: " + COMMAND_WORD + " 1" + " c/t";

    private final Index targetIndex;
    private final Category category;

    private Client clientToMatch;

    public MatchCommand(Index index, Category category) {
        this.targetIndex = index;
        this.category = category;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (!ListPanelController.isCurrentDisplayActiveList()) {
            throw new CommandNotAvailableInClosedViewException();
        }
        List<Client> lastShownList;

        if (category.isStudent()) {
            lastShownList = model.getFilteredStudentList();
        } else {
            lastShownList = model.getFilteredTutorList();
        }

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        clientToMatch = lastShownList.get(targetIndex.getZeroBased());

        MatchContainsKeywordsPredicate predicate = new MatchContainsKeywordsPredicate(clientToMatch);
        if (category.isStudent()) {
            model.updateFilteredTutorList(predicate);
            model.updateFilteredStudentList(new MatchContainsPersonsPredicate(clientToMatch));
            model.updateRankedTutorList();

        } else {
            model.updateFilteredStudentList(predicate);
            model.updateFilteredTutorList(new MatchContainsPersonsPredicate(clientToMatch));
            model.updateRankedStudentList();

        }

        return new CommandResult(getMessageForClientListShownSummary(
                model.getFilteredStudentList().size(), model.getFilteredTutorList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatchCommand // instanceof handles nulls
                && this.targetIndex.equals(((MatchCommand) other).targetIndex))
                && this.category.equals(((MatchCommand) other).category); // state check
    }
}
//@@author
