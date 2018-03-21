package seedu.address.model.module;

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

    public Module() {
        System.out.println("i'm called mod");
    }

    public Module(String moduleCode, String moduleTitle) {
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        timetable = new ArrayList<>();
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public ArrayList<Schedule> getTimetable() {
        return timetable;
    }

    @Override
    public String toString() {
        return "moduleCode: " + moduleCode + " moduleTitle: " + moduleTitle + "\n" + timetable.toString();
    }
}
