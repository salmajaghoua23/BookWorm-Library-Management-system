package lk.ijse.bookWormLibraryManagementSystem.command;

public interface TransactionCommand {
    boolean execute();
    boolean undo();
    String getDescription();
}
