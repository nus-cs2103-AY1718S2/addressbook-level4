package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "password";
    public static final String COMMAND_WORD_ALIAS = "pw";

    public static final String MESSAGE_SUCCESS = "Password set!";

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
