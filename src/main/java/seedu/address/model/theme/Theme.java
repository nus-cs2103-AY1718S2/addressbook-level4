package seedu.address.model.theme;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents the current theme of the address book.
 */
public class Theme {
    public static final String DARK_THEME_KEYWORD = "dark";
    public static final String LIGHT_THEME_KEYWORD = "light";

    public static final ArrayList<String> VALID_THEMES = new ArrayList<>(
            Arrays.asList(DARK_THEME_KEYWORD, LIGHT_THEME_KEYWORD));

    public static final String MESSAGE_THEME_CONSTRAINTS = "Theme can only be "
            + DARK_THEME_KEYWORD + " or " + LIGHT_THEME_KEYWORD;

    public static final String DARK_THEME_CSS_FILE_PATH = "view/DarkTheme.css";
    public static final String LIGHT_THEME_CSS_FILE_PATH = "view/LightTheme.css";

    private static String currentTheme;

    /**
     * Constructs a {@code currentTheme}.
     *
     * @param currentTheme a valid theme.
     */
    public Theme(String currentTheme) {
        requireNonNull(currentTheme);
        checkArgument(isValidTheme(currentTheme), MESSAGE_THEME_CONSTRAINTS);
        this.currentTheme = currentTheme;
    }

    /**
     * Returns true if the given string is a valid theme.
     */
    public static boolean isValidTheme(String test) {
        requireNonNull(test);
        return VALID_THEMES.contains(test);
    }

    /**
     * Returns the current theme.
     */
    public static String getCurrentTheme() {
        return currentTheme;
    }

    /**
     * Sets the current theme to the newTheme.
     */
    public static void setCurrentTheme(String newTheme) {
        requireNonNull(newTheme);
        if (isValidTheme(newTheme)) {
            currentTheme = newTheme;
        }
    }

    @Override
    public String toString() {
        return currentTheme;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Theme); // instanceof handles nulls
    }

    @Override
    public int hashCode() {
        return currentTheme.hashCode();
    }

    /**
     * Changes the current theme of the address book.
     */
    public static void changeTheme(Stage primaryStage, String newTheme) {
        if (isValidTheme(newTheme)) {
            Scene scene = primaryStage.getScene();

            // clear current styles
            scene.getStylesheets().clear();

            // new theme file path
            String newThemeCssFilePath;

            switch (newTheme) {
            case DARK_THEME_KEYWORD:
                newThemeCssFilePath = DARK_THEME_CSS_FILE_PATH;
                break;

            case LIGHT_THEME_KEYWORD:
                newThemeCssFilePath = LIGHT_THEME_CSS_FILE_PATH;
                break;

            default:
                newThemeCssFilePath = DARK_THEME_CSS_FILE_PATH;
            }

            scene.getStylesheets().add(newThemeCssFilePath);
            primaryStage.setScene(scene);
        }
    }
}
