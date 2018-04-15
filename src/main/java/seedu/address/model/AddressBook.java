package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddTransactionCommand.MESSAGE_ONLY_ONE_PAYEE_FOR_PAYDEBT;
import static seedu.address.logic.commands.AddTransactionCommand.MESSAGE_PAYEE_IS_BEING_OVERPAID;
import static seedu.address.logic.commands.AddTransactionCommand.MESSAGE_PAYEE_NOT_OWED_ANY_DEBT;
import static seedu.address.logic.commands.DeletePersonCommand.MESSAGE_DEBT_NOT_PAID;
import static seedu.address.logic.util.CalculationUtil.calculateAmountToAddForPayee;
import static seedu.address.logic.util.CalculationUtil.calculateAmountToAddForPayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Balance;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Debtor;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniqueCreditorList;
import seedu.address.model.person.UniqueDebtorList;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionList;
import seedu.address.model.transaction.TransactionType;
import seedu.address.model.transaction.exceptions.TransactionNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final TransactionList transactions;
    private UniqueDebtorList debtors;
    private UniqueCreditorList creditors;
    private DebtsTable debtsTable;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        transactions = new TransactionList();
        debtors = new UniqueDebtorList();
        creditors = new UniqueCreditorList();
        debtsTable = new DebtsTable();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    public void setPersons(List<Person> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }
    //@@author ongkc
    public void setDebtors(DebtsList debtsList)  {
        this.debtors.setDebtors(debtsList);
    }

    public void setCreditors(DebtsList debtsList) {
        this.creditors.setCreditors(debtsList); }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions.setTransactions(transactions);
    }
    public void setDebtsTable(DebtsTable debtsTable) {
        final DebtsTable replacement = new DebtsTable();
        for (DebtsTable.Entry<Person, DebtsList> entry : debtsTable.entrySet()) {
            DebtsList debtsList = new DebtsList();
            debtsList.putAll(entry.getValue());
            replacement.put(entry.getKey(), debtsList);
        }
        this.debtsTable = replacement;
    }
    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setTags(new HashSet<>(newData.getTagList()));
        List<Person> syncedPersonList = newData.getPersonList().stream()
                .map(this::syncWithMasterTagList)
                .collect(Collectors.toList());
        List<Transaction> syncedTransactionList = newData.getTransactionList();
        DebtsTable syncedDebtsTable = newData.getDebtsTable();
        try {
            setPersons(syncedPersonList);
            setTransactions(syncedTransactionList);
            setDebtsTable(syncedDebtsTable);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("SmartSplit should not have duplicate persons");
        }
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(Person p) throws DuplicatePersonException {
        Person person = syncWithMasterTagList(p);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(person);
        debtsTable.add(person);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncWithMasterTagList(Person)
     */
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedPerson);

        Person syncedEditedPerson = syncWithMasterTagList(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, syncedEditedPerson);
        debtsTable.setPerson(target, editedPerson);
        transactions.setPerson(target, editedPerson);
    }

    /**
     *  Updates the master tag list to include tags in {@code person} that are not in the list.
     *  @return a copy of this {@code person} such that every tag in this person points to a Tag object in the master
     *  list.
     */
    private Person syncWithMasterTagList(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        return new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getBalance(),
                correctTagReferences);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(Person key) throws PersonNotFoundException, CommandException {
        //@@author ongkc
        if (debtExists(key)) {
            throw new CommandException(String.format(MESSAGE_DEBT_NOT_PAID, key));
        }
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
    //@@author ongkc
    /**
     * check if the person to be deleted still owed any unpaid debt
     */
    private boolean debtExists(Person key) {
        DebtsList debtsList = debtsTable.get(key);
        if (debtsList != null) {
            for (Balance value : debtsList.values()) {
                if (value.getDoubleValue() != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, "
                + tags.asObservableList().size() +  " tags, "
                + transactions.asObservableList().size() + " transactions";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public DebtsTable getDebtsTable() {
        return debtsTable;
    }

    @Override
    public ObservableList<Transaction> getTransactionList() {
        return transactions.asObservableList();
    }

    public ObservableList<Debtor> getDebtorsList() {
        return debtors.asObservableList();
    }

    public ObservableList<Creditor> getCreditorsList() {
        return creditors.asObservableList();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
    //@@author ongkc
    /**
     * Adds a {@code transaction} to the list of transactions.
     */
    public void addTransaction(Transaction transaction) throws CommandException {
        //@@author steven-jia
        if (transaction.getTransactionType().toString().toLowerCase()
                .equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            if (transaction.getPayees().asObservableList().size() > 1) {
                throw new CommandException(MESSAGE_ONLY_ONE_PAYEE_FOR_PAYDEBT);
            }
            Person payeeToFind = transaction.getPayees().asObservableList().get(0);
            if (isNotOwedAnyDebt(transaction, payeeToFind)) {
                throw new CommandException(MESSAGE_PAYEE_NOT_OWED_ANY_DEBT);
            } else if (isBeingOverpaid(transaction, payeeToFind)) {
                throw new CommandException(MESSAGE_PAYEE_IS_BEING_OVERPAID);
            }
        }
        //@@author ongkc
        transactions.add(transaction);
        debtsTable.updateDebts(transaction, true);
        debtsTable.display();
    }

    /**
     * Update each payer and payee(s) balance whenever each new transaction is added or deleted
     */
    public void updatePayerAndPayeesBalance(Boolean isAddingTransaction, Transaction transaction, Person payer,
                                            UniquePersonList payees) {
        if (!transaction.getTransactionType().value.toLowerCase().equals(TransactionType.TRANSACTION_TYPE_PAYDEBT)) {
            updatePayerBalance(isAddingTransaction, transaction, payer);
            //@@author steven-jia
            for (int i = 0; i < payees.asObservableList().size(); i++) {
                Person payee = payees.asObservableList().get(i);
                Integer splitMethodValuesListIndex = i + 1;
                updatePayeeBalance(payee, isAddingTransaction, splitMethodValuesListIndex, transaction);
            }
            //@@author
        }
    }

    /**
     * Update payer balance whenever a new transaction is added or deleted
     */
    private void updatePayerBalance(Boolean isAddingTransaction, Transaction transaction, Person payer) {
        payer.addToBalance(calculateAmountToAddForPayer(isAddingTransaction, transaction));
    }
    /**
     * Update payee balance whenever a new transaction is added or deleted
     */
    private void updatePayeeBalance(Person payee,
                                    Boolean isAddingTransaction,
                                    Integer splitMethodValuesListIndex,
                                    Transaction transaction) {
        payee.addToBalance(calculateAmountToAddForPayee(isAddingTransaction,
                    splitMethodValuesListIndex, transaction));
    }
    //@author phmignot
    /**
     * Removes {@code target} from the list of transactions.
     * @throws TransactionNotFoundException if the {@code target} is not in the list of transactions.
     */
    public void removeTransaction(Transaction target) throws TransactionNotFoundException {
        transactions.remove(target);
        debtsTable.updateDebts(target, false);
        debtsTable.display();
    }

    //@@author steven-jia
    private boolean isNotOwedAnyDebt(Transaction transaction, Person payeeToFind) {
        return debtsTable.size() != 0
                && (debtsTable.get(transaction.getPayer()).get(payeeToFind) == null
                || debtsTable.get(transaction.getPayer()).get(payeeToFind).getDoubleValue() <= 0);
    }

    private boolean isBeingOverpaid(Transaction transaction, Person payeeToFind) {
        return transaction.getAmount().getDoubleValue()
                > debtsTable.get(transaction.getPayer()).get(payeeToFind).getDoubleValue();
    }

}
