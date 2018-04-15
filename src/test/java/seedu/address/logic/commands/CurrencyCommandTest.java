package seedu.address.logic.commands;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Before;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ConvertCommand}.
 */
//@@author software-1234
public class CurrencyCommandTest {
    private CurrencyCommand currencyCommand;


    @Before
    public void setUp() {
        Model model = new ModelManager();
        currencyCommand = new CurrencyCommand(INDEX_FIRST_PERSON, "USD", "SGD");
    }
}
