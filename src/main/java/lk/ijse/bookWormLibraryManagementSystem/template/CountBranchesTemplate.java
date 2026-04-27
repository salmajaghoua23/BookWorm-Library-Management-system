package lk.ijse.bookWormLibraryManagementSystem.template;

import lk.ijse.bookWormLibraryManagementSystem.repository.custom.BranchRepository;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.UserRepository;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.impl.BranchRepositoryImpl;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.impl.UserRepositoryImpl;
import org.hibernate.Session;

public class CountBranchesTemplate extends SessionTemplate<Integer>{
    private final BranchRepository branchRepository;

    public CountBranchesTemplate(BranchRepository repo) {
        this.branchRepository = repo;
    }

    @Override
    protected void injectSession(Session session) {
        ((BranchRepositoryImpl) branchRepository).setSession(session);
    }

    @Override
    protected Integer doWork(Session session) {
        return branchRepository.getAllBranchCount();
    }

    @Override
    protected Integer getDefaultValue() { return 0; }

}
