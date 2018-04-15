# deeheenguyen
###### \java\seedu\address\logic\commands\AddInterviewCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.DuplicateInterviewException;

/***
 * Adds an Interview to AddressBook
 */
public class AddInterviewCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addInterview";
    public static final String COMMAND_ALIAS = "ai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an interview to the address book.\n"
            + "Parameters: "
            + PREFIX_INTERVIEW + "INTERVIEW "
            + PREFIX_NAME + "INTERVIEWEE "
            + PREFIX_DATE + "INTERVIEW_DATE "
            + PREFIX_LOCATION + "INTERVIEW_LOCATION \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INTERVIEW + "SE INTERVIEW "
            + PREFIX_NAME + "DAVID "
            + PREFIX_DATE + "04.05.2018 "
            + PREFIX_LOCATION + "SUNTEX CITY ";

    public static final String MESSAGE_SUCCESS = "New interview added: ";
    public static final String MESSAGE_DUPLICATE_INTERVIEW = "This interview already exists in the address book";

    private final Interview toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Job}
     */
    public AddInterviewCommand(Interview interview) {
        requireNonNull(interview);
        toAdd = interview;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addInterview(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateInterviewException e) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERVIEW);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddInterviewCommand // instanceof handles nulls
                && toAdd.equals(((AddInterviewCommand) other).toAdd));
    }
}
```
###### \java\seedu\address\logic\commands\DeleteInterviewCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RefreshReportPanelEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;



/**
 * Deletes a job identified using it's last displayed index from the address book.
 */
public class DeleteInterviewCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteInterview";
    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the interview identified by the index number used in the last interview listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_INTERVIEW_SUCCESS = "Deleted Interview: %1$s";

    private final Index targetIndex;

    private Interview interviewToDelete;

    public DeleteInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(interviewToDelete);
        try {
            model.deleteInterview(interviewToDelete);
            model.refreshReport();
            EventsCenter.getInstance().post(new RefreshReportPanelEvent());
        } catch (InterviewNotFoundException jnfe) {
            throw new AssertionError("The target interview cannot be missing");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_INTERVIEW_SUCCESS, interviewToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Interview> lastShownList = model.getFilteredInterviewList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        interviewToDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteInterviewCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteInterviewCommand) other).targetIndex) // state check
                && Objects.equals(this.interviewToDelete, ((DeleteInterviewCommand) other).interviewToDelete));
    }

}

```
###### \java\seedu\address\logic\commands\ListInterviewCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INTERVIEWS;

/**
 * Lists all jobs in the address book to the user.
 */
public class ListInterviewCommand extends Command {
    public static final String COMMAND_WORD = "listInterview";

    public static final String MESSAGE_SUCCESS = "Listed all interviews";

