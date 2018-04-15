package seedu.address.testutil;

import seedu.address.model.person.Balance;
import seedu.address.model.person.Creditor;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;
//@@author ongkc
/**
 * A utility class to help with building Creditor objects.
 */
public class CreditorBuilder {

    public static final Person DEFAULT_PERSON = SampleDataUtil.getSamplePersons()[0];
    public static final String DEFAULT_DEBT = "0.00";

    private Person person;
    private Balance debt;

    public CreditorBuilder() {
        person = DEFAULT_PERSON;
        debt = new Balance(DEFAULT_DEBT);

    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public CreditorBuilder(Creditor creditorToCopy) {
        person = creditorToCopy.getCreditor();
        debt = creditorToCopy.getDebt();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public CreditorBuilder withCreditor(Person person) {
        this.person = person;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public CreditorBuilder withDebt(String debt) {
        this.debt = new Balance(debt);
        return this;
    }


    public Creditor build() {
        return new Creditor(person, debt);
    }

}
