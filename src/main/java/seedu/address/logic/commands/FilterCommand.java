package seedu.address.logic.commands;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowDefaultPageEvent;
import seedu.address.commons.events.ui.ShowMultiLocationEvent;
import seedu.address.logic.GetDistance;
import seedu.address.logic.RouteOptimization;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DatePredicate;

//@@author meerakanani10
/**
 * Filters and lists all persons in address book whose date contains any of the argument dates.
 * Keyword matching is case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters the list based on the specifed field and given value.";

    private static String stringDuration;

    private final DatePredicate predicate;

    public FilterCommand(DatePredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException {

        RouteOptimization route = new RouteOptimization();
        List<String> optimizedRoute;

        model.updateFilteredPersonList(predicate);
        optimizedRoute = route.getAddresses(model);
        stringDuration = getDuration(optimizedRoute);

        int numberOfPersonsListed = model.getFilteredPersonList().size();

        //no person matchs date
        if (numberOfPersonsListed == 0) {
            EventsCenter.getInstance().post(new ShowDefaultPageEvent());
            return new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsListed));
        }

        //all addresses are invalid
        if (optimizedRoute.size() == 0) {
            EventsCenter.getInstance().post(new ShowDefaultPageEvent());
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed) + "\n" +
                    "All the addresses on " +
                    model.getFilteredPersonList().get(0).getDate().toString() +
                    " cannot be found.";
            return new CommandResult(shown);
        }

        EventsCenter.getInstance().post(new ShowMultiLocationEvent(optimizedRoute));
        //some addresses are invalid
        if (optimizedRoute.size() < numberOfPersonsListed) {
            String shown = getMessageForPersonListShownSummary(numberOfPersonsListed) + "\n" +
                    "At least one address on " +
                    model.getFilteredPersonList().get(0).getDate().toString() +
                    " cannot be found.";
            return new CommandResult(shown);
        }

        //all addresses are valid
        return new CommandResult(getMessageForPersonListShownSummary(numberOfPersonsListed));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }

    public String getDuration(List<String> route) {
        Double duration;
        GetDistance distance = new GetDistance();
        Double totalDuration = 0.0;
        for (int  i = 0; i < route.size() - 1; i++) {
            duration = distance.getTime(route.get(i), route.get(i + 1));
            totalDuration = totalDuration + duration;
        }
        String stringDuration = totalDuration.toString() + " mins";
        return stringDuration;
    }

    public static String getStringDuration() {
        return stringDuration;
    }
}
