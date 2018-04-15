package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.ParserUtil.parseTheaterNumber;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CINEMA;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.cinema.Address;
import seedu.address.model.cinema.Email;
import seedu.address.model.cinema.Name;
import seedu.address.model.cinema.Phone;
import seedu.address.model.cinema.Theater;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATE_WITHOUT_TIME = "25/03/2018";
    private static final String INVALID_TIME_WITHOUT_DATE = "11:10";
    private static final String INVALID_DATE_TIME_FORMAT_1 = "11/11/2015 11.10";
    private static final String INVALID_DATE_TIME_FORMAT_2 = "11.11.2015 11:10";
    private static final String INVALID_DATE_TIME_FORMAT_3 = "11.11.2015  11:10";
    private static final String INVALID_DATE_TIME_FORMAT_4 = "11/11/2015 11:10PM";
    private static final String INVALID_DATE_TIME_FORMAT_5 = "11/2/2015 11:10";
    private static final String INVALID_DATE_TIME_FORMAT_6 = "30/02/2015 11:10";
    private static final String INVALID_DATE_TIME_MINUTES = "11/11/2015 11:11";

    private static final int INVALID_THEATER = -5;

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DATE_TIME = "11/11/2015 11:10";
    private static final int VALID_THEATER = 5;

    private static final String WHITESPACE = " \t\r\n";
    private static final String DATE_TIME_FORMAT = "dd/MM/uuuu HH:mm";

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
        assertEquals(INDEX_FIRST_CINEMA, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_CINEMA, ParserUtil.parseIndex("  1  "));
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

    @Test
    public void parseTheaters_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTheaters((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTheaters((Optional<String>) null));
    }

    @Test
    public void parseTheaters_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseTheaters(String.valueOf(INVALID_THEATER)));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseTheaters(Optional.of(String.valueOf(INVALID_THEATER))));
    }

    @Test
    public void parseTheaters_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTheaters(Optional.empty()).isPresent());
    }

    @Test
    public void parseTheaters_validValueWithoutWhitespace_returnsTheaterList() throws Exception {
        ArrayList<Theater> expectedTheaters = new ArrayList<>(VALID_THEATER);
        for (int i = 1; i <= VALID_THEATER; i++) {
            expectedTheaters.add(new Theater(i));
        }
        assertEquals(expectedTheaters, ParserUtil.parseTheaters(String.valueOf(VALID_THEATER)));
        assertEquals(Optional.of(expectedTheaters),
                ParserUtil.parseTheaters(Optional.of(String.valueOf(VALID_THEATER))));
    }

    //@@author qwlai
    @Test
    public void parseTheaterNumber_null_throwsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTheaterNumber((String) null));
    }

    @Test
    public void parseTheaterNumber_validValueWithWhitespace_returnsTheaterNumber() throws Exception {
        String theaterNumberWithWhitespace = WHITESPACE + VALID_THEATER + WHITESPACE;
        assertEquals(VALID_THEATER, parseTheaterNumber(theaterNumberWithWhitespace));
    }

    @Test
    public void parseTheaterNumber_invalidValue_throwsIllegalValueException() throws Exception {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseTheaterNumber(String.valueOf(INVALID_THEATER)));
    }

    @Test
    public void parseScreeningDateTime_null_throwsNullPointerException() throws Exception {
        Assert.assertThrows(NullPointerException.class, () ->
                ParserUtil.parseScreeningDateTime((String) null));
    }

    @Test
    public void parseScreeningDateTime_invalidDateWithoutTime_throwsDateTimeParseException() throws Exception {
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_WITHOUT_TIME));
    }

    @Test
    public void parseScreeningDateTime_invalidTimeWithoutDate_throwsDateTimeParseException() throws Exception {
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_TIME_WITHOUT_DATE));
    }

    @Test
    public void parseScreeningDateTime_invalidDateTimeFormat_throwsDateTimeParseException() throws Exception {
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_1));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_2));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_3));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_4));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_5));
        Assert.assertThrows(DateTimeParseException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_FORMAT_6));
    }

    @Test
    public void parseScreeningDateTime_invalidTimeValue_throwsIllegalValueException() throws Exception {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseScreeningDateTime(INVALID_DATE_TIME_MINUTES));
    }

    @Test
    public void parseScreeningDateTime_validDateTime_returnsScreeningDateTime() throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime expectedScreeningDateTime = LocalDateTime.parse(VALID_DATE_TIME, dtf);
        assertEquals(expectedScreeningDateTime, ParserUtil.parseScreeningDateTime(VALID_DATE_TIME));
    }

    @Test
    public void parseScreeningDateTime_validDateTimeWithWhitespace_returnsScreeningDateTime() throws Exception {
        String screeningDateTimeWithWhitespace = WHITESPACE + VALID_DATE_TIME + WHITESPACE;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withResolverStyle(ResolverStyle.STRICT);
        LocalDateTime expectedScreeningDateTime = LocalDateTime.parse(VALID_DATE_TIME, dtf);
        assertEquals(expectedScreeningDateTime, ParserUtil.parseScreeningDateTime(screeningDateTimeWithWhitespace));
    }
}
