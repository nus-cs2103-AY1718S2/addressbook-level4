package seedu.address.logic.commands;

import seedu.address.commons.events.logic.ZoomOutEvent;

//@@author jlks96
/**
 * Zooms out the calendar view to show a more general view
 */
public class ZoomOutCommand extends Command {
    public static final String COMMAND_WORD = "zoomout";
    public static final String COMMAND_ALIAS = "zo";

    public static final String MESSAGE_SUCCESS = "Calendar zoomed out";

    @Override
    public CommandResult execute() {
        raise(new ZoomOutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
