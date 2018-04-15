package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.dish.Price;
import seedu.address.model.person.Address;
import seedu.address.model.person.Halal;
import seedu.address.model.person.Name;
import seedu.address.model.person.Order;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Vegetarian;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Count;
import seedu.address.model.task.Distance;

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

    //@@author ZacZequn
    /**
     * Parses a {@code String order} into a {@code Order}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code order} is invalid.
     */
    public static Order parseOrder(String order) throws IllegalValueException {
        requireNonNull(order);
        String trimmedOrder = order.trim();
        if (!Order.isValidOrder(trimmedOrder)) {
            throw new IllegalValueException(Order.MESSAGE_ORDER_CONSTRAINTS);
        }
        return new Order(trimmedOrder);
    }

    /**
     * Parses a {@code Optional<String> order} into an {@code Optional<Order>} if {@code order} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Order> parseOrder(Optional<String> order) throws IllegalValueException {
        requireNonNull(order);
        return order.isPresent() ? Optional.of(parseOrder(order.get())) : Optional.empty();
    }
    //@@author

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

    //@@author ZacZequn
    /**
     * Parses a {@code String halal} into an {@code Halal}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code halal} is invalid.
     */
    public static Halal parseHalal(String halal) throws IllegalValueException {
        requireNonNull(halal);
        String trimmedHalal = halal.trim();
        if (!Halal.isValidHalal(trimmedHalal)) {
            throw new IllegalValueException(Halal.MESSAGE_HALAL_CONSTRAINTS);
        }
        return new Halal(trimmedHalal);
    }

    /**
     * Parses a {@code Optional<String> halal} into an {@code Optional<Halal>} if {@code halal} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Halal> parseHalal(Optional<String> halal) throws IllegalValueException {
        requireNonNull(halal);
        return halal.isPresent() ? Optional.of(parseHalal(halal.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String vegetarian} into an {@code Vegetarian}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code vegetarian} is invalid.
     */
    public static Vegetarian parseVegetarian(String vegetarian) throws IllegalValueException {
        requireNonNull(vegetarian);
        String trimmedVegetarian = vegetarian.trim();
        if (!Vegetarian.isValidVegetarian(trimmedVegetarian)) {
            throw new IllegalValueException(Vegetarian.MESSAGE_VEGETARIAN_CONSTRAINTS);
        }
        return new Vegetarian(trimmedVegetarian);
    }

    /**
     * Parses a {@code Optional<String> vegetarian} into an {@code Optional<Vegetarian>}
     * if {@code vegetarian} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Vegetarian> parseVegetarian(Optional<String> vegetarian) throws IllegalValueException {
        requireNonNull(vegetarian);
        return vegetarian.isPresent() ? Optional.of(parseVegetarian(vegetarian.get())) : Optional.empty();
    }
    //@@author

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
     * Parses a {@code String price} into a {@code Price}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code price} is invalid.
     */
    public static Price parsePrice(String price) throws IllegalValueException {
        requireNonNull(price);
        String trimmedPrice = price.trim();
        if (!Price.isValidPrice(trimmedPrice)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
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
     * Parses a {@code String distance} into a {@code Distance}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code distance} is invalid.
     */
    public static Distance parseDistance(String distance) throws IllegalValueException {
        requireNonNull(distance);
        String trimmedDistance = distance.trim();
        if (!Distance.isValidDistance(trimmedDistance)) {
            throw new IllegalValueException(Distance.MESSAGE_DISTANCE_CONSTRAINTS);
        }
        return new Distance(trimmedDistance);
    }

    /**
     * Parses a {@code Optional<String> distance} into an {@code Optional<Distance>} if {@code distance} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Distance> parseDistance(Optional<String> distance) throws IllegalValueException {
        requireNonNull(distance);
        return distance.isPresent() ? Optional.of(parseDistance(distance.get())) : Optional.empty();
    }

    /**
     * Parses a {@code String count} into a {@code Count}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code count} is invalid.
     */
    public static Count parseCount(String count) throws IllegalValueException {
        requireNonNull(count);
        String trimmedCount = count.trim();
        if (!Count.isValidCount(trimmedCount)) {
            throw new IllegalValueException(Count.MESSAGE_COUNT_CONSTRAINTS);
        }
        return new Count(trimmedCount);
    }

    /**
     * Parses a {@code Optional<String> count} into an {@code Optional<Count>} if {@code count} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Count> parseCount(Optional<String> count) throws IllegalValueException {
        requireNonNull(count);
        return count.isPresent() ? Optional.of(parseCount(count.get())) : Optional.empty();
    }
}
