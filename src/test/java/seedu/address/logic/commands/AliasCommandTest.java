package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMAND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_ADD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.testutil.AliasBuilder;

//@@author jingyinno
/**
 * Contains integration tests (interaction with the Model) and unit tests for AliasCommand.
 */
public class AliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AliasCommand(null);
    }

    @Test
    public void execute_aliasAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        Alias validAlias = new AliasBuilder().build();

        CommandResult commandResult = getAliasCommand(validAlias, modelStub).execute();

        assertEquals(String.format(AliasCommand.MESSAGE_SUCCESS, validAlias), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAlias), modelStub.aliasesAdded);
    }

    @Test
    public void execute_duplicateAlias_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAliasException();
        Alias validAlias = new AliasBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AliasCommand.MESSAGE_DUPLICATE_ALIAS);

        getAliasCommand(validAlias, modelStub).execute();
    }

    @Test
    public void execute_aliasWordAlias_failure() throws Exception {
        //test alias word to be a command word failure
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        List<String> commands = AliasCommand.getCommands();
        for (int i = 0; i < commands.size(); i++) {
            for (int j = 0; j < commands.size(); j++) {
                thrown.expect(CommandException.class);
                Alias invalidAlias = new Alias(commands.get(i), commands.get(j));
                getAliasCommand(invalidAlias, modelStub).execute();
            }
        }
    }

    @Test
    public void execute_commandWordAlias_failure() throws Exception {
        //test invalid command word with valid alias word failure
        ModelStubAcceptingAliasAdded modelStub = new ModelStubAcceptingAliasAdded();
        Alias invalidAlias = new Alias(INVALID_COMMAND_DESC, VALID_ALIAS_ADD);

        thrown.expect(CommandException.class);

        getAliasCommand(invalidAlias, modelStub).execute();
    }


    @Test
    public void equals() {
        Alias edit = new AliasBuilder().withCommand("Edit").build();
        Alias exit = new AliasBuilder().withCommand("Exit").build();

        AliasCommand editAliasCommand = new AliasCommand(edit);
        AliasCommand exitAliasCommand = new AliasCommand(exit);

        // same object -> returns true
        assertTrue(editAliasCommand.equals(editAliasCommand));

        // same values -> returns true
        AliasCommand editAliasCommandCopy = new AliasCommand(edit);
        assertTrue(editAliasCommand.equals(editAliasCommandCopy));

        // different types -> returns false
        assertFalse(editAliasCommand.equals(1));

        // null -> returns false
        assertFalse(editAliasCommand == null);

        // different alias -> returns false
        assertFalse(editAliasCommand.equals(exitAliasCommand));
    }

    /**
     * Generates a new AliasCommand with the details of the given alias.
     */
    private AliasCommand getAliasCommand(Alias alias, Model model) {
        AliasCommand command = new AliasCommand(alias);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateAliasException extends ModelStub {
        @Override
        public void addAlias(Alias alias) throws DuplicateAliasException {
            throw new DuplicateAliasException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the alias being added.
     */
    private class ModelStubAcceptingAliasAdded extends ModelStub {
        private final ArrayList<Alias> aliasesAdded = new ArrayList<>();

        @Override
        public void addAlias(Alias alias) {
            requireNonNull(alias);
            aliasesAdded.add(alias);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
