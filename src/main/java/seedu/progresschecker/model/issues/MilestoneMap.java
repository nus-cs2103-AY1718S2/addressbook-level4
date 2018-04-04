package seedu.progresschecker.model.issues;

import java.util.HashMap;

//@@author adityaa1998
/**
 * Initialises and returns a Hashmap of milestones
 */
public final class MilestoneMap {

    private static HashMap<Milestone, Integer> milestoneMap;

    /* Milestone Mappings */
    private static final Milestone MILESTONE_ONE = new Milestone("v1.1");
    private static final Milestone MILESTONE_TWO = new Milestone("v1.2");
    private static final Milestone MILESTONE_THREE = new Milestone("v1.3");
    private static final Milestone MILESTONE_FOUR = new Milestone("v1.4");
    private static final Milestone MILESTONE_FIVE_RC = new Milestone("v1.5rc");
    private static final Milestone MILESTONE_FIVE = new Milestone("v1.5");

    /**
     * Returns a hashmap of milestones
     */
    public static HashMap<Milestone, Integer> getMilestoneMap() {
        milestoneMap = new HashMap<>();
        createMilestoneHashMap();
        return milestoneMap;
    }

    /**
     * creates a map with the milestone values
     */
    private static void createMilestoneHashMap() {
        //Adding values to the map
        milestoneMap.put(MILESTONE_ONE, 1);
        milestoneMap.put(MILESTONE_TWO, 2);
        milestoneMap.put(MILESTONE_THREE, 3);
        milestoneMap.put(MILESTONE_FOUR, 4);
        milestoneMap.put(MILESTONE_FIVE_RC, 5);
        milestoneMap.put(MILESTONE_FIVE, 6);
    }

}
