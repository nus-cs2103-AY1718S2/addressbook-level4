package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Comparator;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Parses input arguments and creates a new ListCommand object.
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_AUTHOR,
                PREFIX_CATEGORY, PREFIX_STATUS, PREFIX_PRIORITY, PREFIX_RATING, PREFIX_SORT_BY);

        ListCommand.FilterDescriptor filterDescriptor = new ListCommand.FilterDescriptor();
        argMultimap.getValue(PREFIX_TITLE).ifPresent(filterDescriptor::addTitleFilter);
        argMultimap.getValue(PREFIX_AUTHOR).ifPresent(filterDescriptor::addAuthorFilter);
        argMultimap.getValue(PREFIX_CATEGORY).ifPresent(filterDescriptor::addCategoryFilter);

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            filterDescriptor.addStatusFilter(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }

        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            filterDescriptor.addPriorityFilter(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }

        if (argMultimap.getValue(PREFIX_RATING).isPresent()) {
            filterDescriptor.addRatingFilter(ParserUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get()));
        }

        Comparator<Book> comparator = Model.DEFAULT_BOOK_COMPARATOR;
        if (argMultimap.getValue(PREFIX_SORT_BY).isPresent()) {
            SortMode sortMode = SortMode.findSortMode(argMultimap.getValue(PREFIX_SORT_BY).get());
            if (sortMode == null) {
                throw new ParseException(Messages.MESSAGE_INVALID_SORT_BY);
            }
            comparator = sortMode.comparator;
        }

        return new ListCommand(filterDescriptor, comparator);
    }

    /**
     * Represents a sorting mode, which specifies the comparator used to sort the display book list.
     */
    public enum SortMode {
        TITLE(Comparator.comparing(Book::getTitle), "t", "titlea", "ta"),
        TITLED((book1, book2) -> book2.getTitle().compareTo(book1.getTitle()), "td"),
        STATUS(Comparator.comparing(Book::getStatus), "s", "statusa", "sa"),
        STATUSD((book1, book2) -> book2.getStatus().compareTo(book1.getStatus()), "sd"),
        PRIORITY(Comparator.comparing(Book::getPriority), "p", "prioritya", "pa"),
        PRIORITYD((book1, book2) -> book2.getPriority().compareTo(book1.getPriority()), "pd"),
        RATING(Comparator.comparing(Book::getRating), "r", "ratinga", "ra"),
        RATINGD((book1, book2) -> book2.getRating().compareTo(book1.getRating()), "rd");

        private final Comparator<Book> comparator;
        private final String[] aliases;

        SortMode(Comparator<Book> comparator, String... aliases) {
            this.comparator = comparator;
            this.aliases = aliases;
        }

        public Comparator<Book> getComparator() {
            return comparator;
        }

        /**
         * Returns the {@code SortMode} with a name or alias that matches the specified {@code searchTerm}.
         */
        private static SortMode findSortMode(String searchTerm) {
            for (SortMode sortMode : values()) {
                if (Stream.of(sortMode.aliases).anyMatch(searchTerm::equalsIgnoreCase)
                        || searchTerm.equalsIgnoreCase(sortMode.toString())) {
                    return sortMode;
                }
            }

            return null;
        }

    }
}
