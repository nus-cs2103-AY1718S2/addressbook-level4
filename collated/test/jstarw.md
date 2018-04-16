# jstarw
###### /java/seedu/address/logic/commands/AddAppointmentCommandTest.java
``` java
        @Override
        public Person findOnePerson(Predicate<Person> predicate) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public CalendarSource getCalendar() {
            fail("This method should not be called.");
            return null;
        }

        public ArrayList<ArrayList<Double>> getPersonAttrMatrix() {
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicateAppointmentException when trying to add an appointment.
     */
    private class ModelStubThrowingDuplicateAppointmentException extends AddAppointmentCommandTest.ModelStub {

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            throw new DuplicateAppointmentException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the appointment being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends AddAppointmentCommandTest.ModelStub {
        final ArrayList<AppointmentEntry> appointmentAdded = new ArrayList<>();

        @Override
        public void addAppointment(AppointmentEntry appointmentEntry) throws DuplicateAppointmentException {
            requireNonNull(appointmentEntry);
            appointmentAdded.add(appointmentEntry);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public Person findOnePerson(Predicate<Person> predicate) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public CalendarSource getCalendar() {
            fail("This method should not be called.");
            return null;
        }

        public ArrayList<ArrayList<Double>> getPersonAttrMatrix() {
            return null;
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
