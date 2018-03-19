package seedu.address.ui.testutil;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import javafx.scene.Parent;
import javafx.scene.Scene;
import seedu.address.commons.core.Theme;
import seedu.address.ui.UiPart;

/**
 * Provides an isolated stage to test an individual {@code UiPart}.
 */
public class UiPartRule extends StageRule {

    public void setUiPart(final UiPart<? extends Parent> uiPart) {
        try {
            FxToolkit.setupScene(() -> {
                Scene scene = new Scene(uiPart.getRoot());
                scene.getStylesheets().setAll(Theme.DEFAULT_THEME.getCssFile());
                return scene;
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Timeout should not happen.", te);
        }
    }
}
