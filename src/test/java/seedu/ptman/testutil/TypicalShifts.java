package seedu.ptman.testutil;

import static seedu.ptman.testutil.TypicalEmployees.ALICE;
import static seedu.ptman.testutil.TypicalEmployees.BENSON;
import static seedu.ptman.testutil.TypicalEmployees.CARL;
import static seedu.ptman.testutil.TypicalEmployees.getTypicalEmployees;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.exceptions.DuplicateEmployeeException;
import seedu.ptman.model.shift.Shift;
import seedu.ptman.model.shift.exceptions.DuplicateShiftException;

//@@author shanwpf
/**
 * A utility class containing a list of {@code Shift} objects to be used in tests.
 */
public class TypicalShifts {

    public static final Shift SHIFT_MONDAY_AM = new ShiftBuilder().withDate("19-03-18")
            .withStartTime("0800")
            .withEndTime("1300")
            .withCapacity("4").build();
    public static final Shift SHIFT_MONDAY_PM = new ShiftBuilder().withDate("19-03-18")
            .withStartTime("1300")
            .withEndTime("2200")
            .withCapacity("4").build();
    public static final Shift SHIFT_TUESDAY_AM = new ShiftBuilder().withDate("20-03-18")
            .withStartTime("0900")
            .withEndTime("1200")
            .withCapacity("5").build();
    public static final Shift SHIFT_TUESDAY_PM = new ShiftBuilder().withDate("20-03-18")
            .withStartTime("1200")
            .withEndTime("2200")
            .withCapacity("3").build();
    public static final Shift SHIFT_SUNDAY_AM = new ShiftBuilder().withDate("25-03-18")
            .withStartTime("1000")
            .withEndTime("1300")
            .withCapacity("4").build();
    public static final Shift SHIFT_SUNDAY_PM = new ShiftBuilder().withDate("25-03-18")
            .withStartTime("1300")
            .withEndTime("1700")
            .withCapacity("4").build();
    public static final Shift SHIFT_WEDNESDAY_AM = new ShiftBuilder().withDate("21-03-18")
            .withStartTime("0900")
            .withEndTime("1200")
            .withCapacity("5").build();
    public static final Shift SHIFT_WEDNESDAY_PM = new ShiftBuilder().withDate("21-03-18")
            .withStartTime("1200")
            .withEndTime("2200")
            .withCapacity("3").build();
    public static final Shift SHIFT_THURSDAY_AM = new ShiftBuilder().withDate("22-03-18")
            .withStartTime("0900")
            .withEndTime("1200")
            .withCapacity("5")
            .withEmployees(new EmployeeBuilder().build()).build();
    public static final Shift SHIFT_THURSDAY_PM = new ShiftBuilder().withDate("22-03-18")
            .withStartTime("1200")
            .withEndTime("2200")
            .withCapacity("3")
            .withEmployees(ALICE, BENSON, CARL).build();

    public static final Shift SHIFT_RUNNING_OUT = new ShiftBuilder().withDate("22-03-18")
            .withStartTime("0900")
            .withEndTime("1200")
            .withCapacity("1").build();

    private TypicalShifts() {} // prevents instantiation

    public static PartTimeManager getTypicalPartTimeManagerWithShifts() {
        PartTimeManager ptman = new PartTimeManager();
        for (Employee employee : getTypicalEmployees()) {
            try {
                ptman.addEmployee(employee);
            } catch (DuplicateEmployeeException e) {
                throw new AssertionError("not possible");
            }
        }
        for (Shift shift : getTypicalShifts()) {
            try {
                ptman.addShift(shift);
            } catch (DuplicateShiftException e) {
                throw new AssertionError("not possible");
            }
        }
        return ptman;
    }

    public static List<Shift> getTypicalShifts() {
        return new ArrayList<>(Arrays.asList(SHIFT_MONDAY_AM, SHIFT_MONDAY_PM, SHIFT_TUESDAY_AM, SHIFT_TUESDAY_PM,
                SHIFT_WEDNESDAY_AM, SHIFT_WEDNESDAY_PM, SHIFT_THURSDAY_PM, SHIFT_SUNDAY_AM, SHIFT_SUNDAY_PM));
    }
}
