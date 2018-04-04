package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Favourite;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueKey;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.student.dashboard.Milestone;
import seedu.address.model.student.dashboard.Progress;
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_KEY = "ffff00";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PROGRAMMING_LANGUAGE = "Java";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_FAVOURITE = "false";
    public static final String DEFAULT_PATH = "/src/main/resources/view/images/profile_photo_placeholder";

    private UniqueKey key;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ProgrammingLanguage programmingLanguage;
    private Set<Tag> tags;
    private Favourite favourite;
    private Dashboard dashboard;
    private ProfilePicturePath profilePicturePath;

    public StudentBuilder() {
        key = new UniqueKey(DEFAULT_KEY);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        programmingLanguage = new ProgrammingLanguage(DEFAULT_PROGRAMMING_LANGUAGE);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        favourite = new Favourite(DEFAULT_FAVOURITE);
        dashboard = new Dashboard();
        profilePicturePath = new ProfilePicturePath(ProfilePicturePath.DEFAULT_PROFILE_PICTURE);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        key = studentToCopy.getUniqueKey();
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        programmingLanguage = studentToCopy.getProgrammingLanguage();
        tags = new HashSet<>(studentToCopy.getTags());
        favourite = studentToCopy.getFavourite();
        dashboard = studentToCopy.getDashboard();
        profilePicturePath = studentToCopy.getProfilePicturePath();
    }

    /**
     * Sets the {@code UniqueKey} of the {@code Student} that we are building.
     */
    public StudentBuilder withKey(String key) {
        this.key = new UniqueKey(key);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Student} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Student} that we are building.
     */
    public StudentBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Student} that we are building.
     */
    public StudentBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Student} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Student} that we are building.
     */
    public StudentBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Favourite} of the {@code Student} that we are building.
     */
    public StudentBuilder withFavourite(boolean val) {
        this.favourite = new Favourite(val);
        return this;
    }

    /**
     * Sets the {@code programminglanguage} of the {@code Student} that we are building.
     */
    public StudentBuilder withProgrammingLanguage(String progLang) {
        this.programmingLanguage = new ProgrammingLanguage(progLang);
        return this;
    }

    /**
     * Adds a new {@code milestone} to the {@code dashboard} of the {@code Student} that we are building.
     *
     * @throws DuplicateMilestoneException if the new milestone is a duplicate of an existing milestone
     */
    public StudentBuilder withNewMilestone(Milestone milestone) throws DuplicateMilestoneException {
        dashboard.getMilestoneList().add(milestone);
        return this;
    }

    /**
     * Adds a new {@code task} to the specified milestone in the {@code dashboard}
     * of the {@code Student} we are building.
     *
     * @throws DuplicateTaskException if the new task is a duplicate of an existing task
     */
    public StudentBuilder withNewTask(Index milestoneIndex, Task task) throws DuplicateTaskException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        Milestone milestone = dashboard.getMilestoneList().get(milestoneIndex);

        milestone.getTaskList().add(task);
        Progress updatedProgress = new Progress(milestone.getProgress().getTotalTasks() + 1,
                milestone.getProgress().getNumCompletedTasks());
        Milestone updatedMilestone = new Milestone(milestone.getDueDate(), milestone.getTaskList(),
                updatedProgress, milestone.getDescription());
        dashboard.getMilestoneList().setMilestone(milestone, updatedMilestone);

        return this;
    }

    public Student build() {
        return new Student(key, name, phone, email, address, programmingLanguage, tags,
                favourite, dashboard, profilePicturePath);
    }


    }
