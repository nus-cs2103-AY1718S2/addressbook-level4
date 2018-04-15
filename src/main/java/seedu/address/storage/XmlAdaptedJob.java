package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.job.Job;
import seedu.address.model.job.JobTitle;
import seedu.address.model.job.Location;
import seedu.address.model.skill.Skill;
import seedu.address.model.tag.Tag;

//@@author ChengSashankh
/**
 * JAXB-friendly version of the Job.
 */
public class XmlAdaptedJob {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job's %s field is missing!";

    @XmlElement(required = true)
    private String jobTitle;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String skills;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedJob.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedJob() {}

    /**
     * Constructs an {@code XmlAdaptedJob} with the given job details.
     */
    public XmlAdaptedJob(String jobTitle, String location, String skills, List<XmlAdaptedTag> tagged) {
        this.jobTitle = jobTitle;
        this.location = location;
        this.skills = skills;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Job into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedJob(Job source) {
        jobTitle = source.getJobTitle().fullTitle;
        location = source.getLocation().value;
        skills = source.getSkills().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    /**
     * Converts this jaxb-friendly adapted job object into the model's {@code Job} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted job
     */
    public Job toModelType() throws IllegalValueException {
        final List<Tag> jobTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            jobTags.add(tag.toModelType());
        }

        if (this.jobTitle == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, JobTitle.class.getSimpleName()));
        }
        if (!JobTitle.isValidTitle(this.jobTitle)) {
            throw new IllegalValueException(JobTitle.MESSAGE_TITLE_CONSTRAINTS);
        }
        final JobTitle jobTitle = new JobTitle(this.jobTitle);

        if (this.location == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(this.location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location location = new Location(this.location);

        if (this.skills == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Skill.class.getSimpleName()));
        }
        if (!Skill.isValidSkill(this.skills)) {
            throw new IllegalValueException(Skill.MESSAGE_SKILL_CONSTRAINTS);
        }
        final Skill skill = new Skill(this.skills);

        final Set<Tag> tags = new HashSet<>(jobTags);
        return new Job(jobTitle, location, skill, tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedJob)) {
            return false;
        }

        XmlAdaptedJob otherJob = (XmlAdaptedJob) other;
        return Objects.equals(jobTitle, otherJob.jobTitle)
                && Objects.equals(location, otherJob.location)
                && Objects.equals(skills, otherJob.skills)
                && tagged.equals(otherJob.tagged);
    }
}

//@@author
