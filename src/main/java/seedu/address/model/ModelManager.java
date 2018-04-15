package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYEE;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Debtor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Transaction> filteredTransactions;
    private final FilteredList<Debtor> filteredDebtors;
    private final FilteredList<Creditor> filteredCreditors;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTransactions = new FilteredList<>(this.addressBook.getTransactionList());
        filteredDebtors = new FilteredList<>(this.addressBook.getDebtorsList());
        filteredCreditors = new FilteredList<>(this.addressBook.getCreditorsList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException, CommandException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);
        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }
    //@@author steven-jia
    @Override
    public Person findPersonByName(Name name) throws PersonNotFoundException {
        Set<Person> matchingPersons = addressBook.getPersonList()
                .stream()
                .filter(person -> person.getName().equals(name))
                .collect(Collectors.toSet());

        if (!matchingPersons.isEmpty()) {
            return matchingPersons.iterator().next();
        } else {
            throw new PersonNotFoundException();
        }
    }

    public UniquePersonList getPayeesList(ArgumentMultimap argMultimap, Model model)
            throws PersonNotFoundException, IllegalValueException {
        UniquePersonList payees = new UniquePersonList();
        List<String> payeeNamesToAdd = argMultimap.getAllValues(PREFIX_PAYEE);

        if (!payeeNamesToAdd.isEmpty()) {
            for (String payeeName: payeeNamesToAdd) {
                payees.add(model.findPersonByName(ParserUtil.parseName(payeeName)));
            }
        }
        return payees;
    }
    public UniquePersonList getPayeesList(UniquePersonList transactionPayees)
            throws PersonNotFoundException {
        UniquePersonList payees = new UniquePersonList();

        for (Person payee: transactionPayees) {
            try {
                payees.add(findPersonByName(payee.getName()));
            } catch (PersonNotFoundException pnfe) {
                throw new PersonNotFoundException();
            } catch (DuplicatePersonException e) {
                e.printStackTrace();
            }
        }

        return payees;
    }
    //@@author ongkc
    /**
     * Check if the payer or payee in the transaction indicated exists
     */
    @Override
    public boolean personNotFoundInTransaction(Person person) throws PersonFoundException {
        Set<Transaction> matchingTransactions = addressBook.getTransactionList()
                .stream()
                .filter(transaction -> transaction.getPayer().equals(person))
                .collect(Collectors.toSet());

        if (matchingTransactions.isEmpty()) {
            return true;
        } else {
            throw new PersonFoundException();
        }
    }

    @Override
    public List<Transaction> findTransactionsWithPerson(Person person) {
        List<Transaction> matchingTransactions = addressBook.getTransactionList()
                .filtered(transaction -> transaction.getPayer().equals(person));
        return matchingTransactions;
    }
    /**
     * Returns an unmodifiable view of the list of {@code Transaction}
     */
    @Override
    public ObservableList<Transaction> getFilteredTransactionList() {
        return FXCollections.unmodifiableObservableList(filteredTransactions);
    }

    public ObservableList<Debtor> getFilteredDebtors() {
        return FXCollections.unmodifiableObservableList(filteredDebtors);
    }

    public ObservableList<Creditor> getFilteredCreditors() {
        return FXCollections.unmodifiableObservableList(filteredCreditors);
    }
    @Override
    public void addTransaction(Transaction transaction) throws CommandException, PersonNotFoundException {
        addressBook.addTransaction(transaction);
        addressBook.updatePayerAndPayeesBalance(true, transaction, findPersonByName(
                    transaction.getPayer().getName()), getPayeesList(transaction.getPayees()));
        updateFilteredTransactionList(PREDICATE_SHOW_ALL_TRANSACTIONS);
        updateDebtorList(PREDICATE_SHOW_NO_DEBTORS);
        updateCreditorList(PREDICATE_SHOW_NO_CREDITORS);
        updateFilteredPersonList(PREDICATE_SHOW_NO_PERSON);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author phmignot
    @Override
    public void deleteTransaction(Transaction target) throws TransactionNotFoundException, PersonNotFoundException {
        addressBook.updatePayerAndPayeesBalance(false, target,
                findPersonByName(target.getPayer().getName()), getPayeesList(target.getPayees()));
        addressBook.removeTransaction(target);
        updateDebtorList(PREDICATE_SHOW_NO_DEBTORS);
        updateCreditorList(PREDICATE_SHOW_NO_CREDITORS);
        updateFilteredPersonList(PREDICATE_SHOW_NO_PERSON);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //@@author ongkc
    @Override
    public void updateDebtorList(Predicate<Debtor> predicate) {
        requireNonNull(predicate);
        filteredDebtors.setPredicate(predicate);
    }

    @Override
    public void updateCreditorList(Predicate<Creditor> predicate) {
        requireNonNull(predicate);
        filteredCreditors.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTransactionList(Predicate<Transaction> predicate) {
        requireNonNull(predicate);
        filteredTransactions.setPredicate(predicate);
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }
}
