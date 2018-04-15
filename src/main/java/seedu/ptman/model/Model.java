package seedu.ptman.model;

import java.time.LocalDate;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.exceptions.DuplicateEmployeeException;
import seedu.ptman.model.employee.exceptions.EmployeeNotFoundException;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.outlet.exceptions.NoOutletInformationFieldChangeException;
import seedu.ptman.model.shift.Shift;
import seedu.ptman.model.shift.exceptions.DuplicateShiftException;
import seedu.ptman.model.shift.exceptions.ShiftNotFoundException;
import seedu.ptman.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Employee> PREDICATE_SHOW_ALL_EMPLOYEES = unused -> true;
    Predicate<Shift> PREDICATE_SHOW_ALL_SHIFTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyPartTimeManager newData);

    /** Returns the PartTimeManager */
    ReadOnlyPartTimeManager getPartTimeManager();

    /** Deletes the given employee. */
    void deleteEmployee(Employee target) throws EmployeeNotFoundException;

    /** Adds the given employee */
    void addEmployee(Employee employee) throws DuplicateEmployeeException;

    /** Adds the given shift */
    void addShift(Shift shift) throws DuplicateShiftException;

    boolean isAdminMode();

    /**
     * check if given password {@code password}
     * is authorized and set to admin mode
     *
     * @return false if admin mode is not set to true
     */
    boolean setTrueAdminMode(Password password);

    /**
     * guarantee to set false
     */
    void setFalseAdminMode();

    boolean isAdminPassword(Password password);

    void setAdminPassword(Password password);

    /**
     * Store and map employee temporarily password with the given employee and password.
     */
    void storeResetPassword(Employee employee, Password tempPassword);

    /**
     * Store and map employer temporarily password with the given employee and password.
     */
    void storeResetPassword(OutletInformation outlet, Password tempPassword);

    /**
     * check if the {@code tempPassword} given is the temporarily password for the {@code employee}
     */
    boolean isCorrectTempPwd(Employee employee, Password tempPassword);

    /**
     * check if the {@code tempPassword} given is the temporarily password for the {@code outlet}
     */
    boolean isCorrectTempPwd(OutletInformation outlet, Password tempPassword);

    /**
     * Delete tag from all employees
     */
    void deleteTagFromAllEmployee(Tag tag);

    /**
     * Replaces the given employee {@code target} with {@code editedEmployee}.
     *
     * @throws DuplicateEmployeeException if updating the employee's details causes the employee to be equivalent to
     *      another existing employee in the list.
     * @throws EmployeeNotFoundException if {@code target} could not be found in the list.
     */
    void updateEmployee(Employee target, Employee editedEmployee)
            throws DuplicateEmployeeException, EmployeeNotFoundException;

    void updateOutlet(OutletInformation outlet) throws NoOutletInformationFieldChangeException;

    String getEncryptionModeMessage();

    void encryptLocalStorage();

    void decryptLocalStorage();

    /** Returns an unmodifiable view of the filtered employee list */
    ObservableList<Employee> getFilteredEmployeeList();

    /** Returns an unmodifiable sorted view of the filtered employee list */
    ObservableList<Shift> getFilteredShiftList();

    /**
     * Updates the filter of the filtered employee list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEmployeeList(Predicate<Employee> predicate);

    void deleteShift(Shift shiftToDelete) throws ShiftNotFoundException;

    void updateShift(Shift shiftToApply, Shift editedShift) throws ShiftNotFoundException, DuplicateShiftException;

    void updateFilteredShiftList(Predicate<Shift> predicate);

    void setFilteredShiftListToWeek(LocalDate date);

    OutletInformation getOutletInformation();

    boolean getEncryptionMode();
}
