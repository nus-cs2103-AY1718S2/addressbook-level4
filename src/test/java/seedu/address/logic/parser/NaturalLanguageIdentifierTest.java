package seedu.address.logic.parser;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;
import static seedu.address.logic.parser.NaturalLanguageIdentifier.NATURAL_CURRENT_MONTH;
import static seedu.address.logic.parser.NaturalLanguageIdentifier.NATURAL_LAST_MONTH;
import static seedu.address.logic.parser.NaturalLanguageIdentifier.NATURAL_NEXT_MONTH;
import static seedu.address.logic.parser.NaturalLanguageIdentifier.NATURAL_NOW;

import java.time.LocalDateTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


//@@author yungyung04
public class NaturalLanguageIdentifierTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private NaturalLanguageIdentifier identifier = NaturalLanguageIdentifier.getInstance();

    @Test
    public void getInstance_firstTimeCalled_returnInstanceOfClass() {
        assertTrue(identifier instanceof NaturalLanguageIdentifier);
    }

    @Test
    public void getInstance_subsequentCalls_returnSameInstance() {
        NaturalLanguageIdentifier identifierCopy = NaturalLanguageIdentifier.getInstance();
        assertEquals(identifier, identifierCopy);
    }

    @Test
    public void getMonthAsString_recognizableInput_returnMonth() {
        LocalDateTime current = LocalDateTime.now();

        //natural languages which refer to current month
        assertEquals(current.getMonth().name(), identifier.getMonthAsString(NATURAL_CURRENT_MONTH));
        assertEquals(current.getMonth().name(), identifier.getMonthAsString(NATURAL_NOW));

        //natural language which refers to last month
        assertEquals(current.getMonth().minus(1).name(), identifier.getMonthAsString(NATURAL_LAST_MONTH));

        //natural language which refers to next month
        assertEquals(current.getMonth().plus(1).name(), identifier.getMonthAsString(NATURAL_NEXT_MONTH));
    }

    @Test
    public void getMonthAsString_unrecognizableInput_returnInput() {
        LocalDateTime current = LocalDateTime.now();
        String unrecognizable = "unrecognizable input";
        assertEquals(unrecognizable, identifier.getMonthAsString(unrecognizable));
    }

    @Test
    public void getMonthAsString_nullInput_returnInput() {
        LocalDateTime current = LocalDateTime.now();
        String unrecognizable = null;
        thrown.expect(NullPointerException.class);
        String result = identifier.getMonthAsString(unrecognizable);
    }

    @Test
    public void mergeTwoWordedNaturalLanguage_emptyString_returnEmptyString() {
        String[] userInputs = {};
        String[] expectedResults = {};
        String[] results = identifier.mergeTwoWordedNaturalLanguage(userInputs);
        assertArrayEquals(expectedResults, results);
    }

    @Test
    public void mergeTwoWordedNaturalLanguage_oneRecognizableElement_returnInputtedArray() {
        String[] userInputs = {"this"};
        String[] expectedResults = {"this"};
        String[] results = identifier.mergeTwoWordedNaturalLanguage(userInputs);
        assertArrayEquals(expectedResults, userInputs);
    }

    @Test
    public void mergeTwoWordedNaturalLanguage_oneUnrecognizableElement_returnInputtedArray() {
        String[] userInputs = {"unrecognizable"};
        String[] expectedResults = {"unrecognizable"};
        String[] results = identifier.mergeTwoWordedNaturalLanguage(userInputs);
        assertArrayEquals(expectedResults, results);
    }

    @Test
    public void mergeTwoWordedNaturalLanguage_multipleElements_returnMergedArray() {
        String[] userInputs = {"this", "month", "today", "unrecognized", "last", "month", "unrecognized"};
        String[] expectedResults = {"this month", "today", "unrecognized", "last month", "unrecognized"};
        String[] results = identifier.mergeTwoWordedNaturalLanguage(userInputs);
        assertArrayEquals(expectedResults, results);
    }
}
