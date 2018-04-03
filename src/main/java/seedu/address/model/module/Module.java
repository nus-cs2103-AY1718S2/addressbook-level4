package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;


/**
 * Represents a Module from NUSmods
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class Module {
    private String moduleCode = "";
    private String moduleTitle = "";
    private ArrayList<Schedule> timetable = new ArrayList<>();

    public Module(){}

    public Module(String moduleCode, String moduleTitle, ArrayList<Schedule> timetable) {
        requireAllNonNull(moduleCode, moduleTitle);
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        this.timetable = timetable;
    }

    public Module(String moduleCode, String moduleTitle) {
        requireAllNonNull(moduleCode, moduleTitle);
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public ArrayList<Schedule> getScheduleList() {
        return timetable;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(this.getModuleCode())
                && otherModule.getModuleTitle().equals(this.getModuleTitle());
    }

    @Override
    public String toString() {
        return "moduleCode: " + moduleCode + " moduleTitle: " + moduleTitle + "\n" + timetable.toString();
    }
}
