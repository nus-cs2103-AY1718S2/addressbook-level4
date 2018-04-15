//@@author ewaldhew
package seedu.address.ui;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.NotificationsWindowHandle;
import javafx.application.Platform;
import javafx.stage.Stage;

public class NotificationsWindowTest extends GuiUnitTest {

    private NotificationsWindow notificationsWindow;
    private NotificationsWindowHandle notificationsWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> notificationsWindow =
                new NotificationsWindow(new Stage(), getTypicalRuleBook().getRuleList()));
        Stage windowStage = FxToolkit.setupStage((stage) -> stage.setScene(notificationsWindow.getRoot().getScene()));
        FxToolkit.showStage();
        notificationsWindowHandle = new NotificationsWindowHandle(windowStage);
    }

    @Test
    public void display() {
        Platform.runLater(() -> {
            notificationsWindow.show();
            assertTrue(NotificationsWindowHandle.isWindowPresent());
        });
    }

}
