package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.category.TaskCategory;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;

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

    /**
     * Adds a new {@code Task} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withTask(Task task) {
        try {
            addressBook.addTask(task);
        } catch (DuplicateTaskException dte) {
            throw new IllegalArgumentException("task is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code taskCategoryName} into a {@code TaskCategory} and adds it to the {@code AddressBook}
     * that we are building.
     * @param taskCategory
     * @return
     */
    public AddressBookBuilder withTaskCategory(String taskCategory) {
        try {
            addressBook.addTaskCategory(new TaskCategory(taskCategory));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("taskCategoryName is expected to be valid.");
        }
        return this;
    }


    public AddressBook build() {
        return addressBook;
    }
}
