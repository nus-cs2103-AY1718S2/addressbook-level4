package seedu.progresschecker.model.task;

//@@author EdwardKSG
/**
 * Represents a simplified task in the ProgressChecker.
 * It is called "simplified" because it extracts only the attributes we want to use from the Google Tasks
 */
public class SimplifiedTask {
    public final String title;
    public final String notes;
    public final String due;

    public SimplifiedTask (String title, String notes, String due) {
        this.title = title;
        this.notes = notes;
        this.due = due;
    }

    public String getTitle () {
        return this.title;
    }

    public String getNotes () {
        return this.notes;
    }

    public String getDue () {
        return this.due;
    }

}
