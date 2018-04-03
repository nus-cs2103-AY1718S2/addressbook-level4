# KevinCJH
###### /java/seedu/address/ui/PersonCard.java
``` java
    /**
     * Returns the color style for {@code skillName}'s label.
     */
    private String getSkillColorStyleFor(String skillName) {
        // we use the hash code of the skill name to generate a random color, so that the color remain consistent
        // between different runs of the program while still making it random enough between skills.
        return SKILL_COLOR_STYLES[Math.abs(skillName.hashCode()) % SKILL_COLOR_STYLES.length];
    }

```
