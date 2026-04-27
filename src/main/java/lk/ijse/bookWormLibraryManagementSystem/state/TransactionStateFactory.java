package lk.ijse.bookWormLibraryManagementSystem.state;

public class TransactionStateFactory {
    public static TransactionState fromType(String type) {
        switch (type.toLowerCase()) {
            case "borrow": return new BorrowState();
            case "return": return new ReturnState();
            default: throw new IllegalArgumentException("Type inconnu: " + type);
        }
    }

}
