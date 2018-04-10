package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PURPOSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.email.Template;
import seedu.address.model.email.exceptions.DuplicateTemplateException;

//@@author ng95junwei
/**
 * Adds an appointment to the address book.
 */
public class AddTemplateCommand extends Command {

    public static final String COMMAND_WORD = "addtemplate";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a template to the address book. "
            + "Parameters: "
            + PREFIX_PURPOSE + "PURPOSE "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_MESSAGE + "MESSAGE BODY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PURPOSE + "greeting "
            + PREFIX_SUBJECT + "Hello there "
            + PREFIX_MESSAGE + "Luke, I am your father";

    public static final String MESSAGE_SUCCESS = "New template added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEMPLATE = "A template with the "
            + "same purpose already exists in the address book";

    private final Template toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddTemplateCommand(Template template) {
        requireNonNull(template);
        toAdd = template;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addTemplate(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTemplateException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TEMPLATE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTemplateCommand // instanceof handles nulls
                && toAdd.equals(((AddTemplateCommand) other).toAdd)); // state check
    }
}
