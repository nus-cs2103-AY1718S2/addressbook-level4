package seedu.address.logic.commands;

import com.google.api.services.gmail.Gmail;

import javafx.collections.ObservableList;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.GmailUtil;
import seedu.address.model.email.Template;
import seedu.address.model.email.exceptions.TemplateNotFoundException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

//@@author ng95junwei

/**
 * Finds and emails all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Emails all persons whose names matches any of "
            + "the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: NAME TEMPLATE\n"
            + "Example: " + COMMAND_WORD + " alice coldemail";

    private final NameContainsKeywordsPredicate predicate;
    private final String search;

    public EmailCommand(NameContainsKeywordsPredicate predicate, String search) {

        this.predicate = predicate;
        this.search = search;
    }

    /**
     *
     * @param displaySize
     * @return summary message for persons emailed
     */
    public static String getMessageForPersonEmailSummary(int displaySize) {
        return String.format(Messages.MESSAGE_EMAIL_SENT, displaySize);
    }

    @Override
    public CommandResult execute() {
        // Build a new authorized API client service.

        model.updateFilteredPersonList(predicate);
        ObservableList<Person> emailList = model.getFilteredPersonList();
        for (Person p : emailList) {
            try {
                Template template = model.selectTemplate(this.search);
                GmailUtil handler = new GmailUtil();
                Gmail service = handler.getService();
                handler.send(service, p.getEmail().toString(), "",
                        service.users().getProfile("me").getUserId(), template.getTitle(),
                        template.getMessage());
            } catch (TemplateNotFoundException e) {
                return new CommandResult(Messages.MESSAGE_TEMPLATE_NOT_FOUND);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Some Exception occurred");
            }
        }
        return new CommandResult(getMessageForPersonEmailSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailCommand // instanceof handles nulls
                && this.predicate.equals(((EmailCommand) other).predicate)); // state check
    }
}



