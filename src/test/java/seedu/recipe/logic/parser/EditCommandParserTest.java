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
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.PREPARATION_TIME_DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.recipe.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.recipe.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_SECOND_RECIPE;
import static seedu.recipe.testutil.TypicalIndexes.INDEX_THIRD_RECIPE;

import org.junit.Test;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.logic.commands.EditCommand;
import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.PreparationTime;
import seedu.recipe.model.tag.Tag;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PREPARATION_TIME_DESC,
                PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS); // invalid preparationTime
        assertParseFailure(parser, "1" + INVALID_INGREDIENT_DESC,
                Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS); // invalid ingredient
        assertParseFailure(parser, "1" + INVALID_INSTRUCTION_DESC,
                Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS); // invalid recipe
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid preparationTime followed by valid ingredient
        assertParseFailure(parser, "1" + INVALID_PREPARATION_TIME_DESC + INGREDIENT_DESC_AMY,
                PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS);

        // valid preparationTime followed by invalid preparationTime.
        // The test case for invalid preparationTime followed by valid preparationTime
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + PREPARATION_TIME_DESC_BOB + INVALID_PREPARATION_TIME_DESC,
                PreparationTime.MESSAGE_PREPARATION_TIME_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Recipe} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser,
                "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser,
                "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + INVALID_INGREDIENT_DESC
                        + VALID_INSTRUCTION_AMY + VALID_PREPARATION_TIME_AMY,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RECIPE;
        String userInput = targetIndex.getOneBased() + PREPARATION_TIME_DESC_BOB + TAG_DESC_HUSBAND
                + INGREDIENT_DESC_AMY + INSTRUCTION_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPreparationTime(VALID_PREPARATION_TIME_BOB).withIngredient(VALID_INGREDIENT_AMY)
                .withInstruction(VALID_INSTRUCTION_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + PREPARATION_TIME_DESC_BOB + INGREDIENT_DESC_AMY;

        EditRecipeDescriptor descriptor =
                new EditRecipeDescriptorBuilder().withPreparationTime(VALID_PREPARATION_TIME_BOB)
                        .withIngredient(VALID_INGREDIENT_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // preparationTime
        userInput = targetIndex.getOneBased() + PREPARATION_TIME_DESC_AMY;
        descriptor = new EditRecipeDescriptorBuilder().withPreparationTime(VALID_PREPARATION_TIME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // ingredient
        userInput = targetIndex.getOneBased() + INGREDIENT_DESC_AMY;
        descriptor = new EditRecipeDescriptorBuilder().withIngredient(VALID_INGREDIENT_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // recipe
        userInput = targetIndex.getOneBased() + INSTRUCTION_DESC_AMY;
        descriptor = new EditRecipeDescriptorBuilder().withInstruction(VALID_INSTRUCTION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditRecipeDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + PREPARATION_TIME_DESC_AMY + INSTRUCTION_DESC_AMY
                + INGREDIENT_DESC_AMY + TAG_DESC_FRIEND + PREPARATION_TIME_DESC_AMY + INSTRUCTION_DESC_AMY
                + INGREDIENT_DESC_AMY + TAG_DESC_FRIEND + PREPARATION_TIME_DESC_BOB + INSTRUCTION_DESC_BOB
                + INGREDIENT_DESC_BOB + TAG_DESC_HUSBAND;

        EditRecipeDescriptor descriptor =
                new EditRecipeDescriptorBuilder().withPreparationTime(VALID_PREPARATION_TIME_BOB)
                        .withIngredient(VALID_INGREDIENT_BOB).withInstruction(VALID_INSTRUCTION_BOB)
                        .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                        .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_RECIPE;
        String userInput = targetIndex.getOneBased() + INVALID_PREPARATION_TIME_DESC + PREPARATION_TIME_DESC_BOB;
        EditRecipeDescriptor descriptor =
                new EditRecipeDescriptorBuilder().withPreparationTime(VALID_PREPARATION_TIME_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput =
                targetIndex.getOneBased() + INGREDIENT_DESC_BOB + INVALID_PREPARATION_TIME_DESC + INSTRUCTION_DESC_BOB
                        + PREPARATION_TIME_DESC_BOB;
        descriptor = new EditRecipeDescriptorBuilder().withPreparationTime(VALID_PREPARATION_TIME_BOB)
                .withIngredient(VALID_INGREDIENT_BOB)
                .withInstruction(VALID_INSTRUCTION_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_RECIPE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditRecipeDescriptor descriptor = new EditRecipeDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
