package seedu.progresschecker.model.issues;

import java.util.HashMap;
import java.util.List;

import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHMilestone;
import org.kohsuke.github.GHRepository;

import seedu.progresschecker.logic.commands.exceptions.CommandException;

//@@author adityaa1998
/**
 * Initialises and returns a Hashmap of milestones
 */
public final class MilestoneMap {

    private static HashMap<String, GHMilestone> milestoneMap;

    private GHRepository repository;

    /**
     * Returns a hashmap of milestones
     */
    public HashMap<String, GHMilestone> getMilestoneMap() throws CommandException {
        milestoneMap = new HashMap<>();
        createMilestoneHashMap();
        return milestoneMap;
    }

    /**
     * creates a map with the milestone values
     */
    private void createMilestoneHashMap() {
        List<GHMilestone> milestones = repository.listMilestones(GHIssueState.ALL).asList();
        for (int i = 0; i < milestones.size(); i++) {
            milestoneMap.put(milestones.get(i).getTitle(), milestones.get(i));
        }
    }

    public void setRepository(GHRepository repo) {
        repository = repo;
    }
}
