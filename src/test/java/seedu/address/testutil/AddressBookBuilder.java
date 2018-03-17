package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withGroups("Friend").build();}
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
     * Parses {@code groupName} into a {@code Group} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withGroup(String groupName) {
        try {
            addressBook.addGroup(new Group(groupName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("groupName is expected to be valid.");
        }
        return this;
    }

    /**
     * Parses {@code prefName} into a {@code Preference} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPreference(String prefName) {
        try {
            addressBook.addPreference(new Preference(prefName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("prefName is expected to be valid.");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
