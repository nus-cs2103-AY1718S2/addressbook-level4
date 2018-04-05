package seedu.address.logic.commands;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Mailer;
import seedu.address.logic.RouteOptimization;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.DelivDate;
import seedu.address.model.person.Person;

//@@author mattbuot
/**
 * Send an email to the persons listed
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Send an email to list of persons and the optimized itinerary to the driver.";

    public static final String MESSAGE_SUCCESS = "Emails sent successfully.";

    public static final String MESSAGE_ERROR = "An error occurred, emails not sent.";

    @Override
    public CommandResult execute() throws CommandException {

        //we check that the customers listed have their delivery on the same date
        String delivDate = getDate().toString();

        RouteOptimization route = new RouteOptimization();
        List<String> optimizedRoute;

        optimizedRoute = route.getAddresses(model);
        String duration = FilterCommand.getStringDuration();

        boolean result =
                Mailer.emailDriver(optimizedRoute, duration, delivDate)
                        &&  Mailer.emailCustomers(model.getFilteredPersonList());

        String message = result ? MESSAGE_SUCCESS : MESSAGE_ERROR;

        return new CommandResult(message);
    }

    private DelivDate getDate() throws CommandException {
        ObservableList<Person> filteredPersonList = model.getFilteredPersonList();

        if (filteredPersonList.size() > 0) {
            DelivDate date = filteredPersonList.get(0).getDate();
            for (Person p : filteredPersonList) {
                if (!date.equals(p.getDate())) {
                    throw new CommandException("The list is not filtered!");
                }
            }
            return date;
        }
        throw new CommandException("Empty filtered list!");
    }
}
