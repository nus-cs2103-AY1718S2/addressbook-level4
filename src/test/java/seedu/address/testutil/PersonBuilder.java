package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "S8535525Z";
    public static final String DEFAULT_TAGS = "friends";
    public static final String[] DEFAULT_SUBJECTS = {"English A1",  "EMath A1", "Hist A1", "HTamil A1",
                                                     "Chem A1", "Phy A1"};
    public static final String DEFAULT_REMARK = " ";
    public static final String DEFAULT_CCA = " ";
    public static final String DEFAULT_INJURIES_HISTORY = " ";
    public static final String DEFAULT_NAME_OF_KIN = "Betty";
    public static final String DEFAULT_CCA_POSITION = "Member";

    private Name name;
    private Nric nric;
    private Set<Tag> tags;
    private Set<Subject> subjects;
    private Remark remark;
    private Cca cca;
    private InjuriesHistory injuriesHistory;
    private NameOfKin nameOfKin;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        tags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        subjects = SampleDataUtil.getSubjectSet(DEFAULT_SUBJECTS);
        remark = new Remark(DEFAULT_REMARK);
        cca = new Cca(DEFAULT_CCA, DEFAULT_CCA_POSITION);
        injuriesHistory = new InjuriesHistory(DEFAULT_INJURIES_HISTORY);
        nameOfKin = new NameOfKin(DEFAULT_NAME_OF_KIN);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        nric = personToCopy.getNric();
        tags = new HashSet<>(personToCopy.getTags());
        subjects = new HashSet<>(personToCopy.getSubjects());
        remark = personToCopy.getRemark();
        cca = personToCopy.getCca();
        injuriesHistory = personToCopy.getInjuriesHistory();
        nameOfKin = personToCopy.getNameOfKin();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Person} that we are building.
     */
    public PersonBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Parses the {@code subjects} into a {@code Set<Subject>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSubjects(String ... subjects) {
        this.subjects = SampleDataUtil.getSubjectSet(subjects);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Sets the {@code Cca} of the {@code Person} that we are building.
     */
    public PersonBuilder withCca(String cca, String pos) {
        this.cca = new Cca(cca, pos);
        return this;
    }

    /**
     * Sets the {@code InjuriesHistory} of the {@code Person} that we are building.
     */
    public PersonBuilder withInjuriesHistory(String injuriesHistory) {
        this.injuriesHistory = new InjuriesHistory(injuriesHistory);
        return this;
    }

    /**
     * Sets the {@code NameOfKin} of the {@code Person} that we are building.
     */
    public PersonBuilder withNameOfKin(String nameOfKin) {
        this.nameOfKin = new NameOfKin(nameOfKin);
        return this;
    }


    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public Person build() {
        return new Person(name, nric, tags, subjects, remark, cca, injuriesHistory, nameOfKin);
    }

}
