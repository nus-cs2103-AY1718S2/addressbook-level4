package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.rule.Rule;
import seedu.address.ui.RuleCard;

/**
 * Provides a handle for {@code RuleListPanel} containing the list of {@code RuleCard}.
 */
public class RuleListPanelHandle extends NodeHandle<ListView<RuleCard>> {
    public static final String COIN_LIST_VIEW_ID = "#ruleListView";

    private Optional<RuleCard> lastRememberedSelectedRuleCard;

    public RuleListPanelHandle(ListView<RuleCard> ruleListPanelNode) {
        super(ruleListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code RuleCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public RuleCardHandle getHandleToSelectedCard() {
        List<RuleCard> ruleList = getRootNode().getSelectionModel().getSelectedItems();

        if (ruleList.size() != 1) {
            throw new AssertionError("Rule list size expected 1.");
        }

        return new RuleCardHandle(ruleList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<RuleCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the rule.
     */
    public void navigateToCard(Rule rule) {
        List<RuleCard> cards = getRootNode().getItems();
        Optional<RuleCard> matchingCard = cards.stream().filter(card -> card.rule.equals(rule)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Rule does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the rule card handle of a rule associated with the {@code index} in the list.
     */
    public RuleCardHandle getRuleCardHandle(int index) {
        return getRuleCardHandle(getRootNode().getItems().get(index).rule);
    }

    /**
     * Returns the {@code RuleCardHandle} of the specified {@code rule} in the list.
     */
    public RuleCardHandle getRuleCardHandle(Rule rule) {
        Optional<RuleCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.rule.equals(rule))
                .map(card -> new RuleCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Rule does not exist."));
    }

    /**
     * Selects the {@code RuleCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code RuleCard} in the list.
     */
    public void rememberSelectedRuleCard() {
        List<RuleCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedRuleCard = Optional.empty();
        } else {
            lastRememberedSelectedRuleCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code RuleCard} is different from the value remembered by the most recent
     * {@code rememberSelectedRuleCard()} call.
     */
    public boolean isSelectedRuleCardChanged() {
        List<RuleCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedRuleCard.isPresent();
        } else {
            return !lastRememberedSelectedRuleCard.isPresent()
                    || !lastRememberedSelectedRuleCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
