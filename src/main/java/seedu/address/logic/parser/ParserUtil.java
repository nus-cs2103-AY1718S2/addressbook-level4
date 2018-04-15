package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.CalendarEvent.EventEndDate;
import seedu.address.model.CalendarEvent.EventEndTime;
import seedu.address.model.CalendarEvent.EventName;
import seedu.address.model.CalendarEvent.EventStartDate;
import seedu.address.model.CalendarEvent.EventStartTime;
import seedu.address.model.person.ActivityLevel;
import seedu.address.model.person.Address;
import seedu.address.model.person.Age;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Height;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Weight;
import seedu.address.model.tag.Tag;

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

    //@@author wenhao53
    /**
     * Parses a {@code String height} into a {@code Height}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code height} is invalid.
     */
    public static Height parseHeight(String height) throws IllegalValueException {
        requireNonNull(height);
        String trimmedHeight = height.trim();
        if (!Height.isValidHeight(trimmedHeight)) {
            throw new IllegalValueException(Height.MESSAGE_HEIGHT_CONSTRAINTS);
        }
        return new Height(trimmedHeight);
    }

    /**
     * Parses a {@code Optional<String> height} into an {@code Optional<Height>} if {@code height} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Height> parseHeight(Optional<String> height) throws IllegalValueException {
        requireNonNull(height);
        return height.isPresent() ? Optional.of(parseHeight(height.get())) : Optional.empty();
    }
    //@@author

    /**
     * Parses a {@code String weight} into a {@code Weight}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code weight} is invalid.
     */
    public static Weight parseWeight(String weight) throws IllegalValueException {
        requireNonNull(weight);
        String trimmedWeight = weight.trim();
        if (!Weight.isValidWeight(trimmedWeight)) {
            throw new IllegalValueException(Weight.MESSAGE_WEIGHT_CONSTRAINTS);
        }
        return new Weight(trimmedWeight);
    }

    /**
     * Parses a {@code Optional<String> weight} into an {@code Optional<Weight>} if {@code weight} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Weight> parseWeight(Optional<String> weight) throws IllegalValueException {
        requireNonNull(weight);
        return weight.isPresent() ? Optional.of(parseWeight(weight.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(parseAddress(address.get())) : Optional.empty();
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
     * Parses a {@code String gender} into a {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws IllegalValueException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new IllegalValueException(Gender.MESSAGE_GENDER_CONSTRAINTS);
        }
        return new Gender(trimmedGender);
    }

    /**
     * Parses a {@code Optional<String> gender} into an {@code Optional<Gender>} if {@code gender} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Gender> parseGender(Optional<String> gender) throws IllegalValueException {
        requireNonNull(gender);
        return gender.isPresent() ? Optional.of(parseGender(gender.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String age} into a {@code Age}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code age} is invalid.
     */
    public static Age parseAge(String age) throws IllegalValueException {
        requireNonNull(age);
        String trimmedAge = age.trim();
        if (!Age.isValidAge(trimmedAge)) {
            throw new IllegalValueException(Age.MESSAGE_AGE_CONSTRAINTS);
        }
        return new Age(trimmedAge);
    }

    /**
     * Parses a {@code Optional<String> age} into an {@code Optional<Age>} if {@code age} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Age> parseAge(Optional<String> age) throws IllegalValueException {
        requireNonNull(age);
        return age.isPresent() ? Optional.of(parseAge(age.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String activityLevel} into a {@code ActivityLevel}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code activityLevel} is invalid.
     */
    public static ActivityLevel parseActivityLevel(String activityLevel) throws IllegalValueException {
        requireNonNull(activityLevel);
        String trimmedActivityLevel = activityLevel.trim();
        if (!ActivityLevel.isValidActivityLevel(trimmedActivityLevel)) {
            throw new IllegalValueException(ActivityLevel.MESSAGE_ACTIVITYLEVEL_CONSTRAINTS);
        }
        return new ActivityLevel(trimmedActivityLevel);
    }

    /**
     * Parses a {@code Optional<String> activityLevel} into an {@code Optional<ActivityLevel>}
     * if {@code activityLevel} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ActivityLevel> parseActivityLevel(Optional<String> activityLevel) throws
            IllegalValueException {
        requireNonNull(activityLevel);
        return activityLevel.isPresent() ? Optional.of(parseActivityLevel(activityLevel.get())) : Optional.empty();
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

    /**
     * Parses a {@code String eventName} into a {@code EventName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventName} is invalid.
     */
    public static EventName parseEventName(String eventName) throws IllegalValueException {
        requireNonNull(eventName);
        String trimmedName = eventName.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new IllegalValueException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        return new EventName(trimmedName);
    }

    /**
     * Parses a {@code Optional<String> eventName} into an {@code Optional<EventName>} if {@code eventName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventName> parseEventName(Optional<String> eventName) throws IllegalValueException {
        requireNonNull(eventName);
        return eventName.isPresent() ? Optional.of(parseEventName(eventName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String eventStartDate} into a {@code EventStartDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventStartDate} is invalid.
     */
    public static EventStartDate parseEventStartDate(String eventStartDate) throws IllegalValueException {
        requireNonNull(eventStartDate);
        String trimmedStartDate = eventStartDate.trim();
        if (!EventStartDate.isValidDate(trimmedStartDate)) {
            throw new IllegalValueException(EventStartDate.MESSAGE_START_DATE_CONSTRAINTS);
        }
        return new EventStartDate(trimmedStartDate);
    }

    /**
     * Parses a {@code Optional<String> eventStartDate} into an {@code Optional<EventStartDate>}
     * if {@code eventStartDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventStartDate> parseEventStartDate(Optional<String> eventStartDate)
            throws IllegalValueException {
        requireNonNull(eventStartDate);
        return eventStartDate.isPresent() ? Optional.of(parseEventStartDate(eventStartDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String eventEndDate} into a {@code EventEndDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventEndDate} is invalid.
     */
    public static EventEndDate parseEventEndDate(String eventEndDate) throws IllegalValueException {
        requireNonNull(eventEndDate);
        String trimmedEndDate = eventEndDate.trim();
        if (!EventEndDate.isValidDate(trimmedEndDate)) {
            throw new IllegalValueException(EventEndDate.MESSAGE_END_DATE_CONSTRAINTS);
        }
        return new EventEndDate(trimmedEndDate);
    }

    /**
     * Parses a {@code Optional<String> eventEndDate} into an {@code Optional<EventEndDate>}
     * if {@code eventEndDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventEndDate> parseEventEndDate(Optional<String> eventEndDate)
            throws IllegalValueException {
        requireNonNull(eventEndDate);
        return eventEndDate.isPresent() ? Optional.of(parseEventEndDate(eventEndDate.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String eventStartTime} into a {@code EventStartTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventStartTime} is invalid.
     */
    public static EventStartTime parseEventStartTime(String eventStartTime) throws IllegalValueException {
        requireNonNull(eventStartTime);
        String trimmedStartTime = eventStartTime.trim();
        if (!EventStartTime.isValidTime(trimmedStartTime)) {
            throw new IllegalValueException(EventStartTime.MESSAGE_START_TIME_CONSTRAINTS);
        }
        return new EventStartTime(trimmedStartTime);
    }

    /**
     * Parses a {@code Optional<String> eventStartTime} into an {@code Optional<EventStartTime>}
     * if {@code eventStartTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventStartTime> parseEventStartTime(Optional<String> eventStartTime)
            throws IllegalValueException {
        requireNonNull(eventStartTime);
        return eventStartTime.isPresent() ? Optional.of(parseEventStartTime(eventStartTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String eventEndTime} into a {@code EventEndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventEndTime} is invalid.
     */
    public static EventEndTime parseEventEndTime(String eventEndTime) throws IllegalValueException {
        requireNonNull(eventEndTime);
        String trimmedEndTime = eventEndTime.trim();
        if (!EventEndTime.isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(EventEndTime.MESSAGE_END_TIME_CONSTRAINTS);
        }
        return new EventEndTime(trimmedEndTime);
    }

    /**
     * Parses a {@code Optional<String> eventEndTime} into an {@code Optional<EventEndTime>}
     * if {@code eventEndTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EventEndTime> parseEventEndTime(Optional<String> eventEndTime)
            throws IllegalValueException {
        requireNonNull(eventEndTime);
        return eventEndTime.isPresent() ? Optional.of(parseEventEndTime(eventEndTime.get())) : Optional.empty();
    }
}
