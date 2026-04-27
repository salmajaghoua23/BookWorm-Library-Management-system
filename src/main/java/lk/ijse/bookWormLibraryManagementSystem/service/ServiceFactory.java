package lk.ijse.bookWormLibraryManagementSystem.service;

import lk.ijse.bookWormLibraryManagementSystem.service.custom.impl.*;

import java.util.EnumMap;
import java.util.Map;

public class ServiceFactory {

    private static volatile ServiceFactory serviceFactory;

    private final Map<ServiceTypes, SuperService> cache = new EnumMap<>(ServiceTypes.class);
    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            synchronized (ServiceFactory.class) {
                if (serviceFactory == null) {
                    serviceFactory = new ServiceFactory();
                }
            }
        }
        return serviceFactory;
    }

    public enum ServiceTypes {
        ADMIN, BOOK, BRANCH, USER, DELETE, TRANSACTION, DASHBOARD
    }

    public SuperService getService(ServiceTypes types) {
        // computeIfAbsent : cree seulement si absent du cache
        return cache.computeIfAbsent(types, t -> {
            switch (types) {
                case ADMIN:
                    return new AdminServiceImpl();
                case BOOK:
                    return new BookServiceImpl();
                case BRANCH:
                    return new BranchServiceImpl();
                case USER:
                    return new UserServiceImpl();
                case DELETE:
                    return new DeleteServiceImpl();
                case TRANSACTION:
                    return new TransactionServiceImpl();
                case DASHBOARD:
                    return new DashboardServiceImpl();
                default:
                    return null;
            }
        });
    }
}
