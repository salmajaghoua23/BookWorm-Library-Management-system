package lk.ijse.bookWormLibraryManagementSystem.template;

import lk.ijse.bookWormLibraryManagementSystem.repository.custom.UserRepository;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.impl.UserRepositoryImpl;
import org.hibernate.Session;

public class CountUsersTemplate extends SessionTemplate<Integer>{
    private final UserRepository userRepository;

    public CountUsersTemplate(UserRepository repo) {
        this.userRepository = repo;
    }

    @Override
    protected void injectSession(Session session) {
        ((UserRepositoryImpl) userRepository).setSession(session);
    }

    @Override
    protected Integer doWork(Session session) {
        return userRepository.getAllUserCount();
    }

    @Override
    protected Integer getDefaultValue() { return 0; }

}
