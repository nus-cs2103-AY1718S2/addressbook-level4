package seedu.address.model.person;

/**
 * Represents a Person's tutorial participation in the address book.
 */
//@@author Alaru
public class Participation {

    public static final String MESSAGE_PARTICPATION_CONSTRAINTS = "Participation marks cannot be negative or over 100!";

    public final Integer threshold;
    private Integer value;

    /**
     * Constructs a {@code Participation}.
     */
    public Participation() {
        this.value = 0;
        threshold = 50;
    }

    public Participation(String value) {
        this.value = Integer.parseInt(value);
        threshold = 50;
    }

    public Participation(Integer value) {
        this.value = value;
        threshold = 50;
    }

    public void addParticipation(int marks) {
        value = (value + marks) % 101;
    }

    public Integer getMarks() {
        return value;
    }

    public boolean overThreshold() {
        return (value > threshold);
    }

    public static boolean isValidParticipation(String value) {
        return Integer.parseInt(value) <= 100 && Integer.parseInt(value) > -1;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Participation // instanceof handles nulls
                && this.value.equals(((Participation) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
