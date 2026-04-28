package lk.ijse.bookWormLibraryManagementSystem.proxy;

public enum UserRole {
    ADMIN,   // acces complet : save, update, delete, findAll
    USER     // acces limite : findAll, findByTitle, findAvailableBooks
}
