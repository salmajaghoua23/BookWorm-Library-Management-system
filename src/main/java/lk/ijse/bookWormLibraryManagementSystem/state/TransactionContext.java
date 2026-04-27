package lk.ijse.bookWormLibraryManagementSystem.state;

public class TransactionContext {
    private int borrowCount = 0;
    private int returnCount = 0;

    public void incrementBorrowCount() { borrowCount++; }
    public void incrementReturnCount() { returnCount++; }
    public int getBorrowCount() { return borrowCount; }
    public int getReturnCount() { return returnCount; }

}
