package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPECTED_GRADUATION_YEAR;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Filters and lists all persons in address book whose graduation year is before or equal to the specified year..
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filter according the predicates specified. "
            + "Display all persons with fields in the range specified by the input keyword.\n"
            + "Range is either a single predicate, or an interval in the format of `low - high`, "
            + "or a combination of both using commas.\n"
            + "Filterable field is expected graduation year."
            + "Parameters: "
            + "[" + PREFIX_EXPECTED_GRADUATION_YEAR + "EXPECTED GRADUATION YEAR] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXPECTED_GRADUATION_YEAR + " 2020, 2021-2024";

    private final Predicate<Person> predicate;

    public FilterCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.filterFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }

}
