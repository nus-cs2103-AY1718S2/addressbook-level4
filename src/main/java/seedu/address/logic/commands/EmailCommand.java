package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PURPOSE;

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
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PURPOSE + "PURPOSE \n"
            + "Example: " + COMMAND_WORD +  " "
            + PREFIX_NAME + "alice "
            + PREFIX_PURPOSE + "coldemail";

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
        if (emailList.size() == 0) {
            return new CommandResult(Messages.MESSAGE_PERSONS_NOT_FOUND);
        }
        for (Person person : emailList) {
            try {
                Template template = model.selectTemplate(this.search);
                GmailUtil handler = new GmailUtil();
                Gmail service = handler.getService();
                handler.send(service, person.getEmail().toString(), "",
                        service.users().getProfile("me").getUserId(), template.getTitle(),
                        template.getMessage());
            } catch (TemplateNotFoundException e) {
                return new CommandResult(Messages.MESSAGE_TEMPLATE_NOT_FOUND);
            } catch (Exception e) {
                return new CommandResult(Messages.MESSAGE_EMAIL_UNKNOWN_ERROR);
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



