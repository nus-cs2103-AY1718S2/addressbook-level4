package guitests.guihandles;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.ui.MainWindow;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    public static final String TOP_PANE_ID = "#topPane";
    public static final String BOTTOM_LIST_PANE_ID = "#bottomListPane";

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final TitleBarHandle titleBar;
    private final InfoPanelHandle infoPanel;

    private final Stage stage;

    private final AnchorPane topPane;
    private final AnchorPane bottomListPane;

    public MainWindowHandle(Stage stage) {
        super(stage);

        this.stage = stage;
        setWindowDefaultPositionAndSize();

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        titleBar = new TitleBarHandle(getChildNode(TitleBarHandle.TITLE_BAR_ID));
        infoPanel = new InfoPanelHandle(getChildNode(InfoPanelHandle.INFO_ID));

        topPane = getChildNode(TOP_PANE_ID);
        bottomListPane = getChildNode(BOTTOM_LIST_PANE_ID);
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public TitleBarHandle getTitleBar() {
        return titleBar;
    }

    public InfoPanelHandle getInfoPanel() {
        return infoPanel;
    }

    public Point2D getTitleBarPosition() {
        Bounds bounds = topPane.localToScreen(topPane.getBoundsInLocal());
        return new Point2D((bounds.getMinX() + bounds.getMaxX()) / 2, (
                bounds.getMinY() + bounds.getMaxY()) / 2 - bounds.getHeight() / 4);
    }

    public Point2D getResizablePosition() {
        return new Point2D(stage.getWidth() + stage.getX() - MainWindow.WINDOW_CORNER_SIZE + 1,
                stage.getHeight() + stage.getY() - MainWindow.WINDOW_CORNER_SIZE + 1);
    }

    public Point2D getWindowPosition() {
        return new Point2D(stage.getX(), stage.getY());
    }

    public Rectangle2D getWindowBound() {
        return new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight());
    }

    public void setWindowDefaultPositionAndSize() {
        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();
        stage.setWidth(MainWindow.MIN_WINDOW_WIDTH);
        stage.setHeight(MainWindow.MIN_WINDOW_HEIGHT);
        stage.setX(screenBound.getMinX());
        stage.setY(screenBound.getMinY());
    }

    public double getListPaneWidth() {
        return bottomListPane.getWidth();
    }

    public Rectangle2D getSceenBound() {
        return Screen.getPrimary().getVisualBounds();
    }
}
