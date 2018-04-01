package seedu.address.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The index of the student whose info is being required to be displayed at the moment.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RequiredStudentIndex {
    private int requiredStudentIndex;

    /**
     * Constructs a RequiredStudentIndex.
     * This is the no-arg constructor that is required by JAXB.
     */
    public RequiredStudentIndex() {}

    public RequiredStudentIndex(int requiredStudentIndex) {
        this.requiredStudentIndex = requiredStudentIndex;
    }

    public int getRequiredStudentIndex() {
        return requiredStudentIndex;
    }
}
