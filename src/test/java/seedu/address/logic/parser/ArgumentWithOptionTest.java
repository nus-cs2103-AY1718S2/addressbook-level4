// @@author anh2111
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ArgumentWithOptionTest {

    @Test
    public void argumentWithOption_inputArgsWithoutOptions_success() throws ParseException {
        String inputArgs = "n/Anh t/Software";
        ArgumentWithOption args = new ArgumentWithOption(inputArgs);

        assertFalse(args.isOption("add-tag"));
        assertEquals(inputArgs, args.getArgs());
    }

    @Test
    public void argumentWithOption_inputArgsWithSingleOption_success() throws ParseException {
        String inputArgs = "-add-tag t/Anh t/Interview t/Screening";
        String expectedArgs = " t/Anh t/Interview t/Screening";
        String expectedOption = "add-tag";

        ArgumentWithOption args = new ArgumentWithOption(inputArgs);

        assertEquals(expectedArgs, args.getArgs());
        assertTrue(args.isOption(expectedOption));
    }
}
// @@author
