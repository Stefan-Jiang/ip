package duke;

/**
 * Creates a task which can be further classified into different categories.
 */
public class Task {
    protected String taskname;
    protected boolean isDone;

    public Task(String taskname, boolean isDone) {
        this.taskname = taskname;
        this.isDone = isDone;
    }

    /**
     * Returns the status icon to indicate whether the task has been done.
     *
     * @return The status icon.
     */
    protected String getStatusIcon() {
        assert isDone == true || isDone == false : "satatus is empty";
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * Marks a certain task as done
     */
    protected void markAsDone() {
        this.isDone = true;
    }

    /**
     * Returns the string representation of a task
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.taskname;
    }
}
