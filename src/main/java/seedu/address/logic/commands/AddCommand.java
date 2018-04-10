package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEYOWED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWEDUEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWESTARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends UndoableCommand implements PopulatableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + " | Adds a Customer or Runner. "
            + "Refer to the User Guide for help with using this command!"
            + "\n\t"
            + "Parameters:\t"
            + PREFIX_TYPE + " r(or c) "
            + PREFIX_NAME + " NAME "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_ADDRESS + " ADDRESS] "
            + "[" + PREFIX_OWESTARTDATE + " OWE_START_DATE] "
            + "[" + PREFIX_OWEDUEDATE + " OWE_DUE_DATE] "
            + "[" + PREFIX_MONEYOWED + " MONEY_OWED] "
            + "[" + PREFIX_INTEREST + " INTEREST_RATE] "
            + "[" + PREFIX_TAG + " TAG] ... "

            + "\n\t\t"
            + "1. Start and due dates can be specified in natural language."
            + "\n\t\t"
            + "2. Interest is compounded weekly, because how else to make money??"

            + "\n\t"
            + "Example:\t\t" + COMMAND_WORD + " "
            + PREFIX_TYPE + " c "
            + PREFIX_NAME + " Ah Meng "
            + PREFIX_PHONE + " 88888888 "
            + PREFIX_EMAIL + " fatcai@axa.com "
            + PREFIX_ADDRESS + " Pulau Tekong "
            + PREFIX_MONEYOWED + " 1000000 "
            + PREFIX_INTEREST + " 9.71 "
            + PREFIX_OWESTARTDATE + " today "
            + PREFIX_OWEDUEDATE + " 5 May 2018 "
            + PREFIX_TAG + " richboy";

    public static final String MESSAGE_SUCCESS = "New person added!\n%1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    /**
     * For call in PopulatePrefixRequestEvent class, to assign string values.
     */
    public AddCommand() {
        toAdd = null;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    //@@author jonleeyz
    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public String getTemplate() {
        return COMMAND_WORD + " " + PREFIX_TYPE + "  " + PREFIX_NAME + "  "
                + PREFIX_PHONE + "  " + PREFIX_EMAIL + "  " + PREFIX_ADDRESS + "  "
                + PREFIX_MONEYOWED + "  " + PREFIX_INTEREST + "  " + PREFIX_OWESTARTDATE + "  "
                + PREFIX_OWEDUEDATE + "  " + PREFIX_TAG + " ";
    }

    @Override
    public int getCaretIndex() {
        return (COMMAND_WORD + " " + PREFIX_TYPE + " ").length();
    }

    @Override
    public String getUsageMessage() {
        return MESSAGE_USAGE;
    }
    //@@author
}
