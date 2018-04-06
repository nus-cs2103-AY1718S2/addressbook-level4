# RyanAngJY
###### \java\seedu\recipe\commons\events\ui\ShareRecipeEvent.java
``` java
/**
 * Indicates a request to share a recipe
 */
public class ShareRecipeEvent extends BaseEvent {

    public final int targetIndex;
    public final Recipe recipe;

    public ShareRecipeEvent(Index targetIndex, Recipe recipeToShare) {
        this.targetIndex = targetIndex.getZeroBased();
        this.recipe = recipeToShare;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Recipe getTargetRecipe() {
        return recipe;
    }
}
```
###### \java\seedu\recipe\logic\commands\ShareCommand.java
``` java
/**
 * Shares on Facebook a recipe identified using it's last displayed index from the recipe book.
 */
public class ShareCommand extends Command {

    public static final String COMMAND_WORD = "share";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shares on Facebook the recipe identified by the index number used in the last recipe listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SHARE_RECIPE_SUCCESS = "Recipe To Share: %1$s. "
            + "Do make sure that you have an Internet connection.";

    private final Index targetIndex;

    private Recipe recipeToShare;

    public ShareCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Recipe> lastShownList = model.getFilteredRecipeList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
        }

        recipeToShare = lastShownList.get(targetIndex.getZeroBased());
        EventsCenter.getInstance().post(new ShareRecipeEvent(targetIndex, recipeToShare));

        return new CommandResult(String.format(MESSAGE_SHARE_RECIPE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ShareCommand // instanceof handles nulls
                && this.targetIndex.equals(((ShareCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\recipe\logic\parser\ParserUtil.java
``` java

    /**
     * Parses a {@code String url} into an {@code Url}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code url} is invalid.
     */
    public static Url parseUrl(String url) throws IllegalValueException {
        requireNonNull(url);
        String trimmedUrl = url.trim();
        if (!Url.isValidUrl(trimmedUrl)) {
            throw new IllegalValueException(Url.MESSAGE_URL_CONSTRAINTS);
        }
        return new Url(trimmedUrl);
    }

    /**
     * Parses a {@code Optional<String> url} into an {@code Optional<Url>} if {@code url} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Url> parseUrl(Optional<String> url) throws IllegalValueException {
        requireNonNull(url);
        return url.isPresent() ? Optional.of(parseUrl(url.get())) : Optional.empty();
    }

    public static Url getNullReferenceUrl() {
        return new Url(Url.NULL_URL_REFERENCE);
    }

    /**
     * Parses a {@code Optional<String> url} into an {@code Optional<Url>} if {@code url} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Url> parseUrlOnInitialAdd(Optional<String> url) throws IllegalValueException {
        requireNonNull(url);
        return url.isPresent() ? Optional.of(parseUrl(url.get())) : Optional.of(getNullReferenceUrl());
    }

    /**
     * Parses a {@code String image} into an {@code image}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code image} is invalid.
     */
    public static Image parseImage(String image) throws IllegalValueException {
        requireNonNull(image);
        String trimmedImage = image.trim();
        if (!Image.isValidImage(trimmedImage)) {
            throw new IllegalValueException(Image.MESSAGE_IMAGE_CONSTRAINTS);
        }
        return new Image(trimmedImage);
    }

    /**
     * Parses a {@code Optional<String> url} into an {@code Optional<Url>} if {@code url} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Image> parseImage(Optional<String> image) throws IllegalValueException {
        requireNonNull(image);
        return image.isPresent() ? Optional.of(parseImage(image.get())) : Optional.empty();
    }

    public static Image getNullReferenceImage() {
        return new Image(Image.NULL_IMAGE_REFERENCE);
    }

    /**
     * Parses a {@code Optional<String> image} into an {@code Optional<Image>} if {@code image} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Image> parseImageOnInitialAdd(Optional<String> image) throws IllegalValueException {
        requireNonNull(image);
        return image.isPresent() ? Optional.of(parseImage(image.get())) : Optional.of(getNullReferenceImage());
    }
```
###### \java\seedu\recipe\logic\parser\ShareCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ShareCommand object
 */
public class ShareCommandParser implements Parser<ShareCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShareCommand
     * and returns an ShareCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ShareCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ShareCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShareCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\recipe\model\recipe\Image.java
