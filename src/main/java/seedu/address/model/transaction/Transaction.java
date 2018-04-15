//@@author steven-jia
package seedu.address.model.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a Transaction in SmartSplit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {
    private static Integer lastTransactionId = 0;
    private Integer id;
    private Date dateTime;
    private Person payer;
    private final Amount amount;
    private final Description description;
    private UniquePersonList payees;
    private TransactionType transactionType;
    private final SplitMethod splitMethod;
    private ArrayList<Integer> units;
    private ArrayList<Integer> percentages;

    public Transaction(TransactionType transactionType, Person payer, Amount amount, Description description,
                       Date dateTime, UniquePersonList payees, SplitMethod splitMethod, List<Integer> units,
                       List<Integer> percentages) {
        this.transactionType = transactionType;
        this.dateTime = dateTime;
        this.id = lastTransactionId++;
        this.payer = payer;
        this.amount = amount;
        this.description = description;
        this.payees = payees;
        this.splitMethod = splitMethod;
        initializeSplitMethodListValues(units, percentages);
    }

    public Transaction(Transaction transaction) {
        this.transactionType = transaction.getTransactionType();
        this.dateTime = transaction.getDateTime();
        this.id = transaction.getId();
        this.payer = transaction.getPayer();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.payees = transaction.getPayees();
        this.splitMethod = transaction.getSplitMethod();
        this.units = transaction.getUnits();
        this.percentages = transaction.getPercentages();
    }
    /**
     * @param units
     * @param percentages
     * Initializes the split method units list if the split method is by units
     * or initializes the split method percentages list if the split method is by percentage.
     */
    private void initializeSplitMethodListValues(List<Integer> units, List<Integer> percentages) {
        if (this.splitMethod.toString().equals(SplitMethod.SPLIT_METHOD_UNITS)) {
            this.units = new ArrayList<>(units);
            this.percentages = new ArrayList<>();
        } else if (this.splitMethod.toString().equals(SplitMethod.SPLIT_METHOD_PERCENTAGE)) {
            this.units = new ArrayList<>();
            this.percentages = new ArrayList<>(percentages);
        } else {
            this.units = new ArrayList<>();
            this.percentages = new ArrayList<>();
        }
    }
    public void setTransactionType(String transactionType) {
        this.transactionType = new TransactionType(transactionType);
    }
    public Integer getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Person getPayer() {
        return payer;
    }

    public void setPayer(Person payer) {
        this.payer = payer;
    }

    public Amount getAmount() {
        return amount;
    }

    public Description getDescription() {
        return description;
    }

    public UniquePersonList getPayees() {
        return payees;
    }

    public void setPayees(UniquePersonList payees) {
        this.payees = payees;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public SplitMethod getSplitMethod() {
        return splitMethod;
    }

    public ArrayList<Integer> getUnits() {
        return units;
    }

    public ArrayList<Integer> getPercentages() {
        return percentages;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transactionType, dateTime, payer, amount,
                description, payees, splitMethod, units, percentages);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Transaction id: ")
                .append(getId())
                .append("\n Transaction Type: ")
                .append(getTransactionType())
                .append("\n Created on: ")
                .append(getDateTime())
                .append("\n Transaction paid by: ")
                .append(getPayer().getName())
                .append("\n Amount: ")
                .append(getAmount().toString())
                .append("\n Description: ")
                .append(getDescription().toString())
                .append("\n Payees: ")
                .append(getPayees().asObservableList().toString())
                .append("\n Split method: ")
                .append(getSplitMethod().toString())
                .append("\n Units list: ")
                .append(getUnits().toString())
                .append("\n Percentages list: ")
                .append(getPercentages().toString());
        return builder.toString();
    }

    /**
     * Tests if a person is implied in this transaction.
     * @param person to check his implication.
     * @return true if the person is the payer or one of the payee;
     * false otherwise.
     */
    public boolean isImplied(Person person) {
        return (payer.equals(person) || payees.contains(person));
    }

}
