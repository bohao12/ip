package yao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import yao.task.Deadline;
import yao.task.Event;
import yao.task.Task;
import yao.task.Todo;

/**
 * Main class for the Yao chatbot application.
 * Manages user interactions, task list operations, and file storage.
 */
public class Yao {
    private static final String BORDER_LINE = "____________________________________________________________";
    private static final String BANNER = " __   __            \n"
            + " \\ \\ / /_ _  ___   \n"
            + "  \\ V / _` |/ _ \\  \n"
            + "   | | (_| | (_) | \n"
            + "   |_|\\__,_|\\___/  \n";
    private static final String DATA_DIRECTORY = "data";
    private static final String DATA_FILE = "Yao.txt";
    private static final Path FILE_PATH = Paths.get(DATA_DIRECTORY, DATA_FILE);

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
        ArrayList<Task> tasks = new ArrayList<>();
        loadTasks(tasks);

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
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    System.out.println(BORDER_LINE);
                } else if (command.equals("mark") || command.startsWith("mark ")) {
                    String arg = command.substring(4).trim();
                    if (arg.isEmpty()) {
                        throw new YaoException("OOPS!!! Please specify which task number to mark.");
                    }
                    try {
                        int taskIndex = Integer.parseInt(arg) - 1;
                        if (taskIndex < 0 || taskIndex >= tasks.size()) {
                            throw new YaoException("OOPS!!! Task number is out of range.");
                        }
                        tasks.get(taskIndex).markAsDone();
                        saveTasks(tasks);
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(taskIndex));
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
                        if (taskIndex < 0 || taskIndex >= tasks.size()) {
                            throw new YaoException("OOPS!!! Task number is out of range.");
                        }
                        tasks.get(taskIndex).markAsUndone();
                        saveTasks(tasks);
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(taskIndex));
                        System.out.println(BORDER_LINE);
                    } catch (NumberFormatException e) {
                        throw new YaoException("OOPS!!! Task index must be a valid number.");
                    }
                } else if (command.equals("delete") || command.startsWith("delete ")) {
                    String arg = command.substring(6).trim();
                    if (arg.isEmpty()) {
                        throw new YaoException("OOPS!!! Please specify which task number to delete.");
                    }
                    try {
                        int taskIndex = Integer.parseInt(arg) - 1;
                        if (taskIndex < 0 || taskIndex >= tasks.size()) {
                            throw new YaoException("OOPS!!! Task number is out of range.");
                        }
                        Task removedTask = tasks.remove(taskIndex);
                        saveTasks(tasks);
                        System.out.println("Noted. I've removed this task:");
                        System.out.println("  " + removedTask);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
                    tasks.add(newTask);
                    saveTasks(tasks);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
                    tasks.add(newTask);
                    saveTasks(tasks);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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
                    tasks.add(newTask);
                    saveTasks(tasks);
                    System.out.println("Got it. I've added this task:");
                    System.out.println("  " + newTask);
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
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

    /**
     * Loads tasks from the storage file on disk.
     * If the file or directory does not exist, starts with an empty list.
     * Corrupted lines are skipped gracefully.
     *
     * @param tasks The ArrayList to populate with loaded tasks.
     */
    private static void loadTasks(ArrayList<Task> tasks) {
        if (!Files.exists(FILE_PATH)) {
            return;
        }

        try {
            List<String> lines = Files.readAllLines(FILE_PATH);
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Unable to read data file: " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the storage file into a Task object.
     *
     * @param line A line from the storage file.
     * @return The parsed Task, or null if the line is corrupted.
     */
    private static Task parseTaskFromLine(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        if (parts.length < 3) {
            System.out.println("Warning: Skipping corrupted line in data file: " + line);
            return null;
        }

        String type = parts[0];
        String isDoneStr = parts[1];
        String description = parts[2];

        if (!isDoneStr.equals("0") && !isDoneStr.equals("1")) {
            System.out.println("Warning: Skipping corrupted status in data file: " + line);
            return null;
        }

        boolean isDone = isDoneStr.equals("1");
        Task task;

        switch (type) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (parts.length < 4) {
                System.out.println("Warning: Skipping corrupted deadline line: " + line);
                return null;
            }
            task = new Deadline(description, parts[3]);
            break;
        case "E":
            if (parts.length < 4) {
                System.out.println("Warning: Skipping corrupted event line: " + line);
                return null;
            }
            String from = parts[3];
            String to = parts.length > 4 ? parts[4] : "";
            task = new Event(description, from, to);
            break;
        case "TASK":
            task = new Task(description);
            break;
        default:
            System.out.println("Warning: Skipping unrecognized task type in data file: " + line);
            return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Saves the current tasks to the storage file on disk.
     * Automatically creates the parent directory if it does not exist.
     *
     * @param tasks The list containing active tasks.
     */
    private static void saveTasks(ArrayList<Task> tasks) {
        try {
            Path parentDir = FILE_PATH.getParent();
            if (parentDir != null && !Files.exists(parentDir)) {
                Files.createDirectories(parentDir);
            }

            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toFileFormat());
            }
            Files.write(FILE_PATH, lines);
        } catch (IOException e) {
            System.out.println("Warning: Unable to save tasks to file: " + e.getMessage());
        }
    }
}
