package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.util.CalculationUtil.calculateAmountToAddForPayee;

import java.util.HashMap;

import seedu.address.model.person.Balance;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.Transaction;

/**
 * Stores all the debts between the persons in SmartSplit.
 */
//@@author phmignot
public class DebtsTable extends HashMap<Person, DebtsList> {

    public DebtsTable() {
        super();
    }

    /**
     * Updates the DebtsTable due to a transaction
     * payeeDebt is a negative {@Code Balance} value, because the payee owes money.
     * payerDebt is a positive {@Code Balance} value, because the payer is owed.
     * @param transaction to register the table.
     */
    public void updateDebts(Transaction transaction, Boolean isAddingTransaction) {
        Person payer = transaction.getPayer();
        if (!this.containsKey(payer)) {
            this.add(payer);
            System.out.println("Adding payer " + payer.getName().fullName);
        }
        DebtsList payerDebtsList = this.get(payer);
        for (int i = 0; i < transaction.getPayees().asObservableList().size(); i++) {
            Person payee = transaction.getPayees().asObservableList().get(i);
            if (!this.containsKey(payee)) {
                this.add(payee);
                System.out.println("Adding payee " + payee.getName().fullName);
            }
            Balance payerDebtToAdd = calculateAmountToAddForPayee(isAddingTransaction,
                    i + 1, transaction);
            Balance payeeDebtToAdd = payerDebtToAdd.getInverse();
            DebtsList payeeDebtsList = this.get(payee);
            payerDebtsList.updateDebt(payee, payeeDebtToAdd);
            payeeDebtsList.updateDebt(payer, payerDebtToAdd);
        }
    }

    public void add(Person personToAdd) {
        this.putIfAbsent(personToAdd, new DebtsList());
    }


    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     */
    public void setPerson(Person target, Person editedPerson) throws PersonNotFoundException, DuplicatePersonException {
        requireNonNull(editedPerson);
        requireNonNull(target);
        if (!this.containsKey(target)) {
            throw new PersonNotFoundException();
        }
        if (!target.equals(editedPerson) && this.containsKey(editedPerson)) {
            throw new DuplicatePersonException();
        }
        DebtsList targetDebtsList = this.remove(target);
        this.put(editedPerson, targetDebtsList);
        this.replaceAll(((person, debtsList) -> {
            debtsList.setPerson(target, editedPerson);
            return debtsList;
        }));

    }

    /**
     * Displays the content of the Debts Table in the terminal.
     */
    public void display() {
        System.out.println("DEBTS TABLE : ");
        this.forEach(((person, debtsList) -> {
            System.out.println(person.getName().fullName + ": ");
            debtsList.display();
            System.out.println();
        }));
    }
}

