# lohtianwei
###### \java\seedu\address\logic\commands\KeyCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class KeyCommandTest {

    private Model model;

    @Before
    public void start() {
        model = new ModelManager();
    }

    @Test
    public void checkKey() throws Exception {
        //checks that default lock state is false
        assertFalse(model.getLockState());

        //checks that key can lock MTM
        model.lockAddressBookModel();
        assertTrue(model.getLockState());

        //checks that key can unlock MTM
        model.unlockAddressBookModel();
        assertFalse(model.getLockState());

        //checks that toggling works
        model.lockAddressBookModel();
        model.unlockAddressBookModel();
        assertFalse(model.getLockState());

        model.unlockAddressBookModel();
        model.lockAddressBookModel();
        assertTrue(model.getLockState());
    }
}
```
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
###### \java\seedu\address\logic\commands\TogglePrivacyCommandTest.java
``` java
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
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
import seedu.address.testutil.EditPersonPrivacyBuilder;
import seedu.address.testutil.PersonBuilder;

public class TogglePrivacyCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void editPersonPrivacyTest() {
        EditPersonPrivacy epp = new EditPersonPrivacy();

        assertFalse(epp.anyNonNullField());

        EditPersonPrivacy eppBuilder = new EditPersonPrivacyBuilder().setAddressPrivate("false")
                .setEmailPrivate("false").setPhonePrivate("true").setRatingPrivate("false")
                .setRemarkPrivate("true").build();

        epp.setPrivateAddress(false);
        epp.setPrivateEmail(false);
        epp.setPrivatePhone(true);
        epp.setPrivateRating(false);
        epp.setPrivateRemark(true);

        assertEquals(eppBuilder.getPrivateAddress(), epp.getPrivateAddress());
        assertEquals(eppBuilder.getPrivateEmail(), epp.getPrivateEmail());
        assertEquals(eppBuilder.getPrivatePhone(), epp.getPrivatePhone());
        assertEquals(eppBuilder.getPrivateRating(), epp.getPrivateRating());
        assertEquals(eppBuilder.getPrivateRemark(), epp.getPrivateRemark());
    }

    @Test
    public void oneFieldToggledSuccess() throws Exception {
        Index indexLast = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLast.getZeroBased());

        Person listedPerson = new PersonBuilder().withName(lastPerson.getName().toString())
                .withEmail(lastPerson.getEmail().toString()).withRating(lastPerson.getRating().toString())
                .withPhone(lastPerson.getPhone().toString()).build();

        listedPerson.getPhone().setPrivate(true);

        EditPersonPrivacy epp = new EditPersonPrivacyBuilder(listedPerson).setPhonePrivate("true").build();
        TogglePrivacyCommand togglePrivacyCommand = new TogglePrivacyCommand(indexLast, epp);
        togglePrivacyCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        String expected = String.format(TogglePrivacyCommand.MESSAGE_SUCCESS, listedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(lastPerson, listedPerson);
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
###### \java\seedu\address\logic\parser\TogglePrivacyCommandParserTest.java
``` java
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TogglePrivacyCommand;
import seedu.address.logic.commands.TogglePrivacyCommand.EditPersonPrivacy;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EditPersonPrivacyBuilder;

public class TogglePrivacyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format
            (MESSAGE_INVALID_COMMAND_FORMAT, TogglePrivacyCommand.MESSAGE_USAGE);

    private static final String MESSAGE_NO_FIELDS = String.format(TogglePrivacyCommand.MESSAGE_NO_FIELDS);

    private TogglePrivacyCommandParser parser = new TogglePrivacyCommandParser();

    @Test
    public void parseInvalidIndex() {
        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        //negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
        //invalid index
        assertParseFailure(parser, "1 random", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseMissingField_fail() {
        // no prefix specified
        assertParseFailure(parser, "1", MESSAGE_NO_FIELDS);
        //no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
        //nothing specified after command word
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidPrefix_fail() {
        assertParseFailure(parser, "1" + " " + PREFIX_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TogglePrivacyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseOneField_success() throws ParseException {
        Index target = INDEX_FIRST_PERSON;
        String input = target.getOneBased() + " " + PREFIX_PHONE;

        EditPersonPrivacy epp = new EditPersonPrivacyBuilder().setPhonePrivate("false").build();
        TogglePrivacyCommand expected = new TogglePrivacyCommand(target, epp);

        TogglePrivacyCommand actual = parser.parse(input);

        compareTpCommand(expected, actual);
    }

    @Test
    public void parseValidFollowedbyInvalid_success() throws ParseException {
        Index target = INDEX_FIRST_PERSON;
        String input = target.getOneBased() + " " + PREFIX_PHONE + " " + PREFIX_NAME;

        EditPersonPrivacy epp = new EditPersonPrivacyBuilder().setPhonePrivate("false").build();
        TogglePrivacyCommand expected = new TogglePrivacyCommand(target, epp);

        TogglePrivacyCommand actual = parser.parse(input);

        compareTpCommand(expected, actual);
    }

    /**
     * Checks if two TP commands are equal
     * @param expected
     * @param actual
     */
    private void compareTpCommand(TogglePrivacyCommand expected, TogglePrivacyCommand actual) {
        assertEquals(expected.getIndex(), actual.getIndex());
        assertEquals(expected.getEpp().getPrivateRemark(), actual.getEpp().getPrivateRemark());
        assertEquals(expected.getEpp().getPrivateAddress(), actual.getEpp().getPrivateAddress());
        assertEquals(expected.getEpp().getPrivateRating(), actual.getEpp().getPrivateRating());
        assertEquals(expected.getEpp().getPrivatePhone(), actual.getEpp().getPrivatePhone());
        assertEquals(expected.getEpp().getPrivateEmail(), actual.getEpp().getPrivateEmail());
    }
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

    public EditPersonPrivacyBuilder setPhonePrivate(String phone) {
        if (phone.equals("Optional[true]") || phone.equals("true")) {
            epp.setPrivatePhone(true);
        } else if (phone.equals("Optional[false]") || phone.equals("false")) {
            epp.setPrivatePhone(false);
        } else {
            throw new IllegalArgumentException("Privacy of phone should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setEmailPrivate(String email) {
        if (email.equals("Optional[true]") || email.equals("true")) {
            epp.setPrivateEmail(true);
        } else if (email.equals("Optional[false]") || email.equals("false")) {
            epp.setPrivateEmail(false);
        } else {
            throw new IllegalArgumentException("Privacy of email should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setAddressPrivate(String address) {
        if (address.equals("Optional[true]") || address.equals("true")) {
            epp.setPrivateAddress(true);
        } else if (address.equals("Optional[false]") || address.equals("false")) {
            epp.setPrivateAddress(false);
        } else {
            throw new IllegalArgumentException("Privacy of address should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setRatingPrivate(String rating) {
        if (rating.equals("Optional[true]") || rating.equals("true")) {
            epp.setPrivateRating(true);
        } else if (rating.equals("Optional[false]") || rating.equals("false")) {
            epp.setPrivateRating(false);
        } else {
            throw new IllegalArgumentException("Privacy of rating should be true or false");
        }
        return this;
    }

    public EditPersonPrivacyBuilder setRemarkPrivate(String remark) {
        if (remark.equals("Optional[true]") || remark.equals("true")) {
            epp.setPrivateRemark(true);
        } else if (remark.equals("Optional[false]") || remark.equals("false")) {
            epp.setPrivateRemark(false);
        } else {
            throw new IllegalArgumentException("Privacy of remark should be true or false");
        }
        return this;
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
