package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.BrowserWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.BrowserWindowHandle;
import javafx.stage.Stage;

public class BrowserWindowTest extends GuiUnitTest {

    private BrowserWindow browserWindow;
    private BrowserWindowHandle browserWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> browserWindow = new BrowserWindow(BrowserWindow.USERGUIDE_FILE_PATH));
        Stage helpWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(browserWindow.getRoot().getScene()));
        FxToolkit.showStage();
        browserWindowHandle = new BrowserWindowHandle(helpWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = BrowserWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, browserWindowHandle.getLoadedUrl());
    }
}
