package seedu.address.logic.commands;

import com.google.api.services.gmail.Gmail;

import javafx.collections.ObservableList;

import seedu.address.commons.util.GmailUtil;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and emails all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails all persons whose names matches any of "
            + "the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public EmailCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        // Build a new authorized API client service.

        model.updateFilteredPersonList(predicate);
        ObservableList<Person> emailList = model.getFilteredPersonList();
        for (Person p : emailList) {
            System.out.println(p.getEmail());
            try {
                GmailUtil handler = new GmailUtil();
                Gmail service = handler.getService();
                handler.send(service, p.getEmail().toString(), "",
                        service.users().getProfile("me").getUserId(), "Hello", "Hello");
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Some IOException occurred");
            }
        }
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}



