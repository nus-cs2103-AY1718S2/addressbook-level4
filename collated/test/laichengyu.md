# laichengyu
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        @Override
        public void syncAll(JsonObject newData)
                throws DuplicateCoinException, CoinNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public Set<String> getCodeList() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\model\CoinBookTest.java
``` java
        @Override
        public Set<String> getCodeList() {
            return codes;
        }
```
