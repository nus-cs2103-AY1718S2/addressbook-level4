package seedu.address.ui;

import javafx.scene.Scene;

//@@author tiny767
/**
 * stores the styles that can be used for the interface
 */
public class UiTheme {
    private static final String LIGHT_THEME_STYLE = "view/LightTheme.css";
    private static final String DARK_THEME_STYLE = "view/DarkTheme.css";
    private static final String MORNING_THEME_STYLE = "view/MorningTheme.css";
    private static Scene scene = null;
    private static UiTheme instance = null;

    public static void setScene(Scene s) {
        scene = s;
        setDefaultTheme();
    }

    private static void setDefaultTheme() {
        setToMorningTheme();
    }

    public static void setToLightTheme() {
        scene.getStylesheets().setAll(LIGHT_THEME_STYLE);
    }

    public static void setToDarkTheme() {
        scene.getStylesheets().setAll(DARK_THEME_STYLE);
    }

    public static void setToMorningTheme() {
        scene.getStylesheets().setAll(MORNING_THEME_STYLE);
    }

    public static UiTheme getInstance() {
        if (instance == null) {
            instance = new UiTheme();
        }

        return instance;
    }
}
