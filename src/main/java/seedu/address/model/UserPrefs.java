package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.theme.Theme;

/**
 * Represents User's preferences.
 */
public class UserPrefs {
    private static final String DEFAULT_THEME = "dark";

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String addressBookName = "MyAddressBook";

    private String calendarManagerFilePath = "data/calendarmanager.xml";
    private String calendarManagerName = "MyCalendarManager";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0, DEFAULT_THEME);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y, Theme.getCurrentTheme());
    }

    public void setGuiSettings(double width, double height, int x, int y, String theme) {
        guiSettings = new GuiSettings(width, height, x, y, theme);
    }

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public String getCalendarManagerFilePath() {
        return calendarManagerFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public void setCalendarManagerFilePath(String calendarManagerFilePath) {
        this.calendarManagerFilePath = calendarManagerFilePath;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public String getCalendarManagerName() {
        return calendarManagerName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public void setCalendarManagerName(String calendarManagerName) {
        this.calendarManagerName = calendarManagerName;
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
                && Objects.equals(addressBookFilePath, o.addressBookFilePath)
                && Objects.equals(addressBookName, o.addressBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nAddressBook name : " + addressBookName);
        return sb.toString();
    }

}
