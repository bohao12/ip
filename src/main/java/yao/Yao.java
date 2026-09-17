package yao;

import java.util.Scanner;

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

            try {
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
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    String arg = command.substring(4).trim();
                    if (arg.isEmpty()) {
                        throw new YaoException("OOPS!!! Please specify which task number to mark.");
                    }
                    try {
                        int taskIndex = Integer.parseInt(arg) - 1;
                        if (taskIndex < 0 || taskIndex >= taskCount) {
                            throw new YaoException("OOPS!!! Task number is out of range.");
                        }
                        tasks[taskIndex].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[taskIndex]);
                        System.out.println(BORDER_LINE);
                    } catch (NumberFormatException e) {
                        throw new YaoException("OOPS!!! Task index must be a valid number.");
                    }
                } else if (command.equals("unmark") || command.startsWith("unmark ")) {
                    String arg = command.substring(6).trim();
                    if (arg.isEmpty()) {
                        throw new YaoException("OOPS!!! Please specify which task number to unmark.");
                    }
                    try {
                        int taskIndex = Integer.parseInt(arg) - 1;
                        if (taskIndex < 0 || taskIndex >= taskCount) {
                            throw new YaoException("OOPS!!! Task number is out of range.");
                        }
                        tasks[taskIndex].markAsUndone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[taskIndex]);
                        System.out.println(BORDER_LINE);
                    } catch (NumberFormatException e) {
                        throw new YaoException("OOPS!!! Task index must be a valid number.");
                    }
                } else if (command.equals("todo") || command.startsWith("todo ")) {
                    String description = command.substring(4).trim();
                    if (description.isEmpty()) {
                        throw new YaoException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    Task newTask = new Todo(description);
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(BORDER_LINE);
                } else if (command.equals("deadline") || command.startsWith("deadline ")) {
                    String details = command.substring(8).trim();
                    if (details.isEmpty()) {
                        throw new YaoException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String[] parts = details.split(" /by ", 2);
                    String description = parts[0].trim();
                    if (description.isEmpty()) {
                        throw new YaoException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new YaoException("OOPS!!! The deadline must specify a /by time.");
                    }
                    String by = parts[1].trim();
                    Task newTask = new Deadline(description, by);
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(BORDER_LINE);
                } else if (command.equals("event") || command.startsWith("event ")) {
                    String details = command.substring(5).trim();
                    if (details.isEmpty()) {
                        throw new YaoException("OOPS!!! The description of an event cannot be empty.");
                    }
                    String[] parts = details.split(" /from ", 2);
                    String description = parts[0].trim();
                    if (description.isEmpty()) {
                        throw new YaoException("OOPS!!! The description of an event cannot be empty.");
                    }
                    if (parts.length < 2) {
                        throw new YaoException("OOPS!!! An event must specify both /from and /to times.");
                    }
                    String[] timeParts = parts[1].split(" /to ", 2);
                    String from = timeParts[0].trim();
                    if (from.isEmpty() || timeParts.length < 2 || timeParts[1].trim().isEmpty()) {
                        throw new YaoException("OOPS!!! An event must specify both /from and /to times.");
                    }
                    String to = timeParts[1].trim();
                    Task newTask = new Event(description, from, to);
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                    System.out.println(BORDER_LINE);
                } else {
                    throw new YaoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (YaoException e) {
                System.out.println(" " + e.getMessage());
                System.out.println(BORDER_LINE);
            }
        }
    }
}
