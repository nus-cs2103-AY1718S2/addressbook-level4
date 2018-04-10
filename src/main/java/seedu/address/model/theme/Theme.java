package seedu.address.model.theme;

//@@author Yoochard

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Represents a Tag in the address book.
 * Guarantees: notnull; name is valid as declared in {@link #isValidThemeName(String)} (String)}
 */
public class Theme {
    public static final String[] ALL_THEME = {"dark", "bright"};
    public static final String DARK_THEME_CSS_FILE_NAME = "view/DarkTheme.css";
    public static final String BRIGHT_THEME_CSS_FILE_NAME = "view/BrightTheme.css";

    private static String currentTheme;

    /**
     * Change current theme
     */
    public static void changeCurrentTheme(String currentTheme) {
        requireNonNull(currentTheme);
        assert (isValidThemeName(currentTheme));
        if (isValidThemeName(currentTheme)) {
            Theme.currentTheme = currentTheme;
        }
    }

    /**
     * Get current theme
     */
    public static String getTheme() {
        return currentTheme;
    }

    /**
     * Returns true if input theme is valid
     */
    public static boolean isValidThemeName(String inputTheme) {
        return Arrays.asList(ALL_THEME).contains(inputTheme);
    }

    /**
     * Change theme
     */
    public static void changeTheme(Stage primaryStage, String inputTheme) {

        if (isValidThemeName(inputTheme)) {
            Scene scene = primaryStage.getScene();

            String cssFileName;

            switch (inputTheme) {
            case "dark":
                cssFileName = DARK_THEME_CSS_FILE_NAME;
                break;
            case "bright":
                cssFileName = BRIGHT_THEME_CSS_FILE_NAME;
                break;
            default:
                cssFileName = DARK_THEME_CSS_FILE_NAME;
            }

            scene.getStylesheets().add(cssFileName);
            primaryStage.setScene(scene);
        }
    }
}
