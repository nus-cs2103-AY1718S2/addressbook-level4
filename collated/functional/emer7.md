# emer7
###### \java\seedu\address\commons\events\logic\ReviewInputEvent.java
``` java
package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * An event to inform review input is available.
 */
public class ReviewInputEvent extends BaseEvent {

    private String reviewerInput;
    private String reviewInput;

    public ReviewInputEvent(String reviewerInput, String reviewInput) {
        this.reviewerInput = reviewerInput;
        this.reviewInput = reviewInput;
    }

    public String getReviewerInput() {
        return reviewerInput;
    }

    public String getReviewInput() {
        return reviewInput;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\PersonEditedEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a person edited change
 */
public class PersonEditedEvent extends BaseEvent {

    private final int index;
    private final Person newPerson;

    public PersonEditedEvent(int index, Person newPerson) {
        this.index = index;
        this.newPerson = newPerson;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Person getNewPerson() {
        return newPerson;
    }

    public int getIndex() {
        return index;
    }
}
```
###### \java\seedu\address\commons\events\ui\ShowReviewDialogEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the review dialog.
 */
public class ShowReviewDialogEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
        String preppedWords = words.trim();
        String[] wordsInPreppedWords = preppedWords.split("\\s+");
        checkArgument(!preppedWords.isEmpty(), "Word parameter cannot be empty");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        int howManyMatches = 0;

        for (String wordInWords: wordsInPreppedWords) {
            for (String wordInSentence : wordsInPreppedSentence) {
                if (wordInSentence.equalsIgnoreCase(wordInWords)) {
                    howManyMatches++;
                }
            }
        }

        return howManyMatches >= wordsInPreppedWords.length;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        Person toReturn = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                personToEdit.getCalendarId());
        toReturn.setRating(updatedRating);
        toReturn.setReviews(updatedReviews);
        toReturn.setId(personToEdit.getId());
        toReturn.setPhotoName(personToEdit.getPhotoName());
        return toReturn;
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        /**
         * Sets {@code reviews} to this object's {@code reviews}.
         * A defensive copy of {@code reviews} is used internally.
         */
        public void setReviews(Set<Review> reviews) {
            this.reviews = (reviews != null) ? new HashSet<>(reviews) : null;
        }

        /**
         * Returns an unmodifiable review set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code reviews} is null.
         */
        public Optional<Set<Review>> getReviews() {
            return (reviews != null) ? Optional.of(Collections.unmodifiableSet(reviews)) : Optional.empty();
        }
```
###### \java\seedu\address\logic\commands\RateCommand.java
``` java
        Person toReturn = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getCalendarId());
        toReturn.setRating(updatedRating);
        toReturn.setReviews(personToEdit.getReviews());
        toReturn.setId(personToEdit.getId());
        toReturn.setPhotoName(personToEdit.getPhotoName());
        return toReturn;
```
###### \java\seedu\address\logic\commands\ReviewCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.review.Review;

/**
 * Updates the review of an existing person in the address book.
 */
public class ReviewCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "review";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reviews the employee identified "
            + "by the index number used in the last employees listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "A separate pop-up dialog will appear to request for the review.";

    public static final String MESSAGE_REVIEW_PERSON_SUCCESS = "Reviewed employee: %1$s";
    public static final String MESSAGE_NOT_EDITED = "INDEX, REVIEWER, and REVIEW must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This employee already exists in Employees Tracker.";

    private final Index index;
    private final EditCommand.EditPersonDescriptor editPersonDescriptor;

    private Person personToEdit;
    private Person editedPerson;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public ReviewCommand(Index index, EditCommand.EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditCommand.EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target employee cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REVIEW_PERSON_SUCCESS, editedPerson));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEdit = lastShownList.get(index.getZeroBased());
        editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit,
                                             EditCommand.EditPersonDescriptor editPersonDescriptor
    ) throws CommandException {
        assert personToEdit != null;
        assert editPersonDescriptor.getReviews().isPresent();

        Set<Review> oldReviews = personToEdit.getReviews();
        Set<Review> newReviews = editPersonDescriptor.getReviews().get();
        HashSet<Review> updatedReviews = new HashSet<Review>();

        Review newReview = newReviews.iterator().next();
        String newReviewer = newReview.reviewer;
        String newValue = newReview.value;
        Iterator<Review> iterator = oldReviews.iterator();
        while (iterator.hasNext()) {
            Review oldReview = iterator.next();
            String oldReviewer = oldReview.reviewer;
            if (!oldReviewer.equals(newReviewer)) {
                updatedReviews.add(oldReview);
            }
        }
        updatedReviews.add(newReview);

        Person toReturn = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getCalendarId());
        toReturn.setRating(personToEdit.getRating());
        toReturn.setReviews(updatedReviews);
        toReturn.setId(personToEdit.getId());
        toReturn.setPhotoName(personToEdit.getPhotoName());
        return toReturn;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReviewCommand)) {
            return false;
        }

        // state check
        ReviewCommand e = (ReviewCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor)
                && Objects.equals(personToEdit, e.personToEdit);
    }
}
```
###### \java\seedu\address\logic\commands\UnlockCommand.java
``` java
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ReviewCommand.COMMAND_WORD:
            return new ReviewCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_RATING);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME)
                || arePrefixesPresent(argMultimap, PREFIX_TAG)
                || arePrefixesPresent(argMultimap, PREFIX_RATING))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeyphrases = argMultimap.getAllValues(PREFIX_NAME);
        List<String> tagKeyphrases = argMultimap.getAllValues(PREFIX_TAG);
        List<String> ratingKeyphrases = argMultimap.getAllValues(PREFIX_RATING);

        if (nameKeyphrases.contains("") || tagKeyphrases.contains("") || ratingKeyphrases.contains("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new FieldContainKeyphrasesPredicate(nameKeyphrases, tagKeyphrases, ratingKeyphrases));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
```
###### \java\seedu\address\logic\parser\ReviewCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.logic.ReviewInputEvent;
import seedu.address.commons.events.ui.ShowReviewDialogEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.review.Review;

