//package guitests.guihandles;
//
//import javafx.scene.control.ListView;
//import seedu.address.ui.TaskCard;
//
//public class ActivityListPandelHandle {
//    public static final String TASK_LIST_VIEW_ID = "#taskListView";
//
//    private Optional<TaskCard> lastRememberedSelectedTaskCard;
//
//    public ActivityListPanelHandle(ListView<TaskCard> taskListPanelNode) {
//        super(taskListPanelNode);
//    }
//
//    /**
//     * Returns a handle to the selected {@code ActivityCardHandle}.
//     * A maximum of 1 item can be selected at any time.
//     * @throws AssertionError if no card is selected, or more than 1 card is selected.
//     */
//    public ActivityCardHandle getHandleToSelectedCard() {
//        List<ActivityCard> taskList = getRootNode().getSelectionModel().getSelectedItems();
//
//        if (taskList.size() != 1) {
//            throw new AssertionError("Activity list size expected 1.");
//        }
//
//        return new ActivityCardHandle(taskList.get(0).getRoot());
//    }
//
//    /**
//     * Returns the index of the selected card.
//     */
//    public int getSelectedCardIndex() {
//        return getRootNode().getSelectionModel().getSelectedIndex();
//    }
//
//    /**
//     * Returns true if a card is currently selected.
//     */
//    public boolean isAnyCardSelected() {
//        List<ActivityCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();
//
//        if (selectedCardsList.size() > 1) {
//            throw new AssertionError("Card list size expected 0 or 1.");
//        }
//
//        return !selectedCardsList.isEmpty();
//    }
//
//    /**
//     * Navigates the listview to display and select the task.
//     */
//    public void navigateToCard(Activity task) {
//        List<ActivityCard> cards = getRootNode().getItems();
//        Optional<ActivityCard> matchingCard = cards.stream().filter(card -> card.task.equals(task)).findFirst();
//
//        if (!matchingCard.isPresent()) {
//            throw new IllegalArgumentException("Activity does not exist.");
//        }
//
//        guiRobot.interact(() -> {
//            getRootNode().scrollTo(matchingCard.get());
//            getRootNode().getSelectionModel().select(matchingCard.get());
//        });
//        guiRobot.pauseForHuman();
//    }
//
//    /**
//     * Returns the task card handle of a task associated with the {@code index} in the list.
//     */
//    public ActivityCardHandle getActivityCardHandle(int index) {
//        return getActivityCardHandle(getRootNode().getItems().get(index).task);
//    }
//
//    /**
//     * Returns the {@code ActivityCardHandle} of the specified {@code task} in the list.
//     */
//    public ActivityCardHandle getActivityCardHandle(Activity task) {
//        Optional<ActivityCardHandle> handle = getRootNode().getItems().stream()
//                .filter(card -> card.task.equals(task))
//                .map(card -> new ActivityCardHandle(card.getRoot()))
//                .findFirst();
//        return handle.orElseThrow(() -> new IllegalArgumentException("Activity does not exist."));
//    }
//
//    /**
//     * Selects the {@code ActivityCard} at {@code index} in the list.
//     */
//    public void select(int index) {
//        getRootNode().getSelectionModel().select(index);
//    }
//
//    /**
//     * Remembers the selected {@code ActivityCard} in the list.
//     */
//    public void rememberSelectedPersonCard() {
//        List<ActivityCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();
//
//        if (selectedItems.size() == 0) {
//            lastRememberedSelectedPersonCard = Optional.empty();
//        } else {
//            lastRememberedSelectedPersonCard = Optional.of(selectedItems.get(0));
//        }
//    }
//
//    /**
//     * Returns true if the selected {@code ActivityCard} is different from the value remembered by the most recent
//     * {@code rememberSelectedPersonCard()} call.
//     */
//    public boolean isSelectedPersonCardChanged() {
//        List<ActivityCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();
//
//        if (selectedItems.size() == 0) {
//            return lastRememberedSelectedPersonCard.isPresent();
//        } else {
//            return !lastRememberedSelectedPersonCard.isPresent()
//                    || !lastRememberedSelectedPersonCard.get().equals(selectedItems.get(0));
//        }
//    }
//
//    /**
//     * Returns the size of the list.
//     */
//    public int getListSize() {
//        return getRootNode().getItems().size();
//    }
//}
