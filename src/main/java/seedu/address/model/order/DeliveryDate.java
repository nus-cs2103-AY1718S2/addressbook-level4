package seedu.address.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents Order's delivery date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeliveryDate(String)}
 */
public class DeliveryDate {

    public static final String MESSAGE_DELIVERY_DATE_CONSTRAINTS =
            "Date should be DD-MM-YYYY, and it should not be blank";

    public static final String DELIVERY_DATE_VALIDATION_REGEX = "\\d{2}-\\d{2}-\\d{4}"; // format
    public static final String DELIVERY_DATE_VALIDATION_DATE_FORMAT = "dd-MM-yyyy"; // legal dates

    private final String deliveryDate;

    /**
     * Constructs a {@code DeliveryDate}.
     *
     * @param date A valid Date.
     */
    public DeliveryDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDeliveryDate(date), MESSAGE_DELIVERY_DATE_CONSTRAINTS);
        this.deliveryDate = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDeliveryDate(String test) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DELIVERY_DATE_VALIDATION_DATE_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(test);
        } catch (ParseException e) {
            return false;
        }

        return test.matches(DELIVERY_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return deliveryDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeliveryDate // instanceof handles nulls
                && this.deliveryDate.equals(((DeliveryDate) other).deliveryDate)); // state check
    }

    @Override
    public int hashCode() {
        return deliveryDate.hashCode();
    }
}

