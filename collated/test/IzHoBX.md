# IzHoBX
###### \java\seedu\address\logic\commands\RateCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class RateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Person editedPerson = new PersonBuilder().withRating("1").withEmail("alice@example.com")
                .withReviews("supervisor@example.com\nLazy")
                .build();
        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRating("1").build();

        RateCommand rateCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(RateCommand.MESSAGE_RATE_PERSON_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(rateCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code RateCommand} with parameters {@code index} and {@code descriptor}
     */
    private RateCommand prepareCommand(Index index, EditCommand.EditPersonDescriptor descriptor) {
        RateCommand rateCommand = new RateCommand(index, descriptor);
        rateCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return rateCommand;
    }
}
```
###### \java\seedu\address\logic\parser\RateCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RateCommand;

public class RateCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_oneIntegerArg_failure() {
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonIntegerArg_failure() {
        assertParseFailure(parser, "a b", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 b", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "b 1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_indexZeroOrLess_failure() {
        assertParseFailure(parser, "0 5", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "-1 5", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_ratingOutOfBound_failure() {
        assertParseFailure(parser, "1 6", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 0", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 -1", MESSAGE_INVALID_FORMAT);
    }
}
```
