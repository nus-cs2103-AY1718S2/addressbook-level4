package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;

import seedu.address.model.UniqueList;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.book.exceptions.DuplicateBookException;

/**
 * Represents a unique collection of books. Does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueBookList extends UniqueList<Book> {

    /**
     * Adds a book to the list.
     *
     * @throws DuplicateBookException if the book to add is a duplicate of an existing book in the list.
     */
    public void add(Book toAdd) throws DuplicateBookException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBookException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the book {@code target} in the list with {@code editedBook}.
     *
     * @throws BookNotFoundException if {@code target} could not be found in the list.
     * @throws DuplicateBookException if the replacement is equivalent to another existing book in the list.
     */
    public void setBook(Book target, Book editedBook)
            throws BookNotFoundException, DuplicateBookException {
        requireNonNull(editedBook);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BookNotFoundException();
        }

        if (!target.equals(editedBook) && internalList.contains(editedBook)) {
            throw new DuplicateBookException();
        }

        internalList.set(index, editedBook);
    }

    /**
     * Removes the equivalent book from the list.
     *
     * @throws BookNotFoundException if no such book could be found in the list.
     */
    public boolean remove(Book toRemove) throws BookNotFoundException {
        requireNonNull(toRemove);
        final boolean bookFoundAndDeleted = internalList.remove(toRemove);
        if (!bookFoundAndDeleted) {
            throw new BookNotFoundException();
        }
        return bookFoundAndDeleted;
    }

    public void setBooks(Collection<Book> books) throws DuplicateBookException {
        requireAllNonNull(books);
        final UniqueBookList replacement = new UniqueBookList();
        for (final Book book : books) {
            replacement.add(book);
        }
        internalList.setAll(replacement.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueBookList // instanceof handles nulls
                && this.internalList.equals(((UniqueBookList) other).internalList));
    }

}
