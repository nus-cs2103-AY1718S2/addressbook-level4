package seedu.address.testutil;

import seedu.address.model.alias.Alias;

/**
 * A utility class to help with building Alias objects.
 */
public class AliasBuilder {

        public static final String DEFAULT_COMMAND = "add";
        public static final String DEFAULT_ALIAS = "a";

        private String command;
        private String alias;

        public AliasBuilder() {
            command = DEFAULT_COMMAND;
            alias = DEFAULT_ALIAS;
        }

        /**
         * Initializes the AliasBuilder with the data of {@code aliasToCopy}.
         */
        public AliasBuilder(Alias aliasToCopy) {
            command = aliasToCopy.getCommand();
            alias = aliasToCopy.getAlias();
        }

        /**
         * Sets the {@code command} of the {@code Alias} that we are building.
         */
        public AliasBuilder withCommand(String command) {
            this.command = command;
            return this;
        }

        /**
         * Sets the {@code alias} into a {@code Alias} that we are building.
         */
        public AliasBuilder withAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public Alias build() {
            return new Alias(command, alias);
        }
}
