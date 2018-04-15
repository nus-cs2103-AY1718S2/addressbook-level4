//@@author deeheenguyen
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ToggleBrowserPanelEvent;
import seedu.address.model.person.EmailFilter;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person with the email\n"
            + "Example: " + COMMAND_WORD + " abcd@gmail.com";

    private final EmailFilter predicate;

    public ViewCommand(EmailFilter predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        Index targetIndex = Index.fromOneBased(1);
        EventsCenter.getInstance().post(new ToggleBrowserPanelEvent());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.predicate.equals(((ViewCommand) other).predicate)); // state check
    }
}
//@@author
