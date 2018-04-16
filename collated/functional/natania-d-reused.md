# natania-d-reused
###### \java\seedu\organizer\model\Model.java
``` java
    /** Removes the given {@code tag} from all {@code Task}s. */
    void deleteTag(Tag tag);

```
###### \java\seedu\organizer\model\ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) {
        organizer.removeTag(tag);
    }

```
###### \java\seedu\organizer\model\Organizer.java
``` java
    /**
     * Removes all {@code Tag}s that are not used by any {@code Task} in this {@code Organizer}.
     */
    private void removeUnusedTags() {
        Set<Tag> tagsInTasks = tasks.asObservableList().stream()
                .map(Task::getTags)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        tags.setTags(tagsInTasks);
    }

    /**
     * Removes {@code tag} from {@code task} in this {@code Organizer}.
     * @throws TaskNotFoundException if the {@code task} is not in this {@code Organizer}.
     */
    private void removeTagFromTask(Tag tag, Task task) throws TaskNotFoundException {
        Set<Tag> newTags = new HashSet<>(task.getTags());

        if (!newTags.remove(tag)) {
            return;
        }

        Task newTask =
                new Task(task.getName(), task.getUpdatedPriority(), task.getBasePriority(), task.getDeadline(),
                        task.getDateAdded(), task.getDateCompleted(), task.getDescription(), task.getStatus(),
                        newTags, task.getSubtasks(), task.getUser(), task.getRecurrence());

        try {
            updateTask(task, newTask);
        } catch (DuplicateTaskException dte) {
            throw new AssertionError("Modifying a task's tags only should not result in a duplicate. "
                    + "See Task#equals(Object).");
        }
    }

    /**
     * Removes {@code tag} from all tasks in this {@code Organizer}.
     */
    public void removeTag(Tag tag) {
        try {
            for (Task task : tasks) {
                removeTagFromTask(tag, task);
            }
        } catch (TaskNotFoundException tnfe) {
            throw new AssertionError("Impossible: original task is obtained from PrioriTask.");
        }
    }

```
