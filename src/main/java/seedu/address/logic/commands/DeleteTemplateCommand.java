package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.email.exceptions.TemplateNotFoundException;


//@@author ng95junwei
/**
 * Deletes an Template that matches all the input fields from the address book.
 */
public class DeleteTemplateCommand extends Command {

    public static final String COMMAND_WORD = "deletetemplate";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the template which has the specified purpose.\n"
            + "Parameters: PURPOSE "
            + "Example: " + COMMAND_WORD + " "
            + "greeting";

    public static final String MESSAGE_DELETE_TEMPLATE_SUCCESS = "Deleted Template with purpose : %1$s";

    private String purposeToDelete;

    public DeleteTemplateCommand(String purposeToDelete) {

        this.purposeToDelete = purposeToDelete;
    }

    @Override
    public CommandResult execute() throws CommandException {
        System.out.println(purposeToDelete);
        requireNonNull(purposeToDelete);
        try {
            model.deleteTemplate(purposeToDelete);
        } catch (TemplateNotFoundException e) {
            throw new CommandException(Messages.MESSAGE_TEMPLATE_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TEMPLATE_SUCCESS, purposeToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTemplateCommand // instanceof handles nulls
                && this.purposeToDelete.equals(((DeleteTemplateCommand) other).purposeToDelete));
    }
}
