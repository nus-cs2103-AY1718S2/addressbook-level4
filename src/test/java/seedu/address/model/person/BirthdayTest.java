package seedu.address.model.person;

import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Birthday.MESSAGE_INVALID_BIRTHDAY;
import static seedu.address.model.person.Birthday.MESSAGE_INVALID_BIRTHMONTH;
import static seedu.address.model.person.Birthday.MESSAGE_FUTURE_BIRTHDAY;
import static seedu.address.model.person.Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;

import seedu.address.testutil.Assert;

//@@author AzuraAiR
public class BirthdayTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

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
    public void isValidBirthday_nullBirthday_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));
    }

    @Test
    public void isValidBirthday_emptyBirthday_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("");
    }

    @Test
    public void isValidBirthday_birthdayWithSpaces_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("     ");
    }

    @Test
    public void isValidBirthday_tooShortBirthday_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("121212");

    }

    @Test
    public void isValidBirthday_tooLongBirthday_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_BIRTHDAY_CONSTRAINTS);
        Birthday.isValidBirthday("1212121212");
    }

    @Test
    public void isValidBirthday_invalidYear_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_FUTURE_BIRTHDAY);
        Birthday.isValidBirthday("01012020");
    }

    @Test
    public void isValidBirthday_invalidDay_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_INVALID_BIRTHDAY);
        Birthday.isValidBirthday("00011995");
    }

    @Test
    public void isValidBirthday_invalidMonth_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_INVALID_BIRTHMONTH);
        Birthday.isValidBirthday("01131995");
    }

    @Test
    public void isValidBirthday_invalidDayOfMonth_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_INVALID_BIRTHDAY);
        Birthday.isValidBirthday("30021995");
    }

    @Test
    public void isValidBirthday_futureBirthdaySameYear_throwsIllegalArgumentException() {
        LocalDate today = LocalDate.now();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_FUTURE_BIRTHDAY);
        Birthday.isValidBirthday(buildBirthday(today, 10, today.getMonthValue() + 1));
    }

    @Test
    public void isValidBirthday_futureBirthdaySameMonth_throwsIllegalArgumentException() {
        LocalDate today = LocalDate.now();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(MESSAGE_FUTURE_BIRTHDAY);

        Birthday.isValidBirthday(buildBirthday(today, today.getDayOfMonth() + 1, today.getMonthValue()));
    }

    @Test
    public void isValidBirthday_validBirthday_success(){
        try {
        assertTrue(Birthday.isValidBirthday("01011995"));
        } catch (IllegalArgumentException iae) {
            // Should never go here
        }
    }

    @Test
    public void getValidDayMonth() {
        Birthday birthdayStub = new Birthday("01121995");

        assertTrue(birthdayStub.getDay() == 1); // check Day
        assertTrue(birthdayStub.getMonth() == 12); // check Month
        assertTrue(birthdayStub.getYear() == 1995); // check Year
    }

    /**
     * Creates a stub for testing isValidBirthday with user input
     * @param today date of the computer running the rest
     * @param day day to be entered as valid date
     * @param month month to be entered as valid date
     * @return valid birthday whose values are adjusted for testing
     */
    private String buildBirthday(LocalDate today, int day, int month) {
        StringBuilder sb = new StringBuilder();
        int year = today.getYear();

        // Append day
        if (day < 10) { // Set day to 10 to avoid unnecessary trouble with preceding 0
            day = 10;
        } else if (day + 1 > 28) {  // If day exceeds the upper limit of any month, skip to next month
            month += 1;
            day = 10;
        }
        sb.append(day);

        // Append month
        if (month < 10) {   // Add the preceding 0
            sb.append("0");
        } else if (month > 12) {    // If month exceeds year, skip to next year
            year += 1;
            month = 10;
        }
        sb.append(month);
        sb.append(year);

        return sb.toString();
    }
}

