package seedu.recipe.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.recipe.testutil.Assert;

public class InstructionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Instruction(null));
    }

    @Test
    public void constructor_invalidInstruction_throwsIllegalArgumentException() {
        String invalidInstruction = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Instruction(invalidInstruction));
    }

    @Test
    public void isValidInstruction() {
        // null recipe
        Assert.assertThrows(NullPointerException.class, () -> Instruction.isValidInstuction(null));

        // invalid instructions
        assertFalse(Instruction.isValidInstuction("")); // empty string
        assertFalse(Instruction.isValidInstuction(" ")); // spaces only

        // valid instructions
        assertTrue(Instruction.isValidInstuction("Blk 456, Den Road, #01-355"));
        assertTrue(Instruction.isValidInstuction("-")); // one character
        assertTrue(Instruction.isValidInstuction("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long recipe
    }
}
