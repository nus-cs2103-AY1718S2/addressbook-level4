package seedu.ptman.testutil;

import static seedu.ptman.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_SALARY_BOB;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.ptman.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.exceptions.DuplicateEmployeeException;

/**
 * A utility class containing a list of {@code Employee} objects to be used in tests.
 */
public class TypicalEmployees {
    public static final String DEFAULT1_HASH = "wkqTFuX6NX3hucWqn2ZxB24cRo73LssRq7IDOk6Zx00=";
    public static final Employee ALICE = new EmployeeBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255").withSalary("10").withPassword(DEFAULT1_HASH)
            .withTags("friends").build();
    public static final Employee BENSON = new EmployeeBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withSalary("0").withPassword(DEFAULT1_HASH)
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Employee CARL = new EmployeeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();
    public static final Employee DANIEL = new EmployeeBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();
    public static final Employee ELLE = new EmployeeBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();
    public static final Employee FIONA = new EmployeeBuilder().withName("Fiona Kunz").withPassword("DEFAULT1")
            .withPhone("9482427").withEmail("lydia@example.com").withAddress("little tokyo").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();
    public static final Employee GEORGE = new EmployeeBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();

    // Manually added
    public static final Employee HOON = new EmployeeBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();
    public static final Employee IDA = new EmployeeBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSalary("0")
            .withPassword(DEFAULT1_HASH).build();

    // Manually added - Employee's details found in {@code CommandTestUtil}
    public static final Employee AMY = new EmployeeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSalary(VALID_SALARY_AMY)
            .withPassword(DEFAULT1_HASH).withTags(VALID_TAG_FRIEND).build();
    public static final Employee BOB = new EmployeeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSalary(VALID_SALARY_BOB)
            .withPassword(DEFAULT1_HASH).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalEmployees() {} // prevents instantiation

    /**
     * Returns an {@code PartTimeManager} with all the typical employees.
     */
    public static PartTimeManager getTypicalPartTimeManager() {
        PartTimeManager ptman = new PartTimeManager();
        for (Employee employee : getTypicalEmployees()) {
            try {
                ptman.addEmployee(employee);
            } catch (DuplicateEmployeeException e) {
                throw new AssertionError("not possible");
            }
        }
        return ptman;
    }

    public static List<Employee> getTypicalEmployees() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
