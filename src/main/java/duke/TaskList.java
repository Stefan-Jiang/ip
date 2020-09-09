package duke;
import java.util.ArrayList;

/**
 * Creates a task list which contains a arraylist and a list of operations can
 * be performed on it. The javadoc for methods in this class are omitted.
 */
public class TaskList {
    protected ArrayList<Task> list;

    protected TaskList(ArrayList<Task> list) {
        this.list = list;
    }
    
    protected ArrayList<Task> getList() {
        return this.list;
    }

    protected void add(Task task) {
        this.list.add(task);
    }
    
    protected Task get(int number) {
        return this.list.get(number);
    }

    protected void set(int number, Task task) {
        this.list.set(number, task);
    }
    
    protected void remove(int number) {
        this.list.remove(number);
    }
}
