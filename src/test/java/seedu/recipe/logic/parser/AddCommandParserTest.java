package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INSTRUCTION_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INGREDIENT_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Person;
import seedu.recipe.model.recipe.Phone;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple ingredients - last ingredient accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_AMY + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple instructions - last recipe accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_AMY
                + INSTRUCTION_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withIngredient(VALID_INGREDIENT_AMY).withInstruction(VALID_INSTRUCTION_AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + INGREDIENT_DESC_AMY + INSTRUCTION_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB,
                expectedMessage);

        // missing ingredient prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_INGREDIENT_BOB + INSTRUCTION_DESC_BOB,
                expectedMessage);

        // missing recipe prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + VALID_INSTRUCTION_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_INGREDIENT_BOB + VALID_INSTRUCTION_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid ingredient
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_INGREDIENT_DESC + INSTRUCTION_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS);

        // invalid recipe
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INVALID_INSTRUCTION_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INGREDIENT_DESC_BOB + INVALID_INSTRUCTION_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
