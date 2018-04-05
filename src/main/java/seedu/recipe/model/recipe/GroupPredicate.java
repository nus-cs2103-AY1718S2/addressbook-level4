package seedu.recipe.model.recipe;

import java.util.function.Predicate;

//@@author hoangduong1607
public class GroupPredicate implements Predicate<Recipe> {
    private final GroupName groupName;

    public GroupPredicate(GroupName groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean test(Recipe recipe) {
        return recipe.getGroupNames().stream().anyMatch(groupName1 -> groupName1.equals(groupName1));
    }
}
