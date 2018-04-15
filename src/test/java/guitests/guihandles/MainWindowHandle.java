package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final PersonListPanelHandle personListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final BirthdayListHandle birthdayList;
    private final GoogleMapsDisplayHandle mapPanel;
    private final AliasListHandle aliasList;

    public MainWindowHandle(Stage stage) {
        super(stage);

        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        birthdayList = new BirthdayListHandle(getChildNode(BirthdayListHandle.BIRTHDAYS_LIST_ID));
        mapPanel = new GoogleMapsDisplayHandle(getChildNode(GoogleMapsDisplayHandle.MAP_ID));
        aliasList = new AliasListHandle(getChildNode(AliasListHandle.ALIAS_LIST_ID));
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

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BirthdayListHandle getBirthdayList() {
        return birthdayList;
    }

    public GoogleMapsDisplayHandle getMapPanel() {
        return mapPanel;
    }

    public AliasListHandle getAliasList() {
        return aliasList;
    }
}
