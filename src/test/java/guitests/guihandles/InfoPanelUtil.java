package guitests.guihandles;

import guitests.GuiRobot;

/**
 * Helper methods for dealing with {@code InfoPanel}.
 */
public class InfoPanelUtil {
    /**
     * If the {@code infoPanelHandle}'s {@code Panel} is loading, sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilInfoPanelLoaded(InfoPanelHandle infoPanelHandle) {
        new GuiRobot().waitForEvent(infoPanelHandle::isLoaded);
    }
}