/**
 * Parses input arguments and creates a new Review object
 */
public class ReviewCommandParser implements Parser<ReviewCommand> {

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private String reviewerInput;
    private String reviewInput;

    /**
     * Parses the given {@code String} of arguments in the context of the ReviewCommand
     * and returns a ReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Scanner sc = new Scanner(args);
        if (!sc.hasNextInt()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(((Integer) sc.nextInt()).toString());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReviewCommand.MESSAGE_USAGE));
        }

        EventsCenter.getInstance().registerHandler(this);
        EventsCenter.getInstance().post(new ShowReviewDialogEvent());

        try {
            assert reviewerInput != null;
            assert !reviewerInput.isEmpty();

            String reviewer = ParserUtil.parseEmail(reviewerInput).value;

            assert reviewInput != null;

            if (reviewInput.isEmpty()) {
                reviewInput = "-";
            }
            String review = reviewInput.trim();

            String combined = reviewer + "\n" + review;
            HashSet<Review> combinedSet = new HashSet<Review>();
            combinedSet.add(new Review(combined));

            EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
            editPersonDescriptor.setReviews(combinedSet);
            if (!editPersonDescriptor.isAnyFieldEdited()) {
                throw new ParseException(ReviewCommand.MESSAGE_NOT_EDITED);
            }

            return new ReviewCommand(index, editPersonDescriptor);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    @Subscribe
    private void getReviewInput(ReviewInputEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        reviewerInput = event.getReviewerInput();
        reviewInput = event.getReviewInput();
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
        model.updateFilteredPersonList(new HideAllPersonPredicate());
        EventsCenter.getInstance().post(new HideDetailPanelEvent());
```
###### \java\seedu\address\model\AddressBook.java
``` java
        Person toReturn = new Person(
                person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                correctTagReferences, person.getCalendarId());
        toReturn.setRating(person.getRating());
        toReturn.setReviews(person.getReviews());
        toReturn.setId(person.getId());
        toReturn.setPhotoName(person.getPhotoName());
        return toReturn;
```
###### \java\seedu\address\model\person\FieldContainKeyphrasesPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s fields matches any of the keyphrases given.
 */
public class FieldContainKeyphrasesPredicate implements Predicate<Person> {
    private final Predicate<Person> namePredicate;
    private final Predicate<Person> tagPredicate;
    private final Predicate<Person> ratingPredicate;

