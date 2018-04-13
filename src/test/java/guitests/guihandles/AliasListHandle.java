package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//@@author jingyinno
/**
 * A handler for the {@code AliasList} of the UI
 */
public class AliasListHandle extends NodeHandle<TableView> {

    public static final String ALIAS_LIST_ID = "#aliasList";

    public AliasListHandle(TableView AliasListNode) {
        super(AliasListNode);
    }

    public ObservableList<TableColumn> getTables() {
        return getRootNode().getColumns();
    }

    public boolean getFront() {
        return getRootNode().getChildrenUnmodifiable().get(0).equals(this);
    }
}
