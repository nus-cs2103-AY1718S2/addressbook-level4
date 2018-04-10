# ng95junwei
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public ObservableList<Template> getAllTemplates() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Template selectTemplate(String search) {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getTemplateList_return_observableList() {
        ModelManager modelManager = new ModelManager();
        assertThat(modelManager.getAllTemplates(), instanceOf(ObservableList.class));
    }
```
