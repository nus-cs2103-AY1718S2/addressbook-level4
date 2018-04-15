package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_BY_PERCENTAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_BY_UNITS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_METHOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.Transaction;

//@@author ongkc
/**
 * Adds a new transaction to the SplitSplit application.
 */
public class AddTransactionCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtransaction";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new transaction to the address book. \n"
            + "Parameters: "
            + PREFIX_TRANSACTION_TYPE + "TRANSACTION TYPE "
            + PREFIX_PAYER + "PAYER NAME "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "[" + PREFIX_PAYEE + "PAYEE NAME] "
            + PREFIX_SPLIT_METHOD + "SPLIT METHOD "
            + PREFIX_SPLIT_BY_UNITS + "LIST OF UNITS "
            + PREFIX_SPLIT_BY_PERCENTAGE + "LIST OF PERCENTAGES...\n"
            //@@author steven-jia
            + "The transaction type options are: \"payment\" or \"paydebt\". \n"
            + "The split method options are: \"evenly\", \"units\", or \"percentage\". "
            + "Only add a split method if you are recording a payment (i.e. the transaction type is \"payment\"). \n"
            + "Units and percentages must be entered as a comma-separated list. "
            + "The first number in the list is associated with the payer "
            + "and subsequent numbers are associated with each payee in the order in which they are listed. \n"
            + "Example 1: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "payment "
            + PREFIX_PAYER + "John Doe "
            + PREFIX_AMOUNT + "120.00 "
            + PREFIX_DESCRIPTION + "Taxi ride to NUS "
            + PREFIX_PAYEE + "Alex Yeoh "
            + PREFIX_PAYEE + "Bernice Yu "
            + PREFIX_SPLIT_METHOD + "evenly \n"
            + "Example 2: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "payment "
            + PREFIX_PAYER + "Alex Yeoh "
            + PREFIX_AMOUNT + "50.00 "
            + PREFIX_DESCRIPTION + "Team dinner "
            + PREFIX_PAYEE + "John Doe "
            + PREFIX_PAYEE + "Bernice Yu "
            + PREFIX_SPLIT_METHOD + "percentage "
            + PREFIX_SPLIT_BY_PERCENTAGE + "40, 40, 20 \n"
            + "Example 3: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "payment "
            + PREFIX_PAYER + "Bernice Yu "
            + PREFIX_AMOUNT + "35.00 "
            + PREFIX_DESCRIPTION + "Team apparel items "
            + PREFIX_PAYEE + "Alex Yeoh "
            + PREFIX_PAYEE + "John Doe "
            + PREFIX_SPLIT_METHOD + "units "
            + PREFIX_SPLIT_BY_UNITS + "4, 2, 1 \n"
            + "Example 4: " + COMMAND_WORD + " "
            + PREFIX_TRANSACTION_TYPE + "paydebt "
            + PREFIX_PAYER + "Bernice Yu "
            + PREFIX_AMOUNT + "40.00 "
            + PREFIX_DESCRIPTION + "Amount owed for taxi ride "
            + PREFIX_PAYEE + "John Doe";

    public static final String MESSAGE_INVALID_NUMBER_OF_VALUES = "The number of %1$s values does not match"
            + " the number of persons involved. Remember to include the payer in the count.";
    public static final String MESSAGE_INVALID_PERCENTAGE_VALUES = "The sum of the percentages does not equal 100.";
    public static final String MESSAGE_ONLY_ONE_PAYEE_FOR_PAYDEBT = "Paydebt transactions can only have 1 payee";
    public static final String MESSAGE_PAYEE_NOT_OWED_ANY_DEBT = "Payee is not owed any debt";
    public static final String MESSAGE_PAYEE_IS_BEING_OVERPAID = "Payee is being overpaid. Ensure that the transaction "
            + "amount does not exceed the balance owed.";
    public static final String MESSAGE_TOO_MANY_PREFIXES_FOR_PAYDEBT = "Paydebt transactions do not require "
            + "a split method, a list of units, nor a list of percentages. Ensure that those prefixes are not entered.";
    public static final String MESSAGE_NONEXISTENT_PERSON = "The specified payer or payee(s) do not exist";
    public static final String MESSAGE_PAYEE_IS_PAYER = "A payee cannot be the payer";
    public static final String MESSAGE_MISSING_PERCENTAGES_VALUES = "The percentage value is missing.";
    public static final String MESSAGE_MISSING_UNITS_VALUES = "The unit value is missing.";
    //@@author ongkc
    public static final String MESSAGE_SUCCESS = "New transaction added";

    private final Transaction toAdd;

    /**
     * Creates an AddTransactionCommand to add the specified {@code Transaction}
     */
    public AddTransactionCommand(Transaction transaction) {
        requireNonNull(transaction);
        toAdd = transaction;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addTransaction(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(MESSAGE_NONEXISTENT_PERSON);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTransactionCommand// instanceof handles nulls
                && toAdd.equals(((AddTransactionCommand) other).toAdd));
    }
}
