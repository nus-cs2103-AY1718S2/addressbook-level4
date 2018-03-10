package seedu.address.model.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Title;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code BookShelf} with sample data.
 */
public class SampleDataUtil {

    public static Book[] getSampleBooks() {
        return new Book[]{
            new Book(Collections.singleton(new Author("Andy Weir")), new Title("Artemis"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction")),
                new Description("This is Artemis.")),
            new Book(Collections.singleton(new Author("Sylvain Neuvel")), new Title("Waking Gods"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction")),
                new Description("This is Waking Gods.")),
            new Book(Collections.singleton(new Author("James S. A. Corey")), new Title("Babylon's Ashes"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is Babylon's Ashes.")),
            new Book(Collections.singleton(new Author("John Scalzi")), new Title("The Collapsing Empire"),
                CollectionUtil.toSet(new Category("Fiction"), new Category("Science Fiction"),
                    new Category("Space Opera")),
                new Description("This is The Collapsing Empire."))
        };
    }

    public static ReadOnlyBookShelf getSampleBookShelf() {
        try {
            BookShelf sampleBookShelf = new BookShelf();
            for (Book sampleBook : getSampleBooks()) {
                sampleBookShelf.addBook(sampleBook);
            }
            return sampleBookShelf;
        } catch (DuplicateBookException e) {
            throw new AssertionError("sample data cannot contain duplicate books", e);
        }
    }

    @Deprecated
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    @Deprecated
    public static ReadOnlyAddressBook getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Person samplePerson : getSamplePersons()) {
                sampleAb.addPerson(samplePerson);
            }
            return sampleAb;
        } catch (DuplicatePersonException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    @Deprecated
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
