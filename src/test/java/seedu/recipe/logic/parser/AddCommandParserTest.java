package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INGREDIENT_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INSTRUCTION_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_PREPARATION_TIME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.testutil.RecipeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe =
            new RecipeBuilder().withName(VALID_NAME_BOB).withPreparationTime(VALID_PREPARATION_TIME_BOB)
                .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB)
                    .withUrl(VALID_URL_BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser,
            PREAMBLE_WHITESPACE + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple names - last name accepted
        assertParseSuccess(parser,
            NAME_DESC_AMY + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple preparationTimes - last preparationTime accepted
        assertParseSuccess(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_AMY + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple ingredients - last ingredient accepted
        assertParseSuccess(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_AMY + INGREDIENT_DESC_BOB
                + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple instructions - last recipe accepted
        assertParseSuccess(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_AMY
                + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple tags - all accepted
        Recipe expectedRecipeMultipleTags =
            new RecipeBuilder().withName(VALID_NAME_BOB).withPreparationTime(VALID_PREPARATION_TIME_BOB)
                .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB)
                .withUrl(VALID_URL_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedRecipeMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Recipe expectedRecipe =
            new RecipeBuilder().withName(VALID_NAME_AMY).withPreparationTime(VALID_PREPARATION_TIME_AMY)
                .withIngredient(VALID_INGREDIENT_AMY).withInstruction(VALID_INSTRUCTION_AMY)
                    .withUrl(VALID_URL_AMY).withTags().build();
        assertParseSuccess(parser,
            NAME_DESC_AMY + PREPARATION_TIME_DESC_AMY + INGREDIENT_DESC_AMY + INSTRUCTION_DESC_AMY
                + URL_DESC_AMY, new AddCommand(expectedRecipe));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
            VALID_NAME_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB, expectedMessage);

        // missing preparationTime prefix
        assertParseFailure(parser,
            NAME_DESC_BOB + VALID_PREPARATION_TIME_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB, expectedMessage);

        // missing ingredient prefix
        assertParseFailure(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + VALID_INGREDIENT_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB, expectedMessage);

        // missing instruction prefix
        assertParseFailure(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + VALID_INSTRUCTION_BOB
                + URL_DESC_BOB, expectedMessage);

        // missing url prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + VALID_URL_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
            VALID_NAME_BOB + VALID_PREPARATION_TIME_BOB + VALID_INGREDIENT_BOB + VALID_INSTRUCTION_BOB
                + VALID_URL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
            INVALID_NAME_DESC + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid preparationTime
        assertParseFailure(parser,
            NAME_DESC_BOB + INVALID_PREPARATION_TIME_DESC + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS);

        // invalid ingredient
        assertParseFailure(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INVALID_INGREDIENT_DESC + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS);

        // invalid instruction
        assertParseFailure(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INVALID_INSTRUCTION_DESC
                + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);

        // invalid url
        assertParseFailure(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + INVALID_URL_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Url.MESSAGE_URL_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
            NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                + URL_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
            INVALID_NAME_DESC + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INVALID_INSTRUCTION_DESC
                + URL_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB
                + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
