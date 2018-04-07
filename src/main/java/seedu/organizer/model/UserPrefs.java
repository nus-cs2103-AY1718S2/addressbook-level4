package seedu.organizer.model;

import java.util.Objects;

import seedu.organizer.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String organizerFilePath = "data/organizer.xml";
    private String organizerName = "MyOrganizer";

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

    public String getOrganizerFilePath() {
        return organizerFilePath;
    }

    public void setOrganizerFilePath(String organizerFilePath) {
        this.organizerFilePath = organizerFilePath;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
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
                && Objects.equals(organizerFilePath, o.organizerFilePath)
                && Objects.equals(organizerName, o.organizerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, organizerFilePath, organizerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + organizerFilePath);
        sb.append("\nOrganizer name : " + organizerName);
        return sb.toString();
    }

}
