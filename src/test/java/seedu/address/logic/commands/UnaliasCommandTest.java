package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_FIND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_HISTORY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_LIST_COMMAND;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.building.Building;
import seedu.address.model.building.exceptions.BuildingNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AliasBuilder;

//@@author jingyinno
public class UnaliasCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void constructor_nullAlias_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UnaliasCommand(null);
    }

    @Test
    public void execute_unaliasRemovedByModel_removeSuccessful() throws Exception {
        ModelStubAcceptingUnaliasAdded modelStub = new ModelStubAcceptingUnaliasAdded();

        String validUnalias = VALID_ALIAS_LIST;
        Alias validUnaliasAlias = new AliasBuilder().withCommand(VALID_ALIAS_LIST_COMMAND)
                .withAlias(VALID_ALIAS_LIST).build();
        ArrayList<Alias> expectedAliasesList = new ArrayList<Alias>();

        CommandResult commandResult = getUnaliasCommand(validUnalias, modelStub).execute();

        assertEquals(String.format(UnaliasCommand.MESSAGE_SUCCESS, validUnaliasAlias), commandResult.feedbackToUser);
        assertEquals(expectedAliasesList, modelStub.aliases);
    }

    @Test
    public void execute_unaliasRemovedByModel_removeFailure() throws Exception {
        ModelStubAcceptingUnaliasAdded modelStub = new ModelStubAcceptingUnaliasAdded();

        String invalidUnalias = VALID_ALIAS_FIND;

        thrown.expect(CommandException.class);
        thrown.expectMessage(UnaliasCommand.MESSAGE_UNKNOWN_UNALIAS);

        getUnaliasCommand(invalidUnalias, modelStub).execute();
    }

    @Test
    public void equals() {
        UnaliasCommand listUnaliasCommand = new UnaliasCommand(VALID_ALIAS_LIST);
        UnaliasCommand historyUnaliasCommand = new UnaliasCommand(VALID_ALIAS_HISTORY);

        // same object -> returns true
        assertTrue(listUnaliasCommand.equals(listUnaliasCommand));

        // same values -> returns true
        UnaliasCommand listUnaliasCommandCopy = new UnaliasCommand(VALID_ALIAS_LIST);
        assertTrue(listUnaliasCommand.equals(listUnaliasCommandCopy));

        // different types -> returns false
        assertFalse(listUnaliasCommand.equals(1));

        // null -> returns false
        assertFalse(listUnaliasCommand == null);

        // different unalias -> returns false
        assertFalse(listUnaliasCommand.equals(historyUnaliasCommand));
    }

    /**
     * Generates a new UnaliasCommand with the details of the given alias.
     */
    private UnaliasCommand getUnaliasCommand(String unalias, Model model) {
        UnaliasCommand command = new UnaliasCommand(unalias);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public void updatePassword(byte[] password)  {
            fail("This method should not be called.");
        }

        @Override
        public void removeAlias(String toRemove) throws AliasNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException {
            fail("This method should not be called.");
        }

        @Override
        public void exportAddressBook(String filepath, Password password) {
            fail("This method should not be called.");
        }

        @Override
        public void addAlias(Alias alias) throws DuplicateAliasException {
            fail("This method should not be called.");
        }

        @Override
        public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
                throws BuildingNotFoundException {
            fail("This method should not be called.");
            return null;
        }
    }

    /**
     * A Model stub that always accept the alias being removed.
     */
    private class ModelStubAcceptingUnaliasAdded extends ModelStub {
        private final ArrayList<Alias> aliases = new ArrayList<>();

        @Override
        public void removeAlias(String unalias) throws AliasNotFoundException {
            aliases.add(new Alias(VALID_ALIAS_LIST_COMMAND, VALID_ALIAS_LIST));
            requireNonNull(unalias);
            boolean isRemove = false;
            for (int i = 0; i < aliases.size(); i++) {
                if (aliases.get(i).getAlias().equals(unalias)) {
                    aliases.remove(aliases.get(i));
                    isRemove = true;
                    break;
                }
            }

            if (!isRemove) {
                throw new AliasNotFoundException();
            }
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
