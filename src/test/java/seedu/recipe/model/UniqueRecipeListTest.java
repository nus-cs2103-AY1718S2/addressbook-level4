package seedu.recipe.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.recipe.UniqueRecipeList;

public class UniqueRecipeListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRecipeList uniqueRecipeList = new UniqueRecipeList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRecipeList.asObservableList().remove(0);
    }
}
