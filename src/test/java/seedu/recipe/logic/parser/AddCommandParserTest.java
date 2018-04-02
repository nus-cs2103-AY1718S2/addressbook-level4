package seedu.recipe.logic.parser;

import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recipe.logic.commands.CommandTestUtil.CALORIES_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.CALORIES_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.COOKING_TIME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.COOKING_TIME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.IMG_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.IMG_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INGREDIENT_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.INSTRUCTION_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_CALORIES_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_COOKING_TIME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INGREDIENT_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_INSTRUCTION_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_PREPARATION_TIME_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_SERVINGS_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.recipe.logic.commands.CommandTestUtil.LF;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.SERVINGS_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.SERVINGS_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.URL_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.URL_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_CALORIES_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_CALORIES_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_COOKING_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_COOKING_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_IMG_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_IMG_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_SERVINGS_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_SERVINGS_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.model.recipe.Calories;
import seedu.recipe.model.recipe.CookingTime;
import seedu.recipe.model.recipe.Image;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Servings;
import seedu.recipe.model.recipe.Url;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.testutil.RecipeBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Recipe expectedRecipe =
                new RecipeBuilder().withName(VALID_NAME_BOB).withPreparationTime(VALID_PREPARATION_TIME_BOB)
                        .withServings(VALID_SERVINGS_BOB).withCalories(VALID_CALORIES_BOB)
                        .withCookingTime(VALID_COOKING_TIME_BOB)
                        .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB)
                        .withUrl(VALID_URL_BOB).withImage(VALID_IMG_BOB).withTags(VALID_TAG_FRIEND).build();

        String newString = IMG_DESC_BOB;
        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                        + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                        + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB
                        + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple names - last name accepted
        assertParseSuccess(parser,
                NAME_DESC_AMY + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                        + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                        + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB
                        + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple preparationTimes - last preparationTime accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_AMY + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                        + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                        + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB
                        + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple ingredients - last ingredient accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_AMY + INGREDIENT_DESC_BOB
                        + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                        + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB
                        + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple instructions - last recipe accepted
        assertParseSuccess(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_AMY
                        + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                        + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_BOB
                        + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        //@@author RyanAngJY
        // multiple urls - last url accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                + INSTRUCTION_DESC_BOB + URL_DESC_AMY + URL_DESC_BOB + IMG_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));

        // multiple images - last image accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                + INSTRUCTION_DESC_BOB + URL_DESC_BOB + IMG_DESC_AMY + IMG_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedRecipe));
        //@@author


        // multiple tags - all accepted
        Recipe expectedRecipeMultipleTags =
                new RecipeBuilder().withName(VALID_NAME_BOB).withPreparationTime(VALID_PREPARATION_TIME_BOB)
                        .withServings(VALID_SERVINGS_BOB).withCalories(VALID_CALORIES_BOB)
                        .withCookingTime(VALID_COOKING_TIME_BOB)
                        .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB)
                        .withUrl(VALID_URL_BOB).withImage(VALID_IMG_BOB)
                        .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + COOKING_TIME_DESC_BOB + SERVINGS_DESC_BOB + CALORIES_DESC_BOB
                        + URL_DESC_BOB + IMG_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedRecipeMultipleTags));
    }

    //@@author kokonguyen191
    @Test
    public void parse_allFieldsPresentWithNewLineDelimiter_success() {
        Recipe expectedRecipe = new RecipeBuilder().withName(VALID_NAME_AMY).withServings(VALID_SERVINGS_AMY)
                .withPreparationTime(VALID_PREPARATION_TIME_AMY).withIngredient(VALID_INGREDIENT_AMY)
                .withCookingTime(VALID_COOKING_TIME_AMY).withCalories(VALID_CALORIES_AMY)
                .withInstruction(VALID_INSTRUCTION_AMY).withUrl(VALID_URL_AMY)
                .withImage(VALID_IMG_AMY).withTags(VALID_TAG_FRIEND).build();

        // Multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));


        // multiple ingredients - last ingredient accepted
        assertParseSuccess(parser, INGREDIENT_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple preparationTimes - last instruction accepted
        assertParseSuccess(parser, INSTRUCTION_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple instructions - last cooking time
        assertParseSuccess(parser, COOKING_TIME_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple instructions - last preparation time
        assertParseSuccess(parser, PREPARATION_TIME_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple instructions - last calories
        assertParseSuccess(parser, CALORIES_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple instructions - last servings
        assertParseSuccess(parser, SERVINGS_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + COOKING_TIME_DESC_AMY
                        + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple url - last url accepted
        assertParseSuccess(parser, URL_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + URL_DESC_AMY + LF + IMG_DESC_AMY
                        + LF + COOKING_TIME_DESC_AMY + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple images - last image accepted
        assertParseSuccess(parser, URL_DESC_BOB + LF
                        + NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY
                        + LF + INGREDIENT_DESC_AMY + LF + INSTRUCTION_DESC_AMY
                        + LF + URL_DESC_AMY + LF + IMG_DESC_AMY + LF + IMG_DESC_AMY
                        + LF + COOKING_TIME_DESC_AMY + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        // multiple tags - all accepted
        Recipe expectedRecipeMultipleTags = new RecipeBuilder().withName(VALID_NAME_AMY).withServings(
                VALID_SERVINGS_AMY).withPreparationTime(VALID_PREPARATION_TIME_AMY).withIngredient(VALID_INGREDIENT_AMY)
                .withCookingTime(VALID_COOKING_TIME_AMY).withCalories(VALID_CALORIES_AMY)
                .withInstruction(VALID_INSTRUCTION_AMY).withUrl(VALID_URL_AMY).withImage(VALID_IMG_AMY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_AMY + LF + PREPARATION_TIME_DESC_AMY + LF + INGREDIENT_DESC_AMY
                        + LF + INSTRUCTION_DESC_AMY + LF + URL_DESC_AMY + LF + IMG_DESC_AMY
                        + LF + COOKING_TIME_DESC_AMY + LF + CALORIES_DESC_AMY + LF + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipeMultipleTags));
    }
    //@@author

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Recipe expectedRecipe =
                new RecipeBuilder().withName(VALID_NAME_AMY).withPreparationTime(VALID_PREPARATION_TIME_AMY)
                        .withIngredient(VALID_INGREDIENT_AMY).withInstruction(VALID_INSTRUCTION_AMY)
                        .withServings(VALID_SERVINGS_AMY).withCalories(VALID_CALORIES_AMY)
                        .withCookingTime(VALID_COOKING_TIME_AMY).withUrl(VALID_URL_AMY)
                        .withImage(VALID_IMG_AMY).withTags().build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + PREPARATION_TIME_DESC_AMY + INGREDIENT_DESC_AMY + INSTRUCTION_DESC_AMY
                        + URL_DESC_AMY + IMG_DESC_AMY + COOKING_TIME_DESC_AMY + CALORIES_DESC_AMY + SERVINGS_DESC_AMY,
                new AddCommand(expectedRecipe));

        expectedRecipe = new RecipeBuilder().withName(VALID_NAME_AMY)
                .withPreparationTime(PreparationTime.NULL_PREPARATION_TIME_REFERENCE)
                .withIngredient(Ingredient.NULL_INGREDIENT_REFERENCE)
                .withInstruction(Instruction.NULL_INSTRUCTION_REFERENCE).withServings(Servings.NULL_SERVINGS_REFERENCE)
                .withCalories(Calories.NULL_CALORIES_REFERENCE).withCookingTime(CookingTime.NULL_COOKING_TIME_REFERENCE)
                .withUrl(Url.NULL_URL_REFERENCE).withImage(Image.NULL_IMAGE_REFERENCE).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY, new AddCommand(expectedRecipe));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                VALID_NAME_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB, expectedMessage);

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

        //@@author kokonguyen191
        // invalid ingredient
        assertParseFailure(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INVALID_INGREDIENT_DESC + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS);

        // invalid instruction
        assertParseFailure(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INVALID_INSTRUCTION_DESC
                        + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS);

        // invalid preparation time
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_PREPARATION_TIME_DESC + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS);

        // invalid cooking time
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_COOKING_TIME_DESC + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                CookingTime.MESSAGE_COOKING_TIME_CONSTRAINTS);

        // invalid calories
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_CALORIES_DESC + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Calories.MESSAGE_CALORIES_CONSTRAINTS);

        // invalid servings
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_SERVINGS_DESC + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Servings.MESSAGE_SERVINGS_CONSTRAINTS);

        //@@author RyanAngJY
        // invalid url
        assertParseFailure(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + INVALID_URL_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Url.MESSAGE_URL_CONSTRAINTS);
        //@@author

        // invalid tag
        assertParseFailure(parser,
                NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB + INSTRUCTION_DESC_BOB
                        + URL_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_NAME_DESC + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                        + INVALID_INSTRUCTION_DESC + URL_DESC_BOB, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_BOB
                        + INSTRUCTION_DESC_BOB + URL_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
