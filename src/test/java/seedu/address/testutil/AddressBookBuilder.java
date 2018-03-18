package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.RecipeBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code RecipeBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private RecipeBook recipeBook;

    public AddressBookBuilder() {
        recipeBook = new RecipeBook();
    }

    public AddressBookBuilder(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    /**
     * Adds a new {@code Person} to the {@code RecipeBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        try {
            recipeBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code RecipeBook} that we are building.
     */
    public AddressBookBuilder withTag(String tagName) {
        try {
            recipeBook.addTag(new Tag(tagName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tagName is expected to be valid.");
        }
        return this;
    }

    public RecipeBook build() {
        return recipeBook;
    }
}
