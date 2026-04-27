package lk.ijse.bookWormLibraryManagementSystem.state;

public interface TransactionState {
    void process(TransactionContext context);
    String getLabel();
    String getPieChartColor();
}
