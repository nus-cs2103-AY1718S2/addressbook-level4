package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.JerseyNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Rating;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.TeamName;

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
    public static final String MESSAGE_INVALID_INPUT = "You have entered an invalid input.";
    public static final String UNSPECIFIED_FIELD = "<UNSPECIFIED>";

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

    //@@author jordancjq
    /**
     * Parses {@code String oneBasedIndexes} into a {@code List<Index>} and returns it. Leading and trailing
     * whitespaces will be trimmed.
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws IllegalValueException {
        String trimmedIndexes = oneBasedIndexes.trim();

        String[] splitOneBasedIndexes = trimmedIndexes.split("\\s+");

        Set<String> uniqueIndexes = new HashSet<>(Arrays.asList(splitOneBasedIndexes));

        List<Index> indexList = new ArrayList<>();

        for (String index : uniqueIndexes) {
            indexList.add(parseIndex(index));
        }

        return indexList;
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
     * Parses {@code String teamName} into an {@code TeamName}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static TeamName parseTeamName(String teamName) throws IllegalValueException {
        requireNonNull(teamName);
        String trimmedTeamName = teamName.trim();
        if (!TeamName.isValidName(trimmedTeamName)) {
            throw new IllegalValueException(TeamName.MESSAGE_TEAM_NAME_CONSTRAINTS);
        }

        return new TeamName(trimmedTeamName);
    }

    /**
     * Parses a {@code Optional<String> teamName} into an {@code Optional<TeamName>} if {@code teamName} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TeamName> parseTeamName(Optional<String> teamName) throws IllegalValueException {
        requireNonNull(teamName);
        return teamName.isPresent() ? Optional.of(parseTeamName(teamName.get())) : Optional.empty();
    }

    //@@author jordancjq
    /**
     * Parses a {@code Optional<String> value} into the specified value or {@code UNSPECIFIED_FIELD} if is empty
     */
    public static Optional<String> parseValue(Optional<String> value, String messageConstraints)
            throws IllegalValueException {
        if (value.isPresent() && value.get().equals(UNSPECIFIED_FIELD)) {
            throw new IllegalValueException(messageConstraints);
        } else {
            return Optional.of(value.orElse(UNSPECIFIED_FIELD));
        }
    }

    //@@author
    /**
     * Parses a {@code String tagColour} into a {@code String ta}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code tag} is invalid.
     */
    public static String parseTagColour(String tagColour) throws IllegalValueException {
        requireNonNull(tagColour);
        String trimmedTagColour = tagColour.trim();
        if (!trimmedTagColour.getClass().equals(String.class) ||  (trimmedTagColour.contains(" "))) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_COLOUR_CONSTRAINTS);
        }
        return trimmedTagColour;
    }

    /**
     * Parses a {@code String rating} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code rating} is invalid.
     */
    public static Rating parseRating(String rating) throws IllegalValueException {
        requireNonNull(rating);
        String trimmedRating = rating.trim();
        if (!Rating.isValidRating(trimmedRating)) {
            throw new IllegalValueException(Rating.MESSAGE_RATING_CONSTRAINTS);
        }
        return new Rating(trimmedRating);
    }

    /**
     * Parses a {@code Optional<String> rating} into an {@code Optional<Rating>} if {@code rating} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Rating> parseRating(Optional<String> rating) throws IllegalValueException {
        requireNonNull(rating);
        return rating.isPresent() ? Optional.of(parseRating(rating.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String position} into a {@code Position}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code position} is invalid.
     */
    public static Position parsePosition(String position) throws IllegalValueException {
        requireNonNull(position);
        String trimmedPosition = position.trim();
        if (!Position.isValidPosition(trimmedPosition)) {
            throw new IllegalValueException(Position.MESSAGE_POSITION_CONSTRAINTS);
        }
        return new Position(trimmedPosition);
    }

    /**
     * Parses a {@code Optional<String> position} into an {@code Optional<Position>} if {@code position} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Position> parsePosition(Optional<String> position) throws IllegalValueException {
        requireNonNull(position);
        return position.isPresent() ? Optional.of(parsePosition(position.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String jerseyNumber} into a {@code JerseyNumber}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code jerseyNumber} is invalid.
     */
    public static JerseyNumber parseJerseyNumber(String jerseyNumber) throws IllegalValueException {
        requireNonNull(jerseyNumber);
        String trimmedJerseyNumber = jerseyNumber.trim();
        if (!JerseyNumber.isValidJerseyNumber(trimmedJerseyNumber)) {
            throw new IllegalValueException(JerseyNumber.MESSAGE_JERSEY_NUMBER_CONSTRAINTS);
        }
        return new JerseyNumber(trimmedJerseyNumber);
    }

    /**
     * Parses a {@code Optional<String> jerseyNumber} into an {@code Optional<JerseyNumber>}
     * if {@code jerseyNumber} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<JerseyNumber> parseJerseyNumber(Optional<String> jerseyNumber) throws IllegalValueException {
        requireNonNull(jerseyNumber);
        return jerseyNumber.isPresent() ? Optional.of(parseJerseyNumber(jerseyNumber.get())) : Optional.empty();
    }

}
