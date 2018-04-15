package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String moviePlannerFilePath = "data/movieplanner.xml";
    private String encryptedMoviePlannerFilePath = "data/encryptedmovieplanner.xml";
    private String moviePlannerName = "MyMoviePlanner";

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

    public String getMoviePlannerFilePath() {
        return moviePlannerFilePath;
    }

    public void setMoviePlannerFilePath(String moviePlannerFilePath) {
        this.moviePlannerFilePath = moviePlannerFilePath;
    }

    public String getEncryptedMoviePlannerFilePath() {
        return encryptedMoviePlannerFilePath;
    }

    public void setEncryptedMoviePlannerFilePath(String encryptedMoviePlannerFilePath) {
        this.encryptedMoviePlannerFilePath = encryptedMoviePlannerFilePath;
    }

    public String getMoviePlannerName() {
        return moviePlannerName;
    }

    public void setMoviePlannerName(String moviePlannerName) {
        this.moviePlannerName = moviePlannerName;
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
                && Objects.equals(moviePlannerFilePath, o.moviePlannerFilePath)
                && Objects.equals(encryptedMoviePlannerFilePath, o.encryptedMoviePlannerFilePath)
                && Objects.equals(moviePlannerName, o.moviePlannerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, moviePlannerFilePath, encryptedMoviePlannerFilePath, moviePlannerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + moviePlannerFilePath);
        sb.append("\nMoviePlanner name : " + moviePlannerName);
        return sb.toString();
    }

}
