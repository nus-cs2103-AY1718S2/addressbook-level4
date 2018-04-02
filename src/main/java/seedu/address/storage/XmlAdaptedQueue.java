package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted version of the PatientQueue.
 */
public class XmlAdaptedQueue {

    @XmlValue
    private int queueNo;

    /**
     * Constructs an XmlAdaptedQueue.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedQueue() {}

    public XmlAdaptedQueue(Integer queueNo) {
        this.queueNo = queueNo;
    }

    /**
     * Converts this jaxb-friendly adapted queue object into integer.
     */
    public int toModelType() {
        return queueNo;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof  XmlAdaptedQueue)) {
            return false;
        }

        return queueNo == ((XmlAdaptedQueue) other).queueNo;
    }
}
