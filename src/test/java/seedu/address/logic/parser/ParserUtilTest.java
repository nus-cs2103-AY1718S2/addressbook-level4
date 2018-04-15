package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.MapCommand;
import seedu.address.model.alias.Alias;
import seedu.address.model.building.Building;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    //@@author AzuraAiR
    private static final String VALID_TIMETABLE = "http://modsn.us/oNZLY";
    private static final String VALID_BIRTHDAY = "01011995";
    private static final String INVALID_TIMETABLE = "http://google.com/";
    private static final String INVALID_BIRTHDAY = "31021985";
    //@@author

    //@@author jingyinno
    private static final String VALID_BUILDING = "COM1";
    private static final String VALID_BUILDING_2 = "COM2";
    private static final String INVALID_BUILDING = "COM*";
    private static final String VALID_LOCATION = "com1";
    private static final String VALID_POSTAL_CODE = "117417";
    private static final String VALID_POSTAL_CODE_2 = "138527";
    private static final String VALID_ALIAS = "add1";
    private static final String INVALID_ALIAS = "add*";
    private static final Alias ADD_ALIAS = new Alias(AddCommand.COMMAND_WORD, VALID_ALIAS);
    //@@author

    //@@author yeggasd
    private static final String VALID_ODD = "odd";
    private static final String VALID_EVEN = "even";
    private static final String INVALID_ODDEVEN = "ord";
    //@@author

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((Optional<String>) null));
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(INVALID_NAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseName(Optional.of(INVALID_NAME)));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(VALID_NAME)));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
        assertEquals(Optional.of(expectedName), ParserUtil.parseName(Optional.of(nameWithWhitespace)));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((Optional<String>) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePhone(Optional.of(INVALID_PHONE)));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(VALID_PHONE)));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
        assertEquals(Optional.of(expectedPhone), ParserUtil.parsePhone(Optional.of(phoneWithWhitespace)));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((Optional<String>) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS)));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
        assertEquals(Optional.of(expectedAddress), ParserUtil.parseAddress(Optional.of(VALID_ADDRESS)));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
        assertEquals(Optional.of(expectedAddress), ParserUtil.parseAddress(Optional.of(addressWithWhitespace)));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((Optional<String>) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEmail(Optional.of(INVALID_EMAIL)));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
        assertEquals(Optional.of(expectedEmail), ParserUtil.parseEmail(Optional.of(VALID_EMAIL)));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
        assertEquals(Optional.of(expectedEmail), ParserUtil.parseEmail(Optional.of(emailWithWhitespace)));
    }

    //@@author AzuraAiR
    @Test
    public void parseBirthday_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBirthday((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBirthday((Optional<String>) null));
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBirthday(INVALID_BIRTHDAY));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBirthday(Optional
                .of(INVALID_BIRTHDAY)));
    }

    @Test
    public void parseTimetable_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        assertEquals(expectedBirthday, ParserUtil.parseBirthday(VALID_BIRTHDAY));
        assertEquals(Optional.of(expectedBirthday), ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY)));
    }


    @Test
    public void parseTimetable_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimetable((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTimetable((Optional<String>) null));
    }

    @Test
    public void parseTimetable_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTimetable(INVALID_TIMETABLE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTimetable(Optional
                .of(INVALID_TIMETABLE)));
    }

    @Test
    public void parseTimetable_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTimetable(Optional.empty()).isPresent());
    }

    @Test
    public void parseTimetable_validValueWithoutWhitespace_returnsTimetable() throws Exception {
        Timetable expectedTimetable = new Timetable(VALID_TIMETABLE);
        assertEquals(expectedTimetable, ParserUtil.parseTimetable(VALID_TIMETABLE));
        assertEquals(Optional.of(expectedTimetable), ParserUtil.parseTimetable(Optional.of(VALID_TIMETABLE)));
    }

    @Test
    public void parseTimetable_validValueWithWhitespace_returnsTrimmedTimetable() throws Exception {
        String timetableWithWhitespace = WHITESPACE + VALID_TIMETABLE + WHITESPACE;
        Timetable expectedTimetable = new Timetable(VALID_TIMETABLE);
        assertEquals(expectedTimetable, ParserUtil.parseTimetable(timetableWithWhitespace));
        assertEquals(Optional.of(expectedTimetable), ParserUtil.parseTimetable(Optional
                .of(timetableWithWhitespace)));
    }
    //@@author

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    //@@author jingyinno
    @Test
    public void parseBuilding_validBuilding() throws Exception {
        Building building = new Building(VALID_BUILDING);
        assertEquals(building, ParserUtil.parseBuilding(VALID_BUILDING));
    }

    @Test
    public void parseBuilding_invalidBuilding_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseBuilding(INVALID_BUILDING));
    }

    @Test
    public void parseBuilding_invalidBuilding_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseBuilding(null));
    }

    @Test
    public void parseUnalias_validUnAlias() throws Exception {
        assertEquals(VALID_ALIAS, ParserUtil.parseUnalias(VALID_ALIAS));
    }

    @Test
    public void parseUnalias_invalidUnAlias_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUnalias(INVALID_ALIAS));
    }

    @Test
    public void parseUnalias_invalidUnAlias_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUnalias(null));
    }

    @Test
    public void parseAlias_validAliasCommandAndAlias() throws Exception {
        assertEquals(ADD_ALIAS, ParserUtil.parseAlias(AddCommand.COMMAND_WORD, VALID_ALIAS));
    }

    @Test
    public void parseAlias_validAliasCommandAndinvalidAlias_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAlias(AddCommand.COMMAND_WORD,
                INVALID_ALIAS));
    }

    @Test
    public void parseAlias_invalidAliasCommandAndvalidAlias_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAlias(INVALID_ALIAS, VALID_ALIAS));
    }

    @Test
    public void parseAlias_invalidAliasCommandAndvalidAlias_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias(null, VALID_ALIAS));
    }

    @Test
    public void parseAlias_validAliasCommandAndinvalidAlias_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAlias(AddCommand.COMMAND_WORD, null));
    }

    @Test
    public void parseLocations_validLocation_success() {
        assertEquals(VALID_POSTAL_CODE, ParserUtil.parseLocations(VALID_POSTAL_CODE));
    }

    @Test
    public void parseLocations_multipleLocations_success() {
        String[] locations = new String[] {VALID_POSTAL_CODE, VALID_POSTAL_CODE_2};
        String joinedLocations = String.join(MapCommand.SPLIT_TOKEN, locations);
        assertEquals(joinedLocations , ParserUtil.parseLocations(joinedLocations));
    }

    @Test
    public void parseLocations_validNusLocations_success() {
        assertEquals(VALID_POSTAL_CODE , ParserUtil.parseLocations(VALID_BUILDING));
    }

    @Test
    public void parseLocations_validNusLocationsMixedCase_success() {
        assertEquals(VALID_POSTAL_CODE , ParserUtil.parseLocations(VALID_LOCATION));
    }

    @Test
    public void parseLocations_validMultipleNusLocations_success() {
        String[] locationsPostalCode = new String[] {VALID_POSTAL_CODE, VALID_POSTAL_CODE};
        String expectedLocations = String.join(MapCommand.SPLIT_TOKEN, locationsPostalCode);
        String[] locations = new String[] {VALID_BUILDING, VALID_BUILDING_2};
        String joinedLocations = String.join(MapCommand.SPLIT_TOKEN, locations);
        assertEquals(expectedLocations , ParserUtil.parseLocations(joinedLocations));
    }

    @Test
    public void parseLocations_validMixedLocations_success() {
        String[] locationsPostalCode = new String[] {VALID_POSTAL_CODE_2, VALID_POSTAL_CODE};
        String expectedLocations = String.join(MapCommand.SPLIT_TOKEN, locationsPostalCode);
        String[] locations = new String[] {VALID_POSTAL_CODE_2, VALID_BUILDING};
        String joinedLocations = String.join(MapCommand.SPLIT_TOKEN, locations);
        assertEquals(expectedLocations , ParserUtil.parseLocations(joinedLocations));
    }

    @Test
    public void parseLocations_invalidLocation_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLocations(null));
    }
    //@@author

    //@@author yeggasd
    @Test
    public void parseOddEven_validOddEven() throws Exception {
        assertEquals(VALID_ODD, ParserUtil.parseOddEven(VALID_ODD));
        assertEquals(VALID_EVEN, ParserUtil.parseOddEven(VALID_EVEN));

        //with trailing and leading spaces
        assertEquals(VALID_ODD, ParserUtil.parseOddEven(" " + VALID_ODD + " "));
        assertEquals(VALID_EVEN, ParserUtil.parseOddEven(" " + VALID_EVEN + " "));
    }

    @Test
    public void  parseOddEven_nullGive_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseOddEven(null));
    }

    @Test
    public void  parseOddEven_invalidOddEven_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseOddEven(INVALID_ODDEVEN));
    }
    //@@author
}
