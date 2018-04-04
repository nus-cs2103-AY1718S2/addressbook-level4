//@@author nhatquang3112
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * JAXB-friendly version of the To-do.
 */
public class XmlAdaptedToDo {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "ToDo's %s field is missing!";

    @XmlElement(required = true)
    private String content;

    @XmlElement(required = true)
    private String status;

    /**
     * Constructs an XmlAdaptedToDo.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedToDo() {}

    /**
     * Constructs an {@code XmlAdaptedToDo} with the given details.
     */
    public XmlAdaptedToDo(String content, String status) {
        this.content = content;
        this.status = status;
    }

    /**
     * Constructs an {@code XmlAdaptedToDo} with the given details.
     * Status is "undone" by default
     */
    public XmlAdaptedToDo(String content) {
        this.content = content;
        this.status = "undone";
    }

    /**
     * Converts a given To-do into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedToDo
     */
    public XmlAdaptedToDo(ToDo source) {

        content = source.getContent().value;
        status = source.getStatus().value;
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

        if (this.status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        if (!Status.isValidStatus(this.status)) {
            throw new IllegalValueException(Status.MESSAGE_STATUS_CONSTRAINTS);
        }
        final Status status = new Status(this.status);

        return new ToDo(content, status);
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
