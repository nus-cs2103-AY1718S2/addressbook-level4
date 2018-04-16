package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.LinkedInWindow.LINKEDIN_URL;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.LinkedInWindowHandle;
import javafx.stage.Stage;

//@@author x3tsunayh

public class LinkedInWindowTest extends GuiUnitTest {

    private LinkedInWindow linkedInWindow;
    private LinkedInWindowHandle linkedInWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> linkedInWindow = new LinkedInWindow());
        Stage linkedInWindowStage = FxToolkit.setupStage((stage)
            -> stage.setScene(linkedInWindow.getRoot().getScene()));
        FxToolkit.showStage();
        linkedInWindowHandle = new LinkedInWindowHandle(linkedInWindowStage);
    }

    @Test
    public void display() {
        URL expectedLinkedInPage = null;
        try {
            expectedLinkedInPage = new URL(LINKEDIN_URL);
        } catch (MalformedURLException e) {
            throw new AssertionError("LinkedIn URL broken");
        }
        assertEquals(expectedLinkedInPage, linkedInWindowHandle.getLoadedUrl());
    }
}
