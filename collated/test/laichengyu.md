# laichengyu
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        @Override
        public void syncAll(HashMap<String, Price> newPriceMetrics)
                throws DuplicateCoinException, CoinNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public List<String> getCodeList() {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\model\CoinBookTest.java
``` java
        @Override
        public List<String> getCodeList() {
            return Collections.unmodifiableList(coins.stream()
                    .map(coin -> coin.getCode().toString())
                    .collect(Collectors.toList()));
        }
```
