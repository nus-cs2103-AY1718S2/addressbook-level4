package seedu.address.logic.commands;
//@@author crizyli

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.GetEmployeesRequestEvent;
import seedu.address.commons.events.model.ReturnedEmployeesEvent;
import seedu.address.model.person.Person;

/**
 * export employees to a csv file.
 */
public class ExportEmployeesCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports current employees to a csv file.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "All Employees Exported to employees.csv!";

    public static final String MESSAGE_FAIL = "Export fail! Make sure you haven't opened employees.csv and try again!";

    public static final String EXPORT_FILE_PATH = "data/employees.csv";

    private ObservableList<Person> employees;

    private boolean isTest;

    public ExportEmployeesCommand() {
        registerAsAnEventHandler(this);
    }

    public ExportEmployeesCommand(boolean isTest) {
        this.isTest = isTest;
        registerAsAnEventHandler(this);
    }

    @Override
    public CommandResult execute() {

        EventsCenter.getInstance().post(new GetEmployeesRequestEvent());

        File csv;
        if (isTest) {
            csv = new File("employees.csv");
        } else {
            csv = new File(EXPORT_FILE_PATH);
        }

        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(csv));
            bw.write("Name,Phone,Email,Address,Tags\n");
            for (Person p : employees) {
                String temp = p.getName().fullName + "," + p.getPhone().value + "," + p.getEmail().value + ","
                        + p.getAddress().value.replaceAll(",", " ");
                if (!p.getTags().isEmpty()) {
                    temp = temp + "," + p.getTags().toString()
                            .replaceAll(", ", " | ");
                }
                temp = temp + "\n";
                bw.write(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult(MESSAGE_FAIL);
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    protected void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Subscribe
    private void handleReturnedEmployeesEvent(ReturnedEmployeesEvent event) {
        this.employees = event.getEmployees();
    }
}
