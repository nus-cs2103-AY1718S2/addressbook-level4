package seedu.recipe.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.recipe.logic.CommandHistory;
import seedu.recipe.logic.UndoRedoStack;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.exceptions.DuplicateRecipeException;
import seedu.recipe.model.recipe.exceptions.RecipeNotFoundException;
import seedu.recipe.testutil.RecipeBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullRecipe_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_recipeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRecipeAdded modelStub = new ModelStubAcceptingRecipeAdded();
        Recipe validRecipe = new RecipeBuilder().build();

        CommandResult commandResult = getAddCommandForRecipe(validRecipe, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validRecipe), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validRecipe), modelStub.recipesAdded);
    }

    @Test
    public void execute_duplicateRecipe_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateRecipeException();
        Recipe validRecipe = new RecipeBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_RECIPE);

        getAddCommandForRecipe(validRecipe, modelStub).execute();
    }

    @Test
    public void equals() {
        Recipe alice = new RecipeBuilder().withName("Alice").build();
        Recipe bob = new RecipeBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different recipe -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given recipe.
     */
    private AddCommand getAddCommandForRecipe(Recipe recipe, Model model) {
        AddCommand command = new AddCommand(recipe);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addRecipe(Recipe recipe) throws DuplicateRecipeException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyRecipeBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteRecipe(Recipe target) throws RecipeNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateRecipe(Recipe target, Recipe editedRecipe)
                throws DuplicateRecipeException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Recipe> getFilteredRecipeList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateRecipeException when trying to add a recipe.
     */
    private class ModelStubThrowingDuplicateRecipeException extends ModelStub {
        @Override
        public void addRecipe(Recipe recipe) throws DuplicateRecipeException {
            throw new DuplicateRecipeException();
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            return new RecipeBook();
        }
    }

    /**
     * A Model stub that always accept the recipe being added.
     */
    private class ModelStubAcceptingRecipeAdded extends ModelStub {
        final ArrayList<Recipe> recipesAdded = new ArrayList<>();

        @Override
        public void addRecipe(Recipe recipe) throws DuplicateRecipeException {
            requireNonNull(recipe);
            recipesAdded.add(recipe);
        }

        @Override
        public ReadOnlyRecipeBook getRecipeBook() {
            return new RecipeBook();
        }
    }

}
