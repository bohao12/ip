package yao.command;

import yao.Storage;
import yao.TaskList;
import yao.Ui;

/**
 * Command to terminate the Yao chatbot session.
 */
public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
