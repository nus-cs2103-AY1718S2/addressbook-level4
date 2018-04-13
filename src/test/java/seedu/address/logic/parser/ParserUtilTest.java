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
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.customer.LateInterest;
import seedu.address.model.person.customer.MoneyBorrowed;
import seedu.address.model.person.customer.StandardInterest;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_MONEY_BORROWED_NEGATIVE = "-34.0985";
    private static final String INVALID_MONEY_BORROWED_NOT_DOUBLE = "34.0d985";
    private static final String INVALID_STANDARD_INTEREST_NEGATIVE = "-34.0985";
    private static final String INVALID_STANDARD_INTEREST_NOT_DOUBLE = "34.0d985";
    private static final String INVALID_LATE_INTEREST_NEGATIVE = "-34.0985";
    private static final String INVALID_LATE_INTEREST_NOT_DOUBLE = "34.0d985";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_MONEY_BORROWED = "34.0985";
    private static final String VALID_STANDARD_INTEREST = "34.0985";
    private static final String VALID_LATE_INTEREST = "34.0985";

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

    /*
    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS)));
    }
    */

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

    //@@author jonleeyz
    @Test
    public void parseMoneyBorrowed_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMoneyBorrowed((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMoneyBorrowed((Optional<String>) null));
    }

    @Test
    public void parseMoneyBorrowed_invalidValueNotDouble_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(INVALID_MONEY_BORROWED_NOT_DOUBLE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(Optional.of(INVALID_MONEY_BORROWED_NOT_DOUBLE)));
    }

    @Test
    public void parseMoneyBorrowed_invalidValueNegative_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(INVALID_MONEY_BORROWED_NEGATIVE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseMoneyBorrowed(Optional.of(INVALID_MONEY_BORROWED_NEGATIVE)));
    }

    @Test
    public void parseMoneyBorrowed_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseMoneyBorrowed(Optional.empty()).isPresent());
    }

    @Test
    public void parseMoneyBorrowed_validValueWithoutWhitespace_returnsMoneyBorrowed() throws Exception {
        MoneyBorrowed expectedMoneyBorrowed = new MoneyBorrowed(Double.parseDouble(VALID_MONEY_BORROWED));
        assertEquals(expectedMoneyBorrowed, ParserUtil.parseMoneyBorrowed(VALID_MONEY_BORROWED));
        assertEquals(Optional.of(expectedMoneyBorrowed),
                ParserUtil.parseMoneyBorrowed(Optional.of(VALID_MONEY_BORROWED)));
    }

    @Test
    public void parseMoneyBorrowed_validValueWithWhitespace_returnsTrimmedMoneyBorrowed() throws Exception {
        String moneyBorrowedWithWhitespace = WHITESPACE + VALID_MONEY_BORROWED + WHITESPACE;
        MoneyBorrowed expectedMoneyBorrowed = new MoneyBorrowed(Double.parseDouble(moneyBorrowedWithWhitespace));
        assertEquals(expectedMoneyBorrowed, ParserUtil.parseMoneyBorrowed(VALID_MONEY_BORROWED));
        assertEquals(Optional.of(expectedMoneyBorrowed),
                ParserUtil.parseMoneyBorrowed(Optional.of(VALID_MONEY_BORROWED)));
    }

    @Test
    public void parseStandardInterest_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                ParserUtil.parseStandardInterest((String) null));
        Assert.assertThrows(NullPointerException.class, () ->
                ParserUtil.parseStandardInterest((Optional<String>) null));
    }

    @Test
    public void parseStandardInterest_invalidValueNotDouble_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(INVALID_STANDARD_INTEREST_NOT_DOUBLE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(Optional.of(INVALID_STANDARD_INTEREST_NOT_DOUBLE)));
    }

    @Test
    public void parseStandardInterest_invalidValueNegative_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(INVALID_STANDARD_INTEREST_NEGATIVE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseStandardInterest(Optional.of(INVALID_STANDARD_INTEREST_NEGATIVE)));
    }

    @Test
    public void parseStandardInterest_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseStandardInterest(Optional.empty()).isPresent());
    }

    @Test
    public void parseStandardInterest_validValueWithoutWhitespace_returnsStandardInterest() throws Exception {
        StandardInterest expectedStandardInterest = new StandardInterest(Double.parseDouble(VALID_STANDARD_INTEREST));
        assertEquals(expectedStandardInterest, ParserUtil.parseStandardInterest(VALID_STANDARD_INTEREST));
        assertEquals(Optional.of(expectedStandardInterest),
                ParserUtil.parseStandardInterest(Optional.of(VALID_STANDARD_INTEREST)));
    }

    @Test
    public void parseStandardInterest_validValueWithWhitespace_returnsTrimmedStandardInterest() throws Exception {
        String standardInterestWithWhitespace = WHITESPACE + VALID_STANDARD_INTEREST + WHITESPACE;
        StandardInterest expectedStandardInterest =
                new StandardInterest(Double.parseDouble(standardInterestWithWhitespace));
        assertEquals(expectedStandardInterest, ParserUtil.parseStandardInterest(VALID_STANDARD_INTEREST));
        assertEquals(Optional.of(expectedStandardInterest),
                ParserUtil.parseStandardInterest(Optional.of(VALID_STANDARD_INTEREST)));
    }

    @Test
    public void parseLateInterest_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLateInterest((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLateInterest((Optional<String>) null));
    }

    @Test
    public void parseLateInterest_invalidValueNotDouble_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(INVALID_LATE_INTEREST_NOT_DOUBLE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(Optional.of(INVALID_LATE_INTEREST_NOT_DOUBLE)));
    }

    @Test
    public void parseLateInterest_invalidValueNegative_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(INVALID_LATE_INTEREST_NEGATIVE));
        Assert.assertThrows(IllegalValueException.class, () ->
                ParserUtil.parseLateInterest(Optional.of(INVALID_LATE_INTEREST_NEGATIVE)));
    }

    @Test
    public void parseLateInterest_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseLateInterest(Optional.empty()).isPresent());
    }

    @Test
    public void parseLateInterest_validValueWithoutWhitespace_returnsLateInterest() throws Exception {
        LateInterest expectedLateInterest = new LateInterest(Double.parseDouble(VALID_LATE_INTEREST));
        assertEquals(expectedLateInterest, ParserUtil.parseLateInterest(VALID_LATE_INTEREST));
        assertEquals(Optional.of(expectedLateInterest),
                ParserUtil.parseLateInterest(Optional.of(VALID_LATE_INTEREST)));
    }

    @Test
    public void parseLateInterest_validValueWithWhitespace_returnsTrimmedLateInterest() throws Exception {
        String lateInterestWithWhitespace = WHITESPACE + VALID_LATE_INTEREST + WHITESPACE;
        LateInterest expectedLateInterest =
                new LateInterest(Double.parseDouble(lateInterestWithWhitespace));
        assertEquals(expectedLateInterest, ParserUtil.parseLateInterest(VALID_LATE_INTEREST));
        assertEquals(Optional.of(expectedLateInterest),
                ParserUtil.parseLateInterest(Optional.of(VALID_LATE_INTEREST)));
    }
    //@@author
}
