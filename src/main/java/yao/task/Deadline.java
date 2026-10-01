package yao.task;

import java.time.LocalDate;
import java.time.LocalTime;
import yao.DateTimeUtil;
import yao.YaoException;

/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {
    /** Parsed date and time container for the deadline. */
    protected DateTimeUtil.ParsedDateTime byDateTime;

    /**
     * Constructs a Deadline task with description and deadline date/time string.
     *
     * @param description Description of the task.
     * @param by Due date/time string.
     * @throws YaoException If the date/time string cannot be parsed into a valid date.
     */
    public Deadline(String description, String by) throws YaoException {
        super(description);
        this.byDateTime = DateTimeUtil.parseDateTime(by);
    }

    /**
     * Returns the date when this deadline is due.
     *
     * @return The LocalDate object.
     */
    public LocalDate getDate() {
        return byDateTime.getDate();
    }

    /**
     * Returns the time when this deadline is due, if specified.
     *
     * @return The LocalTime object, or null if no time was specified.
     */
    public LocalTime getTime() {
        return byDateTime.getTime();
    }

    /**
     * Returns the formatted date/time string of the deadline limit.
     *
     * @return Formatted date/time string.
     */
    public String getBy() {
        return byDateTime.toDisplayString();
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        return byDateTime.getDate().equals(date);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDateTime.toDisplayString() + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + byDateTime.toStorageString();
    }
}
