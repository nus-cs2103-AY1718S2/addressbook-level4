package seedu.address.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MilestoneBuilder;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.dashboard.exceptions.TaskNotFoundException;

/**
 * A utility class to help with building Dashboard objects.
 */
public class DashboardBuilder {

    private UniqueMilestoneList milestoneList;

    /**
     * Initializes the DashboardBuilder with the data of {@code dashboardToCopy}.
     */
    public DashboardBuilder(Dashboard dashboardToCopy) {
        requireNonNull(dashboardToCopy);
        milestoneList = dashboardToCopy.getMilestoneList();
    }

    /**
     * Adds a new {@code Milestone} to the {@code Dashboard} we are building.
     *
     * @throws DuplicateMilestoneException if the new milestone is a duplicate of an existing milestone in the dashboard
     */
    public DashboardBuilder withNewMilestone(Milestone milestone) throws DuplicateMilestoneException {
        requireNonNull(milestone);
        milestoneList.add(milestone);

        return this;
    }

    /**
     * Removes the {@code Milestone} from the {@code Dashboard} of the {@code Student} that we are building.
     *
     * @throws MilestoneNotFoundException if the specified milestone is not found in the dashboard
     */
    public DashboardBuilder withoutMilestone(Milestone milestone) throws MilestoneNotFoundException {
        requireNonNull(milestone);
        milestoneList.remove(milestone);

        return this;
    }

    /**
     * Adds a new {@code Task} to the specified {@code Milestone} in the {@code Dashboard} we are building
     *
     * @throws DuplicateTaskException if the new task is a duplicate of an existing task in the milestone
     */
    public DashboardBuilder withNewTask(Index milestoneIndex, Task task) throws DuplicateTaskException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(milestoneIndex, task);

        Milestone targetMilestone = milestoneList.get(milestoneIndex);
        Milestone updatedMilestone = new MilestoneBuilder(targetMilestone).withNewTask(task).build();
        milestoneList.setMilestone(targetMilestone, updatedMilestone);

        return this;
    }

    /**
     * Removes the {@code Task} from the specified {@code Milestone} in the {@code Dashboard} we are building.
     *
     * @throws TaskNotFoundException if the specified task is not found in the milestone
     */
    public DashboardBuilder withoutTask(Index milestoneIndex, Task task) throws DuplicateMilestoneException,
            MilestoneNotFoundException, TaskNotFoundException {
        requireAllNonNull(milestoneIndex, task);

        Milestone targetMilestone = milestoneList.get(milestoneIndex);
        Milestone updatedMilestone = new MilestoneBuilder(targetMilestone).withoutTask(task).build();
        milestoneList.setMilestone(targetMilestone, updatedMilestone);

        return this;
    }

    /**
     * Marks the specified {@code Task} from a {@code Milestone} in the {@code Dashboard} we are building as completed.
     */
    public DashboardBuilder withTaskCompleted(Index milestoneIndex, Index taskIndex) throws DuplicateTaskException,
            TaskNotFoundException, DuplicateMilestoneException, MilestoneNotFoundException {
        requireAllNonNull(milestoneIndex, taskIndex);

        Milestone targetMilestone = milestoneList.get(milestoneIndex);
        Milestone updatedMilestone = new MilestoneBuilder(targetMilestone).withTaskCompleted(taskIndex).build();
        milestoneList.setMilestone(targetMilestone, updatedMilestone);

        return this;
    }

    public Dashboard build() {
        return new Dashboard(milestoneList);
    }
}
