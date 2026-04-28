package lk.ijse.bookWormLibraryManagementSystem.proxy;

import lk.ijse.bookWormLibraryManagementSystem.entity.Book;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.BookRepository;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.impl.BookRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class BookRepositoryProxy implements BookRepository {
    private final BookRepository realRepo;
    private final UserRole role;

    public BookRepositoryProxy(UserRole role) {
        this.realRepo = new BookRepositoryImpl();
        this.role = role;
        System.out.println("[PROXY] Créé avec le rôle : " + role);
    }

    @Override
    public void save(Book entity) {
        checkAdmin("save");
        System.out.println("[PROXY]  save() autorisé → délégué au vrai repo");
        realRepo.save(entity);
    }

    @Override
    public void update(Book entity) {
        checkAdmin("update");
        System.out.println("[PROXY]  update() autorisé → délégué au vrai repo");
        realRepo.update(entity);
    }

    @Override
    public void delete(Book entity) {
        checkAdmin("delete");
        System.out.println("[PROXY]  delete() autorisé → délégué au vrai repo");
        realRepo.delete(entity);
    }

    @Override
    public Book getData(int id) {
        System.out.println("[PROXY] getData() lecture libre, pas de vérification");
        return realRepo.getData(id); // retourne Book (pas Optional)
    }

    @Override
    public List<Book> getAllId() {
        System.out.println("[PROXY] getAllId() lecture libre, pas de vérification");
        return realRepo.getAllId();
    }

    @Override public int getAllBookCount() {
        System.out.println("[PROXY] getAllBookCount() lecture libre, pas de vérification");
        return realRepo.getAllBookCount();// libre : lecture seule
    }

    private void checkAdmin(String operation) {
        if (role != UserRole.ADMIN) {
            System.out.println("[PROXY] ACCÈS REFUSÉ — rôle " + role + " ne peut pas faire : " + operation);
            throw new SecurityException( "[PROXY] Opération '" + operation + "' réservée aux admins." );
        }
    }
    // Ajoute cette méthode dans BookRepositoryProxy
    public void checkPermission(String operation) {
        System.out.println("[PROXY] Vérification du rôle pour : " + operation);
        checkAdmin(operation); // lève SecurityException si pas ADMIN, sinon ne fait rien
        System.out.println("[PROXY]  Rôle " + role + " autorisé pour : " + operation);
    }
}