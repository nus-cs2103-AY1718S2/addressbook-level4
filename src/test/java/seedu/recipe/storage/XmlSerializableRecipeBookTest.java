package seedu.recipe.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.commons.exceptions.IllegalValueException;
import seedu.recipe.commons.util.FileUtil;
import seedu.recipe.commons.util.XmlUtil;
import seedu.recipe.model.RecipeBook;
import seedu.recipe.testutil.TypicalRecipes;

public class XmlSerializableRecipeBookTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlSerializableRecipeBookTest/");
    private static final File TYPICAL_RECIPES_FILE = new File(TEST_DATA_FOLDER + "typicalRecipesRecipeBook.xml");
    private static final File INVALID_RECIPE_FILE = new File(TEST_DATA_FOLDER + "invalidRecipeRecipeBook.xml");
    private static final File INVALID_TAG_FILE = new File(TEST_DATA_FOLDER + "invalidTagRecipeBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRecipesFile_success() throws Exception {
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_RECIPES_FILE,
                XmlSerializableRecipeBook.class);
        RecipeBook recipeBookFromFile = dataFromFile.toModelType();
        RecipeBook typicalRecipesRecipeBook = TypicalRecipes.getTypicalRecipeBook();
        assertEquals(recipeBookFromFile, typicalRecipesRecipeBook);
    }

    @Test
    public void toModelType_invalidRecipeFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(INVALID_RECIPE_FILE,
                XmlSerializableRecipeBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidTagFile_throwsIllegalValueException() throws Exception {
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TAG_FILE,
                XmlSerializableRecipeBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }
}
