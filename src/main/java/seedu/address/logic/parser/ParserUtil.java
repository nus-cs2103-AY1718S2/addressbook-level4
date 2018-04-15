package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.FileExistsException;
import seedu.address.commons.exceptions.IllegalFilenameException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.TimeUtil;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.OrderStatus;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.Preference;
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
    public static final String MESSAGE_FILENAME_CONSTRAINTS = "\"Filename should be of the format nameOfFile \"\n"
            + "and adhere to the following constraints:\\n\"\n"
            + "1. The nameOfFile should only contain characters from digits 0-9 and alphabets a-z or A-Z\"\n"
            + "2. The nameOfFile should be 30 characters or less.\"\n";
    public static final String MESSAGE_FILE_ALREADY_EXISTS = "\"File already exists, choose another filename.\"\n";

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
     * Parses a {@code String group} into a {@code Group}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code group} is invalid.
     */
    public static Group parseGroup(String group) throws IllegalValueException {
        requireNonNull(group);
        String trimmedGroup = group.trim();
        if (!Tag.isValidTagName(trimmedGroup)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Group(trimmedGroup);
    }

    /**
     * Parses {@code Collection<String> groups} into a {@code Set<Group>}.
     */
    public static Set<Group> parseGroups(Collection<String> groups) throws IllegalValueException {
        requireNonNull(groups);
        final Set<Group> groupSet = new HashSet<>();
        for (String groupName : groups) {
            groupSet.add(parseGroup(groupName));
        }
        return groupSet;
    }

    /**
     * Parses a {@code String pref} into a {@code Preference}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code preference} is invalid.
     */
    public static Preference parsePreference(String pref) throws IllegalValueException {
        requireNonNull(pref);
        String trimmedPreference = pref.trim();
        if (!Tag.isValidTagName(trimmedPreference)) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        return new Preference(trimmedPreference);
    }

    /**
     * Parses {@code Collection<String> preferences} into a {@code Set<Perference>}.
     */
    public static Set<Preference> parsePreferences(Collection<String> preferences) throws IllegalValueException {
        requireNonNull(preferences);
        final Set<Preference> preferenceSet = new HashSet<>();
        for (String prefName : preferences) {
            preferenceSet.add(parsePreference(prefName));
        }
        return preferenceSet;
    }

    //@@author amad-person
    /**
     * Parses a {@code String orderInformation} into a {@code OrderInformation}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code orderInformation} is invalid.
     */
    public static OrderInformation parseOrderInformation(String orderInformation) throws IllegalValueException {
        requireNonNull(orderInformation);
        String trimmedOrderInformation = orderInformation.trim();
        if (!OrderInformation.isValidOrderInformation(trimmedOrderInformation)) {
            throw new IllegalValueException(OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);
        }
        return new OrderInformation(trimmedOrderInformation);
    }

    /**
     * Parses a {@code Optional<String> orderInformation} into an {@code Optional<OrderInformation>}
     * if {@code orderInformation} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<OrderInformation> parseOrderInformation(Optional<String> orderInformation)
            throws IllegalValueException {
        requireNonNull(orderInformation);
        return orderInformation.isPresent()
                ? Optional.of(parseOrderInformation(orderInformation.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String orderStatus} into a {@code OrderStatus}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code orderStatus} is invalid.
     */
    public static OrderStatus parseOrderStatus(String orderStatus) throws IllegalValueException {
        requireNonNull(orderStatus);
        String trimmedOrderStatus = orderStatus.trim();
        if (!OrderStatus.isValidOrderStatus(trimmedOrderStatus)) {
            throw new IllegalValueException(OrderStatus.MESSAGE_ORDER_STATUS_CONSTRAINTS);
        }
        return new OrderStatus(trimmedOrderStatus);
    }

    /**
     * Parses a {@code Optional<String> orderStatus} into an {@code Optional<OrderStatus>}
     * if {@code orderStatus} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<OrderStatus> parseOrderStatus(Optional<String> orderStatus)
            throws IllegalValueException {
        requireNonNull(orderStatus);
        return orderStatus.isPresent()
                ? Optional.of(parseOrderStatus(orderStatus.get()))
                : Optional.empty();
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
     * Parses a {@code Optional<String> price} into an {@code Optional<Price>}
     * if {@code price} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Price> parsePrice(Optional<String> price)
            throws IllegalValueException {
        requireNonNull(price);
        return price.isPresent()
                ? Optional.of(parsePrice(price.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String quantity} into a {@code Quantity}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code quantity} is invalid.
     */
    public static Quantity parseQuantity(String quantity) throws IllegalValueException {
        requireNonNull(quantity);
        String trimmedQuantity = quantity.trim();
        if (!Quantity.isValidQuantity(trimmedQuantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        return new Quantity(trimmedQuantity);
    }

    /**
     * Parses a {@code Optional<String> quantity} into an {@code Optional<Quantity>}
     * if {@code quantity} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Quantity> parseQuantity(Optional<String> quantity)
            throws IllegalValueException {
        requireNonNull(quantity);
        return quantity.isPresent()
                ? Optional.of(parseQuantity(quantity.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String deliveryDate} into a {@code DeliveryDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code deliveryDate} is invalid.
     */
    public static DeliveryDate parseDeliveryDate(String deliveryDate) throws IllegalValueException {
        requireNonNull(deliveryDate);
        String trimmedDeliveryDate = deliveryDate.trim();
        if (!DeliveryDate.isValidDeliveryDate(trimmedDeliveryDate)) {
            throw new IllegalValueException(DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        }
        return new DeliveryDate(trimmedDeliveryDate);
    }

    /**
     * Parses a {@code Optional<String> deliveryDate} into an {@code Optional<DeliveryDate>}
     * if {@code deliveryDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DeliveryDate> parseDeliveryDate(Optional<String> deliveryDate)
            throws IllegalValueException {
        requireNonNull(deliveryDate);
        return deliveryDate.isPresent()
                ? Optional.of(parseDeliveryDate(deliveryDate.get()))
                : Optional.empty();
    }
    //@@ author

    /**
     * Parses a {@code String eventTitle} into a {@code EntryTitle}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code eventTitle} is invalid.
     */
    public static EntryTitle parseEventTitle(String eventTitle) throws IllegalValueException {
        requireNonNull(eventTitle);
        String trimmedEventTitle = eventTitle.trim();
        if (!EntryTitle.isValidEntryTitle(trimmedEventTitle)) {
            throw new IllegalValueException(EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);
        }
        return new EntryTitle(trimmedEventTitle);
    }

    /**
     * Parses a {@code Optional<String> eventTitle} into an {@code Optional<EntryTitle>}
     * if {@code eventTitle} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EntryTitle> parseEventTitle(Optional<String> eventTitle)
            throws IllegalValueException {
        requireNonNull(eventTitle);
        return eventTitle.isPresent()
                ? Optional.of(parseEventTitle(eventTitle.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String startDate} into a {@code StartDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code startDate} is invalid.
     */
    public static StartDate parseStartDate(String startDate) throws IllegalValueException {
        requireNonNull(startDate);
        String trimmedStartDate = startDate.trim();
        if (!DateUtil.isValidDate(trimmedStartDate)) {
            throw new IllegalValueException(StartDate.MESSAGE_START_DATE_CONSTRAINTS);
        }
        return new StartDate(trimmedStartDate);
    }

    /**
     * Parses a {@code Optional<String> startDate} into an {@code Optional<StartDate>}
     * if {@code startDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartDate> parseStartDate(Optional<String> startDate)
            throws IllegalValueException {
        requireNonNull(startDate);
        return startDate.isPresent()
                ? Optional.of(parseStartDate(startDate.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String endDate} into a {@code EndDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code endDate} is invalid.
     */
    public static EndDate parseEndDate(String endDate) throws IllegalValueException {
        requireNonNull(endDate);
        String trimmedEndDate = endDate.trim();
        if (!DateUtil.isValidDate(trimmedEndDate)) {
            throw new IllegalValueException(EndDate.MESSAGE_END_DATE_CONSTRAINTS);
        }
        return new EndDate(trimmedEndDate);
    }

    /**
     * Parses a {@code Optional<String> endDate} into an {@code Optional<EndDate>}
     * if {@code endDate} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndDate> parseEndDate(Optional<String> endDate)
            throws IllegalValueException {
        requireNonNull(endDate);
        return endDate.isPresent()
                ? Optional.of(parseEndDate(endDate.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String startTime} into a {@code StartTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code startTime} is invalid.
     */
    public static StartTime parseStartTime(String startTime) throws IllegalValueException {
        requireNonNull(startTime);
        String trimmedStartTime = startTime.trim();
        if (!TimeUtil.isValidTime(trimmedStartTime)) {
            throw new IllegalValueException(StartTime.MESSAGE_START_TIME_CONSTRAINTS);
        }
        return new StartTime(trimmedStartTime);
    }

    /**
     * Parses a {@code Optional<String> startTime} into an {@code Optional<StartTime>}
     * if {@code startTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<StartTime> parseStartTime(Optional<String> startTime)
            throws IllegalValueException {
        requireNonNull(startTime);
        return startTime.isPresent()
                ? Optional.of(parseStartTime(startTime.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String endTime} into a {@code EndTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code endTime} is invalid.
     */
    public static EndTime parseEndTime(String endTime) throws IllegalValueException {
        requireNonNull(endTime);
        String trimmedEndTime = endTime.trim();
        if (!TimeUtil.isValidTime(trimmedEndTime)) {
            throw new IllegalValueException(EndTime.MESSAGE_END_TIME_CONSTRAINTS);
        }
        return new EndTime(trimmedEndTime);
    }

    /**
     * Parses a {@code Optional<String> endTime} into an {@code Optional<EndTime>}
     * if {@code endTime} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<EndTime> parseEndTime(Optional<String> endTime)
            throws IllegalValueException {
        requireNonNull(endTime);
        return endTime.isPresent()
                ? Optional.of(parseEndTime(endTime.get()))
                : Optional.empty();
    }

    /**
     * Parses a {@code String filename} into an {@code filename}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalFilenameException if the given {@code filename} is invalid.
     * @throws FileExistsException if the given {@code filename} already exists.
     */
    public static String parseFilename(String filename) throws IllegalFilenameException, FileExistsException  {
        //requireNonNull(filename);
        filename = filename.trim();
        String filenameVerified = "";
        int filenameLength = filename.length();

        for (int i = 0; i < filenameLength; i++) {
            char charI = filename.charAt(i);

            //if ((charI >= 48 && charI <= 57) || (charI >= 65 && charI <= 90) || (charI >= 97 && charI <= 122)) {
            if (Character.isDigit(charI) || Character.isLetter(charI)) {
                filenameVerified = filenameVerified + charI;
            }
        }

        File outputFile = new File(filenameVerified + ".csv");

        if (filenameLength < 1 || filenameLength > 30 || !(filename.equals(filenameVerified))) {
            throw new IllegalFilenameException(MESSAGE_FILENAME_CONSTRAINTS);
        } else if (outputFile.exists()) {
            throw new FileExistsException(MESSAGE_FILE_ALREADY_EXISTS);
        }

        return filename;
    }

    /**
     * Parses {@code date} into an {@code LocalDate} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static LocalDate parseTargetDate(String date) throws IllegalValueException {
        String trimmedDate = date.trim();
        if (!DateUtil.isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_INVALID_DATE_FORMAT);
        }

        LocalDate localDate;

        try {
            localDate = DateUtil.convertStringToDate(trimmedDate);
        } catch (DateTimeParseException dtpe) {
            throw new AssertionError("Given date should be valid for conversion.");
        }

        return localDate;
    }
}
