package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.subject.Subject;

/**
 *
 */
public class XmlAdaptedSubject {

    @XmlValue
    private String subjectName;
    @XmlValue
    private String subjectGrade;

    /**
     * Constructs an XmlAdaptedSubject.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSubject() {}

    /**
     * Constructs a {@code XmlAdaptedSubject} with the given {@code subject}.
     */
    public XmlAdaptedSubject(String subject) {
        String[] splitSubjectStr = subject.trim().split("\\s+");
        String subjectName = splitSubjectStr[0];
        String subjectGrade = splitSubjectStr[1];
        this.subjectName = subjectName;
        this.subjectGrade = subjectGrade;
    }

    /**
     * Converts a given Subject into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSubject(Subject source) {
        subjectName = source.subjectName;
        subjectGrade = source.subjectGrade;
    }

    /**
     * Converts this jaxb-friendly adapted subject object into the model's subject object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Subject toModelType() throws IllegalValueException {
        if (!Subject.isValidSubjectName(subjectName)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_NAME_CONSTRAINTS);
        }
        if (!Subject.isValidSubjectGrade(subjectGrade)) {
            throw new IllegalValueException(Subject.MESSAGE_SUBJECT_GRADE_CONSTRAINTS);
        }
        return new Subject(subjectName, subjectGrade);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedSubject)) {
            return false;
        }

        return (subjectName.equals(((XmlAdaptedSubject) other).subjectName)
                && subjectGrade.equals(((XmlAdaptedSubject) other).subjectGrade));
    }
}
