package seedu.recipe.storage;

import static org.junit.Assert.assertEquals;
import static seedu.recipe.storage.XmlAdaptedRecipe.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.recipe.testutil.TypicalRecipes.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.model.recipe.Ingredient;
import seedu.recipe.model.recipe.Instruction;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Phone;
import seedu.recipe.testutil.Assert;

public class XmlAdaptedRecipeTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_INSTRUCTION = " ";
    private static final String INVALID_INGREDIENT = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_INGREDIENT = BENSON.getIngredient().toString();
    private static final String VALID_INSTRUCTION = BENSON.getInstruction().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validRecipeDetails_returnsRecipe() throws Exception {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(BENSON);
        assertEquals(BENSON, recipe.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(INVALID_NAME, VALID_PHONE, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(null, VALID_PHONE, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, INVALID_PHONE, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(VALID_NAME, null, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidIngredient_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_PHONE, INVALID_INGREDIENT, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = Ingredient.MESSAGE_INGREDIENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullIngredient_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(VALID_NAME, VALID_PHONE, null, VALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Ingredient.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidInstruction_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_PHONE, VALID_INGREDIENT, INVALID_INSTRUCTION, VALID_TAGS);
        String expectedMessage = Instruction.MESSAGE_INSTRUCTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_nullInstruction_throwsIllegalValueException() {
        XmlAdaptedRecipe recipe = new XmlAdaptedRecipe(VALID_NAME, VALID_PHONE, VALID_INGREDIENT, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Instruction.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, recipe::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedRecipe recipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_PHONE, VALID_INGREDIENT, VALID_INSTRUCTION, invalidTags);
        Assert.assertThrows(IllegalValueException.class, recipe::toModelType);
    }

}
