package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

//@@author x3tsunayh

/**
 * Lists all events in the event book to the user.
 */
public class ListAllEventsCommand extends Command {
    public static final String COMMAND_WORD = "listallevents";
    public static final String COMMAND_WORD_TWO = "listevents";
    public static final String COMMAND_WORD_THREE = "allevents";

    @Override
    public CommandResult execute() {
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult("All "
                + getMessageForEventListShownSummary(model.getFilteredEventList().size()));
    }
}
