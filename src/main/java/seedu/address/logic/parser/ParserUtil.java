package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.SplitMethod;
import seedu.address.model.transaction.TransactionType;

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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(parseName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(parsePhone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws IllegalValueException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(parseEmail(email.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
    //@@author steven-jia
    /**
     * Parses a {@code String amount} into a {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Amount parseAmount(String amount) throws IllegalValueException {
        requireNonNull(amount);
        String parsedAmount = amount.trim();
        if (!parsedAmount.contains(".")) {
            parsedAmount += ".00";
        }
        if (!Amount.isValidAmount(parsedAmount)) {
            throw new IllegalValueException(Amount.MESSAGE_AMOUNT_CONSTRAINTS);
        }
        return new Amount(parsedAmount);
    }

    /**
     * Parses {@code Collection<String> Amount} into a {@code Set<Amount>}.
     */
    public static Optional<Amount> parseAmount(Optional<String> amount) throws IllegalValueException {
        requireNonNull(amount);
        return amount.isPresent() ? Optional.of(parseAmount(amount.get())) : Optional.empty();
    }
    //@@author ongkc
    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static Description parseDescription(String description) throws IllegalValueException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses {@code Collection<String> Description} into a {@code Set<Description>}.
     */
    public static Optional<Description> parseDescription(Optional<String> description) throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(parseDescription(description.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> TransactionType} into a {@code Set<TransactionType>}.
     */
    public static TransactionType parseTransactionType(String type) throws IllegalValueException {
        requireNonNull(type);
        String trimmedType = type.trim();
        if (!TransactionType.isValidTransactionType(trimmedType)) {
            throw new IllegalValueException(TransactionType.MESSAGE_TRANSACTION_TYPE_CONSTRAINTS);
        }
        return new TransactionType(trimmedType);
    }

    //@@author steven-jia
    /**
     * Parses a {@code String splitMethod} into a {@code SplitMethod}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code splitMethod} is invalid.
     */
    public static SplitMethod parseSplitMethod(String splitMethod) throws IllegalValueException {
        requireNonNull(splitMethod);
        String trimmedSplitMethod = splitMethod.trim();
        if (!SplitMethod.isValidSplitMethod(trimmedSplitMethod)) {
            throw new IllegalValueException(SplitMethod.MESSAGE_SPLIT_METHOD_CONSTRAINTS);
        }
        return new SplitMethod(trimmedSplitMethod);
    }

    /**
     * Parses {@code String splitMethod} into a {@code SplitMethod}.
     */
    public static SplitMethod parseSplitMethod(Optional<String> splitMethod) throws IllegalValueException {
        requireNonNull(splitMethod);
        return splitMethod.isPresent() ? parseSplitMethod(splitMethod.get())
                : new SplitMethod(SplitMethod.SPLIT_METHOD_EVENLY);
    }

    /**
     * Parses a {@code String unitsList} into a {@code List<Integer>}.
     * Leading and trailing whitespaces between values will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static List<Integer> parseUnitsList(String unitsList) throws IllegalValueException {
        requireNonNull(unitsList);
        ArrayList<Integer> trimmedUnitsList = new ArrayList<>();
        if (unitsList.matches("[0-9]+(,( )?[0-9]+)*")) {
            String[] unitsArray = unitsList.split(",");
            for (String unit: unitsArray) {
                trimmedUnitsList.add(Integer.valueOf(unit.trim()));
            }
        } else {
            throw new IllegalValueException("List of units can only take comma-separated integers");
        }
        return trimmedUnitsList;
    }

    /**
     * Parses {@code Collection<String> unitsList} into a {@code List<Integer>}.
     */
    public static List<Integer> parseUnitsList(Optional<String> unitsList) throws IllegalValueException {
        requireNonNull(unitsList);
        return unitsList.isPresent() ? parseUnitsList(unitsList.get()) : Collections.emptyList();
    }

    /**
     * Parses a {@code String percentagesList} into a {@code List<Integer>}.
     * Leading and trailing whitespaces between values will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static List<Integer> parsePercentagesList(String percentagesList) throws IllegalValueException {
        requireNonNull(percentagesList);
        ArrayList<Integer> trimmedPercentagesList = new ArrayList<>();
        if (percentagesList.matches("[0-9]+(,( )?[0-9]+)*")) {
            String[] percentagesArray = percentagesList.split(",");
            for (String percentage: percentagesArray) {
                trimmedPercentagesList.add(Integer.valueOf(percentage.trim()));
            }
        } else {
            throw new IllegalValueException("List of percentages can only take comma-separated integers");
        }
        return trimmedPercentagesList;
    }

    /**
     * Parses {@code Collection<String> percentagesList} into a {@code List<Integer>}.
     */
    public static List<Integer> parsePercentagesList(Optional<String> percentagesList) throws IllegalValueException {
        requireNonNull(percentagesList);
        return percentagesList.isPresent() ? parsePercentagesList(percentagesList.get()) : Collections.emptyList();
    }

}
