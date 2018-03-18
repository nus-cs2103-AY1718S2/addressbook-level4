package seedu.address.model.student.dashboard;

import java.util.Objects;

/**
 * Represents a homework in a Student's Dashboard
 * Guarantees: details are present and not null, immutable.
 */
public class Homework {

    private final String desc;
    private final Date dueDate;
    private final boolean isCompleted;

    public Homework(String desc, Date dueDate) {
        this.desc = desc;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public Homework(String desc, Date dueDate, boolean isCompleted) {
        this.desc = desc;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    /**
     * Creates and return a deep copy of the {@code toCopy} Homework
     */
    public static Homework copyHomework(Homework toCopy) {
        String copyDesc = new String(toCopy.getDesc());
        Date copyDate = new Date(toCopy.getDueDate().getValue());
        boolean copyIsCompleted = toCopy.isCompleted();

        return new Homework(copyDesc, copyDate, copyIsCompleted);
    }

    public String getDesc() {
        return desc;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj // short circuit if same object
                || (obj instanceof Homework // instanceof handles null
                && this.desc.equals(((Homework) obj).getDesc())
                && this.dueDate.equals(((Homework) obj).getDueDate())
                && this.isCompleted == ((Homework) obj).isCompleted());
    }

    @Override
    public String toString() {
        return "Desc: " + desc + " Due Date: " + dueDate.toString() + "Completed: " + isCompleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(desc, dueDate, isCompleted);
    }
}
