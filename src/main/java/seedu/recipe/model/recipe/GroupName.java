package seedu.recipe.model.recipe;

//@@author hoangduong1607
public class GroupName extends Name {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Recipe group names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public GroupName(String name) {
        super(name);
    }
}
