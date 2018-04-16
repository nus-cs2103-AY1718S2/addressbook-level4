package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * Represents a Person's value in the address book
 * Guarantees: immutable; is valid as declare in {@link #isValid(Double)}}
 */
public class Expenditure {
    public static final String MESSAGE_EXPENDITURE_CONSTRAINTS =
            "Person's expenditure must be positive numerical number, Type: Double";

    public final Double value;

    /**
     * @param value a valid value
     */
    public Expenditure(Double value) {
        if (value == null) {
            this.value = 0.0;
        } else {
            checkArgument(this.isValid(value), this.MESSAGE_EXPENDITURE_CONSTRAINTS);
            this.value = value;
        }

    }

    /**
     * checks if the expenditure is valid
     *
     * @param expenditure
     * @return
     */
    public static boolean isValid(Double expenditure) {
        requireNonNull(expenditure);
        return (expenditure >= 0.0);
    }

    @Override
    public String toString() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(this.value);
    }


    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Expenditure
                && this.value == ((Expenditure) other).value);
    }

}
