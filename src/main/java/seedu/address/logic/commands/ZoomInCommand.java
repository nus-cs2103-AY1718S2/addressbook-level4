package seedu.address.logic.commands;

import seedu.address.commons.events.logic.ZoomInEvent;

//@@author jlks96
/**
 * Zooms in the calendar view to show a more detailed view
 */
public class ZoomInCommand extends Command {
    public static final String COMMAND_WORD = "zoomin";
    public static final String COMMAND_ALIAS = "zi";

    public static final String MESSAGE_SUCCESS = "Calendar zoomed in";

    @Override
    public CommandResult execute() {
        raise(new ZoomInEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
