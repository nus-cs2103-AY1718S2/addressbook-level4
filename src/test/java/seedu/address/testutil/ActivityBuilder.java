package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.activity.*;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Activity objects.
 */
public class ActivityBuilder {

    public static final String DEFAULT_NAME = "CIP";
    public static final String DEFAULT_DATETIME = "04/04/2018 08:10";
    public static final String DEFAULT_REMARK = "123, Jurong West Ave 6";
    public static final String DEFAULT_TAGS = "optional";

    private Name name;
    private DateTime dateTime;
    private Remark remark;
    private Set<Tag> tags;

    public ActivityBuilder() {
        name = new Name(DEFAULT_NAME);
        dateTime = new DateTime(DEFAULT_DATETIME);
        remark = new Remark(DEFAULT_REMARK);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the ActivityBuilder with the data of {@code activityToCopy}.
     */
    public ActivityBuilder(Activity activityToCopy) {
        name = activityToCopy.getName();
        dateTime = activityToCopy.getDateTime();
        remark = activityToCopy.getRemark();
        tags = new HashSet<>(activityToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Activity} that we are building.
     */
    public ActivityBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Activity} that we are building.
     */
    public ActivityBuilder withDateTime(String dateTime) {
        this.dateTime = new DateTime(dateTime);
        return this;
    }


    public Activity build() {
        return new Activity(name, dateTime, remark, tags);
    }

}
