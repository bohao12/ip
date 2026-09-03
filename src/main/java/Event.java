
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event task with description, start time, and end time.
     *
     * @param description Description of the event task.
     * @param from        Start date/time string.
     * @param to          End date/time string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
