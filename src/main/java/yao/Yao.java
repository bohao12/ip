package yao;

import java.util.Scanner;
import yao.task.Deadline;
import yao.task.Event;
import yao.task.Task;
import yao.task.Todo;

/**
 * Main class for the Yao chatbot application.
 * Manages user interactions and task list operations.
 */
public class Yao {
    private static final int MAX_TASKS = 100;
    private static final String BORDER_LINE = "____________________________________________________________";
    private static final String BANNER = " __   __            \n"
            + " \\ \\ / /_ _  ___   \n"
            + "  \\ V / _` |/ _ \\  \n"
            + "   | | (_| | (_) | \n"
            + "   |_|\\__,_|\\___/  \n";

    /**
     * Main entry point for the Yao chatbot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        System.out.println(BORDER_LINE);
        System.out.print(BANNER);
        System.out.println("Hello! I'm Yao.");
        System.out.println("What can I do for you?");
        System.out.println(BORDER_LINE);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        while (true) {
            String command = scanner.nextLine();
            System.out.println(BORDER_LINE);

            if (command.equals("bye")) {
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(BORDER_LINE);
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println((i + 1) + "." + tasks[i]);
                }
                System.out.println(BORDER_LINE);
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5).trim()) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  " + tasks[taskIndex]);
                System.out.println(BORDER_LINE);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7).trim()) - 1;
                tasks[taskIndex].markAsUndone();
                System.out.println("OK, I've marked this task as not done yet:");
                System.out.println("  " + tasks[taskIndex]);
                System.out.println(BORDER_LINE);
            } else if (command.startsWith("todo ")) {
                String description = command.substring(5).trim();
                Task newTask = new Todo(description);
                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(BORDER_LINE);
            } else if (command.startsWith("deadline ")) {
                String details = command.substring(9).trim();
                String[] parts = details.split(" /by ", 2);
                String description = parts[0];
                String by = parts.length > 1 ? parts[1] : "";
                Task newTask = new Deadline(description, by);
                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(BORDER_LINE);
            } else if (command.startsWith("event ")) {
                String details = command.substring(6).trim();
                String[] parts = details.split(" /from ", 2);
                String description = parts[0];
                String from = "";
                String to = "";
                if (parts.length > 1) {
                    String[] timeParts = parts[1].split(" /to ", 2);
                    from = timeParts[0];
                    to = timeParts.length > 1 ? timeParts[1] : "";
                }
                Task newTask = new Event(description, from, to);
                tasks[taskCount] = newTask;
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + newTask);
                System.out.println("Now you have " + taskCount + " tasks in the list.");
                System.out.println(BORDER_LINE);
            } else {
                tasks[taskCount] = new Task(command);
                taskCount++;
                System.out.println("added: " + command);
                System.out.println(BORDER_LINE);
            }
        }
    }
}
