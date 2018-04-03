package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.EndDateTime;
import seedu.address.model.appointment.StartDateTime;
import seedu.address.model.appointment.Title;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PROFILE_PICTURE = "andre.jpp";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_MONTH = "03/2018";
    private static final String INVALID_DATE = "2018/3/3";
    private static final String INVALID_YEAR = "201";
    private static final String INVALID_WEEK = "2018-7";
    private static final String INVALID_TITLE = "";
    private static final String INVALID_START_DATE_TIME = "2018/3/3 18-20";
    private static final String INVALID_END_DATE_TIME = "2018/3/3 18-20";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_PROFILE_PICTURE =
            "./src/test/data/images/alex.jpeg";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_MONTH = "2018-03";
    private static final String VALID_DATE = "2018-03-20";
    private static final String VALID_YEAR = "2018";
    private static final String VALID_WEEK = "2018 23";
    private static final String VALID_TITLE = "Meeting";
    private static final String VALID_START_DATE_TIME = "2018-03-03 18:20";
    private static final String VALID_END_DATE_TIME = "2018-03-03 18:20";

    private static final String EMPTY_STRING = "";
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

    //@@author trafalgarandre
    @Test
    public void parseProfilePicture_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseProfilePicture((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseProfilePicture((Optional<String>) null));
    }

    @Test
    public void parseProfilePicture_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseProfilePicture(INVALID_PROFILE_PICTURE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseProfilePicture(Optional.of(INVALID_PROFILE_PICTURE)));
    }

    @Test
    public void parseProfilePicture_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseProfilePicture(Optional.empty()).isPresent());
    }

    @Test
    public void parseProfilePicture_validValueWithoutWhitespace_returnsProfilePicture() throws Exception {
        ProfilePicture expectedProfilePicture = new ProfilePicture(VALID_PROFILE_PICTURE);
        assertEquals(expectedProfilePicture, ParserUtil.parseProfilePicture(VALID_PROFILE_PICTURE));
        assertEquals(Optional.of(expectedProfilePicture),
            ParserUtil.parseProfilePicture(Optional.of(VALID_PROFILE_PICTURE)));
    }

    @Test
    public void parseProfilePictUre_validValueWithWhitespace_returnsTrimmedProfilePicture() throws Exception {
        String profilePictureWithWhitespace = WHITESPACE + VALID_PROFILE_PICTURE + WHITESPACE;
        ProfilePicture expectedProfilePicture = new ProfilePicture(VALID_PROFILE_PICTURE);
        assertEquals(expectedProfilePicture, ParserUtil.parseProfilePicture(profilePictureWithWhitespace));
        assertEquals(Optional.of(expectedProfilePicture),
            ParserUtil.parseProfilePicture(Optional.of(profilePictureWithWhitespace)));
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

    //@@author trafalgarandre
    @Test
    public void parsetTitle_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTitle((Optional<String>) null));
    }

    @Test
    public void parseTitle_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTitle(INVALID_TITLE));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseTitle(Optional.of(INVALID_TITLE)));
    }

    @Test
    public void parseTitle_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTitle(Optional.empty()).isPresent());
    }

    @Test
    public void parseTitle_validValueWithoutWhitespace_returnsTitle() throws Exception {
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(VALID_TITLE));
        assertEquals(Optional.of(expectedTitle), ParserUtil.parseTitle(Optional.of(VALID_TITLE)));
    }

    @Test
    public void parseTitle_validValueWithWhitespace_returnsTrimmedTitle() throws Exception {
        String titleWithWhitespace = WHITESPACE + VALID_TITLE + WHITESPACE;
        Title expectedTitle = new Title(VALID_TITLE);
        assertEquals(expectedTitle, ParserUtil.parseTitle(titleWithWhitespace));
        assertEquals(Optional.of(expectedTitle), ParserUtil.parseTitle(Optional.of(titleWithWhitespace)));
    }

    @Test
    public void parsetStartDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStartDateTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseStartDateTime((Optional<String>) null));
    }

    @Test
    public void parseStartDateTime_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseStartDateTime(INVALID_START_DATE_TIME));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStartDateTime(Optional.of(INVALID_START_DATE_TIME)));
    }

    @Test
    public void parseStartDateTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseStartDateTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseStartDateTime_validValueWithoutWhitespace_returnsStartDateTime() throws IllegalValueException {
        StartDateTime expectedStartDateTime = new StartDateTime(VALID_START_DATE_TIME);
        assertEquals(expectedStartDateTime, ParserUtil.parseStartDateTime(VALID_START_DATE_TIME));
        assertEquals(Optional.of(expectedStartDateTime),
            ParserUtil.parseStartDateTime(Optional.of(VALID_START_DATE_TIME)));
    }

    @Test
    public void parseStartDateTime_validValueWithWhitespace_returnsTrimmedStartDateTime() throws IllegalValueException {
        String startDateTimeWithWhitespace = WHITESPACE + VALID_START_DATE_TIME + WHITESPACE;
        StartDateTime expectedStartDateTime = new StartDateTime(VALID_START_DATE_TIME);
        assertEquals(expectedStartDateTime, ParserUtil.parseStartDateTime(startDateTimeWithWhitespace));
        assertEquals(Optional.of(expectedStartDateTime),
            ParserUtil.parseStartDateTime(Optional.of(startDateTimeWithWhitespace)));
    }

    @Test
    public void parsetEndDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDateTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEndDateTime((Optional<String>) null));
    }

    @Test
    public void parseEndDateTime_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseEndDateTime(INVALID_END_DATE_TIME));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseEndDateTime(Optional.of(INVALID_END_DATE_TIME)));
    }

    @Test
    public void parseEndDateTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEndDateTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseEndDateTime_validValueWithoutWhitespace_returnsEndDateTime() throws IllegalValueException {
        EndDateTime expectedEndDateTime = new EndDateTime(VALID_END_DATE_TIME);
        assertEquals(expectedEndDateTime, ParserUtil.parseEndDateTime(VALID_END_DATE_TIME));
        assertEquals(Optional.of(expectedEndDateTime),
            ParserUtil.parseEndDateTime(Optional.of(VALID_END_DATE_TIME)));
    }

    @Test
    public void parseEndDateTime_validValueWithWhitespace_returnsTrimmedEndDateTime() throws IllegalValueException {
        String endDateTimeWithWhitespace = WHITESPACE + VALID_END_DATE_TIME + WHITESPACE;
        EndDateTime expectedEndDateTime = new EndDateTime(VALID_END_DATE_TIME);
        assertEquals(expectedEndDateTime, ParserUtil.parseEndDateTime(endDateTimeWithWhitespace));
        assertEquals(Optional.of(expectedEndDateTime),
            ParserUtil.parseEndDateTime(Optional.of(endDateTimeWithWhitespace)));
    }

    @Test
    public void parseYearMonth_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYearMonth(INVALID_MONTH));
    }

    @Test
    public void parseYearMonth_null_returnsYearMonth() throws IllegalValueException {
        YearMonth expectedYearMonth = null;
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(EMPTY_STRING));
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(WHITESPACE));
    }

    @Test
    public void parseYearMonth_validValueWithoutWhitespace_returnsYearMonth() throws Exception {
        YearMonth expectedYearMonth = YearMonth.parse(VALID_MONTH);
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(VALID_MONTH));
    }

    @Test
    public void parseYearMonth_validValueWithWhitespace_returnsTrimmedYearMonth() throws Exception {
        String yearMonthWithWhitespace = WHITESPACE + VALID_MONTH + WHITESPACE;
        YearMonth expectedYearMonth = YearMonth.parse(VALID_MONTH);
        assertEquals(expectedYearMonth, ParserUtil.parseYearMonth(yearMonthWithWhitespace));
    }

    @Test
    public void parseDate_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_null_returnsDate() throws IllegalValueException {
        LocalDate expectedDate = null;
        assertEquals(expectedDate, ParserUtil.parseDate(EMPTY_STRING));
        assertEquals(expectedDate, ParserUtil.parseDate(WHITESPACE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws IllegalValueException {
        LocalDate expectedDate = LocalDate.parse(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        LocalDate expectedDate = LocalDate.parse(VALID_DATE);
        assertEquals(expectedDate, ParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseYear_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
    }

    @Test
    public void parseYear_null_returnsYear() throws IllegalValueException {
        Year expectedYear = null;
        assertEquals(expectedYear, ParserUtil.parseYear(EMPTY_STRING));
        assertEquals(expectedYear, ParserUtil.parseYear(WHITESPACE));
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsYear() throws IllegalValueException {
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String yearWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYear(yearWithWhitespace));
    }

    @Test
    public void parseWeek_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYearOfWeek(INVALID_WEEK));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseWeek(INVALID_WEEK));
    }

    @Test
    public void parseWeek_null_returnsWeek() throws IllegalValueException {
        Year expectedYear = null;
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(EMPTY_STRING));
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(WHITESPACE));
        int expectedWeek = 0;
        assertEquals(expectedWeek, ParserUtil.parseWeek(EMPTY_STRING));
        assertEquals(expectedWeek, ParserUtil.parseWeek(WHITESPACE));
    }

    @Test
    public void parseWeek_validValueWithoutWhitespace_returnsWeek() throws IllegalValueException {
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(VALID_WEEK));
        int expectedWeek = Integer.parseInt(VALID_WEEK.substring(5));
        assertEquals(expectedWeek, ParserUtil.parseWeek(VALID_WEEK));
    }

    @Test
    public void parseWeek_validValueWithWhitespace_returnsTrimmedWeek() throws Exception {
        String weekWithWhitespace = WHITESPACE + VALID_WEEK + WHITESPACE;
        Year expectedYear = Year.parse(VALID_YEAR);
        assertEquals(expectedYear, ParserUtil.parseYearOfWeek(weekWithWhitespace));
        int expectedWeek = Integer.parseInt(VALID_WEEK.substring(5));
        assertEquals(expectedWeek, ParserUtil.parseWeek(weekWithWhitespace));
    }
}
