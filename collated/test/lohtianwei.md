# lohtianwei
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getEmptyAddressBook;
import static seedu.address.testutil.TypicalPersons.getSortedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SortCommandTest {

    @Rule
    public ExpectedException error = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model emptyModel = new ModelManager(getEmptyAddressBook(), new UserPrefs());
    private Model sortedByNameDesc = new ModelManager(
            getSortedAddressBook("name", "desc"), new UserPrefs());

    private Model sortedByAdd = new ModelManager(
            getSortedAddressBook("address", "asc"), new UserPrefs());
    private Model sortedByAddDesc = new ModelManager(
            getSortedAddressBook("address", "desc"), new UserPrefs());

    private Model sortedByEmail = new ModelManager(getSortedAddressBook("email", "asc"), new UserPrefs());
    private Model sortedByEmailDesc = new ModelManager(
            getSortedAddressBook("email", "desc"), new UserPrefs());

    @Test
    public void noPlayers() throws CommandException {
        error.expect(CommandException.class);
        prepareCommand("name", "asc", emptyModel).execute();
    }

    @Test
    public void emptySortField_throwsNullPointerEx() {
        error.expect(NullPointerException.class);
        new SortCommand("name", null);
    }

    @Test
    public void emptySortOrder_throwsNullPointerEx() {
        error.expect(NullPointerException.class);
        new SortCommand(null, "asc");
    }

    @Test
    public void sortByName_success() throws Exception {
        SortCommand so = prepareCommand("name", "asc", model);
        String expected = String.format(MESSAGE_SUCCESS, "name", "asc");
        assertCommandSuccess(so, model, expected, model);
    }

    @Test
    public void sortByNameDesc_success() throws Exception {
        SortCommand so = prepareCommand("name", "desc", model);
        String expected = String.format(MESSAGE_SUCCESS, "name", "desc");
        assertCommandSuccess(so, model, expected, sortedByNameDesc);
    }

    @Test
    public void sortByAdd_success() throws Exception {
        SortCommand so = prepareCommand("address", "asc", model);
        String expected = String.format(MESSAGE_SUCCESS, "address", "asc");
        assertCommandSuccess(so, model, expected, sortedByAdd);
    }

    @Test
    public void sortByAddDesc_success() throws Exception {
        SortCommand so = prepareCommand("address", "desc", model);
        String expected = String.format(MESSAGE_SUCCESS, "address", "desc");
        assertCommandSuccess(so, model, expected, sortedByAddDesc);
    }

    @Test
    public void sortByEmail_success() throws Exception {
        SortCommand so = prepareCommand("email", "asc", model);
        String expected = String.format(MESSAGE_SUCCESS, "email", "asc");
        assertCommandSuccess(so, model, expected, sortedByEmail);
    }

    @Test
    public void sortByEmailDesc_success() throws Exception {
        SortCommand so = prepareCommand("email", "desc", model);
        String expected = String.format(MESSAGE_SUCCESS, "email", "desc");
        assertCommandSuccess(so, model, expected, sortedByEmailDesc);
    }

    /**
     * Returns a {@code sortCommand} with the parameters {@code field and @code order}.
     */
    private SortCommand prepareCommand(String field, String order, Model model) {
        SortCommand sortCommand = new SortCommand(field, order);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
```
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.commands.SortCommand.BY_ASCENDING;
//import static seedu.address.logic.commands.SortCommand.BY_DESCENDING;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

//import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void noArguments_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArguments_failure() {
        //more than 1 field entered
        assertParseFailure(parser, "name" + " " + "address" + " " + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //invalid field entered
        assertParseFailure(parser, "invalid" + "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //invalid sort order entered
        assertParseFailure(parser, "name" + " " + "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //no field entered
        assertParseFailure(parser, "asc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        //no order entered
        assertParseFailure(parser, "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
    /*
    @Test
    public void parse_validArguments_success() {
        //valid input for sort by name in asc order
        assertParseSuccess(parser, "name" + " " + BY_ASCENDING,
                new SortCommand("name", BY_ASCENDING));

        //valid input for sort by name in desc order
        assertParseSuccess(parser, "name" + " " + BY_DESCENDING,
                new SortCommand("name", BY_DESCENDING));

        //valid input for sort by address in asc order
        assertParseSuccess(parser, "address" + " " + BY_ASCENDING,
                new SortCommand("address", BY_ASCENDING));

        //valid input for sort by address in desc order
        assertParseSuccess(parser, "address" + " " + BY_DESCENDING,
                new SortCommand("address", BY_DESCENDING));

        //valid input for sort by phone in asc order
        assertParseSuccess(parser, "phone" + " " + BY_ASCENDING,
                new SortCommand("phone", BY_ASCENDING));

        //valid input for sort by phone in desc order
        assertParseSuccess(parser, "phone" + " " + BY_DESCENDING,
                new SortCommand("phone", BY_DESCENDING));

        //valid input for sort by email in asc order
        assertParseSuccess(parser, "email" + " " + BY_ASCENDING,
                new SortCommand("email", BY_ASCENDING));

        //valid input for sort by email in desc order
        assertParseSuccess(parser, "email" + " " + BY_DESCENDING,
                new SortCommand("email", BY_DESCENDING));
    }
    */
}
```
###### \java\seedu\address\testutil\EditPersonPrivacyBuilder.java
``` java
import seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building EditPersonPrivacy objects.
 */
public class EditPersonPrivacyBuilder {

    private EditPersonPrivacy epp;
    public EditPersonPrivacyBuilder() {
        epp = new EditPersonPrivacy();
    }

    public EditPersonPrivacyBuilder(EditPersonPrivacy epp) {
        this.epp = new EditPersonPrivacy(epp);
    }

    /**
     * Returns an {@code EditPersonPrivacy} with fields containing {@code person}'s privacy details
     */
    public EditPersonPrivacyBuilder(Person p) {
        epp = new EditPersonPrivacy();
        epp.setPrivateAddress(p.getAddress().isPrivate());
        epp.setPrivateEmail(p.getEmail().isPrivate());
        epp.setPrivatePhone(p.getPhone().isPrivate());
        epp.setPrivateRemark(p.getRemark().isPrivate());
        epp.setPrivateRating(p.getRating().isPrivate());
    }

    public EditPersonPrivacy build() {
        return epp;
    }
}
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Person> getTypicalPersonsSortedByAddress() {
        return new ArrayList<>(Arrays.asList(DANIEL, ALICE, BENSON, GEORGE, FIONA, ELLE, CARL));
    }

    public static List<Person> getTypicalPersonsSortedByEmail() {
        return new ArrayList<>(Arrays.asList(ALICE, GEORGE, DANIEL, CARL, BENSON, FIONA, ELLE));
    }

    public static List<Person> getTypicalPersonsSortedByPhone() {
        return new ArrayList<>(Arrays.asList(ALICE, DANIEL, ELLE, FIONA, GEORGE, CARL, BENSON));
    }

    public static AddressBook getSortedAddressBook(String field, String order) {
        AddressBook ab = new AddressBook();
        List<Person> personList;

        switch(field) {
        case "name":
            personList = getTypicalPersons();
            break;
        case "phone":
            personList = getTypicalPersonsSortedByPhone();
            break;
        case "email":
            personList = getTypicalPersonsSortedByEmail();
            break;
        case "address":
            personList = getTypicalPersonsSortedByAddress();
            break;
        default:
            personList = getTypicalPersons();
        }

        if (order.equals("desc")) {
            Collections.reverse(personList);
        }

        for (Person person : personList) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }

        return ab;
    }

    public static AddressBook getEmptyAddressBook() {
        AddressBook ab = new AddressBook();
        return ab;
    }
}
```
