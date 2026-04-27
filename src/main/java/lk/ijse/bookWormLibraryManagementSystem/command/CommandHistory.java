package lk.ijse.bookWormLibraryManagementSystem.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class CommandHistory {
    private final Deque<TransactionCommand> history = new ArrayDeque<>();

    public boolean execute(TransactionCommand cmd) {
        boolean success = cmd.execute();
        if (success) history.push(cmd);
        return success;
    }

    public boolean undoLast() {
        if (history.isEmpty()) return false;
        return history.pop().undo();
    }

    public String getLastDescription() {
        return history.isEmpty() ? "Aucune action" : history.peek().getDescription();
    }

}
