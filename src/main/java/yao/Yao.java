package yao;

import java.util.ArrayList;
import yao.task.Deadline;
import yao.task.Event;
import yao.task.Task;
import yao.task.Todo;

/**
 * Main class for the Yao chatbot application.
 * Coordinates user interactions, task list operations, and file storage.
 */
public class Yao {
    private static final String DEFAULT_FILE_PATH = "data/Yao.txt";

    private final Storage storage;
    private final Ui ui;

    /**
     * Initializes the Yao application with a specified data file path.
     *
     * @param filePath Relative or absolute path to the data storage file.
     */
    public Yao(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
    }

    /**
     * Initializes the Yao application using the default data file path.
     */
    public Yao() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Runs the main command processing loop for the Yao chatbot.
     */
    public void run() {
        ui.showWelcome();

        ArrayList<Task> tasks;
        try {
            tasks = storage.load();
        } catch (YaoException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new ArrayList<>();
        }

        while (true) {
            String command = ui.readCommand();
            ui.showLine();

            try {
                if (command.equals("bye")) {
                    ui.showGoodbye();
                    break;
                } else if (command.equals("list")) {
                    ui.showTaskList(tasks);
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
                        storage.save(tasks);
                        ui.showTaskMarked(tasks.get(taskIndex));
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
                        storage.save(tasks);
                        ui.showTaskUnmarked(tasks.get(taskIndex));
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
                        storage.save(tasks);
                        ui.showTaskDeleted(removedTask, tasks.size());
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
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
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
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
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
                    storage.save(tasks);
                    ui.showTaskAdded(newTask, tasks.size());
                } else {
                    throw new YaoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }
            } catch (YaoException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * Main entry point for the Yao chatbot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Yao("data/Yao.txt").run();
    }
}