    public FieldContainKeyphrasesPredicate(List<String> nameKeyphrases,
                                           List<String> tagKeyphrases,
                                           List<String> ratingKeyphrases) {
        this.namePredicate = new NameContainsKeyphrasesPredicate(nameKeyphrases);
        this.tagPredicate = new TagContainsKeyphrasesPredicate(tagKeyphrases);
        this.ratingPredicate = new RatingContainsKeyphrasesPredicate(ratingKeyphrases);
    }


    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) && tagPredicate.test(person) && ratingPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FieldContainKeyphrasesPredicate // instanceof handles nulls
                && this.namePredicate.equals(((FieldContainKeyphrasesPredicate) other).namePredicate)
                && this.tagPredicate.equals(((FieldContainKeyphrasesPredicate) other).tagPredicate)
                && this.ratingPredicate.equals(((
                        FieldContainKeyphrasesPredicate) other).ratingPredicate)); // state check
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Returns an immutable review set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(reviews.toSet());
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = new UniqueReviewList(reviews);
    }

    public String getPersonUrl() {
        return "https://calendar.google.com/calendar/embed?src="
                + calendarId.replaceAll("@", "%40") + "&ctz=Asia%2FSingapore";
    }

    /**
     * Set the photo field which is the path to the photo.
     */
    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
```
###### \java\seedu\address\model\person\RatingContainsKeyphrasesPredicate.java
``` java
package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Rating} matches any of the keyphrases given.
 */
public class RatingContainsKeyphrasesPredicate implements Predicate<Person> {
    private final List<String> keyphrases;

    public RatingContainsKeyphrasesPredicate(List<String> keyphrases) {
        this.keyphrases = keyphrases;
    }

    @Override
    public boolean test(Person person) {
        return keyphrases.isEmpty()
                || keyphrases.stream()
                .anyMatch(keyphrase -> StringUtil.containsWordsIgnoreCase(
                        person.getRating().value.toString(), keyphrase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RatingContainsKeyphrasesPredicate // instanceof handles nulls
                && this.keyphrases.equals(((RatingContainsKeyphrasesPredicate) other).keyphrases)); // state check
    }

}
```
###### \java\seedu\address\model\person\TagContainsKeyphrasesPredicate.java
``` java
package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code UniqueTagList} matches any of the keyphrases given.
 */
public class TagContainsKeyphrasesPredicate implements Predicate<Person> {
    private final List<String> keyphrases;

    public TagContainsKeyphrasesPredicate(List<String> keyphrases) {
        this.keyphrases = keyphrases;
    }

    @Override
    public boolean test(Person person) {
        Iterator tagsSetIterator = person.getTags().iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(tagsSetIterator.next());
        while (tagsSetIterator.hasNext()) {
            sb.append(" " + tagsSetIterator.next());
        }
        String tagStringList = sb.toString()
                .replace("[", "")
                .replace("]", "");
        return keyphrases.isEmpty()
                ||  keyphrases.stream()
                .anyMatch(keyphrase -> StringUtil.containsWordsIgnoreCase(tagStringList, keyphrase));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeyphrasesPredicate // instanceof handles nulls
                && this.keyphrases.equals(((TagContainsKeyphrasesPredicate) other).keyphrases)); // state check
    }

}
```
###### \java\seedu\address\model\review\Review.java
``` java

package seedu.address.model.review;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Email.EMAIL_VALIDATION_REGEX;

/**
 * Represents a Person's review in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCombined(String)}
 */
public class Review {
    public static final String MESSAGE_REVIEW_CONSTRAINTS =
            "Employee reviewer and review can take any values, and they should not be blank.";
    private static final String DEFAULT_REVIEWER = "-";
    private static final String DEFAULT_REVIEW = "-";

    public final String reviewer;
    public final String value;

    /**
     * Constructs a {@code Review} for a new person who hasn't been assigned a review.
     */
    public Review() {
        reviewer = DEFAULT_REVIEWER;
        value = DEFAULT_REVIEW;
    }

    /**
     * Constructs a {@code Review}.
     *
     * @param combined A valid combined reviewer and review.
     */
    public Review(String combined) {
        checkArgument(isValidCombined(combined), MESSAGE_REVIEW_CONSTRAINTS);
        this.reviewer = combined.split("[\\r\\n]+", 2)[0].trim();
        this.value = combined.split("[\\r\\n]+", 2)[1].trim();
    }

    /**
     * Returns true if a given string is a valid combined reviewer and review.
     */
    public static boolean isValidCombined(String test) {
        return test.split("[\\r\\n]+", 2).length == 2
                && (isValidReviewer(test.split("[\\r\\n]+", 2)[0].trim()));
    }

