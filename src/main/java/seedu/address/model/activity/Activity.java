package seedu.address.model.activity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//@@author YuanQQLer
/**
 * Represents a Activity in the desk board.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Activity {


    private final Name name;
    private final DateTime dateTime;
    private final Remark remark;

    private final UniqueTagList tags;
    private final boolean isCompleted;
    /**
     * Every field must be present and not null.
     */
    public Activity(Name name, DateTime dateTime, Remark remark, Set<Tag> tags) {
        requireAllNonNull(name, dateTime, tags);
        this.name = name;
        this.dateTime = dateTime;
        this.remark = remark;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.isCompleted = false;
    }

    public Activity(Name name, DateTime dateTime, Remark remark, Set<Tag> tags, boolean isCompleted) {
        requireAllNonNull(name, dateTime, tags);
        this.name = name;
        this.dateTime = dateTime;
        this.remark = remark;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.isCompleted = isCompleted;
    }

    public Name getName() {
        return name;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public abstract String getActivityType();

    public abstract Activity copy(Set<Tag> tags);

    public boolean isCompleted() {
        return isCompleted;
    }

    public abstract Activity getCompletedCopy();
}
