package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.model.Model;
import seedu.address.model.PersonIsAddedBeforeDateInputAndContainsTagsPredicate;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes all persons with specified tags added before the date input by user.
 */
public class  DeleteBeforeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletebefore";
    public static final String COMMAND_ALIAS = "db";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons added before the input date.\n"
            + "Parameters: "
            + PREFIX_DATE + "DATE (must be in the format: dd/MM/yyyy) "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "01/01/2010 "
            + PREFIX_TAG + "non-client"
            + PREFIX_TAG + "cash cow";

    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Deleted all persons with tags %s added before %s";

    private final DateAdded inputDate;
    private final Set<Tag> inputTags;
    private final PersonIsAddedBeforeDateInputAndContainsTagsPredicate predicate;

    public DeleteBeforeCommand(DateAdded inputDate, Set<Tag> inputTags) {
        this.inputDate = inputDate;
        this.inputTags = inputTags;
        this.predicate = new PersonIsAddedBeforeDateInputAndContainsTagsPredicate(inputTags, inputDate.toString());
    }


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(predicate);
        try {
            model.updateFilteredPersonList(predicate);
            model.deletePersons(model.getFilteredPersonList());
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Any of the target persons cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSONS_SUCCESS, inputTags, inputDate));
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBeforeCommand // instanceof handles nulls
                && this.inputDate.equals(((DeleteBeforeCommand) other).inputDate)); // state check
    }
}
