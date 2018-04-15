package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Debtor;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.Transaction;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the list of transactions */
    ObservableList<Transaction> getFilteredTransactionList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /**
     * Removes any filter from the transaction list.
     */
    void updateFilteredTransactionList();

    /**
     * Filters the transaction list by the person.
     * @param person to filter with.
     */
    void updateFilteredTransactionList(Person person);

    ObservableList<Debtor> getFilteredDebtorsList();

    ObservableList<Creditor> getFilteredCreditorsList();

    void updateDebtorsAndCreditorList(Person person);

    void updateDebtorsAndCreditorList();

}
