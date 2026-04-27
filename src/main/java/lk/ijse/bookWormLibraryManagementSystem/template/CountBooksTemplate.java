package lk.ijse.bookWormLibraryManagementSystem.template;

import lk.ijse.bookWormLibraryManagementSystem.repository.custom.BookRepository;
import lk.ijse.bookWormLibraryManagementSystem.repository.custom.impl.BookRepositoryImpl;
import org.hibernate.Session;

public class CountBooksTemplate extends SessionTemplate<Integer>{
    private final BookRepository bookRepository;

    public CountBooksTemplate(BookRepository repo) {
        this.bookRepository = repo;
    }

    @Override
    protected void injectSession(Session session) {
        ((BookRepositoryImpl) bookRepository).setSession(session);
    }

    @Override
    protected Integer doWork(Session session) {
        return bookRepository.getAllBookCount();
    }

    @Override protected Integer getDefaultValue() { return 0; }

}