    @Override
    public CommandResult execute() {
        model.updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\ViewCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ToggleBrowserPanelEvent;
import seedu.address.model.person.EmailFilter;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the person with the email\n"
            + "Example: " + COMMAND_WORD + " abcd@gmail.com";

    private final EmailFilter predicate;

    public ViewCommand(EmailFilter predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        Index targetIndex = Index.fromOneBased(1);
        EventsCenter.getInstance().post(new ToggleBrowserPanelEvent());
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                && this.predicate.equals(((ViewCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\parser\AddInterviewCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;

import seedu.address.model.person.Name;

/***
 * Parses input arguments and creates a new AddInterview1Command object
 */

public class AddInterviewCommandParser implements Parser<AddInterviewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddInterviewCommand
     * and returns an AddInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INTERVIEW, PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INTERVIEW, PREFIX_DATE, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterviewCommand.MESSAGE_USAGE));
        }

        try {
            InterviewTitle title = ParserUtil.parseInterviewTitle(argMultimap.getValue(PREFIX_INTERVIEW)).get();
            Name interviewee = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            InterviewLocation location = ParserUtil.parseInterviewLocation(argMultimap.getValue(PREFIX_LOCATION)).get();

            Interview interview = new Interview(title, interviewee, date, location);
            return new AddInterviewCommand(interview);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
//author@@
```
###### \java\seedu\address\logic\parser\DeleteInterviewCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteJobCommand object
 */
public class DeleteInterviewCommandParser implements Parser<DeleteInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteInterviewCommand
     * and returns an DeleteInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteInterviewCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteInterviewCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }
    }

}

```
###### \java\seedu\address\logic\parser\FindInterviewCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.FindInterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.interview.InterviewMatchInterviewee;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindInterviewCommandParser implements Parser<FindInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindInterviewCommand
     * and returns an FindInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindInterviewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        if (keywords.length > 1 || keywords.length == 0) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }
        if (!Name.isValidName(keywords[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindInterviewCommand.MESSAGE_USAGE));
        }
        return new FindInterviewCommand(new InterviewMatchInterviewee(keywords[0]));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String string} into a trimmed {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code string} is invalid.
     */
    public static InterviewLocation parseInterviewLocation(String string) throws IllegalValueException {
        requireNonNull(string);
        String trimmedString = string.trim();
        if (!Location.isValidLocation(trimmedString)) {
            throw new IllegalValueException(InterviewLocation.MESSAGE_LOCATION_CONSTRAINTS);
        }
        return new InterviewLocation(trimmedString);
    }

    /**
     * Parses a {@code Optional<String> string} into an {@code Optional<Location>} if {@code string} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<InterviewLocation> parseInterviewLocation(Optional<String> string)
            throws IllegalValueException {
        requireNonNull(string);
        return string.isPresent() ? Optional.of(parseInterviewLocation(string.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String link} into an {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Link parseLink(String link) throws IllegalValueException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!Link.isValidLink(trimmedLink)) {
            throw new IllegalValueException(Link.MESSAGE_LINK_CONSTRAINTS);
        }
        return new Link(link);
    }

    /**
     * Parses a {@code Optional<String> link} into an {@code Optional<Link>} if {@code link} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Link> parseLink(Optional<String> link) throws IllegalValueException {
        requireNonNull(link);
        return link.isPresent() ? Optional.of(parseLink(link.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String link} into an {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static InterviewTitle parseInterviewTitle(String interviewTitle) throws IllegalValueException {
        requireNonNull(interviewTitle);
        String trimmedInterviewTitle = interviewTitle.trim();
        if (!Link.isValidLink(trimmedInterviewTitle)) {
            throw new IllegalValueException(InterviewTitle.MESSAGE_TITLE_CONSTRAINTS);
        }
        return new InterviewTitle(interviewTitle);
    }

    /**
     * Parses a {@code Optional<String> link} into an {@code Optional<Link>} if {@code link} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<InterviewTitle> parseInterviewTitle(Optional<String> interviewTitle)
                throws IllegalValueException {
        requireNonNull(interviewTitle);
        return interviewTitle.isPresent() ? Optional.of(parseInterviewTitle(interviewTitle.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String link} into an {@code Link}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Date parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(date);
    }

    /**
     * Parses a {@code Optional<String> link} into an {@code Optional<Link>} if {@code link} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }
}
```
###### \java\seedu\address\logic\parser\ViewCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmailFilter;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!Email.isValidEmail(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(new EmailFilter(new Email(trimmedArgs)));
    }
}
//author@@ deeheenguyen
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addInterview(Interview interview) throws DuplicateInterviewException {
        interviews.add(interview);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws InterviewNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeInterview(Interview key) throws InterviewNotFoundException {
        if (interviews.remove(key)) {
            return true;
        } else {
            throw new InterviewNotFoundException();
        }
    }
```
###### \java\seedu\address\model\interview\Date.java
``` java
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Interview's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date must be in format dd.mm.yyyy, and it should not be blank";


    /*
     * The first character of the date  must not be a whitespace,
     * be format dd-mm-yyyy.
     */
    public static final String DATE_VALIDATION_REGEX =
            "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid interview location.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.interview.Date // instanceof handles nulls
                && this.value.equals(((seedu.address.model.interview.Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//author@@ deeheenguyen
```
###### \java\seedu\address\model\interview\Interview.java
``` java
package seedu.address.model.interview;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Name;

/**
 * Represents an Interview in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Interview {
    private InterviewTitle interviewTitle;
    private Name interviewee;
    private Date date;
    private InterviewLocation interviewLocation;

    public Interview(InterviewTitle interviewTitle, Name interviewee, Date date, InterviewLocation location) {
        requireAllNonNull(interviewTitle, interviewee, date, location);
        this.interviewTitle = interviewTitle;
        this.interviewee = interviewee;
        this.date = date;
        this.interviewLocation = location;
    }

    public InterviewTitle getInterviewTitle() {
        return this.interviewTitle;
    }

    public Name getInterviewee() {
        return this.interviewee;
    }

    public Date getDate() {
        return this.date;
    }

    public InterviewLocation getInterviewLocation() {
        return this.interviewLocation;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Interview)) {
            return false;
        }
        Interview otherInterview = (Interview) other;
        return otherInterview.getInterviewTitle().equals(this.getInterviewTitle())
                && otherInterview.getInterviewee().equals(this.getInterviewee())
                && otherInterview.getDate().equals(this.getDate())
                && otherInterview.getInterviewLocation().equals(this.getInterviewLocation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(interviewTitle, interviewee, date, interviewLocation);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Interview Title: ")
                .append(getInterviewTitle())
                .append(" Interviewee: ")
                .append(getInterviewee())
                .append(" Date: ")
                .append(getDate())
                .append(" Interview Location: ")
                .append(getInterviewLocation());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\interview\InterviewLocation.java
``` java
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Interview's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLocation(String)}
 */
public class InterviewLocation {

    public static final String MESSAGE_LOCATION_CONSTRAINTS =
            "Interview locations can take any values, and it should not be blank";


    /*
     * The first character of the location must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String LOCATION_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param location A valid location.
     */
    public InterviewLocation(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_LOCATION_CONSTRAINTS);
        this.value = location;
    }

    /**
     * Returns true if a given string is a valid interview location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(LOCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.interview.InterviewLocation // instanceof handles nulls
                && this.value.equals(((seedu.address.model.interview.InterviewLocation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//author@@ deeheenguyen
```
###### \java\seedu\address\model\interview\InterviewMatchInterviewee.java
``` java
package seedu.address.model.interview;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code skills or address} matches any of the keywords given.
 */

public class InterviewMatchInterviewee implements Predicate<Interview> {
    private final String keyword;

    public InterviewMatchInterviewee(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Interview interview) {
        return StringUtil.containsWordIgnoreCase(interview.getInterviewee().fullName, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewMatchInterviewee // instanceof handles nulls
                && this.keyword.equals(((InterviewMatchInterviewee) other).keyword)); // state check
    }

}
//author@@
```
###### \java\seedu\address\model\interview\InterviewTitle.java
``` java
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Interview's title in the address book.
 * Guarantees: immutable; is valid as declared in {@title #isValidTitle(String)}
 */

public class InterviewTitle {
    public static final String MESSAGE_TITLE_CONSTRAINTS =
            "Interview Title should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullTitle;

    /**
     * Constructs a {@code JobTitle}.
     *
     * @param title A valid interview title.
     */
    public InterviewTitle(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_TITLE_CONSTRAINTS);
        this.fullTitle = title;
    }

    /**
     * Returns true if a given string is a valid job title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(TITLE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullTitle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.interview.InterviewTitle // instanceof handles nulls
                && this.fullTitle.equals(((InterviewTitle) other).fullTitle)); // state check
    }

    @Override
    public int hashCode() {
        return fullTitle.hashCode();
    }
}
//author@@ deeheenguyen
```
###### \java\seedu\address\model\interview\UniqueInterviewList.java
``` java
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.interview.exceptions.DuplicateInterviewException;
import seedu.address.model.interview.exceptions.InterviewNotFoundException;

/**
 * A list of interviews that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Interview#equals(Object)
 */
public class UniqueInterviewList implements Iterable<Interview> {

    private final ObservableList<Interview> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(Interview toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicateInterviewException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Interview toAdd) throws DuplicateInterviewException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInterviewException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     *
     * @throws DuplicateInterviewException if the replacement is equivalent to another existing person in the list.
     * @throws InterviewNotFoundException if {@code target} could not be found in the list.
     */
    public void setInterview(Interview target, Interview editedInterview)
            throws DuplicateInterviewException, InterviewNotFoundException {
        requireNonNull(editedInterview);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InterviewNotFoundException();
        }

        if (!target.equals(editedInterview) && internalList.contains(editedInterview)) {
            throw new DuplicateInterviewException();
        }

        internalList.set(index, editedInterview);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws InterviewNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Interview toRemove) throws InterviewNotFoundException {
        requireNonNull(toRemove);
        final boolean interviewFoundAndDeleted = internalList.remove(toRemove);
        if (!interviewFoundAndDeleted) {
            throw new InterviewNotFoundException();
        }
        return interviewFoundAndDeleted;
    }

    public void setInterviews(UniqueInterviewList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setInterviews(List<Interview> interviews) throws DuplicateInterviewException {
        requireAllNonNull(interviews);
        final UniqueInterviewList replacement = new UniqueInterviewList();
        for (final Interview interview : interviews) {
            replacement.add(interview);
        }
        setInterviews(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Interview> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Interview> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueInterviewList // instanceof handles nulls
                && this.internalList.equals(((UniqueInterviewList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
//author@@ deeheenguyen
```
###### \java\seedu\address\model\ModelManager.java
``` java
    //=========== Filtered Interview List Accessors =============================================================
    @Override
    public synchronized void addInterview(Interview interview) throws DuplicateInterviewException {
        addressBook.addInterview(interview);
        updateFilteredInterviewList(PREDICATE_SHOW_ALL_INTERVIEWS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteInterview(Interview target) throws InterviewNotFoundException {
        addressBook.removeInterview(target);
        indicateAddressBookChanged();
    }

    /**
     * Returns an unmodifiable view of the list of {@code Interview} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Interview> getFilteredInterviewList() {
        return FXCollections.unmodifiableObservableList(filteredInterviews);
    }

    @Override
    public void updateFilteredInterviewList(Predicate<Interview> predicate) {
        requireNonNull(predicate);
        filteredInterviews.setPredicate(predicate);
    }
```
###### \java\seedu\address\model\person\EmailFilter.java
``` java
package seedu.address.model.person;

import java.util.function.Predicate;


/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EmailFilter implements Predicate<Person> {
    private final String email;

    public EmailFilter (Email email) {
        this.email = email.toString();
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().toString().equals(this.email);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailFilter // instanceof handles nulls
                && this.email.equals(((EmailFilter) other).email)); // state check
    }

}
```
###### \java\seedu\address\model\person\Link.java
``` java
package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Link {

    public static final String MESSAGE_LINK_CONSTRAINTS = "Link should be start with http";
    public static final String LINK_VALIDATION_REGEX =
            "^(https?:\\\\/\\\\/)?(www\\.)?([\\\\w]+\\\\.)+[\u200C\u200B\\\\w]{2,63}\\\\/?";

    public final String value;

    /**
     * Constructs a {@code Tag}.
     *
     * @param link A valid url.
     */
    public Link(String link) {
        checkArgument(isValidLink(link), MESSAGE_LINK_CONSTRAINTS);
        this.value = link;
    }

    /**
     * Returns true if a given string is a valid link name.
     */
    public static boolean isValidLink(String test) {
        //return test.matches(LINK_VALIDATION_REGEX);
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Link // instanceof handles nulls
                && this.value.equals(((Link) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + value + ']';
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedInterview.java
``` java
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.interview.Date;
import seedu.address.model.interview.Interview;
import seedu.address.model.interview.InterviewLocation;
import seedu.address.model.interview.InterviewTitle;
import seedu.address.model.person.Name;

/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedInterview {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";

    @XmlElement(required = true)
    private String interviewTitle;
    @XmlElement(required = true)
    private String interviewLocation;
    @XmlElement(required = true)
    private String interviewee;
    @XmlElement (required = true)
    private String date;

    /**
     * Constructs an XmlAdaptedInterview.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedInterview() {}

    /**
     * Constructs an {@code XmlAdaptedInterview} with the given interview details.
     */
    public XmlAdaptedInterview(String interviewTitle, String interviewee, String date, String location) {
        this.interviewTitle = interviewTitle;
        this.interviewLocation = location;
        this.date = date;
        this.interviewee = interviewee;
    }

    /**
     * Converts a given Interview into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedInterview
     */
    public XmlAdaptedInterview(Interview source) {
        interviewTitle = source.getInterviewTitle().toString();
        interviewLocation = source.getInterviewLocation().toString();
        interviewee = source.getInterviewee().toString();
        date = source.getDate().toString();
    }

    /**
     * Converts this jaxb-friendly adapted interview object into the model's job object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted interview
     */
    public Interview toModelType() throws IllegalValueException {
        if (this.interviewTitle == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewTitle.class.getSimpleName()));
        }
        if (!InterviewTitle.isValidTitle(this.interviewTitle)) {
            throw new IllegalValueException(InterviewTitle.MESSAGE_TITLE_CONSTRAINTS);
        }
        final InterviewTitle interviewTitle = new InterviewTitle(this.interviewTitle);

        if (this.interviewee == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.interviewee)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name interviewee = new Name(this.interviewee);

        if (this.interviewLocation == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, InterviewLocation.class.getSimpleName()));
        }
        if (!InterviewLocation.isValidLocation(this.interviewLocation)) {
            throw new IllegalValueException(InterviewLocation.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final InterviewLocation interviewLocation = new InterviewLocation(this.interviewLocation);

        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(this.date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date date = new Date(this.date);

        return new Interview(interviewTitle, interviewee, date, interviewLocation);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedInterview)) {
            return false;
        }

        XmlAdaptedInterview otherInterview = (XmlAdaptedInterview) other;
        return Objects.equals(interviewTitle, otherInterview.interviewTitle)
                && Objects.equals(interviewee, otherInterview.interviewee)
                && Objects.equals(interviewLocation, otherInterview.interviewLocation)
                && Objects.equals(date, otherInterview.date);
    }
}

```
###### \java\seedu\address\ui\InterviewCard.java
``` java
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.interview.Interview;

/**
 * An UI component that displays information of a {@code Interview}.
 */
public class InterviewCard extends UiPart<Region> {
    private static final String FXML = "InterviewListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Interview interview;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label interviewTitle;
    @FXML
    private Label interviewLocation;
    @FXML
    private Label interviewee;
    @FXML
    private Label date;

    public InterviewCard(Interview interview, int displayedIndex) {
        super(FXML);
        this.interview = interview;
        id.setText(displayedIndex + ". ");
        interviewTitle.setText(interview.getInterviewTitle().toString());
        interviewLocation.setText(interview.getInterviewLocation().value);
        interviewee.setText(interview.getInterviewee().toString());
        date.setText(interview.getDate().toString());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobCard)) {
            return false;
        }

        // state check
        InterviewCard card = (InterviewCard) other;
        return id.getText().equals(card.id.getText())
                && interview.equals(card.interview);
    }
}




```
###### \java\seedu\address\ui\InterviewListPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InterviewPanelSelectionChangedEvent;
import seedu.address.model.interview.Interview;

/**
 * Panel containing the list of jobs.
 */
public class InterviewListPanel extends UiPart<Region> {
    private static final String FXML = "InterviewListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(InterviewListPanel.class);

    @FXML
    private ListView<InterviewCard> interviewListView;

    public InterviewListPanel(ObservableList<Interview> interviewList) {
        super(FXML);
        setConnections(interviewList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Interview> interviewList) {
        ObservableList<InterviewCard> mappedList = EasyBind.map(
                interviewList, (interview) ->
                        new InterviewCard(interview, interviewList.indexOf(interview) + 1));
        interviewListView.setItems(mappedList);
        interviewListView.setCellFactory(listView -> new InterviewListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        interviewListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in interview list panel changed to : '" + newValue + "'");
                        raise(new InterviewPanelSelectionChangedEvent(newValue));
                    }
                });
    }


    /**
     * Scrolls to the {@code JobCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            interviewListView.scrollTo(index);
            interviewListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InterviewCard}.
     */
    class InterviewListViewCell extends ListCell<InterviewCard> {

        @Override
        protected void updateItem(InterviewCard interview, boolean empty) {
            super.updateItem(interview, empty);

            if (empty || interview == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(interview.getRoot());
            }
        }
    }


}
```
