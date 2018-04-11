package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Remark;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_REMARK = " ";
    private static final String INVALID_DATETIME_INCOMPLETE = "2018-02-28";
    private static final String INVALID_DATETIME_DATE = "2018-02-29";
    private static final String INVALID_DATETIME_TIME = "2018-02-28 25:30";
    private static final String INVALID_YEAR = "3hrhnfian";
    private static final String INVALID_YEAR_MONTH = "qurh9hp38";
    private static final String INVALID_MONTH = "ai";
    private static final String INVALID_DAY = "aiendoh3";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_REMARK = "nil";
    private static final String VALID_DATETIME = "2018-12-31 12:30";
    private static final String VALID_YEAR = "2018";
    private static final String VALID_YEAR_MONTH = "2018-12";
    private static final String VALID_MONTH = "12";
    private static final String VALID_DAY = "2018-12-31";


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

    //@@author wynonaK
    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDateTime((Optional<String>) null));
    }

    @Test
    public void parseDateTime_invalidDateTimeIncomplete_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDateTime(INVALID_DATETIME_INCOMPLETE));
        Assert.assertThrows(IllegalValueException.class, (
            ) -> ParserUtil.parseDateTime(Optional.of(INVALID_DATETIME_INCOMPLETE)));
    }

    @Test
    public void parseDateTime_invalidDate_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDateTime(INVALID_DATETIME_DATE));
        Assert.assertThrows(IllegalValueException.class, (
        ) -> ParserUtil.parseDateTime(Optional.of(INVALID_DATETIME_DATE)));
    }

    @Test
    public void parseDateTime_invalidTime_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDateTime(INVALID_DATETIME_TIME));
        Assert.assertThrows(IllegalValueException.class, (
        ) -> ParserUtil.parseDateTime(Optional.of(INVALID_DATETIME_TIME)));
    }

    @Test
    public void parseDateTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDateTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseDateTime_validValueWithoutWhitespace_returnsDateTime() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime expectedLocalDateTime = LocalDateTime.parse(VALID_DATETIME, formatter);
        assertEquals(expectedLocalDateTime, ParserUtil.parseDateTime(VALID_DATETIME));
        assertEquals(Optional.of(expectedLocalDateTime), ParserUtil.parseDateTime(Optional.of(VALID_DATETIME)));
    }

    @Test
    public void parseDateTime_validValueWithWhitespace_returnsTrimmedDateTime() throws Exception {
        String dateTimeWithWhitespace = WHITESPACE + VALID_DATETIME + WHITESPACE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime expectedLocalDateTime = LocalDateTime.parse(VALID_DATETIME, formatter);
        assertEquals(expectedLocalDateTime, ParserUtil.parseDateTime(dateTimeWithWhitespace));
        assertEquals(Optional.of(expectedLocalDateTime), ParserUtil.parseDateTime(Optional.of(dateTimeWithWhitespace)));
    }

    @Test
    public void parseRemark_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseRemark((Optional<String>) null));
    }

    @Test
    public void parseRemark_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseRemark(INVALID_REMARK));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseRemark(Optional.of(INVALID_REMARK)));
    }

    @Test
    public void parseRemark_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemark_validValueWithoutWhitespace_returnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(VALID_REMARK));
        assertEquals(Optional.of(expectedRemark), ParserUtil.parseRemark(Optional.of(VALID_REMARK)));
    }

    @Test
    public void parseRemark_validValueWithWhitespace_returnsTrimmedRemark() throws Exception {
        String remarkWithWhitespace = WHITESPACE + VALID_REMARK + WHITESPACE;
        Remark expectedRemark = new Remark(VALID_REMARK);
        assertEquals(expectedRemark, ParserUtil.parseRemark(remarkWithWhitespace));
        assertEquals(Optional.of(expectedRemark), ParserUtil.parseRemark(Optional.of(remarkWithWhitespace)));
    }

    @Test
    public void parseYear_null_returnsTodayYear() throws Exception {
        Year expectedYear = Year.now();
        assertEquals(expectedYear, ParserUtil.parseYear(""));
        assertEquals(Optional.of(expectedYear), ParserUtil.parseYear(Optional.of("")));
    }

    @Test
    public void parseYear_invalidYear_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseYear(INVALID_YEAR));
        Assert.assertThrows(IllegalValueException.class, (
        ) -> ParserUtil.parseYear(Optional.of(INVALID_YEAR)));
    }

    @Test
    public void parseYear_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseYear(Optional.empty()).isPresent());
    }

    @Test
    public void parseYear_validValueWithoutWhitespace_returnsYear() throws Exception {
        Year expectedYear = Year.of(2018);
        assertEquals(expectedYear, ParserUtil.parseYear(VALID_YEAR));
        assertEquals(Optional.of(expectedYear), ParserUtil.parseYear(Optional.of(VALID_YEAR)));
    }

    @Test
    public void parseYear_validValueWithWhitespace_returnsTrimmedYear() throws Exception {
        String yearWithWhitespace = WHITESPACE + VALID_YEAR + WHITESPACE;
        Year expectedYear = Year.of(2018);
        assertEquals(expectedYear, ParserUtil.parseYear(yearWithWhitespace));
        assertEquals(Optional.of(expectedYear), ParserUtil.parseYear(Optional.of(yearWithWhitespace)));
    }

    @Test
    public void parseMonth_null_returnsTodayMonth() throws Exception {
        YearMonth expectedYearMonth = YearMonth.now();
        assertEquals(expectedYearMonth, ParserUtil.parseMonth(""));
        assertEquals(Optional.of(expectedYearMonth), ParserUtil.parseMonth(Optional.of("")));
    }

    @Test
    public void parseMonth_invalidMonth_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMonth(INVALID_YEAR_MONTH));
        Assert.assertThrows(IllegalValueException.class, (
        ) -> ParserUtil.parseMonth(Optional.of(INVALID_YEAR_MONTH)));
    }

    @Test
    public void parseMonth_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseMonth(Optional.empty()).isPresent());
    }

    @Test
    public void parseMonth_validValueWithoutWhitespace_returnsMonth() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth expectedYearMonth = YearMonth.parse(VALID_YEAR_MONTH, formatter);
        assertEquals(expectedYearMonth, ParserUtil.parseMonth(VALID_YEAR_MONTH));
        assertEquals(Optional.of(expectedYearMonth), ParserUtil.parseMonth(Optional.of(VALID_YEAR_MONTH)));
    }

    @Test
    public void parseMonthOnly_validValueWithoutWhitespace_returnsMonth() throws Exception {
        YearMonth expectedYearMonth = YearMonth.now().withMonth(Integer.parseInt(VALID_MONTH));
        assertEquals(expectedYearMonth, ParserUtil.parseMonth(VALID_MONTH));
        assertEquals(Optional.of(expectedYearMonth), ParserUtil.parseMonth(Optional.of(VALID_MONTH)));
    }

    @Test
    public void parseMonth_validValueWithWhitespace_returnsTrimmedMonth() throws Exception {
        String yearMonthWithWhitespace = WHITESPACE + VALID_YEAR_MONTH + WHITESPACE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth expectedYearMonth = YearMonth.parse(VALID_YEAR_MONTH, formatter);
        assertEquals(expectedYearMonth, ParserUtil.parseMonth(yearMonthWithWhitespace));
        assertEquals(Optional.of(expectedYearMonth), ParserUtil.parseMonth(Optional.of(yearMonthWithWhitespace)));
    }


    @Test
    public void parseMonthOnly_validValueWithWhitespace_returnsTrimmedMonth() throws Exception {
        String yearMonthWithWhitespace = WHITESPACE + VALID_MONTH + WHITESPACE;
        YearMonth expectedYearMonth = YearMonth.now().withMonth(Integer.parseInt(VALID_MONTH));
        assertEquals(expectedYearMonth, ParserUtil.parseMonth(yearMonthWithWhitespace));
        assertEquals(Optional.of(expectedYearMonth), ParserUtil.parseMonth(Optional.of(yearMonthWithWhitespace)));
    }

    @Test
    public void parseDay_null_returnsTodayDay() throws Exception {
       LocalDate expectedLocalDate = LocalDate.now();
        assertEquals(expectedLocalDate, ParserUtil.parseDate(""));
        assertEquals(Optional.of(expectedLocalDate), ParserUtil.parseDate(Optional.of("")));
    }

    @Test
    public void parseDay_invalidDay_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseDate(INVALID_DAY));
        Assert.assertThrows(IllegalValueException.class, (
        ) -> ParserUtil.parseYear(Optional.of(INVALID_DAY)));
    }

    @Test
    public void parseDay_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseDay_validValueWithoutWhitespace_returnsDay() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expectedDate = LocalDate.parse(VALID_DAY, formatter);
        assertEquals(expectedDate, ParserUtil.parseDate((VALID_DAY)));
        assertEquals(Optional.of(expectedDate), ParserUtil.parseDate(Optional.of(VALID_DAY)));
    }

    @Test
    public void parseDay_validValueWithWhitespace_returnsTrimmedDay() throws Exception {
        String dayWithWhitespace = WHITESPACE + VALID_DAY + WHITESPACE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expectedDate = LocalDate.parse(VALID_DAY, formatter);
        assertEquals(expectedDate, ParserUtil.parseDate(dayWithWhitespace));
        assertEquals(Optional.of(expectedDate), ParserUtil.parseDate(Optional.of(dayWithWhitespace)));
    }
}

