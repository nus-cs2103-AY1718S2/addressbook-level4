package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.book.Author;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Title;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 * {@code ParserUtil} contains methods that take in {@code Optional} as parameters. However, it goes against Java's
 * convention (see https://stackoverflow.com/a/39005452) as {@code Optional} should only be used a return type.
 * Justification: The methods in concern receive {@code Optional} return values from other methods as parameters and
 * return {@code Optional} values based on whether the parameters were present. Therefore, it is redundant to unwrap the
 * initial {@code Optional} before passing to {@code ParserUtil} as a parameter and then re-wrap it into an
 * {@code Optional} return value inside {@code ParserUtil} methods.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String title} into a {@code Title}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Title parseTitle(String title) {
        requireNonNull(title);
        String trimmedTitle = title.trim();
        return new Title(trimmedTitle);
    }

    /**
     * Parses a {@code Optional<String> title} into an {@code Optional<Title>} if {@code title} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Title> parseTitle(Optional<String> title) {
        requireNonNull(title);
        return title.isPresent() ? Optional.of(parseTitle(title.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String author} into a {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Author parseAuthor(String author) {
        requireNonNull(author);
        String trimmedAuthor = author.trim();
        return new Author(trimmedAuthor);
    }

    /**
     * Parses {@code Collection<String> authors} into a {@code Set<Author>}.
     */
    public static Set<Author> parseAuthors(Collection<String> authors) {
        requireNonNull(authors);
        final Set<Author> authorSet = new HashSet<>();
        for (String author : authors) {
            authorSet.add(parseAuthor(author));
        }
        return authorSet;
    }

    /**
     * Parses a {@code String category} into a {@code Category}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Category parseCategory(String category) {
        requireNonNull(category);
        String trimmedCategory = category.trim();
        return new Category(trimmedCategory);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>}.
     */
    public static Set<Category> parseCategories(Collection<String> categories) {
        requireNonNull(categories);
        final Set<Category> categorySet = new HashSet<>();
        for (String category : categories) {
            categorySet.add(parseCategory(category));
        }
        return categorySet;
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Description parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code Optional<String> description} into an {@code Optional<Description>}
     * if {@code description} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Description> parseDescription(Optional<String> description) {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(parseDescription(description.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String rating} into a {@code Rating}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Rating parseRating(int rating) {
        requireNonNull(rating);
        return new Rating(rating);
    }

    /**
     * Parses a {@code Optional<String> rating} into an {@code Optional<Rating>}
     * if {@code rating} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Rating> parseRating(Optional<Integer> rating) {
        requireNonNull(rating);
        return rating.isPresent() ? Optional.of(parseRating(rating.get())) : Optional.empty();
    }

}
