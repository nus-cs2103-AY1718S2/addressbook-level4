package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.util.SampleDataUtil;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand)
            throws CommandException {
        try {
            Command command = parser.parse(userInput, new ModelManager());
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage)
            throws CommandException {
        try {
            parser.parse(userInput, new ModelManager());
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser}
     * is successful and the addtransaction command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseTransactionSuccess(Parser parser, String userInput, Command expectedCommand)
            throws CommandException, DuplicatePersonException {
        try {
            ModelManager modelManager = new ModelManager();
            modelManager.addPerson(SampleDataUtil.getSamplePersons()[0]);
            modelManager.addPerson(SampleDataUtil.getSamplePersons()[1]);
            modelManager.addPerson(SampleDataUtil.getSamplePersons()[2]);
            Command command = parser.parse(userInput, modelManager);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful
     * for add transaction and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseTransactionFailure(Parser parser, String userInput, String expectedMessage)
            throws CommandException, DuplicatePersonException {
        try {
            ModelManager modelManager = new ModelManager();
            modelManager.addPerson(SampleDataUtil.getSamplePersons()[0]);
            modelManager.addPerson(SampleDataUtil.getSamplePersons()[1]);
            modelManager.addPerson(SampleDataUtil.getSamplePersons()[2]);
            parser.parse(userInput, modelManager);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
