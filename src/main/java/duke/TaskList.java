package duke;
import java.util.ArrayList;

/**
 * Creates a task list which contains a arraylist and a list of operations can
 * be performed on it. The javadoc for methods in this class are omitted.
 */
public class TaskList {
    protected ArrayList<Task> list;

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }
    
    public ArrayList<Task> getList() {
        return this.list;
    }

    public void add(Task task) {
        this.list.add(task);
    }
    
    public Task get(int number) {
        return this.list.get(number);
    }

    public void set(int number, Task task) {
        this.list.set(number, task);
    }
    
    public void remove(int number) {
        this.list.remove(number);
    }
}
