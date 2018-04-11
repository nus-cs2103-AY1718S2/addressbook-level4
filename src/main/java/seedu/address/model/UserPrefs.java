package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String addressBookFilePath = "data/addressbook.xml";
    private String addressBookName = "reInsurance";
    private String exportPortfolioFilePath = "data/portfolio.csv";
    private String calendarIdFilePath = "data/calendarId.txt";
    private String passwordFilePath = "data/password.txt";

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

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    public void setAddressBookFilePath(String addressBookFilePath) {
        this.addressBookFilePath = addressBookFilePath;
    }

    public String getAddressBookName() {
        return addressBookName;
    }

    public void setAddressBookName(String addressBookName) {
        this.addressBookName = addressBookName;
    }

    public String getExportPortfolioFilePath() {
        return exportPortfolioFilePath;
    }

    public void setExportPortfolioFilePath(String exportPortfolioFilePath) {
        this.exportPortfolioFilePath = exportPortfolioFilePath;
    }

    public String getCalendarIdFilePath() {
        return calendarIdFilePath;
    }

    public void setCalendarIdFilePath(String calendarIdFilePath) {
        this.calendarIdFilePath = calendarIdFilePath;
    }
 
    public String getPasswordFilePath() {
        return passwordFilePath;
    }

    public void setPasswordFilePath(String passwordFilePath) {
        this.passwordFilePath = passwordFilePath;
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
                && Objects.equals(addressBookName, o.addressBookName)
                && Objects.equals(calendarIdFilePath, o.calendarIdFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath, addressBookName, calendarIdFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + addressBookFilePath);
        sb.append("\nAddressBook name : " + addressBookName);
        sb.append("\nGoogle Calendar ID file location : " + calendarIdFilePath);
        return sb.toString();
    }
}
