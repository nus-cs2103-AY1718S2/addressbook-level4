# crizyli-unused
###### \java\seedu\address\model\person\CalendarId.java
``` java

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's calendarId
 */
public class CalendarId {

    public final String value;

    public CalendarId(String calendarId) {
        requireNonNull(calendarId);
        this.value = calendarId;
    }

    public String getValue() {
        return value;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPassword.java
``` java
import javax.xml.bind.annotation.XmlValue;

/**
 * JAXB-friendly adapted version of the Password.
 */
public class XmlAdaptedPassword {

    @XmlValue
    private String password;

    /**
     * Constructs an XmlAdaptedPassword.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPassword() {}

    /**
     * Constructs a {@code XmlAdaptedPassword} with the given {@code password}.
     */
    public XmlAdaptedPassword(String password) {
        this.password = password;
    }

    /**
    * Converts this jaxb-friendly adapted password object into the model's password object.
    */
    public String toModelType() {
        return this.password;
    }
}
```
