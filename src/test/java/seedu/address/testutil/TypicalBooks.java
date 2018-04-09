package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISBN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Catalogue;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * A utility class containing a list of {@code Book} objects to be used in tests.
 */
public class TypicalBooks {

    public static final Book ANIMAL = new BookBuilder().withTitle("Animal Farm")
        .withAuthor("George Orwell")
        .withAvail("Available")
        .withIsbn("9780736692427")
        .withTags("political", "satire").build();
    public static final Book BREAKING = new BookBuilder().withTitle("Breaking Dawn")
        .withAuthor("Stephenie Meyer")
        .withAvail("Available")
        .withIsbn("9780316067928")
        .withTags("fiction").build();
    public static final Book CALIFORNIA = new BookBuilder().withTitle("California Girl")
        .withAuthor("Jefferson Parker")
        .withIsbn("9780060562373")
        .withAvail("Available")
        .withTags("unlabelled").build();
    public static final Book DELIRIUM = new BookBuilder().withTitle("Delirium")
        .withAuthor("Lauren Oliver")
        .withIsbn("9780061726835")
        .withAvail("Available").build();
    public static final Book EMMA = new BookBuilder().withTitle("Emma")
        .withAuthor("Jane Austen")
        .withIsbn("9780141439587")
        .withAvail("Available").build();
    public static final Book FATEFUL = new BookBuilder().withTitle("Fateful")
        .withAuthor("Claudia Gray")
        .withIsbn("9780062006202")
        .withAvail("Available").build();
    public static final Book GONE = new BookBuilder().withTitle("Gone Girl")
        .withAuthor("Gillian Flynn")
        .withIsbn("9780753827666")
        .withAvail("Available").build();

    // Manually added
    public static final Book HOLES = new BookBuilder().withTitle("Holes")
        .withAuthor("Louis Sachar")
        .withIsbn("9780439244190")
        .withAvail("Available").build();
    public static final Book INVISIBLE = new BookBuilder().withTitle("Invisible Man")
        .withAuthor("Ralph Ellison")
        .withIsbn("9780140023350")
        .withAvail("Available").build();

    // Manually added - Book's details found in {@code CommandTestUtil}
    public static final Book AMY = new BookBuilder().withTitle(VALID_TITLE_AMY)
        .withAuthor(VALID_AUTHOR_AMY)
        .withIsbn(VALID_ISBN_AMY)
        .withAvail(VALID_AVAIL_AMY)
        .withTags(VALID_TAG_FRIEND).build();
    public static final Book BOB = new BookBuilder().withTitle(VALID_TITLE_BOB)
        .withAuthor(VALID_AUTHOR_BOB)
        .withIsbn(VALID_ISBN_BOB)
        .withAvail(VALID_AVAIL_BOB)
        .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
        .build();

    public static final String KEYWORD_MATCHING_GIRL = "Girl"; // A keyword that matches GIRL
    public static final String KEYWORD_MATCHING_BREAKING = "Breaking"; // A keyword that matches BREAKING

    private TypicalBooks() {
    } // prevents instantiation

    /**
     * Returns an {@code Catalogue} with all the typical books.
     */
    public static Catalogue getTypicalCatalogue() {
        Catalogue ab = new Catalogue();
        for (Book book : getTypicalBooks()) {
            try {
                ab.addBook(book);
            } catch (DuplicateBookException e) {
                throw new AssertionError("not possible");
            }
        }
        return ab;
    }

    public static List<Book> getTypicalBooks() {
        return new ArrayList<>(Arrays.asList(ANIMAL, BREAKING, CALIFORNIA, DELIRIUM, EMMA, FATEFUL, GONE));
    }
}
