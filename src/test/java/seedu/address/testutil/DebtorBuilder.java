package seedu.address.testutil;

import seedu.address.model.person.Balance;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Debtor;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
//@@author ongkc
/**
 * A utility class to help with building Debtor objects.
 */
public class DebtorBuilder {
    public static final Person DEFAULT_PERSON = SampleDataUtil.getSamplePersons()[0];
    public static final String DEFAULT_DEBT = "0.00";

    private Person person;
    private Balance debt;

    public DebtorBuilder() {
        person = DEFAULT_PERSON;
        debt = new Balance(DEFAULT_DEBT);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public DebtorBuilder(Creditor creditorToCopy) {
        person = creditorToCopy.getCreditor();
        debt = creditorToCopy.getDebt();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public DebtorBuilder withDebtor(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public DebtorBuilder withDebt(String debt) {
        this.debt = new Balance(debt);
        return this;
    }


    public Debtor build() {
        return new Debtor(person, debt);
    }

}
