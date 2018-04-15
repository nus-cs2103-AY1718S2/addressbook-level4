package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.lesson.Day;
import seedu.address.model.lesson.Time;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.dashboard.Date;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.student.miscellaneousinfo.Remarks;
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
     * Parses a {@code String programmingLanguage} into an {@code programminglanguage}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code programmingLanguage} is invalid.
     */
    public static ProgrammingLanguage parseSubject(String subject) throws IllegalValueException {
        requireNonNull(subject);
        String trimmedSubject = subject.trim();
        if (!ProgrammingLanguage.isValidProgrammingLanguage(trimmedSubject)) {
            throw new IllegalValueException(ProgrammingLanguage.MESSAGE_PROGRAMMING_LANGUAGE_CONSTRAINTS);
        }
        return new ProgrammingLanguage(trimmedSubject);
    }

    /**
     * Parses a {@code Optional<String> programmingLanguage} into an {@code Optional<programminglanguage>}
     * if {@code programmingLanguage} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProgrammingLanguage> parseSubject(Optional<String> subject) throws IllegalValueException {
        requireNonNull(subject);
        return subject.isPresent() ? Optional.of(parseSubject(subject.get())) : Optional.empty();
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

    /**
     * Parses {@code String time} into a {@code Time}.
     */
    public static Time parseTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedSubject = time.trim();
        if (!Time.isValidTime(trimmedSubject)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        return new Time(trimmedSubject);
    }

    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<Time>} if {@code time} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Time> parseTime(Optional<String> time) throws IllegalValueException {
        requireNonNull(time);
        return time.isPresent() ? Optional.of(parseTime(time.get())) : Optional.empty();
    }

    /**
     * Parses {@code String day} into a {@code Day}.
     */
    public static Day parseDay(String day) throws IllegalValueException {
        requireNonNull(day);
        String trimmedDay = day.trim().toLowerCase();
        if (!Day.isValidDay(trimmedDay)) {
            throw new IllegalValueException(Day.MESSAGE_DAY_CONSTRAINTS);
        }
        return new Day(trimmedDay);
    }

    /**
     * Parses a {@code Optional<String> day} into an {@code Optional<Day>} if {@code day} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Day> parseDay(Optional<String> day) throws IllegalValueException {
        requireNonNull(day);
        return day.isPresent() ? Optional.of(parseDay(day.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code date} is invalid.
     */
    public static Date parseDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        return new Date(trimmedDate);
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(parseDate(date.get())) : Optional.empty();
    }

    //@@author samuelloh
    /**
     * Parses a {@code Optional<String> path} into an {@code Optional<ProfilePicturePath>} if {@code path} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<ProfilePicturePath> parsePictureUrl(Optional<String> path) throws
            IllegalValueException {

        requireNonNull(path);
        return path.isPresent() ? Optional.of(parsePictureUrl(path.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String path} into a {@code ProfilePicturePath}.
     * @throws IllegalValueException if the given {@code path} is invalid.
     */
    public static ProfilePicturePath parsePictureUrl(String path) throws IllegalValueException {
        requireNonNull(path);
        if (!ProfilePicturePath.isValidPath(path)) {
            throw new IllegalValueException(ProfilePicturePath.MESSAGE_PICTURE_CONSTRAINTS);
        }
        return new ProfilePicturePath(path);
    }

    /**
     * Parses a {@code String allergies} into an {@code allergies}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code allergies} is invalid.
     */
    public static Allergies parseAllergies(String allergies) throws IllegalValueException {
        requireNonNull(allergies);
        String trimmedAllergies = allergies.trim();
        if (!Allergies.isValidAllergies(trimmedAllergies)) {
            throw new IllegalValueException(Allergies.MESSAGE_ALLERGIES_CONSTRAINTS);
        }
        return new Allergies(trimmedAllergies);
    }

    /**
     * Parses a {@code Optional<String> allergies} into an {@code Optional<Allergies>} if {@code allergies} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Allergies> parseAllergies(Optional<String> allergies) throws IllegalValueException {
        requireNonNull(allergies);
        return allergies.isPresent() ? Optional.of(parseAllergies(allergies.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String nextOfKinName} into a {@code NextOfKinName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code nextOfKinName} is invalid.
     */
    public static NextOfKinName parseNextOfKinName(String nextOfKinName) throws IllegalValueException {
        requireNonNull(nextOfKinName);
        String trimmedNextOfKinName = nextOfKinName.trim();
        if (!NextOfKinName.isValidNextOfKinName(trimmedNextOfKinName)) {
            throw new IllegalValueException(NextOfKinName.MESSAGE_NEXTOFKINNAME_CONSTRAINTS);
        }
        return new NextOfKinName(trimmedNextOfKinName);
    }

    /**
     * Parses a {@code Optional<String> nextOfKinName} into a {@code Optional<NextOfKinName>}
     * if {@code nextOfKinName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NextOfKinName> parseNextOfKinName(Optional<String> nextOfKinName)
            throws IllegalValueException {
        requireNonNull(nextOfKinName);
        return nextOfKinName.isPresent() ? Optional.of(parseNextOfKinName(nextOfKinName.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String nextOfKinPhone} into a {@code NextOfKinPhone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code nextOfKinPhone} is invalid.
     */
    public static NextOfKinPhone parseNextOfKinPhone(String nextOfKinPhone) throws IllegalValueException {
        requireNonNull(nextOfKinPhone);
        String trimmedNextOfKinPhone = nextOfKinPhone.trim();
        if (!NextOfKinPhone.isValidNextOfKinPhone(trimmedNextOfKinPhone)) {
            throw new IllegalValueException(NextOfKinPhone.MESSAGE_NEXTOFKINPHONE_CONSTRAINTS);
        }
        return new NextOfKinPhone(trimmedNextOfKinPhone);
    }

    /**
     * Parses a {@code Optional<String> nextOfKinPhone} into a {@code Optional<NextOfKinPhone>}
     * if {@code nextOfKinPhone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NextOfKinPhone> parseNextOfKinPhone(Optional<String> nextOfKinPhone)
            throws IllegalValueException {
        requireNonNull(nextOfKinPhone);
        return nextOfKinPhone.isPresent() ? Optional.of(parseNextOfKinPhone(nextOfKinPhone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String remarks} into a {@code Remarks} instance.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code remarks} is invalid.
     */
    public static Remarks parseRemarks(String remarks) throws IllegalValueException {
        requireNonNull(remarks);
        String trimmedRemarks = remarks.trim();
        if (!Remarks.isValidRemarks(trimmedRemarks)) {
            throw new IllegalValueException(Remarks.MESSAGE_REMARKS_CONSTRAINTS);
        }
        return new Remarks(remarks);
    }
    /**
     * Parses a {@code Optional<String> remarks} into a {@code Optional<Remarks>} instance
     * if {@code remarks} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remarks> parseRemarks(Optional<String> remarks)
            throws IllegalValueException {
        requireNonNull(remarks);
        return remarks.isPresent() ? Optional.of(parseRemarks(remarks.get())) : Optional.empty();
    }
    //@@author
}

