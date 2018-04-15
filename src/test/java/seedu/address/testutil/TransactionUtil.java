package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPLIT_METHOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class for Transaction.
 */
public class TransactionUtil {

    /**
     * Returns an add command string for adding the {@code transaction}.
     */
    public static String getAddTransactionCommand(Transaction transaction) {
        return AddTransactionCommand.COMMAND_WORD + " " + getTransactionDetails(transaction);
    }

    /**
     * Returns the part of command string for the given {@code transaction}'s details.
     */
    public static String getTransactionDetails(Transaction transaction) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TRANSACTION_TYPE + transaction.getTransactionType().toString() + " ");
        sb.append(PREFIX_PAYER + transaction.getPayer().getName().fullName + " ");
        sb.append(PREFIX_AMOUNT + transaction.getAmount().value + " ");
        sb.append(PREFIX_DESCRIPTION + transaction.getDescription().value + " ");
        sb.append(PREFIX_PAYEE + "Benson Meier" + " ");
        sb.append(PREFIX_SPLIT_METHOD + transaction.getSplitMethod().method.toString() + " ");
        return sb.toString();
    }

}
