package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.NextOfKin;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String nric;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedSubject> subjects = new ArrayList<>();
    @XmlElement(required = true)
    private String remark;
    @XmlElement
    private String cca;
    @XmlElement
    private String pos;
    @XmlElement
    private String injuriesHistory;
    @XmlElement
    private String nextOfKin;
    @XmlElement
    private String nameOfKin;
    @XmlElement
    private String phone;
    @XmlElement
    private String email;
    @XmlElement
    private String remarkOfKin;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String nric, List<XmlAdaptedTag> tagged, List<XmlAdaptedSubject> subjects,
                            String remark, String cca, String injuriesHistory, String nextOfKin) {
        this.name = name;
        this.nric = nric;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (subjects != null) {
            this.subjects = new ArrayList<>(subjects);
        }
        this.cca = cca;
        this.injuriesHistory = injuriesHistory;
        this.nextOfKin = nextOfKin;
        this.nameOfKin = nameOfKin;
        this.phone = phone;
        this.email = email;
        this.remarkOfKin = remarkOfKin;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        nric = source.getNric().value;
        tagged = new ArrayList<>();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
        subjects = new ArrayList<>();
        for (Subject subject : source.getSubjects()) {
            subjects.add(new XmlAdaptedSubject(subject));
        }
        remark = source.getRemark().value;
        cca = source.getCca().value;
        pos = source.getCca().pos;
        injuriesHistory = source.getInjuriesHistory().value;
        nameOfKin = source.getNextOfKin().fullName;
        phone = source.getNextOfKin().phone;
        email = source.getNextOfKin().email;
        remarkOfKin = source.getNextOfKin().remark;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Subject> personSubjects = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        for (XmlAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
        }

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name name = new Name(this.name);

        if (this.nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(this.nric)) {
            throw new IllegalValueException(Nric.MESSAGE_NRIC_CONSTRAINTS);
        }
        final Nric nric = new Nric(this.nric);

        final Set<Tag> tags = new HashSet<>(personTags);
        final Set<Subject> subjects = new HashSet<>(personSubjects);

        if (this.remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark remark = new Remark(this.remark);

        if (this.cca == null || this.pos == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Cca.class.getSimpleName()));
        }

        final Cca cca = new Cca(this.cca, this.pos);

        if (this.injuriesHistory == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    InjuriesHistory.class.getSimpleName()));
        }

        final InjuriesHistory injuriesHistory = new InjuriesHistory(this.injuriesHistory);

        if (this.nameOfKin == null || this.phone == null || this.email == null || this.remarkOfKin == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NextOfKin.class.getSimpleName()));
        }

        final NextOfKin nextOfKin = new NextOfKin(this.nameOfKin, this.phone, this.email, this.remarkOfKin);

        return new Person(name, nric, tags, subjects, remark, cca, injuriesHistory, nextOfKin);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(nric, otherPerson.nric)
                && tagged.equals(otherPerson.tagged)
                && subjects.equals(otherPerson.subjects)
                && remark.equals(otherPerson.remark)
                && cca.equals(otherPerson.cca);
    }
}
