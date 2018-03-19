package seedu.recipe.model;

import java.util.Objects;

import seedu.recipe.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String recipeBookFilePath = "data/recipebook.xml";
    private String recipeBookName = "MyRecipeBook";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getRecipeBookFilePath() {
        return recipeBookFilePath;
    }

    public void setRecipeBookFilePath(String recipeBookFilePath) {
        this.recipeBookFilePath = recipeBookFilePath;
    }

    public String getRecipeBookName() {
        return recipeBookName;
    }

    public void setRecipeBookName(String recipeBookName) {
        this.recipeBookName = recipeBookName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(recipeBookFilePath, o.recipeBookFilePath)
                && Objects.equals(recipeBookName, o.recipeBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, recipeBookFilePath, recipeBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + recipeBookFilePath);
        sb.append("\nRecipeBook name : " + recipeBookName);
        return sb.toString();
    }

}
