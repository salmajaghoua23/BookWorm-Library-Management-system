package lk.ijse.bookWormLibraryManagementSystem.decorator;

import lk.ijse.bookWormLibraryManagementSystem.dto.TransactionDto;
import lk.ijse.bookWormLibraryManagementSystem.projection.AdminProjection;
import lk.ijse.bookWormLibraryManagementSystem.service.custom.DashboardService;

import java.util.List;
import java.util.logging.Logger;

public class LoggedDashboardService implements DashboardService {

    private final DashboardService wrapped;   // le vrai service
    private static final Logger log =
            Logger.getLogger(LoggedDashboardService.class.getName());

    public LoggedDashboardService(DashboardService wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public int getAllBookCount() {
        log.info("[DASHBOARD] getAllBookCount() appele");
        int count = wrapped.getAllBookCount();
        log.info("[DASHBOARD] getAllBookCount() = " + count);
        return count;
    }

    @Override
    public int getAllUserCount() {
        log.info("[DASHBOARD] getAllUserCount() appele");
        int count = wrapped.getAllUserCount();
        log.info("[DASHBOARD] getAllUserCount() = " + count);
        return count;
    }

    @Override
    public List<TransactionDto> getAllOverDueBorrowers() {
        log.info("[DASHBOARD] getAllOverDueBorrowers() appele");
        List<TransactionDto> result = wrapped.getAllOverDueBorrowers();
        log.info("[DASHBOARD] Overdue count = " + result.size());
        return result;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return wrapped.getAllTransactions();  // delegue sans log
    }

    @Override
    public List<AdminProjection> getAdminIdAndName() {
        return wrapped.getAdminIdAndName();
    }

    @Override
    public int getAllBranchCount() {
        return wrapped.getAllBranchCount();
    }

}
