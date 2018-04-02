package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.programminglanguage.ProgrammingLanguage;
import seedu.address.model.student.MiscellaneousInfo.ProfilePicturePath;
import seedu.address.model.student.dashboard.Dashboard;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Student in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final ProgrammingLanguage programmingLanguage;
    private final Favourite favourite;
    private final Dashboard dashboard;
    private UniqueKey uniqueKey;
    private final UniqueTagList tags;
    private ProfilePicturePath profilePicturePath;

    /**
     * Every field must be present and not null. For when dashboard, favourite and profilePictureURL is not initialised
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = new Favourite(false); // Default value
        this.dashboard = new Dashboard();
        this.profilePicturePath = new ProfilePicturePath(ProfilePicturePath.DEFAULT_PROFILE_PICTURE);
    }

    /**
     * Every field must be present and not null. For when dashboard is not initialised
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags, Favourite fav, ProfilePicturePath profilePicturePath) {
        requireAllNonNull(name, phone, email, address, tags, fav);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = new Dashboard();
        this.profilePicturePath = profilePicturePath;
    }

    /**
     *Every field must be present and not null. For when {@code profilePicturePath} is not initialised.
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags, Favourite fav, Dashboard dashboard) {
        requireAllNonNull(name, phone, email, address, tags, fav);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = dashboard;
        this.profilePicturePath = new ProfilePicturePath(ProfilePicturePath.DEFAULT_PROFILE_PICTURE);
    }



    /**
     * Every field must be present and not null. For when all attributes can be passed in as parameters
     */
    public Student(Name name, Phone phone, Email email, Address address, ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags, Favourite fav, Dashboard dashboard,
                   ProfilePicturePath profilePicturePath) {
        requireAllNonNull(name, phone, email, address, tags, fav);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = dashboard;
        this.profilePicturePath = profilePicturePath;
    }

    /**
     * Constructors for Students with UniqueKey being read in ( read from file )
     */
    /**
     * Every field must be present and not null. For when dashboard and favourite is not initialised
     */
    public Student(UniqueKey uniqueKey, Name name, Phone phone, Email email, Address address,
                   ProgrammingLanguage programmingLanguage, Set<Tag> tags) {
        requireAllNonNull(uniqueKey, name, phone, email, address, tags);
        this.uniqueKey = uniqueKey;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = new Favourite(false); // Default value
        this.dashboard = new Dashboard();
        this.profilePicturePath = getProfilePicturePath();
    }

    /**
     * Every field must be present and not null. For when dashboard is not initialised
     */
    public Student(UniqueKey uniqueKey, Name name, Phone phone, Email email, Address address,
                   ProgrammingLanguage programmingLanguage, Set<Tag> tags,
                   ProfilePicturePath profilePicturePath, Favourite fav) {
        requireAllNonNull(uniqueKey, name, phone, email, address, tags, fav);
        this.uniqueKey = uniqueKey;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = new Dashboard();
        this.profilePicturePath = profilePicturePath;
    }

    /** TODO RequireNonNull for uniquekey
     * Every field must be present and not null. For when all attributes can be passed in as parameters
     */
    public Student(UniqueKey uniqueKey, Name name, Phone phone, Email email,
                   Address address, ProgrammingLanguage programmingLanguage, Set<Tag> tags, Favourite fav,
                   Dashboard dashboard, ProfilePicturePath profilePicturePath) {
        requireAllNonNull(name, phone, email, address, tags, fav);
        this.uniqueKey = uniqueKey;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = dashboard;
        this.profilePicturePath = profilePicturePath;
    }

    /**
     *Every field must be present and not null. For when {@code profilePicturePath} is not initialised.
     */
    public Student(UniqueKey uniqueKey, Name name, Phone phone, Email email, Address address,
                   ProgrammingLanguage programmingLanguage,
                   Set<Tag> tags, Favourite fav, Dashboard dashboard) {
        requireAllNonNull(uniqueKey, name, phone, email, address, tags, fav);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        // protect internal tags from changes in the arg list
        this.programmingLanguage = programmingLanguage;
        this.tags = new UniqueTagList(tags);
        this.favourite = fav;
        this.dashboard = dashboard;
        this.profilePicturePath = new ProfilePicturePath(ProfilePicturePath.DEFAULT_PROFILE_PICTURE);
    }


    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Favourite getFavourite() {
        return favourite;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public UniqueKey getUniqueKey() {
        return uniqueKey;
    }

    public ProfilePicturePath getProfilePicturePath() {
        return profilePicturePath;
    }


    /**
     * Returns true if Student is in favourites, else returns false.
     */
    public boolean isFavourite() {
        return favourite.isFavourite();
    }

    public ProgrammingLanguage getProgrammingLanguage() {
        return programmingLanguage;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public void setKey(UniqueKey uniqueKey) {
        if (this.uniqueKey == null) {
            this.uniqueKey = uniqueKey;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getName().equals(this.getName())
                && otherStudent.getPhone().equals(this.getPhone())
                && otherStudent.getEmail().equals(this.getEmail())
                && otherStudent.getAddress().equals(this.getAddress())
                && otherStudent.getProgrammingLanguage().equals(this.getProgrammingLanguage())
                && otherStudent.getDashboard().equals(this.getDashboard());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, programmingLanguage, dashboard);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Programming Language: ")
                .append(getProgrammingLanguage())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Favourite: ")
                .append(getFavourite())
                .append(" ProfilePicturePath: ")
                .append(getProfilePicturePath())
                .append(" Dashboard: ")
                .append(getDashboard());

        return builder.toString();
    }

    /**
     * TODO Delete later
     * @return Test String
     */
    public String toStrings() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" UniqueKey: ")
                .append(getUniqueKey())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Programming Language: ")
                .append(getProgrammingLanguage())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Favourite: ")
                .append(getFavourite())
                .append(" ProfilePicturePath: ")
                .append(getProfilePicturePath())
                .append(" Dashboard: ")
                .append(getDashboard())
                ;
        return builder.toString();
    }

}
