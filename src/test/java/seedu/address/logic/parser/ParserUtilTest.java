package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TestUtil.AND_TOKEN;
import static seedu.address.testutil.TestUtil.COIN_0;
import static seedu.address.testutil.TestUtil.COIN_1;
import static seedu.address.testutil.TestUtil.COIN_2;
import static seedu.address.testutil.TestUtil.COIN_3;
import static seedu.address.testutil.TestUtil.COIN_4;
import static seedu.address.testutil.TestUtil.COIN_5;
import static seedu.address.testutil.TestUtil.COIN_6;
import static seedu.address.testutil.TestUtil.COIN_7;
import static seedu.address.testutil.TestUtil.EOF_TOKEN;
import static seedu.address.testutil.TestUtil.LEFT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.NOT_TOKEN;
import static seedu.address.testutil.TestUtil.NUM_TOKEN;
import static seedu.address.testutil.TestUtil.OR_TOKEN;
import static seedu.address.testutil.TestUtil.PREFIX_TAG_TOKEN;
import static seedu.address.testutil.TestUtil.RIGHT_PAREN_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_ONE_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_THREE_TOKEN;
import static seedu.address.testutil.TestUtil.STRING_TWO_TOKEN;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.coin.Code;
import seedu.address.model.coin.Coin;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
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
        assertEquals(INDEX_FIRST_COIN, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_COIN, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseAmount_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAmount("1.1a");
    }

    @Test
    public void parseAmount_validInput_success() throws Exception {
        // No whitespaces
        assertEquals("1.23450000", ParserUtil.parseAmount("1.2345").getValue());

        // Leading and trailing whitespaces
        assertEquals("1.23450000", ParserUtil.parseAmount("  1.2345  ").getValue());
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
        Code expectedCode = new Code(VALID_NAME);
        assertEquals(expectedCode, ParserUtil.parseName(VALID_NAME));
        assertEquals(Optional.of(expectedCode), ParserUtil.parseName(Optional.of(VALID_NAME)));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Code expectedCode = new Code(VALID_NAME);
        assertEquals(expectedCode, ParserUtil.parseName(nameWithWhitespace));
        assertEquals(Optional.of(expectedCode), ParserUtil.parseName(Optional.of(nameWithWhitespace)));
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

    //@@author Eldon-Chung
    @Test
    public void parseCommand_validArgument_returnsPredicate() throws Exception {
        TokenStack tokenStack = initTokenStack(PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, AND_TOKEN, NOT_TOKEN,
                LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, STRING_TWO_TOKEN, OR_TOKEN, PREFIX_TAG_TOKEN,
                STRING_THREE_TOKEN, RIGHT_PAREN_TOKEN, EOF_TOKEN);
        Predicate<Coin> condition = ParserUtil.parseCondition(tokenStack);

        assertFalse(condition.test(COIN_0));
        assertFalse(condition.test(COIN_1));
        assertFalse(condition.test(COIN_2));
        assertFalse(condition.test(COIN_3));
        assertTrue(condition.test(COIN_4));
        assertFalse(condition.test(COIN_5));
        assertFalse(condition.test(COIN_6));
        assertFalse(condition.test(COIN_7));
    }

    @Test
    public void parseCondition_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCondition(null);
    }

    @Test
    public void parseCondition_invalidArgumentSyntax_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        TokenStack tokenStack = initTokenStack(LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, STRING_ONE_TOKEN, EOF_TOKEN);
        ParserUtil.parseCondition(tokenStack);
    }

    @Test
    public void parseCondition_invalidArgumentSemantics_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        TokenStack tokenStack = initTokenStack(LEFT_PAREN_TOKEN, PREFIX_TAG_TOKEN, NUM_TOKEN, EOF_TOKEN);
        ParserUtil.parseCondition(tokenStack);
    }

    private static TokenStack initTokenStack(Token... tokens) {
        return new TokenStack(new ArrayList<Token>(Arrays.asList(tokens)));
    }
    //@@author
}
