package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Detail;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.TimeTableLink;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;

/**
 * JAXB-friendly version of the To-do.
 */
public class XmlAdaptedToDo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ToDo's %s field is missing!";

    @XmlElement(required = true)
    private String content;

    /**
     * Constructs an XmlAdaptedToDo.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedToDo() {}

    /**
     * Constructs an {@code XmlAdaptedToDo} with the given to-do details.
     */
    public XmlAdaptedToDo(String content) {
        this.content = content;
    }

    /**
     * Converts a given To-do into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedToDo
     */
    public XmlAdaptedToDo(ToDo source) {
        content = source.getContent().value;
    }

    /**
     * Converts this jaxb-friendly adapted to-do object into the model's To-do object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted to-do
     */
    public ToDo toModelType() throws IllegalValueException {
        if (this.content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(this.content)) {
            throw new IllegalValueException(Content.MESSAGE_CONTENT_CONSTRAINTS);
        }
        final Content content = new Content(this.content);

        return new ToDo(content);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedToDo)) {
            return false;
        }

        XmlAdaptedToDo otherToDo = (XmlAdaptedToDo) other;
        return Objects.equals(content, otherToDo.content);
    }
}
