package seedu.address.model;

import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String coinBookFilePath = "data/coinbook.xml";
    private String coinBookName = "MyCoinBook";
    private String ruleBookFilePath = "data/rulebook.xml";
    private String ruleBookName = "MyRuleBook";

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

    public String getCoinBookFilePath() {
        return coinBookFilePath;
    }

    public void setCoinBookFilePath(String coinBookFilePath) {
        this.coinBookFilePath = coinBookFilePath;
    }

    public String getCoinBookName() {
        return coinBookName;
    }

    public void setCoinBookName(String coinBookName) {
        this.coinBookName = coinBookName;
    }

    public String getRuleBookFilePath() {
        return ruleBookFilePath;
    }

    public void setRuleBookFilePath(String ruleBookFilePath) {
        this.ruleBookFilePath = ruleBookFilePath;
    }

    public String getRuleBookName() {
        return ruleBookName;
    }

    public void setRuleBookName(String ruleBookName) {
        this.ruleBookName = ruleBookName;
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
                && Objects.equals(coinBookFilePath, o.coinBookFilePath)
                && Objects.equals(coinBookName, o.coinBookName)
                && Objects.equals(ruleBookFilePath, o.ruleBookFilePath)
                && Objects.equals(ruleBookName, o.ruleBookName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, coinBookFilePath, coinBookName,
                ruleBookFilePath, ruleBookName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data files : " + coinBookFilePath + ", " + ruleBookFilePath);
        sb.append("\nCoinBook name : " + coinBookName);
        return sb.toString();
    }

}
