package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUILDING_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUILDING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUILDING_2;

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

public class VacantCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullBuilding_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new VacantCommand(null);
    }

    @Test
    public void execute_validBuildingRetrieval_success() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingBuilding();
        Building validBuilding = new Building(VALID_BUILDING_1);

        CommandResult commandResult = getVacantCommand(validBuilding, modelStub).execute();

        assertEquals(String.format(VacantCommand.MESSAGE_SUCCESS, validBuilding), commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidBuildingRetrieval_failure() throws Exception {
        ModelStub modelStub = new ModelStubAcceptingBuilding();
        Building validBuilding = new Building(INVALID_BUILDING_3);

        thrown.expect(CommandException.class);

        getVacantCommand(validBuilding, modelStub).execute();
    }

    @Test
    public void equals() throws Exception {
        Building validBuildingOne = new Building(VALID_BUILDING_1);
        Building validBuildingTwo = new Building(VALID_BUILDING_2);

        VacantCommand oneVacantCommand = new VacantCommand(validBuildingOne);

        // same object -> returns true

        assertTrue(validBuildingOne.equals(validBuildingOne));

        // same values -> returns true
        VacantCommand validBuildingOneCopy = new VacantCommand(validBuildingOne);
        assertTrue(oneVacantCommand.equals(validBuildingOneCopy));

        // different types -> returns false
        assertFalse(validBuildingOne.equals(1));

        // null -> returns false
        assertFalse(validBuildingOne == null);

        // different vacant -> returns false
        assertFalse(validBuildingOne.equals(validBuildingTwo));
    }

    /**
     * Generates a new VacantCommand with the details of the given building
     */
    private VacantCommand getVacantCommand(Building building, Model model) {
        VacantCommand command = new VacantCommand(building);
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
     * A Model stub that always accept the building being requested.
     */
    private class ModelStubAcceptingBuilding extends ModelStub {
        private ArrayList<ArrayList<String>> roomsSchedule = new ArrayList<ArrayList<String>>();

        @Override
        public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
                throws BuildingNotFoundException {

            if (!Building.isValidBuilding(building)) {
                throw new BuildingNotFoundException();
            }
            ArrayList<String> rooms = new ArrayList<>();
            rooms.add("room");
            roomsSchedule.add(rooms);
            return roomsSchedule;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
