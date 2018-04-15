# glorialaw
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: blank timezone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + BLANK_TIMEZONE_DESC + COMMENT_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, CustTimeZone.MESSAGE_TIMEZONE_CONSTRAINTS);

        /* Case: invalid timezone -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + INVALID_TIMEZONE_DESC + COMMENT_DESC_AMY
                + TAG_DESC_FRIEND;
        assertCommandFailure(command, CustTimeZone.MESSAGE_TIMEZONE_CONSTRAINTS);

```
###### /java/systemtests/EditCommandSystemTest.java
``` java
        /* Case: invalid timezone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + INVALID_TIMEZONE_DESC,
                CustTimeZone.MESSAGE_TIMEZONE_CONSTRAINTS);

        /* Case: blank timezone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + BLANK_TIMEZONE_DESC,
                CustTimeZone.MESSAGE_TIMEZONE_CONSTRAINTS);

```
