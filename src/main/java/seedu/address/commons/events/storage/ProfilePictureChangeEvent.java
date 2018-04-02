package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.student.MiscellaneousInfo.ProfilePicturePath;
import seedu.address.model.student.Student;
import seedu.address.model.student.UniqueKey;

/**
 * Indicates a change in the profile picture of the student.
 */
public class ProfilePictureChangeEvent extends BaseEvent {

    private final Student student;
    private final ProfilePicturePath urlToChangeTo;
    private final UniqueKey uniqueKey;

    public ProfilePictureChangeEvent(Student student){
        this.student = student;
        this.urlToChangeTo = student.getProfilePicturePath();
        this.uniqueKey = student.getUniqueKey();
    }

    public ProfilePicturePath getUrlToChangeTo() {
        return urlToChangeTo;
    }

    public UniqueKey getUniqueKey() {
        return uniqueKey;
    }

    @Override
    public String toString() {
        return "Changing Url of profile picture for student: " + student.toString() ;
    }
}
