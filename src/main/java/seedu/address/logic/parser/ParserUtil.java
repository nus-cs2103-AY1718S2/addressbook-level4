package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.building.Building.retrieveNusBuildingIfExist;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.MapCommand;
import seedu.address.model.alias.Alias;
import seedu.address.model.building.Building;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
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
    public static final String MESSAGE_NOT_ODDEVEN = "String is not even or odd.";

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
     * Parses a {@code String birthday} into a {@code Birthday}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code birthday} is invalid.
     */
    public static Birthday parseBirthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        String trimmedBirthday = birthday.trim();

        try {
            Birthday.isValidBirthday(trimmedBirthday);
        } catch (IllegalArgumentException iae) {
            throw new IllegalValueException(iae.getMessage());
        }
        return new Birthday(trimmedBirthday);
    }

    /**
     * Parses a {@code Optional<String> birthday} into an {@code Optional<Birthday>} if {@code birthday} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Birthday> parseBirthday(Optional<String> birthday) throws IllegalValueException {
        requireNonNull(birthday);
        return birthday.isPresent() ? Optional.of(parseBirthday(birthday.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String timetableUrl} into a {@code Timetable}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code birthday} is invalid.
     */
    public static Timetable parseTimetable(String timetableUrl) throws IllegalValueException {
        requireNonNull(timetableUrl);
        String trimmedUrl = timetableUrl.trim();

        if (!trimmedUrl.equals(Timetable.EMPTY_LINK) && !Timetable.isValidUrl(timetableUrl)) {
            throw new IllegalValueException(Timetable.MESSAGE_URL_CONSTRAINTS);
        }
        return new Timetable(trimmedUrl);
    }

    /**
     * Parses a {@code Optional<String> timetableUrl} into an {@code Optional<Timetable>}
     * if {@code timetableUrl} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Timetable> parseTimetable(Optional<String> timetableUrl) throws IllegalValueException {
        requireNonNull(timetableUrl);
        return timetableUrl.isPresent() ? Optional.of(parseTimetable(timetableUrl.get())) : Optional.empty();
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

    //@@author jingyinno
    /**
     * Parses a {@code String command} and {@code String alias} into {@code Alias}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given command and alias are invalid.
     */
    public static Alias parseAlias(String command, String alias) throws IllegalValueException {
        requireNonNull(command, alias);
        if (!Alias.isValidAliasParameter(command) || !Alias.isValidAliasParameter(alias)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);

        }
        return new Alias(command, alias);
    }

    /**
     * Parses a {@code String locations} into {@code String formattedLocations}
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseLocations(String locations) {
        requireNonNull(locations);
        String[] locationsArray = locations.split(MapCommand.SPLIT_TOKEN);
        checkForAndRetrieveNusBuildings(locationsArray);
        return identifyNumberOfSpecifiedLocations(locationsArray);
    }

    /**
     * Identifies if one or more locations are specified in the user input
     */
    private static String identifyNumberOfSpecifiedLocations(String[] locationsArray) {
        if (locationsArray.length >= MapCommand.TWO_LOCATIONS_WORD_LENGTH) {
            return String.join(MapCommand.SPLIT_TOKEN, locationsArray);
        } else {
            return locationsArray[MapCommand.FIRST_LOCATION_INDEX];
        }
    }


    /**
     * Replace NUS building names with respective postal code
     */
    private static void checkForAndRetrieveNusBuildings(String[] locationsArray) {
        for (int i = 0; i < locationsArray.length; i++) {
            locationsArray[i] = locationsArray[i].trim();
            locationsArray[i] = retrieveNusBuildingIfExist(locationsArray[i]);
        }
    }

    /**
     * Parses a {@code String unalias}
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code unalias} is invalid.
     */
    public static String parseUnalias(String unalias) throws IllegalValueException {
        requireNonNull(unalias);
        if (!Alias.isValidAliasParameter(unalias)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);

        }
        return unalias;
    }

    /**
     * Parses a {@code String building} into a {@code Building}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code building} is invalid.
     */
    public static Building parseBuilding(String building) throws IllegalValueException {
        requireNonNull(building);
        String trimmedBuilding = building.trim();
        if (!Building.isValidBuilding(trimmedBuilding)) {
            throw new IllegalValueException(Building.MESSAGE_BUILDING_CONSTRAINTS);
        }
        return new Building(trimmedBuilding);
    }
    //@@author

    //@@author yeggasd
    /**
     * Parses a (@code String oddEven)
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if not odd or even
     */
    public static String parseOddEven(String oddEven) throws IllegalValueException {
        requireNonNull(oddEven);
        String trimmedOddEven = oddEven.trim();
        if (!StringUtil.isOddEven(trimmedOddEven)) {
            throw new IllegalValueException(MESSAGE_NOT_ODDEVEN);
        }
        return trimmedOddEven;
    }
    //@@author
}
