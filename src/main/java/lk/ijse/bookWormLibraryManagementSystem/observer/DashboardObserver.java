package lk.ijse.bookWormLibraryManagementSystem.observer;

import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;

public interface DashboardObserver {
    void onOverdueDetected(TransactionDto transaction);
    void onDataRefreshed();

}
