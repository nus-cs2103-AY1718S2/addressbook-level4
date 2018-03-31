package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.job.Job;
import seedu.address.ui.JobCard;

/**
 * Provides a handle for {@code JobListPanel} containing the list of {@code JobCard}.
 */
public class JobListPanelHandle extends NodeHandle<ListView<JobCard>> {
    public static final String JOB_LIST_VIEW_ID = "#jobListView";

    private Optional<JobCard> lastRememberedSelectedJobCard;

    public JobListPanelHandle(ListView<JobCard> jobListPanelNode) {
        super(jobListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code JobCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public JobCardHandle getHandleToSelectedCard() {
        List<JobCard> jobList = getRootNode().getSelectionModel().getSelectedItems();

        if (jobList.size() != 1) {
            throw new AssertionError("Job list size expected 1.");
        }

        return new JobCardHandle(jobList.get(0).getRoot());
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
        List<JobCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the job.
     */
    public void navigateToCard(Job job) {
        List<JobCard> cards = getRootNode().getItems();
        Optional<JobCard> matchingCard = cards.stream().filter(card -> card.job.equals(job)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Job does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the job card handle of a job associated with the {@code index} in the list.
     */
    public JobCardHandle getJobCardHandle(int index) {
        return getJobCardHandle(getRootNode().getItems().get(index).job);
    }

    /**
     * Returns the {@code JobCardHandle} of the specified {@code job} in the list.
     */
    public JobCardHandle getJobCardHandle(Job job) {
        Optional<JobCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.job.equals(job))
                .map(card -> new JobCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Job does not exist."));
    }

    /**
     * Selects the {@code JobCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code JobCard} in the list.
     */
    public void rememberSelectedJobCard() {
        List<JobCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedJobCard = Optional.empty();
        } else {
            lastRememberedSelectedJobCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code JobCard} is different from the value remembered by the most recent
     * {@code rememberSelectedJobCard()} call.
     */
    public boolean isSelectedJobCardChanged() {
        List<JobCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedJobCard.isPresent();
        } else {
            return !lastRememberedSelectedJobCard.isPresent()
                    || !lastRememberedSelectedJobCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
