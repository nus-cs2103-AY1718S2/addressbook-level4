package seedu.address.model.theme;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Collectors;

//@@author aquarinte
/**
 * Represents a Theme in the address book.
 */
public class Theme {

    private static String[] themes = {"dark", "light"};
    private static String[] themesLocation = {"/view/DarkTheme.css", "/view/LightTheme.css"};

    public static final String MESSAGE_THEME_CONSTRAINTS = "Please specify one of the following themes:\n"
            + Arrays.stream(themes).collect(Collectors.joining(", "));

    public final String selectedThemePath;

    /**
     * Constructs a {@code Theme}.
     *
     * @param themeName A valid theme name.
     */
    public Theme(String themeName) {
        requireNonNull(themeName);
        checkArgument(isValidThemeName(themeName.toLowerCase()), MESSAGE_THEME_CONSTRAINTS);
        selectedThemePath = themesLocation[Arrays.asList(themes).indexOf(themeName)];
    }

    /**
     * Returns true if a given string is a valid theme name.
     */
    public static boolean isValidThemeName(String themeName) {
        boolean isValid = Arrays.stream(themes).anyMatch(themeName::equals);
        return isValid;
    }

    public String getThemeName() {
        return themes[Arrays.asList(themesLocation).indexOf(selectedThemePath)];
    }

    public String getThemePath() {
        return selectedThemePath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Theme // instanceof handles nulls
                && this.selectedThemePath.equals(((Theme) other).selectedThemePath)); // state check
    }

    @Override
    public int hashCode() {
        return selectedThemePath.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + selectedThemePath + ']';
    }
}
