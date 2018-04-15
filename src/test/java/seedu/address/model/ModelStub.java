package seedu.address.model;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.building.Building;
import seedu.address.model.building.exceptions.BuildingNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author jingyinno
/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void addPerson(Person person) throws DuplicatePersonException {
        fail("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        fail("This method should not be called.");
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData, HashMap<String, String> newAliasList) {
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
    public void updateAliasesMapping(HashMap<String, String> aliases) {
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
    public void uploadAddressBook(String filepath, Password password) {
        fail("This method should not be called.");
    }

    @Override
    public void addAlias(Alias alias) throws DuplicateAliasException {
        fail("This method should not be called.");
    }

    @Override
    public HashMap<String, String> getAliasList() {
        return new HashMap<String, String>();
    }

    @Override
    public String getCommandFromAlias(String aliasKey) {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> getUiFormattedAliasList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building)
            throws BuildingNotFoundException {
        fail("This method should not be called.");
        return null;
    }
}
