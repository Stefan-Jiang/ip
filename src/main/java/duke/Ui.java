package duke;
import java.util.ArrayList;

/**
 * Creates an ui interface for interactions with the product user.
 */
public class Ui {
    /**
     * Prints out greeting messages to the user.
     */
    public void greet() {
        String logo = "     ____        _        \n"
                + "    |  _ \\ _   _| | _____ \n"
                + "    | | | | | | | |/ / _ \\\n"
                + "    | |_| | |_| |   <  __/\n"
                + "    |____/ \\__,_|_|\\_\\___|\n";

        System.out.println("    -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        System.out.println("    Hello from\n" + logo);
        System.out.println("    Hey there! This is Duke here~");
        System.out.println("    How may I help you today?");
        System.out.println("    -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
    }

    /**
     * Prints out "noproblem" messages to the user.
     */
    public void noProblem() {
        System.out.println("     No problem! I've added this task to the list:");
    }

    /**
     * Prints out "markasdone" messages to the user.
     */
    public void markAsDone(Task thisTask) {
        System.out.println("     Nice! I've marked this task as done:");
        System.out.println("       " + thisTask.toString());
    }

    /**
     * Prints out "deletedtask" messages to the user.
     */
    public void deletedTask(Task thisTask, ArrayList<Task> list) {
        System.out.println("     Sure! I've removed this task for you:");
        System.out.println("       " + thisTask.toString());
        System.out.println("     Now you have " + list.size() + " tasks in the list.");
    }

    /**
     * Prints out "updatedtask" messages to the user.
     * 
     * @param list The list of tasks.
     */
    public void updatedTask(ArrayList<Task> list) {
        System.out.println("       " + list.get(list.size() - 1));
        System.out.println("     Now you have " + list.size() + " tasks in the list.");
    }

    /**
     * Prints out a list of tasks to the user.
     */
    public void printList(ArrayList<Task> list) {
        Task thisTask;
        System.out.println("     Here are the tasks in your list:");
        for (int i = 1; i <= list.size(); i++) {
            thisTask = list.get(i - 1);
            System.out.println("     " + i + "." + thisTask.toString());
        }
    }

    /**
     * Prints out a line to the user.
     */
    public void printLine() {
        System.out.println("    -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
    }

    /**
     * Prints out the error messages to the user.
     */
    public void printError(DukeException ex) {
        System.out.println(ex.getMessage());
    }

    /**
     * Prints out byebye messages to the user.
     */
    public void bye() {
        System.out.println("    -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
        System.out.println("    Bye bye~ See ya!");
        System.out.println("    -x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-x-");
    }
}
