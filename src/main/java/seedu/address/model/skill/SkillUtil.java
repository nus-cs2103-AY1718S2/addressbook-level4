// @@author kush1509
package seedu.address.model.skill;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.StringUtil;

/**
 * Utility class for skill matching.
 * @see JobSkillContainsKeywordsPredicate
 * @see PersonSkillContainsKeywordsPredicate
 */
public class SkillUtil {

    /**
     * Checks if a set of skills contains any of the {@code keywords}.
     */
    public static boolean match(List<String> keywords, Set<Skill> list) {
        String skillList = getSkillsAsString(list);
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(skillList, keyword));
    }

    private static String getSkillsAsString(Set<Skill> skills) {
        // @@author KevinCJH
        Iterator tagsIterator = skills.iterator();
        StringBuilder sb = new StringBuilder();
        if (tagsIterator.hasNext()) {
            sb.append(tagsIterator.next());
        }
        while (tagsIterator.hasNext()) {
            sb.append(" " + tagsIterator.next());
        }
        return (sb.toString()
                .replace("[", "")
                .replace("]", ""));
    }

}

