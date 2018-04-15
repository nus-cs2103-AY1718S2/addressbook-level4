package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.event.Event;
import seedu.address.model.group.Information;
import seedu.address.model.person.Address;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeTableLink;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Content;

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
     * Parses a {@code String content} into a {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code content} is invalid.
     */
    public static Content parseContent(String content) throws IllegalValueException {
        requireNonNull(content);
        String trimmedContent = content.trim();
        if (!Content.isValidContent(trimmedContent)) {
            throw new IllegalValueException(Content.MESSAGE_CONTENT_CONSTRAINTS);
        }
        return new Content(trimmedContent);
    }

    /**
     * Parses a {@code Optional<String> content} into an {@code Optional<Content>} if {@code content} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Content> parseContent(Optional<String> content) throws IllegalValueException {
        requireNonNull(content);
        return content.isPresent() ? Optional.of(parseContent(content.get())) : Optional.empty();
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

    //@@author Isaaaca
    /**
     * Parses a {@code String link} into an {@code TimeTableLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code link} is invalid.
     */
    public static TimeTableLink parseTimeTableLink(String link) throws IllegalValueException {
        requireNonNull(link);
        String trimmedLink = link.trim();
        if (!TimeTableLink.isValidLink(trimmedLink)) {
            throw new IllegalValueException(TimeTableLink.MESSAGE_TIMETABLE_LINK_CONSTRAINTS);
        }
        return new TimeTableLink(trimmedLink);
    }

    /**
     * Parses a {@code Optional<String> link} into an {@code Optional<TimeTableLink>} if {@code link} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TimeTableLink> parseTimeTableLink(Optional<String> link) throws IllegalValueException {
        requireNonNull(link);
        return  link.isPresent() ? Optional.of(parseTimeTableLink(link.get())) : Optional.empty();
    }
    //@@author

    /**
     * Parses a {@code String detail} into a {@code Detail}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code detail} is invalid.
     */
    public static Detail parseDetail(String detail) throws IllegalValueException {
        requireNonNull(detail);
        String trimmedDetail = detail.trim();
        if (!Detail.isValidDetail(trimmedDetail)) {
            throw new IllegalValueException(Detail.MESSAGE_DETAIL_CONSTRAINTS);
        }
        return new Detail(trimmedDetail);
    }

    /**
     * Parses a {@code Optional<String> detail} into an {@code Optional<Detail>} if {@code detail} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Detail> parseDetail(Optional<String> detail) throws IllegalValueException {
        requireNonNull(detail);
        return detail.isPresent() ? Optional.of(parseDetail(detail.get())) : Optional.empty();
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
    //@@author jas5469
    /**
     * Parses a {@code String content} into a {@code Content}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code content} is invalid.
     */
    public static Information parseInformation(String information) throws IllegalValueException {
        requireNonNull(information);
        String trimmedInformation = information.trim();
        if (!Content.isValidContent(trimmedInformation)) {
            throw new IllegalValueException(Information.MESSAGE_INFORMATION_CONSTRAINTS);
        }
        return new Information(trimmedInformation);
    }

    //@@author LeonidAgarth
    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code phone} is invalid.
     */
    public static String parseColor(String color) throws IllegalValueException {
        requireNonNull(color);
        if (Tag.isValidTagColor(color)) {
            return color;
        } else {
            throw new IllegalValueException(Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);
        }
    }


    /**
     * Parses a {@code String name} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code name} is invalid.
     */
    public static String parseEventName(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Event.isValidName(trimmedName)) {
            throw new IllegalValueException(Event.MESSAGE_NAME_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Parses a {@code String venue} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code venue} is invalid.
     */
    public static String parseVenue(String venue) throws IllegalValueException {
        requireNonNull(venue);
        String trimmedVenue = venue.trim();
        if (!Event.isValidName(trimmedVenue)) {
            throw new IllegalValueException(Event.MESSAGE_VENUE_CONSTRAINTS);
        }
        return trimmedVenue;
    }

    /**
     * Parses a {@code String date} into a {@code String}.
     * Date must follow DD/MM/YYYY format
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static String parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Event.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Event.MESSAGE_DATE_CONSTRAINTS);
        }
        return trimmedDate;
    }

    /**
     * Parses a {@code String time} into a {@code String}.
     * Time must follow HHmm format
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static String parseTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!Event.isValidTime(trimmedTime)) {
            throw new IllegalValueException(Event.MESSAGE_TIME_CONSTRAINTS);
        }
        return trimmedTime;
    }
}
