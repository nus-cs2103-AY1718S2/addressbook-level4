package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.todo.ToDo;
import seedu.address.ui.ToDoCard;

/**
 * Provides a handle for {@code ToDoListPanel} containing the list of {@code ToDoCard}.
 */
public class ToDoListPanelHandle extends NodeHandle<ListView<ToDoCard>> {
    public static final String TODO_LIST_VIEW_ID = "#todoListView";

    private Optional<ToDoCard> lastRememberedSelectedToDoCard;

    public ToDoListPanelHandle(ListView<ToDoCard> toDoListPanelNode) {
        super(toDoListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ToDoCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ToDoCardHandle getHandleToSelectedCard() {
        List<ToDoCard> toDoList = getRootNode().getSelectionModel().getSelectedItems();

        if (toDoList.size() != 1) {
            throw new AssertionError("ToDo list size expected 1.");
        }

        return new ToDoCardHandle(toDoList.get(0).getRoot());
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
        List<ToDoCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the to-do.
     */
    public void navigateToCard(ToDo toDo) {
        List<ToDoCard> cards = getRootNode().getItems();
        Optional<ToDoCard> matchingCard = cards.stream().filter(card -> card.todo.equals(toDo)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("ToDo does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the to-do card handle of a to-do associated with the {@code index} in the list.
     */
    public ToDoCardHandle getToDoCardHandle(int index) {
        return getToDoCardHandle(getRootNode().getItems().get(index).todo);
    }

    /**
     * Returns the {@code ToDoCardHandle} of the specified {@code toDo} in the list.
     */
    public ToDoCardHandle getToDoCardHandle(ToDo toDo) {
        Optional<ToDoCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.todo.equals(toDo))
                .map(card -> new ToDoCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("ToDo does not exist."));
    }

    /**
     * Selects the {@code ToDoCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ToDoCard} in the list.
     */
    public void rememberSelectedToDoCard() {
        List<ToDoCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedToDoCard = Optional.empty();
        } else {
            lastRememberedSelectedToDoCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ToDoCard} is different from the value remembered by the most recent
     * {@code rememberSelectedToDoCard()} call.
     */
    public boolean isSelectedToDoCardChanged() {
        List<ToDoCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedToDoCard.isPresent();
        } else {
            return !lastRememberedSelectedToDoCard.isPresent()
                    || !lastRememberedSelectedToDoCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
