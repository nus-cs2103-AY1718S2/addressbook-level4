package seedu.address.ui;

import javafx.scene.image.Image;
import seedu.address.commons.util.AppUtil;

/**
 * Utility class to get icons for popular coins
 */
public class IconUtil {
    //@@author laichengyu
    public static final String ICON_BASE_FILE_PATH = "/images/coin_icons/";
    public static String getCoinFilePath(String code) {
        return ICON_BASE_FILE_PATH + code + ".png";
    }

    public static Image getCoinIcon(String coinCode) {
        try {
            return AppUtil.getImage(getCoinFilePath(coinCode));
        } catch (NullPointerException e) {
            return AppUtil.getImage(getCoinFilePath("empty"));
        }
    }
    //@@author

}