``` java
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.io.File;
import java.net.URL;

import seedu.recipe.MainApp;

/**
 * Represents a Recipe's image in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidImage(String)}
 */
public class Image {

    public static final String NULL_IMAGE_REFERENCE = "-";
    public static final String FILE_PREFIX = "file:";
    public static final String MESSAGE_IMAGE_CONSTRAINTS = "Image path should be valid";
    public static final URL VALID_IMAGE = MainApp.class.getResource("/images/clock.png");
    public static final String VALID_IMAGE_PATH = VALID_IMAGE.toExternalForm().substring(5);
    public final String value;

    /**
     * Constructs a {@code Image}.
     *
     * @param imagePath A valid file path.
     */
    public Image(String imagePath) {
        requireNonNull(imagePath);
        checkArgument(isValidImage(imagePath), MESSAGE_IMAGE_CONSTRAINTS);
        this.value = imagePath;
    }

    /**
     *  Returns true if a given string is a valid file path, or no file path has been assigned
     */
    public static boolean isValidImage(String testImagePath) {
        if (testImagePath.equals(NULL_IMAGE_REFERENCE)) {
            return true;
        }
        File image = new File(testImagePath);
        if (image.exists() && !image.isDirectory()) {
            return true;
        }
        return false;
    }

    public String getUsablePath() {
        return FILE_PREFIX + value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Image // instanceof handles nulls
                && this.value.equals(((Image) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\recipe\model\recipe\Recipe.java
``` java
    public String getTextFormattedRecipe() {
        return NAME_HEADER + getName() + LINE_BREAK
                + INGREDIENTS_HEADER + getIngredient() + LINE_BREAK
                + INSTRUCTIONS_HEADER + getInstruction() + LINE_BREAK;
    }
```
###### \java\seedu\recipe\model\recipe\Url.java
``` java
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.net.URL;

