# hypertun
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_calories() throws Exception {
        CaloriesCommand command = (CaloriesCommand) parser.parseCommand(
                CaloriesCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new CaloriesCommand(INDEX_FIRST_PERSON), command);
    }
```
###### /java/seedu/address/logic/parser/CaloriesCommandParserTest.java
``` java
public class CaloriesCommandParserTest {
    private CaloriesCommandParser parser = new CaloriesCommandParser();

    @Test
    public void parse_validArgs_returnsCaloriesCommand() throws Exception {
        assertParseSuccess(parser, "1", new CaloriesCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CaloriesCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/commands/CaloriesCommandTest.java
``` java
public class CaloriesCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CaloriesCommand caloriesCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(caloriesCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final CaloriesCommand standardCommand = new CaloriesCommand(INDEX_FIRST_PERSON);
        final CaloriesCommand commandWithSameIndex = new CaloriesCommand(INDEX_FIRST_PERSON);
        final CaloriesCommand commandWithDifferentIndex = new CaloriesCommand(INDEX_SECOND_PERSON);


        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void checkIfEventCollected() throws CommandException {
        CaloriesCommand caloriesCommand = prepareCommand(INDEX_FIRST_PERSON);
        caloriesCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowCaloriesEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Executes a {@code CaloriesCommand} with the given {@code index}, and checks that {@code ShowCaloriesEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        CaloriesCommand caloriesCommand = prepareCommand(index);

        try {
            CommandResult commandResult = caloriesCommand.execute();
            assertEquals(String.format(CaloriesCommand.MESSAGE_FIND_CALORIES_SUCCESS,
                    model.getFilteredPersonList().get(index.getZeroBased()).getName().fullName,
                    model.getFilteredPersonList().get(index.getZeroBased()).getAddress().value),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowCaloriesEvent lastEvent = (ShowCaloriesEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.person);
    }

    /**
     * Returns an {@code CaloriesCommand} with parameters {@code index}}
     */
    private CaloriesCommand prepareCommand(Index index) {
        CaloriesCommand caloriesCommand = new CaloriesCommand(index);
        caloriesCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return caloriesCommand;
    }
}
```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_HEIGHT, VALID_WEIGHT, INVALID_GENDER, VALID_AGE, VALID_ACTIVITYLEVEL, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_GENDER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HEIGHT, VALID_WEIGHT, null, VALID_AGE, VALID_ACTIVITYLEVEL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAge_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_HEIGHT, VALID_WEIGHT, VALID_GENDER, INVALID_AGE, VALID_ACTIVITYLEVEL, VALID_TAGS);
        String expectedMessage = Age.MESSAGE_AGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAge_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HEIGHT, VALID_WEIGHT, VALID_GENDER, null, VALID_ACTIVITYLEVEL, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Age.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidActivityLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_HEIGHT, VALID_WEIGHT, VALID_GENDER, VALID_AGE, INVALID_ACTIVITYLEVEL, VALID_TAGS);
        String expectedMessage = ActivityLevel.MESSAGE_ACTIVITYLEVEL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullActivityLevel_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_HEIGHT, VALID_WEIGHT, VALID_GENDER, VALID_AGE, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ActivityLevel.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }
```
###### /java/seedu/address/model/person/ActivityLevelTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ActivityLevelTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ActivityLevel(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidActivityLevel = "1.44";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ActivityLevel(invalidActivityLevel));
    }

    @Test
    public void isValidAge() {
        // null ActivityLevel
        Assert.assertThrows(NullPointerException.class, () -> ActivityLevel.isValidActivityLevel(null));

        // invalid activityLevel
        assertFalse(ActivityLevel.isValidActivityLevel("")); // empty string
        assertFalse(ActivityLevel.isValidActivityLevel(" ")); // space only
        assertFalse(ActivityLevel.isValidActivityLevel("activityLevel")); // non-numeric
        assertFalse(ActivityLevel.isValidActivityLevel("9p.2")); // alphabets within digits
        assertFalse(ActivityLevel.isValidActivityLevel("9 3")); // spaces within digits
        assertFalse(ActivityLevel.isValidActivityLevel("1.40")); // wrong number

        // valid activityLevel numbers
        assertTrue(ActivityLevel.isValidActivityLevel("1.2")); // basal
        assertTrue(ActivityLevel.isValidActivityLevel("1.375"));
        assertTrue(ActivityLevel.isValidActivityLevel("1.9")); // active
    }
}
```
###### /java/seedu/address/model/person/AgeTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AgeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Age(null));
    }

    @Test
    public void constructor_invalidAge_throwsIllegalArgumentException() {
        String invalidAge = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Age(invalidAge));
    }

    @Test
    public void isValidAge() {
        // null Age
        Assert.assertThrows(NullPointerException.class, () -> Age.isValidAge(null));

        // invalid age
        assertFalse(Age.isValidAge("")); // empty string
        assertFalse(Age.isValidAge(" ")); // spaces only
        assertFalse(Age.isValidAge("Age")); // non-numeric
        assertFalse(Age.isValidAge("9p.2")); // alphabets within digits
        assertFalse(Age.isValidAge("9 3")); // spaces within digits

        // valid age numbers
        assertTrue(Age.isValidAge("1")); // exactly 1 numbers
        assertTrue(Age.isValidAge("95"));
        assertTrue(Age.isValidAge("100")); // heavy age
    }
}
```
###### /java/seedu/address/model/person/GenderTest.java
``` java

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidGender = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        Assert.assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // invalid gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("gender")); // non-numeric
        assertFalse(Gender.isValidGender("9p.2")); // alphabets within digits
        assertFalse(Gender.isValidGender("9 5")); // spaces within digits
        assertFalse(Gender.isValidGender("y ")); // other alphabets

        // valid gender
        assertTrue(Gender.isValidGender("m")); // exactly m
        assertTrue(Gender.isValidGender("f")); // exactly f
        assertTrue(Gender.isValidGender("M")); // exactly M
        assertTrue(Gender.isValidGender("F")); // exactly F
    }
}
```
