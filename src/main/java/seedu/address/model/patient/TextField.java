//@@author nhs-work
package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's medical record's text field
 * (symptom/illness/treatment) in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTextField(String)}
 */
public class TextField {

    public static final String MESSAGE_TEXTFIELD_CONSTRAINTS =
            "Patient text field should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * TextField must not be null and it can only contain alphanumeric characters and spaces.
     */
    public static final String TEXTFIELD_VALIDATION_REGEX = "[\\p{Graph}\\p{Blank}]*";

    public final String textField;

    /**
     * Constructs a {@code TextField}.
     *
     * @param textField A valid TextField.
     */
    public TextField(String textField) {
        requireNonNull(textField);
        checkArgument(isValidTextField(textField), MESSAGE_TEXTFIELD_CONSTRAINTS);
        this.textField = textField;
    }

    /**
     * Returns true if a given string is a valid patient TextField.
     */
    public static boolean isValidTextField(String test) {
        return test.matches(TEXTFIELD_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return textField;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TextField // instanceof handles nulls
                && this.textField.equals(((TextField) other).textField)); // state check
    }

    @Override
    public int hashCode() {
        return textField.hashCode();
    }

}
