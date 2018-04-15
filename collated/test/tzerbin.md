# tzerbin
###### \java\seedu\address\storage\XmlAdaptedAppointmentTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalStorageCalendar.DENTAL;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.map.MapAddress;
import seedu.address.testutil.Assert;


public class XmlAdaptedAppointmentTest {
    private static final String INVALID_TITLE = "Event N@me";

    private static final String VALID_STARTTIME = DENTAL.getStartTime().toString();
    private static final String VALID_STARTDATE = DENTAL.getStartDate().toString();
    private static final String VALID_ENDTIME = DENTAL.getEndTime().toString();
    private static final String VALID_ENDDATE = DENTAL.getEndDate().toString();
    private static final String VALID_LOCATION = DENTAL.getMapAddress().toString();

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(DENTAL);
        assertEquals(DENTAL, appointment.toModelType());
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(null,
                LocalTime.parse(VALID_STARTTIME),
                LocalDate.parse(VALID_STARTDATE),
                new MapAddress(VALID_LOCATION),
                LocalTime.parse(VALID_ENDTIME),
                LocalDate.parse(VALID_ENDDATE));
        String expectedMessage = Appointment.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        XmlAdaptedAppointment appointment = new XmlAdaptedAppointment(INVALID_TITLE,
                LocalTime.parse(VALID_STARTTIME),
                LocalDate.parse(VALID_STARTDATE),
                new MapAddress(VALID_LOCATION),
                LocalTime.parse(VALID_ENDTIME),
                LocalDate.parse(VALID_ENDDATE));
        String expectedMessage = Appointment.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableStorageCalendarTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.testutil.TypicalStorageCalendar;

public class XmlSerializableStorageCalendarTest {

    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("src/test/data/XmlSerializableStorageCalendarTest/");
    private static final File TYPICAL_APPOINTMENTS_FILE =
            new File(TEST_DATA_FOLDER + "typicalAppointmentsStorageCalendar.xml");
    private static final File INVALID_APPOINTMENTS_FILE =
            new File(TEST_DATA_FOLDER + "invalidAppointmentsStorageCalendar.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalAppointmentsFile_success() throws Exception {
        XmlSerializableStorageCalendar dataFromFile =
                XmlUtil.getDataFromFile(TYPICAL_APPOINTMENTS_FILE, XmlSerializableStorageCalendar.class);
        List storageCalendarFromFile = dataFromFile.toModelType().getAllAppointments();
        List typicalAppointmentList = TypicalStorageCalendar.getTypicalAppointmentList();
        assertEquals(storageCalendarFromFile, typicalAppointmentList);
    }

    @Test
    public void toModelType_invalidAppointmentFile_throwsDateTimeParseException() throws Exception {
        XmlSerializableStorageCalendar dataFromFile =
                XmlUtil.getDataFromFile(INVALID_APPOINTMENTS_FILE, XmlSerializableStorageCalendar.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

}
```
###### \java\seedu\address\testutil\TypicalStorageCalendar.java
``` java
/**
 * A utility class containing a list of {@code Appointments} objects to be used in tests.
 */
public class TypicalStorageCalendar {

    public static final Appointment CONCERT = new Appointment(
            new AppointmentBuilder().withName("Concert")
                    .withStartTime("19:00")
                    .withStartDate("24-08-2018")
                    .withLocation("Singapore Indoors Stadium")
                    .withEndTime("23:00")
                    .withEndDate("24-08-2018").build());

    public static final Appointment DENTAL = new Appointment(
            new AppointmentBuilder().withName("Dental")
                    .withStartTime("15:30")
                    .withStartDate("25-08-2018")
                    .withLocation("Singapore General Hospital")
                    .withEndTime("15:50")
                    .withEndDate("25-08-2018").build());

    public static final Appointment MEETING = new Appointment(
            new AppointmentBuilder().withName("Meeting")
                    .withStartTime("10:30")
                    .withStartDate("26-08-2018")
                    .withLocation("Mediacorp Campus")
                    .withEndTime("18:00")
                    .withEndDate("26-08-2018").build());

    public static final Appointment OSCAR = new Appointment(
            new AppointmentBuilder().withName("Oscar Awards")
                    .withStartTime("12:30")
                    .withStartDate("12-12-2018")
                    .withLocation("Clementi Rd")
                    .withEndTime("13:30")
                    .withEndDate("12-12-2018").build());

    public static final Appointment GRAMMY = new Appointment(
            new AppointmentBuilder().withName("Grammy Awards")
                    .withStartTime("18:00")
                    .withStartDate("10-10-2019")
                    .withLocation("Commonwealth Ave")
                    .withEndTime("19:00")
                    .withEndDate("10-10-2019").build());


    public static List<Appointment> getTypicalAppointmentList() {
        return new ArrayList<>(Arrays.asList(CONCERT, DENTAL, MEETING));
    }

    public static StorageCalendar generateEmptyStorageCalendar() {
        return new StorageCalendar();
    }
}
```
