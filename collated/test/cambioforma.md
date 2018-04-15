# cambioforma
###### /java/seedu/address/model/person/BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday date
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // invalid birthday dates
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("20052018")); // without dashes
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("2018-09-12")); // yyyy-MM-dd format
        assertFalse(Birthday.isValidBirthday("19- 12-2018")); // spaces within date
        assertFalse(Birthday.isValidBirthday("00-12-2018")); // invalid day
        assertFalse(Birthday.isValidBirthday("19-13-2018")); // invalid month
        assertFalse(Birthday.isValidBirthday("19-12-0000")); // invalid year

        // valid birthday dates
        assertTrue(Birthday.isValidBirthday("01-01-2018"));
        assertTrue(Birthday.isValidBirthday("30-12-1990"));
        assertTrue(Birthday.isValidBirthday("29-02-2016")); //leap year
    }
}
```
###### /java/seedu/address/model/person/AppointmentTest.java
``` java
public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidAppointment_throwsIllegalArgumentException() {
        String invalidAppointment = " ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Appointment(invalidAppointment));
    }

    @Test
    public void isValidAppointment() {
        // invalid appointment dates
        assertFalse(Appointment.isValidAppointment(" ")); // spaces only
        assertFalse(Appointment.isValidAppointment("20052018")); // without dashes
        assertFalse(Appointment.isValidAppointment("appointment")); // non-numeric
        assertFalse(Appointment.isValidAppointment("2018-09-12")); // yyyy-MM-dd format
        assertFalse(Appointment.isValidAppointment("19- 12-2018")); // spaces within date
        assertFalse(Appointment.isValidAppointment("00-12-2018")); // invalid day
        assertFalse(Appointment.isValidAppointment("19-13-2018")); // invalid month
        assertFalse(Appointment.isValidAppointment("19-12-0000")); // invalid year

        // valid appointment dates
        assertTrue(Appointment.isValidAppointment(null)); // null
        assertTrue(Appointment.isValidAppointment("")); // empty string
        assertTrue(Appointment.isValidAppointment("01-01-2018"));
        assertTrue(Appointment.isValidAppointment("30-12-1990"));
        assertTrue(Appointment.isValidAppointment("29-02-2016")); //leap year
    }
}
```
