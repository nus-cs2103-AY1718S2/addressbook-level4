package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.model.score.Score;
//@@author TeyXinHui
/**
 * JAXB-friendly adapted version of the Score.
 */
public class XmlAdaptedScore {

    @XmlValue
    private String score;

    /**
     * Constructs an XmlAdaptedScore.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedScore() {}

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code score}.
     */
    public XmlAdaptedScore(String score) {
        this.score = score;
    }

    /**
     * Converts a given Score into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedScore(Score source) {
        score = source.score;
    }

    /**
     * Converts this jaxb-friendly adapted score object into the model's Score object.
     */
    public Score toModelType() {
        return new Score(score);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedScore)) {
            return false;
        }

        return score.equals(((XmlAdaptedScore) other).score);
    }
    //@@author
}
