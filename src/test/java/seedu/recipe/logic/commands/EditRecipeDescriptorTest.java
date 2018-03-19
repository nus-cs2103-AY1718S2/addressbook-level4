package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.recipe.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INGREDIENT_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_PREPARATION_TIME_BOB;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.recipe.logic.commands.EditCommand.EditRecipeDescriptor;
import seedu.recipe.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecipeDescriptor descriptorWithSameValues = new EditRecipeDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditRecipeDescriptor editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different preparationTime -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withPreparationTime(VALID_PREPARATION_TIME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different ingredient -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withIngredient(VALID_INGREDIENT_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different recipe -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withInstruction(VALID_INSTRUCTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
