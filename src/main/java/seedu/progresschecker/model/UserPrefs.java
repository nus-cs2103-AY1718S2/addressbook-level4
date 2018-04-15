package seedu.progresschecker.model;

import java.util.Objects;

import seedu.progresschecker.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String progressCheckerFilePath = "data/progresschecker.xml";
    private String progressCheckerName = "MyProgressChecker";

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

    public String getProgressCheckerFilePath() {
        return progressCheckerFilePath;
    }

    public void setProgressCheckerFilePath(String progressCheckerFilePath) {
        this.progressCheckerFilePath = progressCheckerFilePath;
    }

    public String getProgressCheckerName() {
        return progressCheckerName;
    }

    public void setProgressCheckerName(String progressCheckerName) {
        this.progressCheckerName = progressCheckerName;
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
                && Objects.equals(progressCheckerFilePath, o.progressCheckerFilePath)
                && Objects.equals(progressCheckerName, o.progressCheckerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, progressCheckerFilePath, progressCheckerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + progressCheckerFilePath);
        sb.append("\nProgressChecker name : " + progressCheckerName);
        return sb.toString();
    }

}
