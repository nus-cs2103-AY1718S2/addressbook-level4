package seedu.address.model.student.dashboard;

/**
 * Represents a task in a milestone
 * Guarantees: details are present and not null, immutable.
 */
public class Task {

    private final String name;
    private final String desc;
    private final boolean isCompleted;

    public Task(String name, String desc) {
        this.name = name;
        this.desc = desc;
        isCompleted = false;
    }

    public Task(String name, String desc, boolean isCompleted) {
        this.name = name;
        this.desc = desc;
        this.isCompleted = isCompleted;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Task // instanceof checks null
                && this.name.equals(((Task) obj).getName())
                && this.desc.equals(((Task) obj).getDesc())
                && this.isCompleted == ((Task) obj).isCompleted());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name)
                .append(" ||")
                .append("Desc: ")
                .append(desc)
                .append(" ||")
                .append("Completed: ")
                .append(isCompleted);
        return builder.toString();
    }
}
