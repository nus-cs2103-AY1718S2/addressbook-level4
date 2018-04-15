// @@author anh2111
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleReportPanelEvent;
import seedu.address.model.tag.Tag;

/**
 * Displays the report to the user.
 */
public class ViewReportCommand extends Command {
    public static final String COMMAND_WORD = "viewreport";
    public static final String COMMAND_ALIAS = "vr";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": view the report for candidates tagged with #POPULATION_TAG\n"
            + "Parameters: pop/POPULATION_TAG\n"
            + "Example: " + COMMAND_WORD + " pop/SEIntern OR " + COMMAND_ALIAS + " pop/SEIntern";
    public static final String MESSAGE_SUCCESS = "Displayed report for #";
    public final Tag population;

    public ViewReportCommand(Tag population) {
        requireNonNull(population);
        this.population = population;
    }

    @Override
    public CommandResult execute() {
        model.updateReport(population);
        EventsCenter.getInstance().post(new ToggleReportPanelEvent());
        return new CommandResult(MESSAGE_SUCCESS + population.tagName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewReportCommand // instanceof handles nulls
                && this.population.equals(((ViewReportCommand) other).population)); // state check
    }
}
// @@author
