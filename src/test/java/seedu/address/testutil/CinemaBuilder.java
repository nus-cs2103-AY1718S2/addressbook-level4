package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.cinema.Address;
import seedu.address.model.cinema.Cinema;
import seedu.address.model.cinema.Email;
import seedu.address.model.cinema.Name;
import seedu.address.model.cinema.Phone;
import seedu.address.model.cinema.Theater;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Cinema objects.
 */
public class CinemaBuilder {

    public static final String DEFAULT_NAME = "Cathay East";
    public static final String DEFAULT_PHONE = "67438807";
    public static final String DEFAULT_EMAIL = "cathayeast@cathay.com";
    public static final String DEFAULT_ADDRESS = "Cathay East, #02-03";
    public static final int DEFAULT_THEATERS = 3;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private ArrayList<Theater> theaters;

    public CinemaBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        theaters = SampleDataUtil.getTheaterList(DEFAULT_THEATERS);
    }

    /**
     * Initializes the CinemaBuilder with the data of {@code cinemaToCopy}.
     */
    public CinemaBuilder(Cinema cinemaToCopy) {
        name = cinemaToCopy.getName();
        phone = cinemaToCopy.getPhone();
        email = cinemaToCopy.getEmail();
        address = cinemaToCopy.getAddress();
        theaters = new ArrayList<>(cinemaToCopy.getTheaters());
    }

    /**
     * Sets the {@code Name} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Theater} of the {@code Cinema} that we are building.
     */
    public CinemaBuilder withTheater(int numOfTheater) {
        this.theaters = SampleDataUtil.getTheaterList(numOfTheater);
        return this;
    }

    public Cinema build() {
        return new Cinema(name, phone, email, address, theaters);
    }

}
