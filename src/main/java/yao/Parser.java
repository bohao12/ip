package yao;

import yao.command.AddCommand;
import yao.command.Command;
import yao.command.DeleteCommand;
import yao.command.ExitCommand;
import yao.command.ListCommand;
import yao.command.MarkCommand;
import yao.command.UnmarkCommand;
import yao.task.Deadline;
import yao.task.Event;
import yao.task.Todo;

/**
 * Parses user input strings into executable Command objects.
 */
public class Parser {

    /**
     * Parses a raw user input command line into a corresponding Command object.
     *
     * @param fullCommand The raw command string entered by the user.
     * @return The executable Command instance.
     * @throws YaoException If the command format is invalid or unrecognized.
     */
    public static Command parse(String fullCommand) throws YaoException {
        if (fullCommand.equals("bye")) {
            return new ExitCommand();
        } else if (fullCommand.equals("list")) {
            return new ListCommand();
        } else if (fullCommand.equals("mark") || fullCommand.startsWith("mark ")) {
            return parseMarkCommand(fullCommand);
        } else if (fullCommand.equals("unmark") || fullCommand.startsWith("unmark ")) {
            return parseUnmarkCommand(fullCommand);
        } else if (fullCommand.equals("delete") || fullCommand.startsWith("delete ")) {
            return parseDeleteCommand(fullCommand);
        } else if (fullCommand.equals("todo") || fullCommand.startsWith("todo ")) {
            return parseTodoCommand(fullCommand);
        } else if (fullCommand.equals("deadline") || fullCommand.startsWith("deadline ")) {
            return parseDeadlineCommand(fullCommand);
        } else if (fullCommand.equals("event") || fullCommand.startsWith("event ")) {
            return parseEventCommand(fullCommand);
        } else {
            throw new YaoException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    private static Command parseMarkCommand(String fullCommand) throws YaoException {
        String arg = fullCommand.substring(4).trim();
        if (arg.isEmpty()) {
            throw new YaoException("OOPS!!! Please specify which task number to mark.");
        }
        try {
            int taskIndex = Integer.parseInt(arg) - 1;
            return new MarkCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new YaoException("OOPS!!! Task index must be a valid number.");
        }
    }

    private static Command parseUnmarkCommand(String fullCommand) throws YaoException {
        String arg = fullCommand.substring(6).trim();
        if (arg.isEmpty()) {
            throw new YaoException("OOPS!!! Please specify which task number to unmark.");
        }
        try {
            int taskIndex = Integer.parseInt(arg) - 1;
            return new UnmarkCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new YaoException("OOPS!!! Task index must be a valid number.");
        }
    }

    private static Command parseDeleteCommand(String fullCommand) throws YaoException {
        String arg = fullCommand.substring(6).trim();
        if (arg.isEmpty()) {
            throw new YaoException("OOPS!!! Please specify which task number to delete.");
        }
        try {
            int taskIndex = Integer.parseInt(arg) - 1;
            return new DeleteCommand(taskIndex);
        } catch (NumberFormatException e) {
            throw new YaoException("OOPS!!! Task index must be a valid number.");
        }
    }

    private static Command parseTodoCommand(String fullCommand) throws YaoException {
        String description = fullCommand.substring(4).trim();
        if (description.isEmpty()) {
            throw new YaoException("OOPS!!! The description of a todo cannot be empty.");
        }
        return new AddCommand(new Todo(description));
    }

    private static Command parseDeadlineCommand(String fullCommand) throws YaoException {
        String details = fullCommand.substring(8).trim();
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
        return new AddCommand(new Deadline(description, by));
    }

    private static Command parseEventCommand(String fullCommand) throws YaoException {
        String details = fullCommand.substring(5).trim();
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
        return new AddCommand(new Event(description, from, to));
    }
}
