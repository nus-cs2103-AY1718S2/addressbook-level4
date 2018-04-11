package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Cca;
import seedu.address.model.person.InjuriesHistory;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.score.Score;
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
    @XmlElement
    private List<XmlAdaptedScore> scores = new ArrayList<>();
    @XmlElement(required = true)
    private String remark;
    @XmlElement
    private String cca;
    @XmlElement
    private String injuriesHistory;

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String nric, List<XmlAdaptedTag> tagged, List<XmlAdaptedSubject> subjects,
                            List<XmlAdaptedScore> scores, String remark, String cca, String injuriesHistory) {
        this.name = name;
        this.nric = nric;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        if (subjects != null) {
            this.subjects = new ArrayList<>(subjects);
        }
        if (scores != null) {
            this.scores = new ArrayList<>(scores);
        }
        this.cca = cca;
        this.injuriesHistory = injuriesHistory;
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
        scores = new ArrayList<>();
        for (Score score : source.getScores()) {
            scores.add(new XmlAdaptedScore(score));
        }
        remark = source.getRemark().value;
        cca = source.getCca().value;
        injuriesHistory = source.getInjuriesHistory().value;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        final List<Subject> personSubjects = new ArrayList<>();
        final List<Score> personScores = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }
        for (XmlAdaptedSubject subject : subjects) {
            personSubjects.add(subject.toModelType());
        }
        for (XmlAdaptedScore score : scores) {
            personScores.add(score.toModelType());
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
        final Set<Score> scores = new HashSet<>(personScores);

        if (this.remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final Remark remark = new Remark(this.remark);

        if (this.cca == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        final Cca cca = new Cca(this.cca);

        if (this.injuriesHistory == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        final InjuriesHistory injuriesHistory = new InjuriesHistory(this.injuriesHistory);

        return new Person(name, nric, tags, subjects, Collections.emptySet(), remark, cca, injuriesHistory);
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
