package seedu.address.logic.commands;

import seedu.address.commons.core.Mailer;

public class EmailCommand extends Command {

    public static final String COMMAND_WORD = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Send an email to list of persons.";

    public static final String MESSAGE_SUCCESS = "Email sent successfully.";

    public static final String MESSAGE_ERROR = "An error occured, email not sent.";


    @Override
    public CommandResult execute() {
        String message =  Mailer.email(model.getFilteredPersonList()) ? MESSAGE_SUCCESS : MESSAGE_ERROR;
        return new CommandResult(message);
    }
}
