package seedu.progresschecker.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.progresschecker.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.progresschecker.testutil.TypicalTaskArgs.ASTERISK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.ASTERISK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COM;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COMPULSORY;
import static seedu.progresschecker.testutil.TypicalTaskArgs.COM_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.DEFAULT_LIST_TITLE;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.FIRST_WEEK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INDEX_FIRST_TASK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_CHARSET;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_DOUBLE;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_MULTIPLE_ARGS;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_NEGATIVE;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_TITLE;
import static seedu.progresschecker.testutil.TypicalTaskArgs.INVALID_ZERO;
import static seedu.progresschecker.testutil.TypicalTaskArgs.LAST_WEEK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.LAST_WEEK_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.OUT_OF_BOUND_WEEK;
import static seedu.progresschecker.testutil.TypicalTaskArgs.SUB;
import static seedu.progresschecker.testutil.TypicalTaskArgs.SUBMISSION;
import static seedu.progresschecker.testutil.TypicalTaskArgs.SUB_INT;
import static seedu.progresschecker.testutil.TypicalTaskArgs.VALID_TITLE_EDGE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.progresschecker.commons.exceptions.IllegalValueException;
import seedu.progresschecker.model.person.Email;
import seedu.progresschecker.model.person.GithubUsername;
import seedu.progresschecker.model.person.Major;
import seedu.progresschecker.model.person.Name;
import seedu.progresschecker.model.person.Phone;
import seedu.progresschecker.model.tag.Tag;
import seedu.progresschecker.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_MAJOR = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_USERNAME = "R@chelGithub";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_MAJOR = "Computer Science";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_USERNAME = "RachelGithub";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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

    //@@author EdwardKSG
    @Test
    public void parseTaskIndex_invalidInputZero_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_ZERO);
    }

    @Test
    public void parseTaskIndex_invalidInputNegative_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_NEGATIVE);
    }

    @Test
    public void parseTaskIndex_invalidInputNotInteger_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_DOUBLE);
    }

    @Test
    public void parseTaskIndex_invalidInputNotNumber_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_CHARSET);
    }

    @Test
    public void parseTaskIndex_invalidInputMultipleArgs_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskIndex(INVALID_MULTIPLE_ARGS);
    }

    @Test
    public void parseTaskIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TASK_INT, ParserUtil.parseTaskIndex(INDEX_FIRST_TASK));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK_INT, ParserUtil.parseTaskIndex(" " + INDEX_FIRST_TASK + " "));
    }

    @Test
    public void parseTaskWeek_invalidInputOutOfBound_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(OUT_OF_BOUND_WEEK);
    }

    @Test
    public void parseTaskWeek_invalidInputZero_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_ZERO);
    }

    @Test
    public void parseTaskWeek_invalidInputNegative_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_NEGATIVE);
    }

    @Test
    public void parseTaskWeek_invalidInputNotInteger_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_DOUBLE);
    }

    @Test
    public void parseTaskWeek_invalidInputNotNumber_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_CHARSET);
    }

    @Test
    public void parseTaskWeek_invalidInputMultipleArgs_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskWeek(INVALID_MULTIPLE_ARGS);
    }

    @Test
    public void parseTaskWeek_validInputFirstWeek_success() throws Exception {
        // No whitespaces
        assertEquals(FIRST_WEEK_INT, ParserUtil.parseTaskWeek(FIRST_WEEK));

        // Leading and trailing whitespaces
        assertEquals(FIRST_WEEK_INT, ParserUtil.parseTaskWeek(" " + FIRST_WEEK + " "));
    }

    @Test
    public void parseTaskWeek_validInputLastWeek_success() throws Exception {
        // No whitespaces
        assertEquals(LAST_WEEK_INT, ParserUtil.parseTaskWeek(LAST_WEEK));

        // Leading and trailing whitespaces
        assertEquals(LAST_WEEK_INT, ParserUtil.parseTaskWeek(" " + LAST_WEEK + " "));
    }

    @Test
    public void parseTaskWeek_validInputAsterisk_success() throws Exception {
        // No whitespaces
        assertEquals(ASTERISK_INT, ParserUtil.parseTaskWeek(ASTERISK));

        // Leading and trailing whitespaces
        assertEquals(ASTERISK_INT, ParserUtil.parseTaskWeek(" " + ASTERISK + " "));
    }

    @Test
    public void parseTaskWeek_validInputCom_success() throws Exception {
        // No whitespaces
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(COMPULSORY));

        // Leading and trailing whitespaces
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(" " + COMPULSORY + " "));

        // No whitespaces-alias
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(COM));

        // Leading and trailing whitespaces-alias
        assertEquals(COM_INT, ParserUtil.parseTaskWeek(" " + COM + " "));
    }

    @Test
    public void parseTaskWeek_validInputSub_success() throws Exception {
        // No whitespaces
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(SUBMISSION));

        // Leading and trailing whitespaces
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(" " + SUBMISSION + " "));

        // No whitespaces-alias
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(SUB));

        // Leading and trailing whitespaces-alias
        assertEquals(SUB_INT, ParserUtil.parseTaskWeek(" " + SUB + " "));
    }

    @Test
    public void parseTaskTitle_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskTitle(INVALID_TITLE);
    }

    @Test
    public void parseTaskTitle_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(DEFAULT_LIST_TITLE, ParserUtil.parseTaskTitle(DEFAULT_LIST_TITLE));

        // Leading and trailing whitespaces
        assertEquals(DEFAULT_LIST_TITLE, ParserUtil.parseTaskTitle(" " + DEFAULT_LIST_TITLE + " "));

        // Valid length without leading and trailing whitespaces, but exceeds limit after having these spaces
        assertEquals(VALID_TITLE_EDGE, ParserUtil.parseTaskTitle(" " + VALID_TITLE_EDGE + " "));
    }
    //@@author

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

    //@@author EdwardKSG
    @Test
    public void parseUsername_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername((Optional<String>) null));
    }

    @Test
    public void parseUsername_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUsername(INVALID_USERNAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUsername(
                Optional.of(INVALID_USERNAME)));
    }

    @Test
    public void parseUsername_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseUsername(Optional.empty()).isPresent());
    }

    @Test
    public void parseUsername_validValueWithoutWhitespace_returnsUsername() throws Exception {
        GithubUsername expectedUsername = new GithubUsername(VALID_USERNAME);
        assertEquals(expectedUsername, ParserUtil.parseUsername(VALID_USERNAME));
        assertEquals(Optional.of(expectedUsername), ParserUtil.parseUsername(Optional.of(VALID_USERNAME)));
    }

    @Test
    public void parseUsername_validValueWithWhitespace_returnsTrimmedUsername() throws Exception {
        String usernameWithWhitespace = WHITESPACE + VALID_USERNAME + WHITESPACE;
        GithubUsername expectedUsername = new GithubUsername(VALID_USERNAME);
        assertEquals(expectedUsername, ParserUtil.parseUsername(usernameWithWhitespace));
        assertEquals(Optional.of(expectedUsername), ParserUtil.parseUsername(Optional.of(usernameWithWhitespace)));
    }


    @Test
    public void parseMajor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseMajor((Optional<String>) null));
    }

    @Test
    public void parseMajor_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMajor(INVALID_MAJOR));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseMajor(Optional.of(INVALID_MAJOR)));
    }

    @Test
    public void parseMajor_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseMajor(Optional.empty()).isPresent());
    }

    @Test
    public void parseMajor_validValueWithoutWhitespace_returnsMajor() throws Exception {
        Major expectedMajor = new Major(VALID_MAJOR);
        assertEquals(expectedMajor, ParserUtil.parseMajor(VALID_MAJOR));
        assertEquals(Optional.of(expectedMajor), ParserUtil.parseMajor(Optional.of(VALID_MAJOR)));
    }

    @Test
    public void parseMajor_validValueWithWhitespace_returnsTrimmedMajor() throws Exception {
        String majorWithWhitespace = WHITESPACE + VALID_MAJOR + WHITESPACE;
        Major expectedMajor = new Major(VALID_MAJOR);
        assertEquals(expectedMajor, ParserUtil.parseMajor(majorWithWhitespace));
        assertEquals(Optional.of(expectedMajor), ParserUtil.parseMajor(Optional.of(majorWithWhitespace)));
    }
    //@@author

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
}
