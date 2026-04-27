package lk.ijse.bookWormLibraryManagementSystem.repository;

import lk.ijse.bookWormLibraryManagementSystem.repository.custom.impl.*;
import java.util.*;
public class RepositoryFactory {

    private static volatile RepositoryFactory repositoryFactory;
    private final Map<RepositoryTypes, SuperRepository> cache = new EnumMap<>(RepositoryTypes.class);

    private RepositoryFactory() {}

    public static RepositoryFactory getInstance() {
        if (repositoryFactory == null) {
            synchronized (RepositoryFactory.class) {
                if (repositoryFactory == null) {
                    repositoryFactory = new RepositoryFactory();
                }
            }
        }
        return repositoryFactory;
    }

    public enum RepositoryTypes {
        ADMIN, BOOK, BRANCH, USER, TRANSACTION, TRANSACTION_DETAIL
    }

    public SuperRepository getRepository(RepositoryTypes types) {
        return cache.computeIfAbsent(types, t -> {
            switch (types) {
                case ADMIN:
                    return new AdminRepositoryImpl();
                case BOOK:
                    return new BookRepositoryImpl();
                case BRANCH:
                    return new BranchRepositoryImpl();
                case USER:
                    return new UserRepositoryImpl();
                case TRANSACTION:
                    return new TransactionRepositoryImpl();
                case TRANSACTION_DETAIL:
                    return new TransactionDetailRepositoryImpl();
                default:
                    return null;
            }
        });
    }
}
