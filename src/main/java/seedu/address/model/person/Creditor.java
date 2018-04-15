package seedu.address.model.person;
//@@author ongkc
/**
 * Represents a Creditor in SmartSplit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Creditor {

    private final Person creditor;
    private Balance debt = new Balance("0.00");

    public Creditor(Person creditor, Balance debt) {
        this.creditor = creditor;
        this.debt = debt;

    }

    public Person getCreditor() {
        return creditor;
    }

    public Balance getDebt() {
        return debt; }
}
