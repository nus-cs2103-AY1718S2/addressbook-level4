package seedu.recipe.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recipe.model.RecipeBook;
import seedu.recipe.storage.XmlAdaptedRecipe;
import seedu.recipe.storage.XmlAdaptedTag;
import seedu.recipe.storage.XmlSerializableRecipeBook;
import seedu.recipe.testutil.RecipeBookBuilder;
import seedu.recipe.testutil.RecipeBuilder;
import seedu.recipe.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validRecipeBook.xml");
    private static final File MISSING_RECIPE_FIELD_FILE = new File(TEST_DATA_FOLDER + "missingRecipeField.xml");
    private static final File INVALID_RECIPE_FIELD_FILE = new File(TEST_DATA_FOLDER + "invalidRecipeField.xml");
    private static final File VALID_RECIPE_FILE = new File(TEST_DATA_FOLDER + "validRecipe.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempRecipeBook.xml"));

    private static final String INVALID_PREPARATION_TIME = "9482asf424";

    private static final String VALID_NAME = "Chicken Rice";
    private static final String VALID_INGREDIENT = "demolishment,bigwig,archer,negative,appearance,afternoon";
    private static final String VALID_INSTRUCTION = "Fill a tea kettle or 2 quart saucepan with water and bring to "
            + "a boil. Remove excess fat from chilled chicken and place in colander over a large bowl. Spread out with"
            + " a fork. Pour hot water over meat through colander.\n"
            + "Place chicken in plastic container with tight fitting lid.\n"
            + "Add onions, chili powder, oregano, garlic powder, cumin, and paprika to chicken.\n"
            + "Refrigerate chicken overnight in plastic container with tight fitting lid.\n"
            + "To make tacos, place chicken mixture in a pan and heat slowly or heat in microwave for 2–3 minutes, "
            + "stirring after 1½ minutes to heat evenly. Combine finely shredded lettuce and cabbage. Mix cheeses "
            + "together. Place ¼ cup heated chicken mixture in a tortilla and top with cheese and vegetables.\n"
            + "Add salsa as desired.";
    private static final String VALID_COOKING_TIME = "20 min";
    private static final String VALID_PREPARATION_TIME = "69 hours";
    private static final String VALID_CALORIES = "5000";
    private static final String VALID_SERVINGS = "2";
    private static final String VALID_URL = "https://www.google.com";
    private static final String VALID_IMAGE = "-";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("best"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, RecipeBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, RecipeBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, RecipeBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        RecipeBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableRecipeBook.class).toModelType();
        assertEquals(9, dataFromFile.getRecipeList().size());
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithMissingRecipeField_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                MISSING_RECIPE_FIELD_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe =
                new XmlAdaptedRecipe(null, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_COOKING_TIME,
                        VALID_PREPARATION_TIME, VALID_CALORIES, VALID_SERVINGS, VALID_URL,
                        VALID_IMAGE, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithInvalidRecipeField_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                INVALID_RECIPE_FIELD_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_COOKING_TIME,
                        INVALID_PREPARATION_TIME, VALID_CALORIES, VALID_SERVINGS, VALID_URL,
                        VALID_IMAGE, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void xmlAdaptedRecipeFromFile_fileWithValidRecipe_validResult() throws Exception {
        XmlAdaptedRecipe actualRecipe = XmlUtil.getDataFromFile(
                VALID_RECIPE_FILE, XmlAdaptedRecipeWithRootElement.class);
        XmlAdaptedRecipe expectedRecipe =
                new XmlAdaptedRecipe(VALID_NAME, VALID_INGREDIENT, VALID_INSTRUCTION, VALID_COOKING_TIME,
                        VALID_PREPARATION_TIME, VALID_CALORIES, VALID_SERVINGS, VALID_URL,
                        VALID_IMAGE, VALID_TAGS);
        assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new RecipeBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new RecipeBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableRecipeBook dataToWrite = new XmlSerializableRecipeBook(new RecipeBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableRecipeBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRecipeBook.class);
        assertEquals(dataToWrite, dataFromFile);

        RecipeBookBuilder builder = new RecipeBookBuilder(new RecipeBook());
        dataToWrite = new XmlSerializableRecipeBook(
                builder.withRecipe(new RecipeBuilder().build()).withTag("food").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableRecipeBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRecipe}
     * objects.
     */
    @XmlRootElement(name = "recipe")
    private static class XmlAdaptedRecipeWithRootElement extends XmlAdaptedRecipe {}
}
