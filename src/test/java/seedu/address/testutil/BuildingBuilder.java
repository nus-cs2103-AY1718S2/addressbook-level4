package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.building.Building;

/**
 * A utility class to help with building {@code Building} objects
 */
public class BuildingBuilder {

    public static final String DEFAULT_BUILDINGNAME = "COM2";

    private static HashMap<String, ArrayList<String>> nusBuildingsAndRooms;

    private HashMap<String, ArrayList<String>> buildingsAndRooms;
    private String buildingName;

    public BuildingBuilder() {
        buildingName = DEFAULT_BUILDINGNAME;
        buildingsAndRooms = createValidNusBuildingsAndRooms();
        nusBuildingsAndRooms = buildingsAndRooms;
    }

    /**
     * Initializes the BuildingBuilder with the data of {@code buildingToCopy}.
     */
    public BuildingBuilder(Building buildingToCopy) {
        buildingName = buildingToCopy.getBuildingName();
        buildingsAndRooms = buildingToCopy.getBuildingsAndRooms();
        nusBuildingsAndRooms = Building.getNusBuildingsAndRooms();
    }

    /**
     * Creates a valid {@code nusBuildingsAndBuildings}
     */
    private HashMap<String, ArrayList<String>> createValidNusBuildingsAndRooms() {
        HashMap<String, ArrayList<String>> validNusBuildingsAndRooms = new HashMap<>();
        ArrayList<String> validRoomsInBuildings = new ArrayList<>();
        validRoomsInBuildings.add(RoomBuilder.DEFAULT_ROOMNAME);
        validNusBuildingsAndRooms.put(DEFAULT_BUILDINGNAME, validRoomsInBuildings);
        return validNusBuildingsAndRooms;
    }

    /**
     * Sets the {@code buildingName} into a {@code Building} that we are building.
     */
    public BuildingBuilder withBuildingName(String buildingName) {
        this.buildingName = buildingName;
        return this;
    }

    /**
     * Sets the {@code nusBuildingsAndRooms} into a {@code Building} that we are building.
     */
    public BuildingBuilder withNusBuildingsAndRooms(HashMap<String, ArrayList<String>> nusBuildingsAndRooms) {
        BuildingBuilder.nusBuildingsAndRooms = nusBuildingsAndRooms;
        return this;
    }

    /**
     * Sets the {@code buildingsAndRooms} into a {@code Building} that we are building.
     */
    public BuildingBuilder withBuildingsAndRooms(HashMap<String, ArrayList<String>> buildingsAndRooms) {
        this.buildingsAndRooms = buildingsAndRooms;
        return this;
    }

    /**
     * Builds a {@code Building} object
     */
    public Building build() {
        Building building = new Building(buildingName);
        Building.setNusBuildingsAndRooms(nusBuildingsAndRooms);
        building.setBuildingsAndRooms(buildingsAndRooms);
        return building;
    }
}
