package lk.ijse.bookWormLibraryManagementSystem.observer;

import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;

import java.util.*;

public class DashboardEventManager {
    private static volatile DashboardEventManager instance;
    private final List<DashboardObserver> observers = new ArrayList<>();

    private DashboardEventManager() {}

    public static DashboardEventManager getInstance() {
        if (instance == null) {
            synchronized (DashboardEventManager.class) {
                if (instance == null) instance = new DashboardEventManager();
            }
        }
        return instance;
    }

    public void addObserver(DashboardObserver observer) {
        observers.add(observer);
    }

    public void notifyOverdue(TransactionDto dto) {
        observers.forEach(o -> o.onOverdueDetected(dto));
    }

    public void notifyRefresh() {
        observers.forEach(DashboardObserver::onDataRefreshed);
    }

}
