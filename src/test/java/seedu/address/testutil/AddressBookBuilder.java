package seedu.address.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalTransactions.getTypicalTransactions;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        try {
            addressBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTag(String tagName) {
        try {
            addressBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    //@phmignot
    /**
     * Adds a new {@code transaction} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTransaction(Transaction transaction) {
        requireNonNull(transaction);
        try {
            addressBook.addTransaction(transaction);
        } catch (CommandException ce) {
            System.out.println(ce.getMessage());
        }
        return this;
    }
    public AddressBook build() {
        return addressBook;
    }

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Transaction transaction : getTypicalTransactions()) {
            try {
                ab.addTransaction(transaction);
            } catch (CommandException ce) {
                System.out.println(ce.getMessage());
            }
        }
        return ab;
    }
}
