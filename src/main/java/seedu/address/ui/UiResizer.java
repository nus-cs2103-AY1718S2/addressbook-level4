package seedu.address.ui;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;

/**
 * Ui Resizer, a utility to manage resize event of Stage such as resizable window
 */
public class UiResizer {

    private Stage stage;

    private double lastX;
    private double lastY;
    private double lastWidth;
    private double lastHeight;

    public UiResizer(Stage stage, GuiSettings guiSettings, double minWidth, double minHeight, int cornerSize) {
        this.stage = stage;

        // Set listeners
        ResizeListener resizeListener = new ResizeListener(stage, minWidth, minHeight, cornerSize);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_PRESSED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_DRAGGED, resizeListener);
        stage.getScene().addEventHandler(MouseEvent.MOUSE_RELEASED, resizeListener);

        // Set last value
        lastX = guiSettings.getWindowCoordinates().x;
        lastY = guiSettings.getWindowCoordinates().y;
        lastWidth = guiSettings.getWindowWidth();
        lastHeight = guiSettings.getWindowHeight();
    }

    private Rectangle2D getScreenBound() {
        return Screen.getPrimary().getVisualBounds();
    }

    /**
     * Maximize / Un-maximize the stage, polyfill for native {@link Stage#setMaximized} feature
     */
    public void toggleMaximize() {
        Rectangle2D screenBound = getScreenBound();
        double stageX = stage.getX();
        double stageY = stage.getY();
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        if (stageWidth == screenBound.getWidth() && stageHeight == screenBound.getHeight()) {
            stage.setX(lastX);
            stage.setY(lastY);
            stage.setWidth(lastWidth);
            stage.setHeight(lastHeight);
        } else {
            lastX = stageX;
            lastY = stageY;
            lastWidth = stageWidth;
            lastHeight = stageHeight;
            stage.setX(screenBound.getMinX());
            stage.setY(screenBound.getMinY());
            stage.setWidth(screenBound.getWidth());
            stage.setHeight(screenBound.getHeight());
        }
    }

    /**
     * Manage the resize event during mouse move and drag
     */
    static class ResizeListener implements EventHandler<MouseEvent> {
        private Stage stage;

        private boolean holding = false;
        private int cornerSize;

        // Starting position of resizing
        private double startX = 0;
        private double startY = 0;

        // Min sizes for stage
        private double minWidth;
        private double minHeight;

        public ResizeListener(Stage stage, double minWidth, double minHeight, int borderSize) {
            this.stage = stage;
            this.minWidth = minWidth;
            this.minHeight = minHeight;
            this.cornerSize = borderSize;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
            String eventType = mouseEvent.getEventType().getName();
            Scene scene = stage.getScene();

            double mouseX = mouseEvent.getSceneX();
            double mouseY = mouseEvent.getSceneY();


            switch (eventType) {

            case "MOUSE_MOVED":
                scene.setCursor((isResizePosition(mouseX, mouseY) || holding) ? Cursor.SE_RESIZE : Cursor.DEFAULT);
                break;

            case "MOUSE_RELEASED":
                holding = false;
                scene.setCursor(Cursor.DEFAULT);
                break;

            case "MOUSE_PRESSED":
                // Left click only
                if (MouseButton.PRIMARY.equals(mouseEvent.getButton())) {
                    holding = isResizePosition(mouseX, mouseY);
                    startX = stage.getWidth() - mouseX;
                    startY = stage.getHeight() - mouseY;
                }
                break;

            case "MOUSE_DRAGGED":
                if (holding) {
                    setStageWidth(mouseX + startX);
                    setStageHeight(mouseY + startY);
                }
                break;

            default:

            }
        }

        /**
         * Check if the X and Y coordinate of the mouse are in the range of draggable position
         *
         * @param x coordinate of the {@code MouseEvent}
         * @param y coordinate of the {@code MouseEvent}
         * @return {@code true} if the coordinate is in the range of draggable position, {@code false} otherwise
         */
        private boolean isResizePosition(double x, double y) {
            Scene scene = stage.getScene();
            return (x > scene.getWidth() - cornerSize && y > scene.getHeight() - cornerSize);
        }

        /**
         * Set the width of the stage, with validation to be larger than {@code minWidth}
         *
         * @param width of the stage
         */
        private void setStageWidth(double width) {
            stage.setWidth(Math.max(width, minWidth));
        }

        /**
         * Set the height of the stage, with validation to be larger than {@code minHeight}
         *
         * @param height of the stage
         */
        private void setStageHeight(double height) {
            stage.setHeight(Math.max(height, minHeight));
        }

    }
}
