package seedu.recipe.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.recipe.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Phone;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_INSTRUCTION = " ";
    private static final String INVALID_INGREDIENT = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_INSTRUCTION = "123 Main Street #0505";
    private static final String VALID_INGREDIENT = "rachel@example.com";
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
    public void parseInstruction_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseInstruction((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseInstruction((Optional<String>) null));
    }

    @Test
    public void parseInstruction_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseInstruction(INVALID_INSTRUCTION));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseInstruction(Optional.of(INVALID_INSTRUCTION)));
    }

    @Test
    public void parseInstruction_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseInstruction(Optional.empty()).isPresent());
    }

    @Test
    public void parseInstruction_validValueWithoutWhitespace_returnsInstruction() throws Exception {
        Instruction expectedInstruction = new Instruction(VALID_INSTRUCTION);
        assertEquals(expectedInstruction, ParserUtil.parseInstruction(VALID_INSTRUCTION));
        assertEquals(Optional.of(expectedInstruction), ParserUtil.parseInstruction(Optional.of(VALID_INSTRUCTION)));
    }

    @Test
    public void parseInstruction_validValueWithWhitespace_returnsTrimmedInstruction() throws Exception {
        String instructionWithWhitespace = WHITESPACE + VALID_INSTRUCTION + WHITESPACE;
        Instruction expectedInstruction = new Instruction(VALID_INSTRUCTION);
        assertEquals(expectedInstruction, ParserUtil.parseInstruction(instructionWithWhitespace));
        assertEquals(Optional.of(expectedInstruction), ParserUtil.parseInstruction(Optional.of(instructionWithWhitespace)));
    }

    @Test
    public void parseIngredient_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredient((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseIngredient((Optional<String>) null));
    }

    @Test
    public void parseIngredient_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseIngredient(INVALID_INGREDIENT));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseIngredient(Optional.of(INVALID_INGREDIENT)));
    }

    @Test
    public void parseIngredient_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseIngredient(Optional.empty()).isPresent());
    }

    @Test
    public void parseIngredient_validValueWithoutWhitespace_returnsIngredient() throws Exception {
        Ingredient expectedIngredient = new Ingredient(VALID_INGREDIENT);
        assertEquals(expectedIngredient, ParserUtil.parseIngredient(VALID_INGREDIENT));
        assertEquals(Optional.of(expectedIngredient), ParserUtil.parseIngredient(Optional.of(VALID_INGREDIENT)));
    }

    @Test
    public void parseIngredient_validValueWithWhitespace_returnsTrimmedIngredient() throws Exception {
        String ingredientWithWhitespace = WHITESPACE + VALID_INGREDIENT + WHITESPACE;
        Ingredient expectedIngredient = new Ingredient(VALID_INGREDIENT);
        assertEquals(expectedIngredient, ParserUtil.parseIngredient(ingredientWithWhitespace));
        assertEquals(Optional.of(expectedIngredient), ParserUtil.parseIngredient(Optional.of(ingredientWithWhitespace)));
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
