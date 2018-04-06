package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JERSEY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM_NAME;
import static seedu.address.logic.parser.ParserUtil.UNSPECIFIED_FIELD;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.DeselectTeamEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.team.TeamName;

/**
 * Adds a player to the address book.
 */
public class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a player to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_TEAM_NAME + "TEAMNAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_JERSEY_NUMBER + "JERSEY_NUMBER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_RATING + "0"
            + PREFIX_POSITION + "1"
            + PREFIX_JERSEY_NUMBER + "17"
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_PARAMETERS = PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_JERSEY_NUMBER + "JERSEY_NUMBER] "
            + "[" + PREFIX_TAG + "TAG]";

    public static final String MESSAGE_SUCCESS = "New player added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This player already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            if (!toAdd.getTeamName().toString().equals(UNSPECIFIED_FIELD)) {
                model.assignPersonToTeam(toAdd, toAdd.getTeamName());
            }
            EventsCenter.getInstance().post(new DeselectTeamEvent());
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        TeamName teamName = toAdd.getTeamName();
        if (!model.getAddressBook().getTeamList().stream().anyMatch(t -> t.getTeamName().equals(teamName))) {
            if (!teamName.toString().equals(UNSPECIFIED_FIELD)) {
                throw new CommandException((Messages.MESSAGE_TEAM_NOT_FOUND));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
