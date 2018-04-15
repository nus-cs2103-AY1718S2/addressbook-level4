# ewaldhew
###### \java\guitests\guihandles\NotificationsWindowHandle.java
``` java
package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code NotificationsWindow} of the application.
 */
public class NotificationsWindowHandle extends StageHandle {

    public static final String NOTIFICATIONS_WINDOW_TITLE = "Notifications";

    private static final String NOTIFICATIONS_WINDOW_PANEL_ID = "#ruleListPanelPlaceholder";

    public NotificationsWindowHandle(Stage stage) {
        super(stage);
    }

    /**
     * Returns true if the window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(NOTIFICATIONS_WINDOW_TITLE);
    }

}
```
###### \java\seedu\address\logic\parser\NotifyCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

public class NotifyCommandParserTest {
    private NotifyCommandParser parser = new NotifyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        assertParseSuccess(parser, "c/TEST", parser.parse("c/TEST"));
    }
}
```
###### \java\seedu\address\ui\NotificationsWindowTest.java
``` java
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
```
