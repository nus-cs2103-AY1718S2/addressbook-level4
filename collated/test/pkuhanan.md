# pkuhanan
###### /java/seedu/address/commons/util/XmlUtilTest.java
``` java
    private static final String VALID_BALANCE = "10";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_MONEY_AMY = "10";
    public static final String VALID_MONEY_BOB = "10";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String MONEY_DESC_AMY = " " + PREFIX_MONEY + VALID_MONEY_AMY;
    public static final String MONEY_DESC_BOB = " " + PREFIX_MONEY + VALID_MONEY_BOB;
```
###### /java/seedu/address/storage/XmlAdaptedPersonTest.java
``` java
    private static final String VALID_BALANCE = BENSON.getMoney().toString();
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Money} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMoney(String money) {
        descriptor.setMoney(new Money(money));
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Money} of the {@code Person} that we are building.
     */
    public PersonBuilder withMoney(String balance) {
        this.balance = new Money(balance);
        return this;
    }
```
