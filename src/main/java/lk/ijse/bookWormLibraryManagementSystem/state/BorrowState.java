package lk.ijse.bookWormLibraryManagementSystem.state;

public class BorrowState implements TransactionState {
    @Override
    public void process(TransactionContext context) {
        context.incrementBorrowCount();
    }
    @Override public String getLabel()          { return "Total Borrowed Books"; }
    @Override public String getPieChartColor()  { return "#008000"; }

}
