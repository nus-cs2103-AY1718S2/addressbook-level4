package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.PersonDetail;

//@@author jstarw
/**
 * Opens up a PersonDetail window
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = "Opens up the details window of a specified person.\n"
            + "Parameters: FULL NAME OF PERSON\n"
            + "Example: " + COMMAND_WORD + " John Doe";;

    public static final String MESSAGE_SUCCESS = "Opened up person detail window";
    public static final String MESSAGE_FAIL = "Failed to open window: person not found.";

    private final NameContainsKeywordsPredicate predicate;

    public ShowCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Person person = model.findOnePerson(predicate);
            loadPersonDetail(person);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_FAIL);
        }
    }

    private void loadPersonDetail(Person person) {
        PersonDetail personDetail = new PersonDetail(person, 1);
        personDetail.show();
    }
}
