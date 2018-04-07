package seedu.address.storage;
//@@author crizyli
import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.photo.Photo;

/**
 * JAXB-friendly adapted version of the Photo.
 */
public class XmlAdaptedPhoto {
    @XmlValue
    private String photoName;

    /**
     * Constructs an XmlAdaptedPhoto.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPhoto() {}

    /**
     * Constructs a {@code XmlAdaptedPhoto} with the given {@code photoName}.
     */
    public XmlAdaptedPhoto(String photoName) {
        this.photoName = photoName;
    }

    /**
     * Converts a given Photo into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedPhoto(Photo source) {
        photoName = source.name;
    }

    /**
     * Converts this jaxb-friendly adapted photo object into the model's Photo object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Photo toModelType() throws IllegalValueException {
        if (!Photo.isValidPhotoName(photoName)) {
            throw new IllegalValueException(Photo.MESSAGE_PHOTO_CONSTRAINTS);
        }
        return new Photo(photoName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPhoto)) {
            return false;
        }

        return photoName.equals(((XmlAdaptedPhoto) other).photoName);
    }
}
