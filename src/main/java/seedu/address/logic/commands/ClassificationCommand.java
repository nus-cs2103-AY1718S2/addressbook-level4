package seedu.address.logic.commands;

import seedu.address.model.person.NameContainsClassificationPredicate;

//@@author wenhao53
/**
 * Finds and lists all persons in the Personal Trainer Pro application whose BodyMassIndexClassification matches
 * any of the given keywords.
 */
public class ClassificationCommand extends Command {

    public static final String COMMAND_WORD = "bmi";

    public static final String INVALID_KEYWORD = "One or more invalid keyword(s) found! \n%1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose BMI classification matches "
            + "any of the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " overweight\n"
            + "KEYWORD: 'Underweight'(BMI < 18.5), 'Acceptable' (18.5 <= BMI <= 24.9), "
                    + "'Overweight' (25 <= BMI <= 29.9), 'Obese' (BMI > 30)\n";

    private final NameContainsClassificationPredicate predicate;

    public ClassificationCommand(NameContainsClassificationPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClassificationCommand // instanceof handles nulls
                && this.predicate.equals(((ClassificationCommand) other).predicate)); // state check
    }
}
