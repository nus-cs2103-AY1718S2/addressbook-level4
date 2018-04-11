package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPanelRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.ui.InfoPanel;
import seedu.address.ui.PdfPanel;

/**
 * Shows a specific panel
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a specific panel. The panel can be either 'info' or 'resume'.\n"
            + "Make sure person are selected before calling this command.\n"
            + "When resume is requested, it will only shows when it is available.\n"
            + "Parameters: PANEL (must be either 'info' or 'resume', case sensitive)\n"
            + "Example: " + COMMAND_WORD + " info";

    public static final String PANEL_INFO = "info";
    public static final String PANEL_RESUME = "resume";

    private static final String MESSAGE_NOT_SELECTED = "A person must be selected before showing a panel.";
    private static final String MESSAGE_RESUME_NA = "The selected person doesn't have a resume";
    private static final String MESSAGE_INVALID_PANEL =
            "Invalid panel requested. Only 'info' and 'resume' are allowed.";
    private static final String MESSAGE_SHOW_SUCCESS = "Showing the requested panel";

    /**
     * Enumeration of acceptable panel
     */
    public enum Panel {
        INFO, RESUME
    }

    private final Panel panel;

    public ShowCommand(Panel panel) {
        requireNonNull(panel);
        this.panel = panel;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Person selectedPerson = model.getSelectedPerson();
        if (selectedPerson == null) {
            throw new CommandException(MESSAGE_NOT_SELECTED);

        } else {
            switch (panel) {
            case INFO:
                EventsCenter.getInstance().post(new ShowPanelRequestEvent(InfoPanel.PANEL_NAME));
                break;
            case RESUME:
                if (selectedPerson.getResume().value != null) {
                    EventsCenter.getInstance().post(new ShowPanelRequestEvent(PdfPanel.PANEL_NAME));
                } else {
                    throw new CommandException(MESSAGE_RESUME_NA);
                }
                break;
            default:
                throw new CommandException(MESSAGE_INVALID_PANEL);
            }

            return new CommandResult(MESSAGE_SHOW_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShowCommand // instanceof handles nulls
                && this.panel.equals(((ShowCommand) other).panel)); // state check
    }
}