/**
 * Represents a Recipe's URL in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Url {

    public static final String NULL_URL_REFERENCE = "-";
    public static final String MESSAGE_URL_CONSTRAINTS = "URL should start with a http:// or https://";
    public final String value;

    /**
     * Constructs a {@code Url}.
     *
     * @param url A valid Url.
     */
    public Url(String url) {
        requireNonNull(url);
        checkArgument(isValidUrl(url), MESSAGE_URL_CONSTRAINTS);
        this.value = url;
    }

    /**
     *  Returns true if a given string is a valid web url, or no url has been assigned
     */
    public static boolean isValidUrl(String testUrl) {
        if (testUrl.equals(NULL_URL_REFERENCE)) {
            return true;
        }
        try {
            URL url = new URL(testUrl);
            url.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Url // instanceof handles nulls
                && this.value.equals(((Url) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\recipe\model\util\HtmlFormatter.java
``` java
package seedu.recipe.model.util;

import static seedu.recipe.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import seedu.recipe.MainApp;
import seedu.recipe.model.recipe.Recipe;

/**
 * HTML formatter for Recipe class.
 */
public class HtmlFormatter {

    public static String getHtmlFormat (Recipe recipe) {

        URL recipeCss = MainApp.class.getResource(FXML_FILE_FOLDER + "Recipe.css");
        URL bootstrapCss = MainApp.class.getResource(FXML_FILE_FOLDER + "bootstrap.css");

        String name = recipe.getName().toString();
        String cookingTime = recipe.getCookingTime().toString();
        String preparationTime = recipe.getPreparationTime().toString();
        String calories = recipe.getCalories().toString();
        String servings = recipe.getServings().toString();
        String image = recipe.getImage().getUsablePath();
        String ingredient = recipe.getIngredient().toString();
        String instruction = recipe.getInstruction().toString();

        return "<html>"
                + "<head>"
                + "<link rel='stylesheet' type='text/css' href='" + bootstrapCss.toExternalForm() + "' />"
                + "<link rel='stylesheet' type='text/css' href='" + recipeCss.toExternalForm() + "' />"
                + "</head>"
                + "<body>"
                + "<div class='row'>"
                + "<h1 class='name'>" + name + "</h1>"
                + "<div class='col-sm-6'>"
                + "<div class='col-sm-3'>"
                + "<h5>Cooking Time:</h5>"
                + "<p>" + cookingTime + "</p>"
                + "</div>"
                + "<div class='col-sm-3'>"
                + "<h5>Preparation Time:</h5>"
                + "<p>" + preparationTime + "</p>"
                + "</div>"
                + "<div class='col-sm-3'>"
                + "<h5>Calories:</h5>"
                + "<p>" + calories + "</p>"
                + "</div>"
                + "<div class='col-sm-3'>"
                + "<h5>Servings:</h5>"
                + "<p>" + servings + "</p>"
                + "</div>"
                + "</div>"
                + "<div class='col-sm-6'>"
                + "<img src='" + image + "' />"
                + "</div>"
                + "<div class='col-sm-12'>"
                + "<div class='col-sm-12'>"
                + "<h5>Ingredients:</h5>"
                + "<p>" + ingredient + "</p>"
                + "</div>"
                + "<div class='col-sm-12'>"
                + "<h5>Instructions:</h5>"
                + "<p>" + instruction + "</p>"
                + "</div>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }
}
```
###### \java\seedu\recipe\storage\XmlAdaptedRecipe.java
``` java
        if (this.url == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName()));
        }
        if (!Url.isValidUrl(this.url)) {
            throw new IllegalValueException(Url.MESSAGE_URL_CONSTRAINTS);
        }
        final Url url = new Url(this.url);

        if (this.image == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Image.class.getSimpleName()));
        }
        if (!Image.isValidImage(this.image)) {
            throw new IllegalValueException(Image.MESSAGE_IMAGE_CONSTRAINTS);
        }
        final Image image = new Image(this.image);
```
###### \java\seedu\recipe\ui\BrowserPanel.java
``` java
    /**
     * Loads the text recipe onto the browser
     */
    private void loadLocalRecipe(Recipe recipe) {
        browser.getEngine().loadContent(HtmlFormatter.getHtmlFormat(recipe));
    }
```
###### \java\seedu\recipe\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleShareRecipeEvent(ShareRecipeEvent event) {
        recipeToShare = event.getTargetRecipe();
        String urlToShare = recipeToShare.getUrl().toString();
        UiUtil.copyToClipboard(recipeToShare.getTextFormattedRecipe());

        if (!urlToShare.equals(Url.NULL_URL_REFERENCE)) {
            loadPage(FacebookHandler.getPostDomain() + recipeToShare.getUrl().toString()
                    + FacebookHandler.getRedirectEmbedded());
        } else {
            loadPage(FacebookHandler.REDIRECT_DOMAIN);
        }
    }

```
###### \java\seedu\recipe\ui\CssSyntax.java
``` java
package seedu.recipe.ui;

/**
 * Contains CSS (Cascading Style Sheet) syntax.
 */
public class CssSyntax {

    /* Prefix definitions */
    public static final String CSS_PROPERTY_BACKGROUND_COLOR = "-fx-background-color: ";
    public static final String CSS_PROPERTY_TEXT_COLOR = "-fx-text-fill: ";
}
```
###### \java\seedu\recipe\ui\RecipeCard.java
``` java
    /**
     * Sets the image for {@code imageView}.
     */
    private void setImageView(ImageView imageView) {
        if (recipe.isNullImage()) {
            return;
        }
        try {
            FileInputStream input = new FileInputStream(recipe.getImage().toString());
            Image image = new Image(input);
            imageView.setImage(image);
        } catch (IOException exception) {
            System.out.println("File not found");
        }
    }

    /**
     * Returns the color style for {@code tagName}'s label.
     */
    private String getTagColorStyleFor(String tagName) {
        // the hash code of the tag name is used to generate a random color for each tag,
        // color remains consistent between different runs of the program since hash code does not change
        String hexadecimalHashCode = UiUtil.convertIntToHexadecimalString(tagName.hashCode());
        String hexadecimalColorCode = UiUtil.convertStringToValidColorCode(hexadecimalHashCode);
        return hexadecimalColorCode;
    }

    /**
     * Creates the tag labels for {@code recipe}.
     */
    private void initTags(Recipe recipe) {
        recipe.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            String labelBackgroundColor = getTagColorStyleFor(tag.tagName);
            UiUtil.setLabelColor(tagLabel, labelBackgroundColor);
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \java\seedu\recipe\ui\RecipeListPanel.java
``` java
    @Subscribe
    private void handleShareRecipeEvent(ShareRecipeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
```
###### \java\seedu\recipe\ui\util\FacebookHandler.java
``` java
package seedu.recipe.ui.util;

/**
 * Manages Facebook related commands and execution
 */
public class FacebookHandler {

    public static final String ACCESS_TOKEN_IDENTIFIER = "#access_token=";
    public static final String REDIRECT_DOMAIN = "https://www.facebook.com/";
    public static final String REDIRECT_EMBEDDED = "&redirect_uri=" + REDIRECT_DOMAIN;
    private static final String APP_ID = "177615459696708";
    public static final String POST_DOMAIN = "https://www.facebook.com/dialog/feed?%20app_id="
            + APP_ID + "&display=popup&amp;&link=";

    private static final String ACCESS_RIGHTS = "user_about_me";
    private static final String AUTH_URL = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id="
            + APP_ID + "&redirect_uri=" + REDIRECT_DOMAIN + "&scope=" + ACCESS_RIGHTS;

    private static final String ACCESS_TOKEN_REGEX = REDIRECT_DOMAIN + "#access_token=(.+)&.*";
    private static final String EXTRACT_PORTION = "$1";

    private static String accessToken = null;

    /**
     * Returns true if FacebookHandler already has an access token.
     */
    public static boolean hasAccessToken() {
        if (accessToken == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if an access token is embedded in the url.
     * If access token is found, set the accessToken variable to be the found access token.
     *
     * @return Returns true when access token is found.
     */
    public static boolean checkAndSetAccessToken(String url) {
        if (url.contains(ACCESS_TOKEN_IDENTIFIER)) {
            String token = extractAccessToken(url);
            setAccessToken(token);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Extracts access token from the given URL.
     */
    public static String extractAccessToken(String url) {
        assert (url.contains(ACCESS_TOKEN_IDENTIFIER));
        return url.replaceAll(ACCESS_TOKEN_REGEX, EXTRACT_PORTION);
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static String getPostDomain() {
        return POST_DOMAIN;
    }

    public static String getRedirectEmbedded() {
        return REDIRECT_EMBEDDED;
    }
}
```
###### \java\seedu\recipe\ui\util\UiUtil.java
``` java
package seedu.recipe.ui.util;

import static seedu.recipe.ui.CssSyntax.CSS_PROPERTY_BACKGROUND_COLOR;
import static seedu.recipe.ui.CssSyntax.CSS_PROPERTY_TEXT_COLOR;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.regex.Pattern;

import javafx.scene.control.Label;

/**
 * Contains utility methods for UI design.
 */
public class UiUtil {

    public static final String HEX_REGEX = "[A-Fa-f0-9]*";
    public static final String HEX_COLOR_BLACK = "#000000";
    public static final String HEX_COLOR_WHITE = "#FFFFFF";

    public static final String HEX_COLOR_REGEX = "#([A-Fa-f0-9]{6})";
    private static final char HEX_COLOR_PREFIX = '#';
    private static final String HEX_COLOR_BUFFER = "000000";
    private static final int HEX_COLOR_LENGTH = 6;

    private static final int NEUTRAL_COLOR_DENSITY = 384;


    /**
     * Returns a hexadecimal string representation of an integer.
     */
    public static String convertIntToHexadecimalString(int integer) {
        return Integer.toHexString(integer);
    }

    /**
     * Returns a valid CSS hexadecimal color code that is as similar
     * as possible to the given string (eg. #f23b21).
     */
    public static String convertStringToValidColorCode(String string) {
        string = removeAllWhitespaceInString(string);

        // HEX_COLOR_BUFFER ensures that the returned value has at least 6 hexadecimal digits
        String extendedHexString = string.concat(HEX_COLOR_BUFFER);

        if (Pattern.matches(HEX_REGEX, string)) {
            return HEX_COLOR_PREFIX + extendedHexString.substring(0, HEX_COLOR_LENGTH);
        } else {
            return HEX_COLOR_WHITE;
        }
    }

    /**
     * Sets the color of a label given a particular background color
     *
     * @@param backgroundColor Preferably a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static void setLabelColor(Label label, String backgroundColor) {
        backgroundColor = removeAllWhitespaceInString(backgroundColor);
        backgroundColor = isValidHexColorCode(backgroundColor) ? backgroundColor : HEX_COLOR_WHITE;

        String textColor = getMatchingColorFromGivenColor(backgroundColor);
        label.setStyle(CSS_PROPERTY_BACKGROUND_COLOR + backgroundColor + "; "
                + CSS_PROPERTY_TEXT_COLOR + textColor + ";");
    }

    /**
     * Sets the text color of a label
     *
     * @@param backgroundColor Preferably a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static String getMatchingColorFromGivenColor(String backgroundColor) {
        backgroundColor = removeAllWhitespaceInString(backgroundColor);
        backgroundColor = isValidHexColorCode(backgroundColor) ? backgroundColor : HEX_COLOR_WHITE;

        if (isColorDark(backgroundColor)) {
            return HEX_COLOR_WHITE;
        } else {
            return HEX_COLOR_BLACK;
        }
    }

    /**
     * Returns true if a given color is dark
     *
     * @@param color Preferably a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static boolean isColorDark(String color) {
        color = removeAllWhitespaceInString(color);
        color = isValidHexColorCode(color) ? color : HEX_COLOR_WHITE;

        int lightnessCount = 0;

        for (int i = 1; i < 6; i = i + 2) {
            String colorDensity = color.substring(i, i + 2);
            lightnessCount += Integer.parseInt(colorDensity, 16);
        }

        if (lightnessCount >= NEUTRAL_COLOR_DENSITY) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if a String is a valid CSS hexadecimal color code (eg. #f23b21)
     */
    public static boolean isValidHexColorCode (String string) {
        return Pattern.matches(HEX_COLOR_REGEX, string);
    }

    /**
     * Returns the given string without whitespaces
     */
    public static String removeAllWhitespaceInString(String string) {
        return string.replaceAll("\\s", "");
    }

    /**
     * Copies a given string to the clipboard
     */
    public static void copyToClipboard(String string) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection stringSelection = new StringSelection(string);
        clipboard.setContents(stringSelection, null);
    }
}
```
