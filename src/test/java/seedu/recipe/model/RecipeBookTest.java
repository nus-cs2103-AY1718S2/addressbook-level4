package seedu.recipe.model;

import static org.junit.Assert.assertEquals;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.tag.Tag;

public class RecipeBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RecipeBook recipeBook = new RecipeBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), recipeBook.getRecipeList());
        assertEquals(Collections.emptyList(), recipeBook.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        recipeBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyRecipeBook_replacesData() {
        RecipeBook newData = getTypicalRecipeBook();
        recipeBook.resetData(newData);
        assertEquals(newData, recipeBook);
    }

    @Test
    public void resetData_withDuplicateRecipes_throwsAssertionError() {
        // Repeat ALICE twice
        List<Recipe> newRecipes = Arrays.asList(ALICE, ALICE);
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        RecipeBookStub newData = new RecipeBookStub(newRecipes, newTags);

        thrown.expect(AssertionError.class);
        recipeBook.resetData(newData);
    }

    @Test
    public void getRecipeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        recipeBook.getRecipeList().remove(0);
    }

    @Test
    public void getTagList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        recipeBook.getTagList().remove(0);
    }

    /**
     * A stub ReadOnlyRecipeBook whose recipes and tags lists can violate interface constraints.
     */
    private static class RecipeBookStub implements ReadOnlyRecipeBook {
        private final ObservableList<Recipe> recipes = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        RecipeBookStub(Collection<Recipe> recipes, Collection<? extends Tag> tags) {
            this.recipes.setAll(recipes);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<Recipe> getRecipeList() {
            return recipes;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
