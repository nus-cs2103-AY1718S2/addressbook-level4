package seedu.address.logic.commands;

import static seedu.address.model.student.miscellaneousinfo.ProfilePicturePath.DEFAULT_PROFILE_PICTURE;

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
import seedu.address.model.student.dashboard.Task;
import seedu.address.model.student.dashboard.exceptions.DuplicateMilestoneException;
import seedu.address.model.student.dashboard.exceptions.DuplicateTaskException;
import seedu.address.model.student.dashboard.exceptions.MilestoneNotFoundException;
import seedu.address.model.student.dashboard.exceptions.TaskNotFoundException;
import seedu.address.model.student.miscellaneousinfo.Allergies;
import seedu.address.model.student.miscellaneousinfo.MiscellaneousInfo;
import seedu.address.model.student.miscellaneousinfo.NextOfKinName;
import seedu.address.model.student.miscellaneousinfo.NextOfKinPhone;
import seedu.address.model.student.miscellaneousinfo.ProfilePicturePath;
import seedu.address.model.student.miscellaneousinfo.Remarks;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Student objects.
 */
public class StudentBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_PROGRAMMING_LANGUAGE = "Java";
    public static final String DEFAULT_TAGS = "friends";
    public static final String DEFAULT_FAVOURITE = "false";
    public static final String DEFAULT_PROFILE_PICTURE_URL = DEFAULT_PROFILE_PICTURE;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ProgrammingLanguage programmingLanguage;
    private Set<Tag> tags;
    private Favourite favourite;
    private ProfilePicturePath profilePicturePath;
    private Dashboard dashboard;
    private UniqueKey uniqueKey;
    private MiscellaneousInfo miscellaneousInfo;

    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        programmingLanguage = new ProgrammingLanguage(DEFAULT_PROGRAMMING_LANGUAGE);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        favourite = new Favourite(DEFAULT_FAVOURITE);
        profilePicturePath = new ProfilePicturePath(DEFAULT_PROFILE_PICTURE_URL);
        dashboard = new Dashboard();
        uniqueKey = UniqueKey.generateRandomKey();
        miscellaneousInfo = new MiscellaneousInfo();
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        email = studentToCopy.getEmail();
        address = studentToCopy.getAddress();
        programmingLanguage = studentToCopy.getProgrammingLanguage();
        tags = new HashSet<>(studentToCopy.getTags());
        favourite = studentToCopy.getFavourite();
        profilePicturePath = studentToCopy.getProfilePicturePath();
        dashboard = studentToCopy.getDashboard();
        uniqueKey = studentToCopy.getUniqueKey();
        miscellaneousInfo = studentToCopy.getMiscellaneousInfo();
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

    //@@author samuelloh
    /**
     * Sets the {@code programminglanguage} of the {@code Student} that we are building.
     */
    public StudentBuilder withProgrammingLanguage(String progLang) {
        this.programmingLanguage = new ProgrammingLanguage(progLang);
        return this;
    }

    /**

     * Sets the {@code profilePicturePath} of the {@code Student} that we are building.
     */
    public StudentBuilder withProfilePictureUrl(String url) {
        this.profilePicturePath = new ProfilePicturePath(url);
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withAllergies(String allergies) {
        this.miscellaneousInfo = new MiscellaneousInfo(new Allergies(allergies), miscellaneousInfo.getNextOfKinName(),
                miscellaneousInfo.getNextOfKinPhone(), miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinName(String nextOfKinName) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                new NextOfKinName(nextOfKinName), miscellaneousInfo.getNextOfKinPhone(),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withNextOfKinPhone(String nextOfKinPhone) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), new NextOfKinPhone(nextOfKinPhone),
                miscellaneousInfo.getRemarks());
        return this;
    }

    /**
     * Sets the {@code Allergies} of the student we are building
     */
    public StudentBuilder withRemarks(String remarks) {
        this.miscellaneousInfo = new MiscellaneousInfo(miscellaneousInfo.getAllergies(),
                miscellaneousInfo.getNextOfKinName(), miscellaneousInfo.getNextOfKinPhone(), new Remarks(remarks));
        return this;
    }
    //@@author

    /**
     * Creates and returns the Student object with the current attributes.
     */
    public Student build() {
        return new Student(uniqueKey, name, phone, email, address, programmingLanguage, tags, favourite, dashboard,
                profilePicturePath, miscellaneousInfo);
    }

    //@@author yapni
    /**
     * Sets the {@code dashboard} of the {@code Student} that we are building.
     */
    public StudentBuilder withDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
        return this;
    }

    /**
     * Adds a new {@code Milestone} to the {@code Dashboard} of the {@code Student} that we are building.
     *
     * @throws DuplicateMilestoneException if the new milestone is a duplicate of an existing milestone
     */
    public StudentBuilder withNewMilestone(Milestone milestone) throws DuplicateMilestoneException {
        this.dashboard = new DashboardBuilder(this.dashboard).withNewMilestone(milestone).build();
        return this;
    }

    /**
     * Removes the {@code Milestone} from the {@code Dashboard} of the {@code Student} that we are building.
     *
     * @throws MilestoneNotFoundException if the specified milestone is not found in the dashboard
     */
    public StudentBuilder withoutMilestone(Milestone milestone) throws MilestoneNotFoundException {
        this.dashboard = new DashboardBuilder(this.dashboard).withoutMilestone(milestone).build();
        return this;
    }

    /**
     * Adds a new {@code Task} to the specified {@code Milestone} in the {@code Dashboard}
     * of the {@code Student} we are building.
     *
     * @throws DuplicateTaskException if the new task is a duplicate of an existing task in the milestone
     */
    public StudentBuilder withNewTask(Index milestoneIndex, Task task) throws DuplicateTaskException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        this.dashboard = new DashboardBuilder(this.dashboard).withNewTask(milestoneIndex, task).build();
        return this;
    }

    /**
     * Removes the {@code Task} from the specified {@code Milestone} in the {@code Dashboard} of the {@code Student}
     * we are building
     *
     * @throws TaskNotFoundException if the specified task is not found in the milestone
     */
    public StudentBuilder withoutTask(Index milestoneIndex, Task task) throws TaskNotFoundException,
            DuplicateMilestoneException, MilestoneNotFoundException {
        this.dashboard = new DashboardBuilder(this.dashboard).withoutTask(milestoneIndex, task).build();
        return this;
    }

    /**
     * Marks a specified {@code Task} from a {@code Milestone} in the {@code Dashboard} of the {@code Student}
     * we are building as completed.
     */
    public StudentBuilder withTaskCompleted(Index milestoneIndex, Index taskIndex) throws DuplicateTaskException,
            TaskNotFoundException, DuplicateMilestoneException, MilestoneNotFoundException {
        this.dashboard = new DashboardBuilder(this.dashboard).withTaskCompleted(milestoneIndex, taskIndex).build();
        return this;
    }
    //@@author
}
