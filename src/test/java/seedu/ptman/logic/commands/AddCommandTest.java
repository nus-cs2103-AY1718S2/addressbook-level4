package seedu.ptman.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.ptman.logic.CommandHistory;
import seedu.ptman.logic.UndoRedoStack;
import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.model.Model;
import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.Password;
import seedu.ptman.model.ReadOnlyPartTimeManager;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.exceptions.DuplicateEmployeeException;
import seedu.ptman.model.employee.exceptions.EmployeeNotFoundException;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.outlet.exceptions.NoOutletInformationFieldChangeException;
import seedu.ptman.model.shift.Shift;
import seedu.ptman.model.shift.exceptions.DuplicateShiftException;
import seedu.ptman.model.shift.exceptions.ShiftNotFoundException;
import seedu.ptman.model.tag.Tag;
import seedu.ptman.testutil.EmployeeBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEmployee_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_employeeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEmployeeAdded modelStub = new ModelStubAcceptingEmployeeAdded();
        Employee validEmployee = new EmployeeBuilder().build();

        CommandResult commandResult = getAddCommandForEmployee(validEmployee, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validEmployee), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validEmployee), modelStub.employeesAdded);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateEmployeeException();
        Employee validEmployee = new EmployeeBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_EMPLOYEE);

        getAddCommandForEmployee(validEmployee, modelStub).execute();
    }

    @Test
    public void equals() {
        Employee alice = new EmployeeBuilder().withName("Alice").build();
        Employee bob = new EmployeeBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different employee -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given employee.
     */
    private AddCommand getAddCommandForEmployee(Employee employee, Model model) {
        AddCommand command = new AddCommand(employee);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addEmployee(Employee employee) throws DuplicateEmployeeException {
            fail("This method should not be called.");
        }

        @Override
        public boolean isAdminPassword(Password password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void setAdminPassword(Password password) {
            fail("This method should not be called.");

        }

        @Override
        public boolean isAdminMode() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean setTrueAdminMode(Password password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void storeResetPassword(Employee employee, Password tempPassword) {
            fail("This method should not be called.");
        }

        @Override
        public void storeResetPassword(OutletInformation outlet, Password tempPassword) {
            fail("This method should not be called.");
        }

        @Override
        public boolean isCorrectTempPwd(OutletInformation outlet, Password tempPassword) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public boolean isCorrectTempPwd(Employee employee, Password tempPassword) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void setFalseAdminMode() {
            fail("This method should not be called.");
        }

        public void addShift(Shift shift) throws DuplicateShiftException {
            fail("This method should not be called.");
        }

        public boolean isAdmin(String password) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void setFilteredShiftListToWeek(LocalDate date) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTagFromAllEmployee(Tag tag) {
            fail("This method should not be called");
        }

        @Override
        public void resetData(ReadOnlyPartTimeManager newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyPartTimeManager getPartTimeManager() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteEmployee(Employee target) throws EmployeeNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateEmployee(Employee target, Employee editedEmployee)
                throws DuplicateEmployeeException {
            fail("This method should not be called.");
        }

        @Override
        public void updateOutlet(OutletInformation outlet) throws NoOutletInformationFieldChangeException {
            fail("This method should not be called.");
        }

        @Override
        public String getEncryptionModeMessage() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public boolean getEncryptionMode() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public ObservableList<Employee> getFilteredEmployeeList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<Shift> getFilteredShiftList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public OutletInformation getOutletInformation() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredEmployeeList(Predicate<Employee> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteShift(Shift shiftToDelete) throws ShiftNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateShift(Shift shiftToApply, Shift editedShift)
                throws ShiftNotFoundException, DuplicateShiftException {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredShiftList(Predicate<Shift> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void encryptLocalStorage() {
            fail("This method should not be called.");
        }

        @Override
        public void decryptLocalStorage() {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateEmployeeException when trying to add an employee.
     */
    private class ModelStubThrowingDuplicateEmployeeException extends ModelStub {
        @Override
        public void addEmployee(Employee employee) throws DuplicateEmployeeException {
            throw new DuplicateEmployeeException();
        }

        @Override
        public boolean isAdminMode() {
            return true;
        }

        @Override
        public ReadOnlyPartTimeManager getPartTimeManager() {
            return new PartTimeManager();
        }
    }

    /**
     * A Model stub that always accept the employee being added.
     */
    private class ModelStubAcceptingEmployeeAdded extends ModelStub {
        final ArrayList<Employee> employeesAdded = new ArrayList<>();

        @Override
        public void addEmployee(Employee employee) throws DuplicateEmployeeException {
            requireNonNull(employee);
            employeesAdded.add(employee);
        }

        @Override
        public boolean isAdminMode()  {
            return true;
        }

        @Override
        public ReadOnlyPartTimeManager getPartTimeManager() {
            return new PartTimeManager();
        }
    }

}
