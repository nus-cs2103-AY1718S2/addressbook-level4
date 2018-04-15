package seedu.recipe.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.recipe.testutil.TypicalRecipes.ALICE;
import static seedu.recipe.testutil.TypicalRecipes.HOON;
import static seedu.recipe.testutil.TypicalRecipes.IDA;
import static seedu.recipe.testutil.TypicalRecipes.getTypicalRecipeBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.commons.util.FileUtil;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.RecipeBook;

public class XmlRecipeBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRecipeBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRecipeBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRecipeBook(null);
    }

    private java.util.Optional<ReadOnlyRecipeBook> readRecipeBook(String filePath) throws Exception {
        return new XmlRecipeBookStorage(filePath).readRecipeBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecipeBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRecipeBook("NotXmlFormatRecipeBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readRecipeBook_invalidRecipeRecipeBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRecipeBook("invalidRecipeRecipeBook.xml");
    }

    @Test
    public void readRecipeBook_invalidAndValidRecipeRecipeBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRecipeBook("invalidAndValidRecipeRecipeBook.xml");
    }

    @Test
    public void readAndSaveRecipeBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRecipeBook.xml";
        RecipeBook original = getTypicalRecipeBook();
        XmlRecipeBookStorage xmlRecipeBookStorage = new XmlRecipeBookStorage(filePath);

        //Save in new file and read back
        xmlRecipeBookStorage.saveRecipeBook(original, filePath);
        ReadOnlyRecipeBook readBack = xmlRecipeBookStorage.readRecipeBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRecipe(HOON);
        original.removeRecipe(ALICE);
        xmlRecipeBookStorage.saveRecipeBook(original, filePath);
        readBack = xmlRecipeBookStorage.readRecipeBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        //Save and read without specifying file path
        original.addRecipe(IDA);
        xmlRecipeBookStorage.saveRecipeBook(original); //file path not specified
        readBack = xmlRecipeBookStorage.readRecipeBook().get(); //file path not specified
        assertEquals(original, new RecipeBook(readBack));

    }

    @Test
    public void saveRecipeBook_nullRecipeBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRecipeBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code recipeBook} at the specified {@code filePath}.
     */
    private void saveRecipeBook(ReadOnlyRecipeBook recipeBook, String filePath) {
        try {
            new XmlRecipeBookStorage(filePath).saveRecipeBook(recipeBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecipeBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveRecipeBook(new RecipeBook(), null);
    }


}
