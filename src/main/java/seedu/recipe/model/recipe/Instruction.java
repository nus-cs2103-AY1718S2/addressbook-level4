package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe instruction in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInstuction(String)}
 */
public class Instruction {

    public static final String MESSAGE_INSTRUCTION_CONSTRAINTS =
            "Recipe instructions can take any values, new lines included and it should not be blank";

    /*
     * The first character of the recipe must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String INSTRUCTION_VALIDATION_REGEX = "\\s*\\S+[\\s?\\S]*\\s*";

    public final String value;

    /**
     * Constructs an {@code Instruction}.
     *
     * @param instruction A valid recipe instruction
     */
    public Instruction(String instruction) {
        requireNonNull(instruction);
        checkArgument(isValidInstuction(instruction), MESSAGE_INSTRUCTION_CONSTRAINTS);
        this.value = instruction;
    }

    /**
     * Returns true if a given string is a valid recipe instruction.
     */
    public static boolean isValidInstuction(String test) {
        return test.matches(INSTRUCTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Instruction // instanceof handles nulls
                && this.value.equals(((Instruction) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
