package seedu.address.model.person;
//@@author ongkc
/**
 * Represents a Debtor in SmartSplit.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Debtor {

    private final Person debtor;
    private Balance debt = new Balance("0.00");

    public Debtor(Person debtor, Balance debt) {
        this.debtor = debtor;
        this.debt = debt;

    }

    public Person getDebtor() {
        return debtor;
    }

    public Balance getDebt() {
        return debt; }

}


