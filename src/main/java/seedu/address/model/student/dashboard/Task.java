package seedu.address.model.student.dashboard;

/**
 * Represents a task in a milestone
 * Guarantees: details are present and not null, immutable.
 */
public class Task {

    private String name;
    private String desc;
    private boolean completed;

    public Task(String name, String desc) {
        this.name = name;
        this.desc = desc;
        completed = false;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Task // instanceof checks null
                && this.name.equals(((Task) obj).getName())
                && this.desc.equals(((Task) obj).getDesc())
                && this.completed == ((Task) obj).isCompleted());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ")
                .append(name)
                .append("Desc: ")
                .append(desc)
                .append("Completed: ")
                .append(completed);
        return builder.toString();
    }
}
