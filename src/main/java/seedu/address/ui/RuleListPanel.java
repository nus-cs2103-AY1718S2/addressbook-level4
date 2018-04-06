package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.RulePanelSelectionChangedEvent;
import seedu.address.model.rule.Rule;

/**
 * Panel containing the list of rules.
 */
public class RuleListPanel extends UiPart<Region> {
    private static final String FXML = "RuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RuleListPanel.class);

    @FXML
    private ListView<RuleCard> ruleListView;

    public RuleListPanel(ObservableList<Rule> ruleList) {
        super(FXML);
        setConnections(ruleList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Rule> ruleList) {
        ObservableList<RuleCard> mappedList = EasyBind.map(
                ruleList, (rule) -> new RuleCard(rule, ruleList.indexOf(rule) + 1));
        ruleListView.setItems(mappedList);
        ruleListView.setCellFactory(listView -> new RuleListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        ruleListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in rule list panel changed to : '" + newValue + "'");
                        raise(new RulePanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code RuleCard}.
     */
    class RuleListViewCell extends ListCell<RuleCard> {

        @Override
        protected void updateItem(RuleCard rule, boolean empty) {
            super.updateItem(rule, empty);

            if (empty || rule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(rule.getRoot());
            }
        }
    }

}
