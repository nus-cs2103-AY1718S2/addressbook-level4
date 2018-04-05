# ValerianRey
###### \java\seedu\address\logic\commands\AddPolicyCommand.java
``` java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.policy.Policy;

/**
 * Add a policy to an existing person in the address book.
 */
public class AddPolicyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_BEGINNING_DATE + "BEGINNING (DD/MM/YYYY) "
            + PREFIX_EXPIRATION_DATE + "EXPIRATION (DD/MM/YYYY) "
            + PREFIX_PRICE + "MONTHLY_PRICE "
            + "[" + PREFIX_ISSUE + "ISSUE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_BEGINNING_DATE + "03/04/2018 "
            + PREFIX_EXPIRATION_DATE + "02/04/2020 "
            + PREFIX_PRICE + "150 "
            + PREFIX_ISSUE + "theft "
            + PREFIX_ISSUE + "car_damage";

    public static final String MESSAGE_POLICY_ADDED_SUCCESS = "Added policy";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Policy policy;

    private Person personToEnroll;
    private Person editedPerson;

    /**
     * @param index  of the person in the filtered person list to edit
     * @param policy policy to add to the person
     */
    public AddPolicyCommand(Index index, Policy policy) {
        requireNonNull(index);
        requireNonNull(policy);

        this.index = index;
        this.policy = policy;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEnroll, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_POLICY_ADDED_SUCCESS));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEnroll = lastShownList.get(index.getZeroBased());
        editedPerson = createPersonWithPolicy(personToEnroll, policy);
    }

    /**
     * Creates and returns a {@code Person} with the specified {@code policyToAdd}
     */
    private static Person createPersonWithPolicy(Person personToEdit, Policy policyToAdd) {
        assert personToEdit != null;

        return new Person(personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getIncome(),
                personToEdit.getActualSpending(),
                personToEdit.getExpectedSpending(),
                personToEdit.getAge(),
                Optional.of(policyToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand e = (AddPolicyCommand) other;
        return index.equals(e.index)
                && policy.equals(e.policy)
                && Objects.equals(personToEnroll, e.personToEnroll);
    }
}
```
###### \java\seedu\address\logic\commands\EditPolicyCommand.java
``` java

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Date;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;

/**
 * Edits an existing policy of a person in the address book.
 */
public class EditPolicyCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the policy of a person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_BEGINNING_DATE + "BEGINNING (DD/MM/YYYY)] "
            + "[" + PREFIX_EXPIRATION_DATE + "EXPIRATION (DD/MM/YYYY)] "
            + "[" + PREFIX_PRICE + "MONTHLY_PRICE] "
            + "[" + PREFIX_ISSUE + "ISSUE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EXPIRATION_DATE + "02/04/2020 "
            + PREFIX_ISSUE + "theft "
            + PREFIX_ISSUE + "car_damage";

    public static final String MESSAGE_POLICY_EDITED_SUCCESS = "Edited policy";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_PERSON_NOT_ENROLLED = "This person did not enroll in a policy yet "
            + "(use add_policy to make it happen)";

    private final Index index;
    private final EditPolicyDescriptor editPolicyDescriptor;

    private Person personToEnroll;
    private Person editedPerson;

    /**
     * @param index  of the person in the filtered person list to edit
     * @param editPolicyDescriptor details to edit the policy with
     */
    public EditPolicyCommand(Index index, EditPolicyDescriptor editPolicyDescriptor) {
        requireNonNull(index);
        requireNonNull(editPolicyDescriptor);

        this.index = index;
        this.editPolicyDescriptor = new EditPolicyDescriptor(editPolicyDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.updatePerson(personToEnroll, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_POLICY_EDITED_SUCCESS));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        personToEnroll = lastShownList.get(index.getZeroBased());
        if (!personToEnroll.getPolicy().isPresent()) {
            throw new CommandException(MESSAGE_PERSON_NOT_ENROLLED);
        }

        Policy policyToEdit = personToEnroll.getPolicy().get();
        Policy editedPolicy = createEditedPolicy(policyToEdit, editPolicyDescriptor);
        editedPerson = createPersonWithPolicy(personToEnroll, editedPolicy);
    }

    /**
     * Creates and returns a {@code Policy} with the details of {@code policyToEdit}
     * edited with {@code editPolicyDescriptor}.
     */
    private static Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor editPolicyDescriptor) {
        assert policyToEdit != null;

        Date updatedBeginning = editPolicyDescriptor.getBeginning().orElse(policyToEdit.getBeginning());
        Date updatedExpiration = editPolicyDescriptor.getExpiration().orElse(policyToEdit.getExpiration());
        Price updatedPrice = editPolicyDescriptor.getPrice().orElse(policyToEdit.getPrice());
        Coverage updatedCoverage = editPolicyDescriptor.getCoverage().orElse(policyToEdit.getCoverage());

        return new Policy(updatedPrice, updatedCoverage, updatedBeginning, updatedExpiration);
    }

    /**
     * Creates and returns a {@code Person} with the specified {@code policyToAdd}
     */
    private static Person createPersonWithPolicy(Person personToEdit, Policy policyToAdd) {
        assert personToEdit != null;

        return new Person(personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                personToEdit.getIncome(),
                personToEdit.getActualSpending(),
                personToEdit.getExpectedSpending(),
                personToEdit.getAge(),
                Optional.of(policyToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditPolicyCommand)) {
            return false;
        }

        EditPolicyCommand e = (EditPolicyCommand) other;
        return index.equals(e.index)
                && editPolicyDescriptor.equals(e.editPolicyDescriptor)
                && Objects.equals(personToEnroll, e.personToEnroll);
    }

    /**
     * Stores the details to edit the policy with. Each non-empty field value will replace the
     * corresponding field value of the policy.
     */
    public static class EditPolicyDescriptor {
        private Date beginning;
        private Date expiration;
        private Price price;
        private Coverage coverage;

        public EditPolicyDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPolicyDescriptor(EditPolicyDescriptor toCopy) {
            setBeginning(toCopy.beginning);
            setExpiration(toCopy.expiration);
            setPrice(toCopy.price);
            setCoverage(toCopy.coverage);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.beginning, this.expiration, this.price, this.coverage);
        }

        public void setBeginning(Date beginning) {
            this.beginning = beginning;
        }

        public Optional<Date> getBeginning() {
            return Optional.ofNullable(beginning);
        }

        public void setExpiration(Date expiration) { this.expiration = expiration; }

        public Optional<Date> getExpiration() { return Optional.ofNullable(expiration); }

        public void setPrice(Price price) { this.price = price; }

        public Optional<Price> getPrice() { return Optional.ofNullable(price); }

        public void setCoverage(Coverage coverage) { this.coverage = coverage; }

        public Optional<Coverage> getCoverage() { return Optional.ofNullable(coverage); }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditPolicyDescriptor)) {
                return false;
            }

            EditPolicyDescriptor e = (EditPolicyDescriptor) other;

            return getBeginning().equals(e.getBeginning())
                    && getExpiration().equals(e.getExpiration())
                    && getPrice().equals(e.getPrice())
                    && getCoverage().equals(e.getCoverage());
        }
    }
}
```
###### \java\seedu\address\logic\parser\AddPolicyCommandParser.java
``` java

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Date;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.Price;

/**
 * Parses input arguments and creates a new AddPolicyCommand object
 */
public class AddPolicyCommandParser implements Parser<AddPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPolicyCommand
     * and returns an AddPolicyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BEGINNING_DATE,
                PREFIX_EXPIRATION_DATE, PREFIX_PRICE, PREFIX_ISSUE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_BEGINNING_DATE, PREFIX_EXPIRATION_DATE, PREFIX_PRICE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
        }

        Price price;
        Date beginningDate;
        Date expirationDate;
        Coverage coverage;

        try {
            price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
            beginningDate = ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_BEGINNING_DATE).get());
            expirationDate = ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_EXPIRATION_DATE).get());
            coverage = new Coverage(ParserUtil.parseIssues(argMultimap.getAllValues(PREFIX_ISSUE)));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!Policy.isValidDuration(beginningDate, expirationDate)) {
            throw new ParseException(Policy.DURATION_CONSTRAINTS);
        }

        Policy policy = new Policy(price, coverage, beginningDate, expirationDate);

        return new AddPolicyCommand(index, policy);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
```
###### \java\seedu\address\logic\parser\EditPolicyCommandParser.java
``` java

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEGINNING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRATION_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISSUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Coverage;
import seedu.address.model.policy.Issue;
import seedu.address.model.policy.Policy;

/**
 * Parses input arguments and creates a new EditPolicyCommand object
 */
public class EditPolicyCommandParser implements Parser<EditPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditPolicyCommand
     * and returns an EditPolicyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditPolicyCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BEGINNING_DATE,
                PREFIX_EXPIRATION_DATE, PREFIX_PRICE, PREFIX_ISSUE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE));
        }

        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();
        try {
            ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_BEGINNING_DATE))
                    .ifPresent(editPolicyDescriptor::setBeginning);
            ParserUtil.parsePolicyDate(argMultimap.getValue(PREFIX_EXPIRATION_DATE))
                    .ifPresent(editPolicyDescriptor::setExpiration);
            ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE)).ifPresent(editPolicyDescriptor::setPrice);
            Optional<List<Issue>> optIssues = parseIssuesForEditPolicy(argMultimap.getAllValues(PREFIX_ISSUE));
            if (optIssues.isPresent()) {
                editPolicyDescriptor.setCoverage(new Coverage(optIssues.get()));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editPolicyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditPolicyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditPolicyCommand(index, editPolicyDescriptor);
    }

    /**
     * Parses {@code List<String> issues} into a {@code List<Issue>} if {@code issues} is non-empty.
     * If {@code issues} contain only one element which is an empty string, it will be parsed into a
     * {@code List<Issue>} containing zero tags.
     */
    private Optional<List<Issue>> parseIssuesForEditPolicy(List<String> issues) throws IllegalValueException {
        assert issues != null;

        if (issues.isEmpty()) {
            return Optional.empty();
        }
        List<String> issueList = issues.size() == 1 && issues.contains("") ? Collections.emptyList() : issues;
        return Optional.of(ParserUtil.parseIssues(issueList));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code String value} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws IllegalValueException {
        requireNonNull(price);
        Double trimmedPrice = Double.parseDouble(price.trim());
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new IllegalValueException(Price.PRICE_CONSTRAINTS);
        }
        return new Price(trimmedPrice);
    }

    /**
     * Parses a {@code Optional<String> price} into an {@code Optional<Price>} if {@code price} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Price> parsePrice(Optional<String> price) throws IllegalValueException {
        requireNonNull(price);
        return price.isPresent() ? Optional.of(parsePrice(price.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static Date parsePolicyDate(String date) throws  IllegalValueException {
        requireNonNull(date);
        String trimmedInput = date.trim();

        String[] parts = trimmedInput.split("/");
        if (parts.length != 3) {
            throw new IllegalValueException(Date.DATE_CONSTRAINTS);
        }

        Integer day = Integer.parseInt(parts[0]);
        Integer monthValue = Integer.parseInt(parts[1]) - 1;    //month value is from 0 to 11 if the input is valid
        Integer year = Integer.parseInt(parts[2]);

        if (monthValue < 0 || monthValue >= Month.values().length) {
            throw new IllegalValueException(Date.DATE_CONSTRAINTS);
        }

        Month month = Month.values()[monthValue];

        if (!Date.isValidDate(day, month, year)) {
            throw new IllegalValueException(Date.DATE_CONSTRAINTS);
        }

        return new Date(day, month, year);
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parsePolicyDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parsePolicyDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String issue} into a {@code Issue}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code issue} is invalid.
     */
    public static Issue parseIssue(String issue) throws IllegalValueException {
        requireNonNull(issue);
        String trimmedUppercaseIssue = issue.trim().toUpperCase();

        if (!Issue.isValidIssueName(trimmedUppercaseIssue)) {
            throw new IllegalValueException(Issue.ISSUE_CONSTRAINTS);
        }

        return Issue.valueOf(trimmedUppercaseIssue);
    }

    /**
     * Parses {@code Collection<String> issues} into a {@code List<Issue>}.
     */
    public static List<Issue> parseIssues(Collection<String> issues) throws IllegalValueException {
        requireNonNull(issues);
        final List<Issue> issuesList = new ArrayList<>();
        for (String issueName : issues) {
            issuesList.add(parseIssue(issueName));
        }
        return issuesList;
    }

```
###### \java\seedu\address\model\policy\Coverage.java
``` java

package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a Policy's coverage of issues.
 * Guarantees: immutable
 */
public class Coverage {
    private final List<Issue> coverage;

    public Coverage(List<Issue> coverage) {
        requireAllNonNull(coverage);
        this.coverage = new ArrayList<Issue>();
        for (Issue issue : coverage) {
            this.coverage.add(issue);
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        if (coverage.size() >= 1) {
            builder.append(coverage.get(0).name().toLowerCase().replaceAll("_", " "));
        }
        for (int i = 1; i < coverage.size(); ++i) {
            builder.append(", ").append(coverage.get(i).name().toLowerCase().replaceAll("_", " "));
        }
        return builder.toString();
    }

    public List<Issue> getIssues() {
        return Collections.unmodifiableList(coverage);
    }

}
```
###### \java\seedu\address\model\policy\Date.java
``` java

package seedu.address.model.policy;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(Integer, Month, Integer)}
 */
public class Date implements Comparable<Date> {
    public static final String DATE_CONSTRAINTS = " Format must be DD/MM/YYYY (leading 0 can be omitted). "
            + "Day must be from 1 to 28, 29, 30 or 31 depending on the month and year. Year must be from 1950 to 2150.";

    public final Integer day;
    public final Month month;
    public final Integer year;

    public Date(Integer day, Month month, Integer year) {
        requireAllNonNull(day, month, year);
        checkArgument(isValidDate(day, month, year), DATE_CONSTRAINTS);
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private static boolean isLeapYear(Integer year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * Returns a negative integer, zero, or a positive integer as this date is before,
     * the same, or after the specified date.
     */
    @Override
    public int compareTo(Date otherDate) {
        if (year > otherDate.year) { return 1; }
        if (year < otherDate.year) { return -1; }
        int monthCmp = month.compareTo(otherDate.month);
        if (monthCmp != 0) { return monthCmp; }
        if (day > otherDate.day) { return 1; }
        if (day < otherDate.day) { return -1; }
        return 0;
    }

    /**
     * Returns true if a given (Integer, Month, Integer) tuple represents a valid date.
     */
    public static boolean isValidDate(Integer day, Month month, Integer year) {
        boolean yearCorrect = year >= 1950 && year <= 2150;

        int daysInMonth = 31;
        if (month == Month.APRIL || month == Month.JUNE || month == Month.SEPTEMBER || month == Month.NOVEMBER) {
            daysInMonth = 30;
        } else if (month == Month.FEBRUARY) {
            daysInMonth = isLeapYear(year) ? 29 : 28;
        }

        return yearCorrect && day >= 1 && day <= daysInMonth;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02d", day))
                .append("/")
                .append(String.format("%02d", Month.toInt(month)))
                .append("/")
                .append(year);
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.policy.Date)) {
            return false;
        }

        seedu.address.model.policy.Date otherDate = (seedu.address.model.policy.Date) other;
        return otherDate.day.equals(this.day)
                && otherDate.month.equals(this.month)
                && otherDate.year.equals(this.year);
    }

}
```
###### \java\seedu\address\model\policy\Issue.java
``` java

package seedu.address.model.policy;

/**
 * Represents the Issues that could happen to a Person.
 * Note that this list is not complete and has to be extended.
 */
public enum Issue {
    THEFT, CAR_DAMAGE, HOUSE_DAMAGE, ILLNESS, CAR_ACCIDENT;

    public static final String ISSUE_CONSTRAINTS = buildIssueConstraint();

    /**
     * Builds the Issue constraints String and returns it
     */
    private static String buildIssueConstraint() {
        StringBuilder sb = new StringBuilder();
        sb.append("Issue must be a correct issue. List of correct issues (can also be lower case) : ");
        for (Issue i : Issue.values()) {
            sb.append(i.toString()).append("   ");
        }
        return sb.toString();
    }

    /**
     *
     * @param issueName the name of the issue
     * @return the Issue assiociated to the specified String
     */
    public static boolean isValidIssueName (String issueName) {
        boolean match = false;
        for (Issue i : Issue.values()) {
            if (i.toString().equals(issueName)) {
                match = true;
            }
        }
        return match;
    }
}
```
###### \java\seedu\address\model\policy\Month.java
``` java

package seedu.address.model.policy;

/**
 * Represents a Date's Month.
 */
public enum Month {
    JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

    /**
     * Returns the value associated with each month (from 1 to 12)
     * Returns 0 in case of incorrect parameter
     */
    public static int toInt(Month month) {
        Month[] months = Month.values();
        for (int i = 0; i < months.length; i++) {
            if (months[i] == month) {
                return i + 1;
            }
        }
        return 0;
    }
}
```
###### \java\seedu\address\model\policy\Policy.java
``` java

package seedu.address.model.policy;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an insurance Policy that a Person can apply to.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy {
    public static final String DURATION_CONSTRAINTS = "Beginning date must be before or the same as expiration date.";

    private final Price price;
    private final Coverage coverage;
    private final Date beginning;
    private final Date expiration;

    /**
     * Every field must be present and not null.
     * Beginning date must be before or the same as expiration date.
     */
    public Policy(Price price, Coverage coverage, Date beginning, Date expiration) {
        requireAllNonNull(price, coverage, beginning, expiration);
        checkArgument(isValidDuration(beginning, expiration), DURATION_CONSTRAINTS);

        this.price = price;
        this.coverage = coverage;
        this.beginning = beginning;
        this.expiration = expiration;
    }


    public Price getPrice() {
        return price;
    }

    public Coverage getCoverage() {
        return coverage;
    }

    public Date getBeginning() {
        return beginning;
    }

    public Date getExpiration() {
        return expiration;
    }

    /**
     * Returns true if a given (beginningDate, expirationDate) tuple represents a valid duration.
     */
    public static boolean isValidDuration(Date beginning, Date expiration) {
        return beginning.compareTo(expiration) <= 0;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof seedu.address.model.policy.Policy)) {
            return false;
        }

        seedu.address.model.policy.Policy otherPolicy = (seedu.address.model.policy.Policy) other;
        return otherPolicy.getPrice().equals(this.getPrice())
                && otherPolicy.getCoverage().equals(this.getCoverage())
                && otherPolicy.getBeginning().equals(this.getBeginning())
                && otherPolicy.getExpiration().equals(this.getExpiration());
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, coverage, beginning, expiration);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("From ")
                .append(getBeginning())
                .append(" to ")
                .append(getExpiration())
                .append("\n           Price: ")
                .append(getPrice())
                .append(" per month\n           Covers ");
        if (coverage.getIssues().isEmpty()) {
            builder.append("nothing");
        } else {
            builder.append(getCoverage());
        }

        return builder.toString();
    }

}
```
###### \java\seedu\address\model\policy\Price.java
``` java

package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Policy's monthly price.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(Double)}
 */
public class Price {
    public static final String PRICE_CONSTRAINTS =
            "Prices must not be negative.";

    public final Double price;

    public Price(Double price) {
        requireNonNull(price);
        checkArgument(isValidPrice(price), PRICE_CONSTRAINTS);
        this.price = price;
    }

    public static boolean isValidPrice(Double price) {
        return price >= 0;
    }

    @Override
    public String toString() {
        return price.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Price
                && this.price == ((Price) other).price);
    }

}
```