    /**
     * Returns true if a given string is a valid reviewer.
     */
    public static boolean isValidReviewer(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return reviewer + "\n" + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Review // instanceof handles nulls
                && this.reviewer.equals(((Review) other).reviewer)
                && this.value.equals(((Review) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
```
###### \java\seedu\address\model\review\UniqueReviewList.java
``` java

package seedu.address.model.review;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of reviews that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Review#equals(Object)
 */
public class UniqueReviewList implements Iterable<Review> {

    private final ObservableList<Review> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ReviewList.
     */
    public UniqueReviewList() {}

    /**
     * Creates a UniqueReviewList using given reviews.
     * Enforces no nulls.
     */
    public UniqueReviewList(Set<Review> reviews) {
        requireAllNonNull(reviews);
        internalList.addAll(reviews);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Reviews in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Review> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Reviews in this list with those in the argument reviews list.
     */
    public void setReviews(Set<Review> reviews) {
        requireAllNonNull(reviews);
        internalList.setAll(reviews);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every review in the argument list exists in this object.
     */
    public void mergeFrom(UniqueReviewList from) {
        final Set<Review> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(review -> !alreadyInside.contains(review))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Review as the given argument.
     */
    public boolean contains(Review toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Review to the list.
     *
     * @throws DuplicateReviewException if the Review to add is a duplicate of an existing Review in the list.
     */
    public void add(Review toAdd) throws DuplicateReviewException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReviewException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Review> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Review> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueReviewList // instanceof handles nulls
                && this.internalList.equals(((UniqueReviewList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueReviewList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateReviewException extends DuplicateDataException {
        protected DuplicateReviewException() {
            super("Operation would result in duplicate reviews");
        }
    }

}
```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a review set containing the list of strings given.
     */
    public static Set<Review> getReviewSet(String... strings) {
        HashSet<Review> reviews = new HashSet<>();
        for (String s : strings) {
            reviews.add(new Review(s));
        }

        return reviews;
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Set<Review> reviews = new HashSet<>(personReviews);
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        Person toReturn = new Person(name, phone, email, address, tags, calendarId);
        toReturn.setRating(rating);
        toReturn.setReviews(reviews);
        toReturn.setId(id);
        toReturn.setPhotoName(photo.getName());
        //System.out.println(toReturn.getName().fullName + "  " + toReturn.getPhotoName());

        return toReturn;
```
###### \java\seedu\address\storage\XmlAdaptedReview.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.review.Review;

/**
 * JAXB-friendly adapted version of the Review.
 */
public class XmlAdaptedReview {

    @XmlValue
    private String review;

    /**
     * Constructs an XmlAdaptedReview.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReview() {}

    /**
     * Constructs a {@code XmlAdaptedReview} with the given {@code review}.
     */
    public XmlAdaptedReview(String review) {
        this.review = review;
    }

    /**
     * Converts a given Review into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedReview(Review source) {
        review = source.toString();
    }

    /**
     * Converts this jaxb-friendly adapted review object into the model's Review object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted review
     */
    public Review toModelType() throws IllegalValueException {
        if (!Review.isValidCombined(review)) {
            throw new IllegalValueException(Review.MESSAGE_REVIEW_CONSTRAINTS);
        }
        return new Review(review);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedReview)) {
            return false;
        }

        return review.equals(((XmlAdaptedReview) other).review);
    }
}
```
###### \java\seedu\address\ui\DetailPanel.java
``` java
    @FXML
    private GridPane detailPanel;
    @FXML
    private Label name;
    @FXML
    private Label address;
    @FXML
    private FlowPane reviews;
```
###### \java\seedu\address\ui\DetailPanel.java
``` java
        name = null;
        address = null;
        reviews = null;
```
###### \java\seedu\address\ui\DetailPanel.java
``` java
        Person person = event.getNewSelection().person;
        name.setText(person.getName().fullName);
        address.setText(person.getAddress().value);
        reviews.getChildren().clear();
        person.getReviews().forEach(review -> reviews.getChildren().add(new Label(review.toString())));
```
###### \java\seedu\address\ui\DetailPanel.java
``` java
    @Subscribe
    public void handlePersonEditedEvent(PersonEditedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Person newPerson = event.getNewPerson();
        name.setText(newPerson.getName().fullName);
        address.setText(newPerson.getAddress().value);
        reviews.getChildren().clear();
        newPerson.getReviews().forEach(review -> reviews.getChildren().add(new Label(review.toString())));
        loadPersonPage(event.getNewPerson());
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void showReviewDialog(ShowReviewDialogEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReviewDialog reviewDialog = new ReviewDialog();
        reviewDialog.show();
    }

```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    public void handlePersonEditedEvent(PersonEditedEvent event) {
        scrollTo(event.getIndex());
    }
```
###### \java\seedu\address\ui\ReviewDialog.java
``` java
package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ReviewInputEvent;

/**
 * Pop-up dialog to prompt user to enter the review.
 */
public class ReviewDialog {

    public static final String REVIEW_DIALOG_PANE_FIELD_ID = "reviewDialogPane";

    private Dialog<Pair<String, String>> dialog;
    private ButtonType reviewButtonType;
    private TextField reviewer;
    private TextArea review;
    private GridPane grid;
    private Node reviewButton;
    private Logger logger = LogsCenter.getLogger(this.getClass());

    public ReviewDialog() {
        dialog = new Dialog<>();
        dialog.getDialogPane().setId(REVIEW_DIALOG_PANE_FIELD_ID);
        dialog.setTitle("Review Dialog");
        dialog.setHeaderText("Reviewer must be a valid email address"
                + "\n"
                + "Review accepts any characters and has no length limit.");

        reviewButtonType = new ButtonType("Review", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(reviewButtonType, ButtonType.CANCEL);

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        reviewer = new TextField();
        reviewer.setPromptText("Enter your email address here");
        reviewer.setId("reviewerInputTextField");
        review = new TextArea();
        review.setPromptText("Enter your review here");
        review.setId("reviewInputTextField");

        grid.add(new Label("Reviewer:"), 0, 0);
        grid.add(reviewer, 1, 0);
        grid.add(new Label("Review:"), 0, 1);
        grid.add(review, 1, 1);

        reviewButton = dialog.getDialogPane().lookupButton(reviewButtonType);
        reviewButton.setDisable(true);

        reviewer.textProperty().addListener((observable, oldValue, newValue) -> {
            reviewButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> reviewer.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == reviewButtonType) {
                return new Pair<>(reviewer.getText(), review.getText());
            }
            return null;
        });
    }

    /**
     * Show the pop-up dialog when called
     */
    public void show() {
        Optional <Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(input -> {
            EventsCenter.getInstance().post(new ReviewInputEvent(input.getKey(), input.getValue()));
        });
    }
}
```
###### \resources\view\DetailPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ScrollPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.web.WebView?>

<StackPane xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane fx:id="detailPanel" gridLinesVisible="true" maxWidth="1.7976931348623157E308" style="-fx-background-color: white;">
        <columnConstraints>
          <ColumnConstraints hgrow="SOMETIMES" maxWidth="132.40000610351564" minWidth="10.0" prefWidth="99.99998168945312" />
          <ColumnConstraints hgrow="ALWAYS" maxWidth="1.7976931348623157E308" minWidth="10.0" prefWidth="101.60001831054687" />
        </columnConstraints>
        <rowConstraints>
          <RowConstraints maxHeight="20.0" minHeight="30.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="20.0" minHeight="30.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints maxHeight="1.7976931348623157E308" minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
            <RowConstraints minHeight="30.0" prefHeight="30.0" vgrow="SOMETIMES" />
        </rowConstraints>
         <children>
            <Label text="Name">
               <font>
                  <Font size="15.0" />
               </font>
               <GridPane.margin>
                  <Insets left="5.0" />
               </GridPane.margin>
            </Label>
            <Label layoutX="10.0" layoutY="75.0" text="Address" GridPane.rowIndex="1">
               <font>
                  <Font size="15.0" />
               </font>
               <padding>
                  <Insets left="5.0" />
               </padding>
            </Label>
            <Label text="Reviews" GridPane.rowIndex="3">
               <font>
                  <Font size="15.0" />
               </font>
               <padding>
                  <Insets left="5.0" />
               </padding>
            </Label>
            <Label text="Calendar" GridPane.rowIndex="2">
               <font>
                  <Font size="15.0" />
               </font>
               <padding>
                  <Insets left="5.0" />
               </padding>
            </Label>
            <Label fx:id="name" GridPane.columnIndex="1">
               <GridPane.margin>
                  <Insets left="5.0" />
               </GridPane.margin></Label>
            <Label fx:id="address" GridPane.columnIndex="1" GridPane.rowIndex="1">
               <GridPane.margin>
                  <Insets left="5.0" />
               </GridPane.margin></Label>
            <ScrollPane hbarPolicy="NEVER" prefHeight="200.0" prefWidth="200.0" vbarPolicy="ALWAYS" GridPane.columnIndex="1" GridPane.rowIndex="3">
               <content>
                  <FlowPane fx:id="reviews" maxHeight="1.7976931348623157E308" orientation="VERTICAL" />
               </content>
            </ScrollPane>
            <WebView fx:id="browser" minHeight="-1.0" minWidth="-1.0" prefHeight="-1.0" prefWidth="-1.0" GridPane.columnIndex="1" GridPane.rowIndex="2" />
         </children>
      </GridPane>
   </children>
</StackPane>
```
