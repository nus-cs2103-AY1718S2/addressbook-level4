package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.todo.ToDo;

/**
 * A utility class containing a list of {@code ToDo} objects to be used in tests.
 */
public class TypicalToDos {
    public static final ToDo TODO_A = new ToDoBuilder().withContent("ToDo A").build();
    public static final ToDo TODO_B = new ToDoBuilder().withContent("ToDo B").build();
    public static final ToDo TODO_C = new ToDoBuilder().withContent("ToDo C").build();
    public static final ToDo TODO_D = new ToDoBuilder().withContent("ToDo D").build();
    public static final ToDo TODO_E = new ToDoBuilder().withContent("ToDo E").build();


    public static List<ToDo> getTypicalToDos() {
        return new ArrayList<>(Arrays.asList(TODO_A, TODO_B, TODO_C));
    }
}
