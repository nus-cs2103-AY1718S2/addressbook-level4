package seedu.address.logic;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CREDITORS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DEBTORS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_CREDITORS;
import static seedu.address.model.Model.PREDICATE_SHOW_NO_DEBTORS;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DebtsList;
import seedu.address.model.DebtsTable;
import seedu.address.model.Model;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Debtor;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionContainsPersonPredicate;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final AddressBookParser addressBookParser;
    private final UndoRedoStack undoRedoStack;

    public LogicManager(Model model) {
        this.model = model;
        history = new CommandHistory();
        addressBookParser = new AddressBookParser(model);
        undoRedoStack = new UndoRedoStack();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException, PersonNotFoundException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = addressBookParser.parseCommand(commandText);
            command.setData(model, history, undoRedoStack);
            CommandResult result = command.execute();
            undoRedoStack.push(command);
            return result;
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return model.getFilteredTransactionList();
    }

    @Override
    public ObservableList<Debtor> getFilteredDebtorsList() {
        return model.getFilteredDebtors();
    }

    @Override
    public ObservableList<Creditor> getFilteredCreditorsList() {
        return model.getFilteredCreditors();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
    //@author phmignot
    /**
     * Update the transaction list to show all transaction
     */
    @Override
    public void updateFilteredTransactionList() {
        model.updateFilteredTransactionList(Model.PREDICATE_SHOW_ALL_TRANSACTIONS);
    }
    /**
     * Update the transaction list to show transactions of the selected person
     */
    @Override
    public void updateFilteredTransactionList(Person person) {
        TransactionContainsPersonPredicate predicate = new TransactionContainsPersonPredicate(person);
        model.updateFilteredTransactionList(predicate);
    }
    //@@author ongkc
    /**
     * Update the Debtor and Creditor list to an empty list
     */
    @Override
    public void updateDebtorsAndCreditorList() {
        model.updateDebtorList(PREDICATE_SHOW_NO_DEBTORS);
        model.updateCreditorList(PREDICATE_SHOW_NO_CREDITORS);
    }
    /**
     * Update the people in the Debtor and Creditor list
     */
    public void updateDebtorsAndCreditorList(Person person) {
        model.updateCreditorList(PREDICATE_SHOW_ALL_CREDITORS);
        model.updateDebtorList(PREDICATE_SHOW_ALL_DEBTORS);
        DebtsTable debtsTable = model.getAddressBook().getDebtsTable();
        DebtsList debtsList = debtsTable.get(person);
        model.getAddressBook().setDebtors(debtsList);
        model.getAddressBook().setCreditors(debtsList);
    }


}
