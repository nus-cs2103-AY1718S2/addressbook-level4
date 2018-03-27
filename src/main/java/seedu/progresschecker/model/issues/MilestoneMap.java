package seedu.progresschecker.model.issues;

import java.util.HashMap;

/**
 * Initialises and returns a Hashmap of milestones
 */
public final class MilestoneMap {

    private static HashMap<String, Integer> milestoneMap;

    /* Milestone Mappings */
    private static final String MILESTONE_ONE = new String("v1.1");
    private static final String MILESTONE_TWO = new String("v1.2");
    private static final String MILESTONE_THREE = new String("v1.3");
    private static final String MILESTONE_FOUR = new String("v1.4");
    private static final String MILESTONE_FIVE_RC = new String("v1.5rc");
    private static final String MILESTONE_FIVE = new String("v1.5");

    /**
     * Returns a hashmap of milestones
     */
    public static HashMap<String, Integer> getMilestoneMap() {
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
