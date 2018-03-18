package seedu.address.model.student.dashboard;

/**
 * Represents a task in a milestone
 * Guarantees: details are present and not null, immutable.
 */
public class Task {

    private final String name;
    private final String description;
    private final boolean isCompleted;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        isCompleted = false;
    }

    public Task(String name, String description, boolean isCompleted) {
        this.name = name;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Task // instanceof checks null
                && this.name.equals(((Task) obj).getName())
                && this.description.equals(((Task) obj).getDescription())
                && this.isCompleted == ((Task) obj).isCompleted());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name)
                .append(" ||")
                .append("Desc: ")
                .append(description)
                .append(" ||")
                .append("Completed: ")
                .append(isCompleted);
        return builder.toString();
    }
}
