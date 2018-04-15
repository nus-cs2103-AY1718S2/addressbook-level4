package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.job.Job;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

//@@author ChengSashankh
/**
 * A utility class to help with building Job objects.
 */
public class JobBuilder {

    public static final String DEFAULT_JOBTITLE = "Software Engineer";
    public static final String DEFAULT_LOCATION = "Geylang";
    public static final String DEFAULT_SKILL = "Java, C, JavaScript";
    public static final String DEFAULT_TAGS = "FreshGrad";

    private JobTitle jobTitle;
    private Skill skill;
    private Location location;
    private Set<Tag> tags;

    public JobBuilder() {
        jobTitle = new JobTitle(DEFAULT_JOBTITLE);
        location = new Location(DEFAULT_LOCATION);
        skill = new Skill(DEFAULT_SKILL);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
    }

    /**
     * Initializes the JobBuilder with the data of {@code jobToCopy}.
     */
    public JobBuilder(Job jobToCopy) {
        jobTitle = jobToCopy.getJobTitle();
        location = jobToCopy.getLocation();
        skill = jobToCopy.getSkills();
        tags = new HashSet<>(jobToCopy.getTags());
    }

    /**
     * Sets the {@code JobTitle} of the {@code Job} that we are building.
     */
    public JobBuilder withJobTitle(String title) {
        this.jobTitle = new JobTitle(title);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Job} that we are building.
     */
    public JobBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Job} that we are building.
     */
    public JobBuilder withLocation(String title) {
        this.location = new Location(title);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that
     * we are building.
     */
    public JobBuilder withSkill(String skill) {
        this.skill = new Skill(skill);
        return this;
    }
    public Job build() {
        return new Job(jobTitle, location, skill, tags);
    }

}
//@@author
