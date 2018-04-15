package seedu.address.logic.commands.util;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.student.Student;
import seedu.address.model.student.dashboard.UniqueMilestoneList;
import seedu.address.model.student.dashboard.UniqueTaskList;

//@@author yapni
/**
 * Utility methods to check if the indexes provided are valid
 */
public class CheckIndexesUtil {

    /**
     * Returns true if the specified indexes are valid, else returns false.
     */
    public static boolean areIndexesValid(List<Student> studentList, Index studentIndex, Index milestoneIndex,
                                                 Index taskIndex) {
        requireAllNonNull(studentList, studentIndex, milestoneIndex, taskIndex);

        if (!isStudentIndexValid(studentList, studentIndex)) {
            return false;
        }

        UniqueMilestoneList milestoneList = studentList.get(studentIndex.getZeroBased())
                .getDashboard().getMilestoneList();
        if (!isMilestoneIndexValid(milestoneList, milestoneIndex)) {
            return false;
        }

        UniqueTaskList taskList = milestoneList.get(milestoneIndex).getTaskList();
        if (!isTaskIndexValid(taskList, taskIndex)) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the specified indexes are valid, else returns false.
     */
    public static boolean areIndexesValid(List<Student> studentList, Index studentIndex, Index milestoneIndex) {
        requireAllNonNull(studentList, studentIndex, milestoneIndex);

        if (!isStudentIndexValid(studentList, studentIndex)) {
            return false;
        }

        UniqueMilestoneList milestoneList = studentList.get(studentIndex.getZeroBased())
                .getDashboard().getMilestoneList();
        if (!isMilestoneIndexValid(milestoneList, milestoneIndex)) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the student index is valid, else returns false.
     */
    public static boolean isStudentIndexValid(List<Student> studentList, Index studentIndex) {
        requireAllNonNull(studentList, studentIndex);

        return studentIndex.getZeroBased() >= 0 && studentIndex.getZeroBased() < studentList.size();
    }

    /**
     * Returns true if the milestone index is valid, else returns false.
     */
    public static boolean isMilestoneIndexValid(UniqueMilestoneList milestoneList, Index milestoneIndex) {
        requireAllNonNull(milestoneList, milestoneIndex);

        return milestoneIndex.getZeroBased() >= 0 && milestoneIndex.getZeroBased() < milestoneList.size();
    }

    /**
     * Returns true if the task index is valid, else returns false.
     */
    public static boolean isTaskIndexValid(UniqueTaskList taskList, Index taskIndex) {
        requireAllNonNull(taskList, taskIndex);

        return taskIndex.getZeroBased() >= 0 && taskIndex.getZeroBased() < taskList.size();
    }
}
