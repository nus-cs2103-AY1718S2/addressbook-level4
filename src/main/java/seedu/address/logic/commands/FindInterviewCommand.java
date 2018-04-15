//author@@ deeheenguyen
package seedu.address.logic.commands;

import java.util.function.Predicate;

import seedu.address.model.interview.Interview;

/**
 * Lists all persons in address book whose name, or email, or phone, or tags contain any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindInterviewCommand extends Command {

    public static final String COMMAND_WORD = "findInterview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all interviews of specific interview\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice";

    private final Predicate<Interview> predicate;

    public FindInterviewCommand(Predicate<Interview> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredInterviewList(predicate);
        return new CommandResult(getMessageForInterviewListShownSummary(model.getFilteredInterviewList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindInterviewCommand // instanceof handles nulls
                && this.predicate.equals(((FindInterviewCommand) other).predicate)); // state check
    }
}
